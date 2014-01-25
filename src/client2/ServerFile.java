package client2;

/**
 * Class representing file on the server
 *
 * @author Krzysiek
 */
public class ServerFile{
    private boolean dir = false;
    private final String name;
    
    /**
     * Constructor that sets the name and type of the file
     *
     * @param name file name
     * @param dir is this file a directory
     */
    public ServerFile(String name, boolean dir){
        this.dir = dir;
        this.name = name;
    }
    
    /**
     * Checks if the file is a directory
     *
     * @return true if file is a directory, else otherwise
     */
    public boolean isDir(){
        return dir;
    }
    
    /**
     * Checks if the file is a root in files hierarchy. In other words, if file name is /
     *
     * @return true if file is a root, else otherwise
     */
    public boolean isRoot(){
        return name.equals("/");
    }
    
    @Override
    public String toString(){
        return name;
    }
}
