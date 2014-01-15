package client2;

import java.awt.Component;
import java.io.File;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

class ServerFileRenderer extends DefaultListCellRenderer {
    private final boolean pad;
    private final Border padBorder = new EmptyBorder(3,3,3,3);

    ServerFileRenderer(boolean pad) {
        this.pad = pad;
    }

    @Override
    public Component getListCellRendererComponent(
        JList list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(
            list,value,index,isSelected,cellHasFocus);
        JLabel l = (JLabel)c;
        ServerFile f = (ServerFile)value;
        l.setText(f.toString());
        l.setIcon(UIManager.getIcon(f.isDir() ? "FileView.directoryIcon" : "FileView.fileIcon"));
        if (pad) {
            l.setBorder(padBorder);
        }

        return l;
    }
    
}
