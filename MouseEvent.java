/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mltailer;

import java.awt.event.MouseListener;
import org.jdesktop.swingx.JXTaskPaneContainer;

/**
 *
 * @author cgood92
 */
    public class MouseEvent implements MouseListener {
        JXTaskPaneContainer pane;

    MouseEvent(JXTaskPaneContainer errorPaneContainer) {
        this.pane = errorPaneContainer;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        this.pane.removeAll();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        
    }
}
