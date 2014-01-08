package client2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Krzysiek
 */
public class ServerTreeModel implements TreeModel{
    Client2 client = null;
    // TODO current dir
    
    public ServerTreeModel(Client2 cli){
        this.client = cli;
    }

    @Override
    public Object getRoot(){
        String curr = null;
        try {
            client.changeDirectory("/");
            curr = client.currentDirectory();
            System.out.println(curr);
            return new ServerFile(curr.substring(curr.indexOf("\"")+1, curr.lastIndexOf("\"")),true,null);
        } catch (IOException ex) {
            Logger.getLogger(ServerTreeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return curr;
    }

    @Override
    public Object getChild(Object parent, int index){
        try {
            String path;
            ServerFile currDir = ((ServerFile)parent);
            System.out.println(parent);
            path = parent + "";
            while(currDir.getContainingDir() != null && !currDir.getContainingDir().isRoot()){
                path = currDir.getContainingDir() + "/" + path;
                currDir = currDir.getContainingDir();
            }
            
            System.out.println("/" + path);
            client.changeDirectory("/");
            client.changeDirectory("/" + path);
            String list = client.listToString();
            System.out.println(list);
            String[] sliceList = list.split("\\s+");
            return new ServerFile(sliceList[(index+3)*9-1],sliceList[(index+2)*9].startsWith("d"),((ServerFile)parent));
        } catch (IOException ex) {
            Logger.getLogger(ServerTreeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getChildCount(Object parent){
        try {
            client.changeDirectory("/" + parent);
            String list = client.listToString();
            String[] sliceList = list.split("\\s+");
            return (sliceList.length / 9) - 2;
        } catch (IOException ex) {
            Logger.getLogger(ServerTreeModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public boolean isLeaf(Object node){
        return !((ServerFile)node).isDir();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue){
    }

    @Override
    public int getIndexOfChild(Object parent, Object child){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTreeModelListener(TreeModelListener l){
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l){
    }
    
}
