/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client2;

import java.io.File;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author root
 */
public class ClientListModel implements ListModel {
    public String currentDirectory = null;
    
    public ClientListModel(String dir){
        currentDirectory = dir;
    }

    @Override
    public int getSize() {
        File d = new File(currentDirectory);
        return d.listFiles().length;
    }

    @Override
    public Object getElementAt(int index) {
        File d = new File(currentDirectory);
        String[] filesInDir = d.list();
        return new File(d,filesInDir[index]){
            @Override
            public String toString(){
                return this.getName();
            }
        };
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
    
}
