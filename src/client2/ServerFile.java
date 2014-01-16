/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client2;

/**
 *
 * @author Krzysiek
 */
public class ServerFile{
    private boolean dir = false;
    private final String name;
    
    public ServerFile(String name, boolean dir){
        this.dir = dir;
        this.name = name;
    }
    
    public boolean isDir(){
        return dir;
    }
    
    public boolean isRoot(){
        return name.equals("/");
    }
    
    @Override
    public String toString(){
        return name;
    }
}
