/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author root
 */
public class ServerListModel implements ListModel {
    protected String currentDirectory = "./";
    protected String list;
    protected Client2 client;
    
    public ServerListModel(String dir, Client2 cli){
        currentDirectory = dir;
        client = cli;
        try {
            client.changeDirectory(dir);
            list = client.listToString();
            //client.changeDirectory("/");
        } catch (IOException ex) {
            Logger.getLogger(ServerListModel.class.getName()).log(Level.SEVERE, "list error", ex);
        }
        System.out.println(list);
    }

    @Override
    public int getSize() {
        if(list.equals("empty")) return 1;
        String lines[] = list.split("\\r?\\n");
        return lines.length;
    }

    @Override
    public Object getElementAt(int index) {
        if(list.equals("empty")) return new ServerFile("..", true);
        String lines[] = list.split("\\r?\\n");
        String line[] = lines[index].split("\\s+");
        String filename = line[line.length-1];
        String isDir = line[0];
        boolean dir = false;
        if(isDir.equals("d")) dir = true;
        
        return new ServerFile(filename,dir);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
    
}