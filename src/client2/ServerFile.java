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
    private ServerFile containigDir;
    private String name;
    
    public ServerFile(String name, boolean dir, ServerFile cdir){
        this.dir = dir;
        this.containigDir = cdir;
        this.name = name;
    }
    public boolean isDir(){
        return dir;
    }
    public ServerFile getContainingDir(){
        return containigDir;
    }
    public boolean isRoot(){
        return name.equals("/");
    }
    @Override
    public String toString(){
        return name;
    }
}
