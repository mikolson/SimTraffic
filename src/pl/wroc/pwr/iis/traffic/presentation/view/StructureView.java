/*
 * StructureView.java
 * Created on 2005-11-08
 * Copyright 2004-2005 e-informatyka.pl. All rights reserved.
 */
package pl.wroc.pwr.iis.traffic.presentation.view;

import java.awt.Graphics;
import java.awt.Panel;

import javax.swing.JPanel;

/**
 * @author M.Stanek <mikol@e-informatyka.pl>
 */
public class StructureView extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * This is the default constructor
     */
    public StructureView() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(392, 251);
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics arg0) {
        // TODO Auto-generated method stub
        super.paintComponent(arg0);
        arg0.fillRect(0,0, 10, 20);
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
