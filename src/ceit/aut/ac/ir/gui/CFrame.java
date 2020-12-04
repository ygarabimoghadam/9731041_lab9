package ceit.aut.ac.ir.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CFrame extends JFrame implements ActionListener {

    private CMainPanel mainPanel;

    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem saveDataItem;
    private JMenuItem exitItem;


    public CFrame(String title) {
        super(title);

        initMenuBar(); //create menuBar

        initMainPanel(); //create main panel
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu jmenu = new JMenu("File");

        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        saveDataItem = new JMenuItem("Save Data");
        exitItem = new JMenuItem("Exit");

        newItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveDataItem.addActionListener(this);
        exitItem.addActionListener(this);

        jmenu.add(newItem);
        jmenu.add(saveItem);
        jmenu.add(saveDataItem);
        jmenu.add(exitItem);

        menuBar.add(jmenu);
        setJMenuBar(menuBar);
    }

    private void initMainPanel() {
        mainPanel = new CMainPanel();
        add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newItem) {
            mainPanel.addNewTab();
        } else if (e.getSource() == saveItem) {
            mainPanel.saveNote();}
          else if(e.getSource() == saveDataItem)  {
              mainPanel.saveData();

          } else if (e.getSource() == exitItem) {
            //TODO: Phase1: check all tabs saved ...
            //done
            mainPanel.saveAndExit();
        System.exit(0);
        } else {
            System.out.println("Nothing detected...");
        }
    }
}
