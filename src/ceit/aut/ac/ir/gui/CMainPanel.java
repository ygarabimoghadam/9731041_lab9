package ceit.aut.ac.ir.gui;

import ceit.aut.ac.ir.model.Note;
import ceit.aut.ac.ir.utils.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class CMainPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private JList<File> directoryList;
    private ArrayList<Integer> com = new ArrayList<>();
    private  Note myNote  = new Note();
     private int j ;

    public CMainPanel() {

        setLayout(new BorderLayout());

        initDirectoryList(); // add JList to main Panel

        initTabbedPane(); // add TabbedPane to main panel

        addNewTab(); // open new empty tab when user open the application
    }

    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void initDirectoryList() {
        File[] files = FileUtils.getFilesInDirectory();
        directoryList = new JList<>(files);

        directoryList.setBackground(new Color(211, 211, 211));
        directoryList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        directoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        directoryList.setVisibleRowCount(-1);
        directoryList.setMinimumSize(new Dimension(130, 100));
        directoryList.setMaximumSize(new Dimension(130, 100));
        directoryList.setFixedCellWidth(130);
        directoryList.setCellRenderer(new MyCellRenderer());
        directoryList.addMouseListener(new MyMouseAdapter());

        add(new JScrollPane(directoryList), BorderLayout.WEST);
    }


    public void addNewTab() {
        JTextArea textPanel = createTextPanel();
        textPanel.setText("Write Something here...");
        tabbedPane.addTab("Tab " + (tabbedPane.getTabCount() + 1), textPanel);
    }

    public void openExistingNote(String content) {
        JTextArea existPanel = createTextPanel();
        existPanel.setText(content);

        int tabIndex = tabbedPane.getTabCount() + 1;
        tabbedPane.addTab("Tab " + tabIndex, existPanel);
        tabbedPane.setSelectedIndex(tabIndex - 1);
    }

    public void saveNote() {
        JTextArea textPanel = (JTextArea) tabbedPane.getSelectedComponent();
        com.add(tabbedPane.getSelectedIndex());
        String note = textPanel.getText();
        if (!note.isEmpty()) {
           // FileUtils.fileWriter(note);
            FileUtils.fileWriterInput(note);
        }
        updateListGUI();
    }

    public void saveNote(Component c ) {
        JTextArea textPanel = (JTextArea) c;
        String note = textPanel.getText();
        if (!note.isEmpty()) {
            if(!(note.contains("title")))
          //  FileUtils.fileWriter(note);
            FileUtils.fileWriterInput(note);
        }
        updateListGUI();
    }

    public void saveAndExit(){
        int i = 0 ;
        for(i = 1 ; i<tabbedPane.getTabCount() ; i++){
            if(!(com.contains(i))){
                Component c = tabbedPane.getComponentAt(i);
                saveNote(c);
            }
        }
    }

    public void saveData(){

        JTextArea textPanel = (JTextArea) tabbedPane.getSelectedComponent();
        String note = textPanel.getText();

         myNote.setContent(note);
         myNote.setDate("99/9/14");
         myNote.setTitle("tab"+j);
        if (myNote!=null) {
            FileUtils.writeSerialize(myNote);

        }
        updateListGUI();
        j++;

    }


    private JTextArea createTextPanel() {
        JTextArea textPanel = new JTextArea();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textPanel;
    }

    private void updateListGUI() {
        File[] newFiles = FileUtils.getFilesInDirectory();
        directoryList.setListData(newFiles);
    }


    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent eve) {
            // Double-click detected
            if (eve.getClickCount() == 2) {
                int index = directoryList.locationToIndex(eve.getPoint());
                System.out.println("Item " + index + " is clicked...");
                //TODO: Phase1: Click on file is handled... Just load content into JTextArea
                // done
                File curr[] = FileUtils.getFilesInDirectory();
                String content = null;
                try {
                    File c = curr[index];
                    String name = c.getName();


                    if(name.contains("tab")){
                    content = FileUtils.readSerialize(curr[index]);

                }
                    else{

                    //content = FileUtils.fileReader(curr[index]);
                    content = FileUtils.fileReaderInput(curr[index]);}
                    System.out.println(curr[index]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openExistingNote(content);
            }
        }
    }

    private class MyCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object object, int index, boolean isSelected, boolean cellHasFocus) {
            if (object instanceof File) {
                File file = (File) object;
                setText(file.getName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setEnabled(list.isEnabled());
            }
            return this;
        }
    }
}
