/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client2;

import java.io.DataInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

/**
 *
 * @author root
 */
public class PutFileThread extends Thread {
    protected Socket passiveSocket = null;
    protected String path;
    protected String filename;
    
    public PutFileThread(Socket passSock, String path, String name){
        passiveSocket = passSock;
        this.path = path;
        filename = name;
    }
    
    @Override
    public void run(){
        
    }
}
