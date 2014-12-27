/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mltailer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
  
import javax.swing.JFrame; 
import javax.swing.JSplitPane; 
  
import org.jdesktop.swingx.JXFrame;  
import org.jdesktop.swingx.JXTaskPaneContainer;  
/**
 *
 * @author cgood92
 */
public class Console extends JXFrame {
    JTextArea consoleOutput = new JTextArea(5, 40);
    
    //Bottom Pane
    JScrollPane bottomScrollPane = new JScrollPane( consoleOutput );
    
    //Center Pane
    JXTaskPaneContainer errorPaneContainer = new JXTaskPaneContainer();  
    JScrollPane centerScrollPane = new JScrollPane( errorPaneContainer );
    
    //Split pane
    
    public Console(){
    super("MarkLogic Console");
    setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    //setPreferredSize(new Dimension(1000, 500));
    
    //Border Laytout
    BorderLayout consoleLayout = new BorderLayout();
    setLayout(consoleLayout); 
    
    //Bottom Scroll Pane
    bottomScrollPane.setViewportView(consoleOutput);
    
    //Top Pane
    JPanel topScrollPane = new JPanel(new GridLayout());
    JButton clearButton = new JButton("Clear");
    clearButton.addMouseListener(new MouseEvent(errorPaneContainer));
    topScrollPane.add(clearButton, 0);
    topScrollPane.setPreferredSize(new Dimension(30, 30));
    add(topScrollPane, BorderLayout.NORTH);
    
    //Split Pane
    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerScrollPane, bottomScrollPane);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(0.75);
    splitPane.setResizeWeight(.75);
    add(splitPane, BorderLayout.CENTER); 
    
    //Package up
    pack();  
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    }
    
    public void makeNewCollapsible(ErrorBlock errorBlock){                   
        errorPaneContainer.add(errorBlock.getObject(), 0);
    }
    
    public void consoleOutputLine(String line){
        consoleOutput.append(line + "\n");
        JScrollBar vertical = bottomScrollPane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
    }
}
