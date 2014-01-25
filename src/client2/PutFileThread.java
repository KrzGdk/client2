package client2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 * Thread which upload file to ftp server
 *
 * @author Krzysztof Gądek
 */
public class PutFileThread extends Thread {
    protected Socket passiveSocket = null;
    protected String path;
    protected String filename;
    protected BufferedReader fromServer = null;
    protected JTextArea textOut;
    protected JProgressBar progress;
    
    /**
     * Parametrized constructor
     *
     * @param passSock server socket to send file to
     * @param path path to the file on local machine
     * @param name name of the file
     * @param fromS buffered reader to get command from server
     * @param textOut text area to write commands to
     * @param prog progress bar to update
     */
    public PutFileThread(Socket passSock, String path, String name, BufferedReader fromS, JTextArea textOut, JProgressBar prog){
        passiveSocket = passSock;
        this.path = path;
        filename = name;
        fromServer = fromS;
        this.textOut = textOut;
        progress = prog;
    }
    
    @Override
    public void run(){
        File f = new File(path + File.separator + filename);
        long size = f.length();
        
        try (FileInputStream fileIn = new FileInputStream(path + File.separator + filename); 
                    DataOutputStream dataOut = new DataOutputStream(passiveSocket.getOutputStream())) {

            int offset, percent;
            long i = 1;
            byte[] data = new byte[1024];
            while( (offset = fileIn.read(data)) != -1){
                dataOut.write(data, 0, offset);
                percent = (int) ((i*1024*100)/size);
                progress.setValue(percent);
                i++;
                if(Thread.currentThread().isInterrupted()) return;
            }
            progress.setValue(0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PutFileThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PutFileThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            textOut.append(fromServer.readLine() + "\n");
        } catch (IOException ex) {
            Logger.getLogger(PutFileThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
