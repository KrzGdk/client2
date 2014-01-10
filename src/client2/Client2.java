/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for handling FTP connection providing basic operations
 *
 * @author Krzyszfot Gądek
 */
public class Client2{
    protected ServerSocket dataSocket = null;
    protected Socket passiveSocket = null;
    protected PrintWriter toServer;
    protected BufferedReader fromServer, stdIn;
    protected int clientPort;
    protected int passiveModePort;
    protected String passiveModeIP;
    protected boolean logged = false, passiveMode = false;
    protected String command, response;
   
    /**
     * Method that connects to the FTP server, initializes command channels
     * and reads welcome messages
     *
     * @param IP IP of the host
     * @throws UnknownHostException
     * @throws IOException
     */
    public void connect(String IP) throws UnknownHostException, IOException{
        Socket server = new Socket(IP, 21);
        toServer = new PrintWriter(server.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        
        //reading welcome messages
        setResponse(fromServer.readLine());
        while(getResponse().charAt(3) == '-'){
            System.out.println(getResponse());
            setResponse(fromServer.readLine());
        }
        System.out.println(getResponse());
    }
    /**
     * Method that sends FTP commands to login the user
     *
     * @param username name of the user
     * @param password user password
     * @return true if logged in, false on failure
     * @throws IOException
     */
    public boolean login(String username, String password) throws IOException{
        toServer.println("USER " + username);
        setResponse(fromServer.readLine());
        if(!response.startsWith("331")){
            return false;
        }
        toServer.println("PASS " + password);
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("230")){
            logged = true;
            return true;
        }
        else return false;
    }
    /**
     * Method that downloads a file from FTP server
     *
     * @param filename name of the file to retrieve
     * @return true on success, false on failure
     * @throws IOException
     */
    public boolean getFile(String filename) throws IOException{
        if(!initializePassiveMode()) return false;
        toServer.println("RETR " + filename);
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("150")){ // file found
            try (RandomAccessFile file = new RandomAccessFile((filename),"rw"); 
                    DataInputStream dataIn = new DataInputStream(passiveSocket.getInputStream())) {

                int offset;
                byte[] data = new byte[1024];
                while( (offset = dataIn.read(data)) != -1){
                    file.write(data, 0, offset);
                }
            }
            setResponse(fromServer.readLine()); // transfer complete
            return getResponse().startsWith("226");
        }
        else return false;
    }
    /**
     * Method that uploads a file to FTP server
     *
     * @param path path to the file on the local disk, must contain directory separator
     * @param filename name of local file
     * @return true on success, false on failure
     * @throws IOException
     * @throws client2.FileTransferFailedException
     */
    public boolean putFile(String path, String filename) throws IOException, FileTransferFailedException{
        if(!initializePassiveMode()) return false;
        toServer.println("STOR " + filename);
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("150")){ 
            try (FileInputStream fileIn = new FileInputStream(path + filename); 
                    DataOutputStream dataOut = new DataOutputStream(passiveSocket.getOutputStream())) {
                
                int offset;
                byte[] data = new byte[1024];
                while( (offset = fileIn.read(data)) != -1){
                    dataOut.write(data, 0, offset);
                }
            }
            setResponse(fromServer.readLine()); // transfer complete
            if(getResponse().startsWith("226")) return true;
            else throw new FileTransferFailedException();
        }
        else throw new FileTransferFailedException();
    }
    /**
     * Method that sends the append command to FTP server
     *
     * @param filename file to append data
     * @return true on success, false on failure
     * @throws IOException
     */
    public boolean appendFile(String filename) throws IOException{
        if(!initializePassiveMode()) return false;
        
        toServer.println("APPE " + filename);
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("150")){
            try (FileInputStream fileIn = new FileInputStream(filename); 
                    DataOutputStream dataOut = new DataOutputStream(passiveSocket.getOutputStream())) {
                
                int offset;
                byte[] data = new byte[1024];
                while( (offset = fileIn.read(data)) != -1){
                    dataOut.write(data, 0, offset);
                }
            }
            setResponse(fromServer.readLine()); // transfer complete
            return getResponse().startsWith("226");
        }
        else return false;
    }
    /**
     * Deletes a file from FTP server
     *
     * @param filename file to delete
     * @return true on success, false on failure
     * @throws IOException
     */
    public boolean deleteFile(String filename) throws IOException{
        toServer.println("DELE " + filename);
        setResponse(fromServer.readLine());
        return getResponse().startsWith("250");
    }
    /**
     * Creates a directory on the server
     *
     * @param name name of the directory
     * @return true on success, false on failure
     * @throws IOException
     */
    public boolean makeDirectory(String name) throws IOException{
        toServer.println("MKD " + name);
        setResponse(fromServer.readLine());
        return getResponse().startsWith("257");
    }
    /**
     * Removes the directory on the server
     *
     * @param name name of the directory
     * @return true on success, false on failure
     * @throws IOException
     */
    public boolean removeDirectory(String name) throws IOException{
        toServer.println("RMD " + name);
        setResponse(fromServer.readLine());
        return getResponse().startsWith("250");
    }
    /**
     * Changes the current working directory on server
     *
     * @param name name of the directory to change to
     * @return true on success, false on failures
     * @throws IOException
     */
    public boolean changeDirectory(String name) throws IOException{
        toServer.println("CWD " + name);
        setResponse(fromServer.readLine());
        return getResponse().startsWith("250");
    }
    /**
     * Sends a FTP comand to get the name of the current directory
     *
     * @return reply from server containing current directory
     * @throws IOException
     */
    public String currentDirectory() throws IOException{
        toServer.println("PWD");
        setResponse(fromServer.readLine());
        return getResponse();
    }
    /**
     * Prints the list of files of the current folder received by the FTP command LIST to standard output
     *
     * @return true on success, false on failure
     * @throws IOException
     */
    public boolean list() throws IOException{
        if(!initializePassiveMode()) return false;
        toServer.println("LIST");
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("150")){ // file found
            try (PrintStream stdOut = new PrintStream(System.out); 
                    DataInputStream dataIn = new DataInputStream(passiveSocket.getInputStream())) {

                int offset;
                byte[] data = new byte[1024];
                while( (offset = dataIn.read(data)) != -1){
                    stdOut.write(data, 0, offset);
                }
            }
            setResponse(fromServer.readLine()); // transfer complete
            return getResponse().startsWith("226");
        }
        else return false;
    }
    /**
     * Retreives the list of files of current folder to string and returns it
     *
     * @return string containing list of files on server
     * @throws IOException
     */
    public String listToString() throws IOException{
        if(!initializePassiveMode()) return null;
        StringBuilder str = new StringBuilder();
        toServer.println("LIST");
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("150")){ // file found
            try (BufferedReader dataIn = new BufferedReader(new InputStreamReader(passiveSocket.getInputStream()))) {

                String tmp; 
                while ((tmp = dataIn.readLine()) != null) {
                    str.append(tmp);
                    str.append("\r\n");
                }
            }
            setResponse(fromServer.readLine()); // transfer complete
            passiveSocket.close();
            if(getResponse().startsWith("226")){
                return str.substring(0, str.length()-2);
            }
            else return null;
        }
        return null;
    }
    /**
     * Sends a QUIT command
     *
     * @throws IOException
     */
    public void quit() throws IOException{
        toServer.println("QUIT");
        setResponse(fromServer.readLine());
    }
    /**
     * Sends a NOOP command
     *
     * @throws IOException
     */
    public void noop() throws IOException{
        toServer.println("NOOP");
        setResponse(fromServer.readLine());
    }
    /**
     * Returns the status of client login
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLogged(){
        return logged;
    }
    public static void main(String[] args){
        Logger.getLogger(Client2.class.getName()).setLevel(Level.INFO);
        try {
            Client2 cli = new Client2();
            Logger.getLogger(Client2.class.getName()).log(Level.INFO, "konstruktor");
            cli.connect("127.123.432.1");
            Logger.getLogger(Client2.class.getName()).log(Level.INFO, "logowanie");
            cli.login("ftp", "ftp");
            Logger.getLogger(Client2.class.getName()).log(Level.INFO, "zalogowany");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client2.class.getName()).log(Level.WARNING, "wyjatek", ex);
        } catch (IOException ex) {
            Logger.getLogger(Client2.class.getName()).log(Level.WARNING, "wyjatek", ex);
        }
    }
    
    /**
     * Method that sends a PASV command and opens a socket to handle the connection
     *
     * @return true on success, false on failure
     * @throws IOException
     */
    private boolean initializePassiveMode() throws IOException{
        toServer.println("PASV");
        setResponse(fromServer.readLine());
        if(getResponse().startsWith("227")){
            int firstBraceIndex = getResponse().indexOf("(");
            String address = getResponse().substring(firstBraceIndex+1,getResponse().length()-1);
            String[] splitAddress = address.split(",");
            String ip1,ip2,ip3,ip4;
            int p1,p2;
            ip1 = splitAddress[0];
            ip2 = splitAddress[1];
            ip3 = splitAddress[2];
            ip4 = splitAddress[3];
            p1 = Integer.parseInt(splitAddress[4]);
            p2 = Integer.parseInt(splitAddress[5]);
            passiveModeIP = ip1 + "." + ip2 + "." + ip3 + "." + ip4;
            passiveModePort = (p1 << 8) + p2;
            passiveSocket = new Socket(passiveModeIP, passiveModePort);
            return true;
        }
        else return false;
    }

    /**
     * @return the response
     */
    public String getResponse(){
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response){
        this.response = response;
    }
}
