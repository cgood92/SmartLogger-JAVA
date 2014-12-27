/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mltailer;

import java.awt.FlowLayout;
import java.util.ArrayList;
import org.jdesktop.swingx.JXTaskPane;  
import javax.swing.JPanel;  
import java.awt.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.text.BadLocationException;

import javax.swing.BorderFactory;

import javax.swing.text.StyledDocument;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.*;
/**
 *
 * @author cgood92
 */
public class ErrorBlock {
    JXTaskPane errorDetails = new JXTaskPane();
    StyledDocument doc = new DefaultStyledDocument();
    SimpleAttributeSet italicStyle = new SimpleAttributeSet();
    SimpleAttributeSet boldStyle = new SimpleAttributeSet();
    SimpleAttributeSet greenStyle = new SimpleAttributeSet();
    SimpleAttributeSet varName = new SimpleAttributeSet();
    SimpleAttributeSet varValue = new SimpleAttributeSet();
    
    public void initializeStyles(){
        italicStyle.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);
        boldStyle.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
        varName.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.blue);
        varName.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
        varValue.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.blue);
        greenStyle.addAttribute(StyleConstants.CharacterConstants.Foreground, new Color(0, 132, 66));
    }
    
    public ErrorBlock(ArrayList<String> linesArray){ 
        initializeStyles();
        String title = getTitle(linesArray);
        errorDetails.setCollapsed(true);
        errorDetails.setTitle(title);
         
        getHeader(linesArray);
        getFrames(linesArray);
        
        JTextPane textpane = new JTextPane(doc);
        //textpane.setText(formattedMessage);
        textpane.setBackground(new Color(238, 238, 238));
        JPanel noWrapPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        noWrapPanel.setPreferredSize(new Dimension(200,200));
        noWrapPanel.add(textpane);
        JScrollPane scrollPane = new JScrollPane(noWrapPanel);
        scrollPane.setPreferredSize(new Dimension(200,200));
        scrollPane.setViewportView(textpane); // creates a wrapped scroll pane using the text pane as a viewport.    
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

        errorDetails.add(scrollPane);
    }
    public void getFrames(ArrayList<String> linesArray){
        boolean inside = false;
        Pattern sp = Pattern.compile("<error:frame>");
        Pattern ep = Pattern.compile("</error:frame>");
        
        try{
            for (String s:linesArray) {
                Matcher sm = sp.matcher(s);
                Matcher em = ep.matcher(s);
                if(sm.find()){
                    inside = true;
                }
                if(inside){
                    if(Pattern.compile("<error:uri>").matcher(s).find()){
                        doc.insertString(doc.getLength(), 
                            "\nFile: " + s.replaceAll("(.*)<error:uri>(.*)</error:uri>(.*)", "$2") + " ", 
                            italicStyle);
                    }
                    if(Pattern.compile("<error:line>").matcher(s).find()){
                        doc.insertString(doc.getLength(), 
                        "Line: " + s.replaceAll("(.*)<error:line>(.*)</error:line>(.*)", "$2") + "\n",
                        italicStyle);
                    }
                    if(Pattern.compile("<error:operation>").matcher(s).find()){
                        doc.insertString(doc.getLength(), 
                        "Function: " + s.replaceAll("(.*)<error:operation>(.*)</error:operation>(.*)", "$2") + "\n",
                        greenStyle);
                        doc.insertString(doc.getLength(), "Variables:\n",
                                varName);
                    }
                    if(Pattern.compile("<error:name").matcher(s).find()){
                        doc.insertString(doc.getLength(), 
                        "  -" + s.replaceAll("(.*)<error:name(.*)>(.*)</error:name>(.*)", "$3") + ": ",
                        varName);
                    }
                    if(Pattern.compile("<error:value>").matcher(s).find()){
                        doc.insertString(doc.getLength(), 
                        s.replaceAll("(.*)<error:value>(.*)</error:value>(.*)", "$2") + "\n",
                        varValue);
                    }
                }
                if(em.find()){
                    inside = false;
                }
            }
        } catch (BadLocationException badLocationException) {
            System.err.println("Bad insert");
        } 
    }
    
    public void getHeader(ArrayList<String> linesArray){
        try{
            for (String s:linesArray) {
                if(Pattern.compile("<error:message>").matcher(s).find()){
                    doc.insertString(doc.getLength(), 
                        s.replaceAll("(.*)<error:message>(.*)</error:message>(.*)", "$2") + "\n",
                        boldStyle);
                }
                if(Pattern.compile("<error:format-string>").matcher(s).find()){
                    doc.insertString(doc.getLength(), 
                    s.replaceAll("(.*)<error:format-string>(.*)</error:format-string>(.*)", "$2") + "\n",
                    boldStyle);
                }
                if(Pattern.compile("<error:expr>").matcher(s).find()){
                    doc.insertString(doc.getLength(), 
                    s.replaceAll("(.*)<error:expr>(.*)</error:expr>(.*)", "$2") + "\n",
                    boldStyle);
                    break;
                }
            }
        } catch (BadLocationException badLocationException) {
            System.err.println("Bad insert");
        } 
    }
    
    public String getTitle(ArrayList<String> linesArray){
        String title = "ERROR";
        String pattern = "(?<=<error:code>)(.*)(?=</error:code>)";
        Pattern p = Pattern.compile(pattern);
        for (String s:linesArray) {
            Matcher m = p.matcher(s);
            if(m.find()){
                title = m.group();   
                break;
            }
        }
        return title;
    }
    public JXTaskPane getObject(){
        return errorDetails;
    }
}
