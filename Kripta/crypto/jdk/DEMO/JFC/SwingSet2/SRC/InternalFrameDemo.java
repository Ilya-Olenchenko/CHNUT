/*
 * @(#)InternalFrameDemo.java	1.4 99/10/19
 *
 * Copyright (c) 1997-1999 by Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;

/**
 * Internal Frames Demo
 *
 * @version 1.4 10/19/99
 * @author Jeff Dinkins
 */
public class InternalFrameDemo extends DemoModule {
    int windowCount = 0;
    JDesktopPane desktop = null;

    ImageIcon icon1, icon2, icon3, icon4;
    ImageIcon smIcon1, smIcon2, smIcon3, smIcon4;

    public Integer FIRST_FRAME_LAYER  = new Integer(1);
    public Integer DEMO_FRAME_LAYER   = new Integer(2);
    public Integer PALETTE_LAYER     = new Integer(3);

    public int FRAME0_X        = 15;
    public int FRAME0_Y        = 280;

    public int FRAME0_WIDTH    = 320;
    public int FRAME0_HEIGHT   = 230;

    public int FRAME_WIDTH     = 225;
    public int FRAME_HEIGHT    = 150;

    public int PALETTE_X      = 375;
    public int PALETTE_Y      = 20;

    public int PALETTE_WIDTH  = 260;
    public int PALETTE_HEIGHT = 230;

    JCheckBox windowResizable   = null;
    JCheckBox windowClosable    = null;
    JCheckBox windowIconifiable = null;
    JCheckBox windowMaximizable = null;

    JTextField windowTitleField = null;
    JLabel windowTitleLabel = null;


    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
	InternalFrameDemo demo = new InternalFrameDemo(null);
	demo.mainImpl();
    }

    /**
     * InternalFrameDemo Constructor
     */
    public InternalFrameDemo(SwingSet2 swingset) {
	super(swingset, "InternalFrameDemo", "toolbar/JDesktop.gif");

	// preload all the icons we need for this demo
	icon1 = createImageIcon("ImageClub/misc/fish.gif", getString("InternalFrameDemo.fish"));
	icon2 = createImageIcon("ImageClub/misc/moon.gif", getString("InternalFrameDemo.moon"));
	icon3 = createImageIcon("ImageClub/misc/sun.gif",  getString("InternalFrameDemo.sun"));
	icon4 = createImageIcon("ImageClub/misc/cab.gif",  getString("InternalFrameDemo.cab"));

	smIcon1 = createImageIcon("ImageClub/misc/fish_small.gif", getString("InternalFrameDemo.fish"));
	smIcon2 = createImageIcon("ImageClub/misc/moon_small.gif", getString("InternalFrameDemo.moon"));
	smIcon3 = createImageIcon("ImageClub/misc/sun_small.gif",  getString("InternalFrameDemo.sun"));
	smIcon4 = createImageIcon("ImageClub/misc/cab_small.gif",  getString("InternalFrameDemo.cab"));

	// Create the desktop pane
	desktop = new JDesktopPane();
	getDemoPanel().add(desktop, BorderLayout.CENTER);

	// Create the "frame maker" palette
	createInternalFramePalette();

	// Create an initial internal frame to show
	JInternalFrame frame1 = createInternalFrame(icon1, FIRST_FRAME_LAYER, 1, 1);
	frame1.setBounds(FRAME0_X, FRAME0_Y, FRAME0_WIDTH, FRAME0_HEIGHT);

	// Create four more starter windows
	createInternalFrame(icon1, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
	createInternalFrame(icon3, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
	createInternalFrame(icon4, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
	createInternalFrame(icon2, DEMO_FRAME_LAYER, FRAME_WIDTH, FRAME_HEIGHT);
    }



    /**
     * Create an internal frame and add a scrollable imageicon to it
     */
    public JInternalFrame createInternalFrame(Icon icon, Integer layer, int width, int height) {
	JInternalFrame jif = new JInternalFrame();

	if(!windowTitleField.getText().equals(getString("InternalFrameDemo.frame_label"))) {
	    jif.setTitle(windowTitleField.getText() + "  ");
	} else {
	    jif = new JInternalFrame(getString("InternalFrameDemo.frame_label") + " " + windowCount + "  ");
	}

	// set properties
	jif.setClosable(windowClosable.isSelected());
	jif.setMaximizable(windowMaximizable.isSelected());
	jif.setIconifiable(windowIconifiable.isSelected());
	jif.setResizable(windowResizable.isSelected());

	jif.setBounds(20*(windowCount%10), 20*(windowCount%10), width, height);
	jif.setContentPane(new ImageScroller(this, icon, 0, windowCount));

	windowCount++;
	
	desktop.add(jif, layer);  

	// Set this internal frame to be selected

	try {
	    jif.setSelected(true);
	} catch (java.beans.PropertyVetoException e2) {
	}

	jif.show();

	return jif;
    }

    public JInternalFrame createInternalFramePalette() {
	JInternalFrame palette = new JInternalFrame(
	    getString("InternalFrameDemo.palette_label")
	);
	palette.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
	palette.getContentPane().setLayout(new BorderLayout());
	palette.setBounds(PALETTE_X, PALETTE_Y, PALETTE_WIDTH, PALETTE_HEIGHT);
	palette.setResizable(true);
	palette.setIconifiable(true);
	desktop.add(palette, PALETTE_LAYER);

	// *************************************
	// * Create create frame maker buttons *
	// *************************************
	JButton b1 = new JButton(smIcon1);
	JButton b2 = new JButton(smIcon2);
	JButton b3 = new JButton(smIcon3);
	JButton b4 = new JButton(smIcon4);

	// add frame maker actions
	b1.addActionListener(new ShowFrameAction(this, icon1));
	b2.addActionListener(new ShowFrameAction(this, icon2));
	b3.addActionListener(new ShowFrameAction(this, icon3));
	b4.addActionListener(new ShowFrameAction(this, icon4));

	// add frame maker buttons to panel
	JPanel p = new JPanel();
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

	JPanel buttons1 = new JPanel();
	buttons1.setLayout(new BoxLayout(buttons1, BoxLayout.X_AXIS));

	JPanel buttons2 = new JPanel();
	buttons2.setLayout(new BoxLayout(buttons2, BoxLayout.X_AXIS));

	buttons1.add(b1);
	buttons1.add(Box.createRigidArea(HGAP15));
	buttons1.add(b2);

	buttons2.add(b3);
	buttons2.add(Box.createRigidArea(HGAP15));
	buttons2.add(b4);

	p.add(Box.createRigidArea(VGAP10));
	p.add(buttons1);
	p.add(Box.createRigidArea(VGAP15));
	p.add(buttons2);
	p.add(Box.createRigidArea(VGAP10));

	palette.getContentPane().add(p, BorderLayout.NORTH);

	// ************************************
	// * Create frame property checkboxes *
	// ************************************
	p = new JPanel() {
	    Insets insets = new Insets(10,15,10,5);
	    public Insets getInsets() {
		return insets;
	    }
	};
	p.setLayout(new GridLayout(2,2));


	windowResizable   = new JCheckBox(getString("InternalFrameDemo.resizable_label"), true);
	windowClosable    = new JCheckBox(getString("InternalFrameDemo.closable_label"), true);
	windowIconifiable = new JCheckBox(getString("InternalFrameDemo.iconifiable_label"), true);
	windowMaximizable = new JCheckBox(getString("InternalFrameDemo.maximizable_label"), true);

	p.add(windowResizable);
	p.add(windowClosable);
	p.add(windowIconifiable);
	p.add(windowMaximizable);

	palette.getContentPane().add(p, BorderLayout.CENTER);


	// ************************************
	// *   Create Frame title textfield   *
	// ************************************
	p = new JPanel() {
	    Insets insets = new Insets(0,0,10,0);
	    public Insets getInsets() {
		return insets;
	    }
	};

	windowTitleField = new JTextField(getString("InternalFrameDemo.frame_label"));
	windowTitleLabel = new JLabel(getString("InternalFrameDemo.title_text_field_label"));

	p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
	p.add(Box.createRigidArea(HGAP5));
	p.add(windowTitleLabel, BorderLayout.WEST);
	p.add(Box.createRigidArea(HGAP5));
	p.add(windowTitleField, BorderLayout.CENTER);
	p.add(Box.createRigidArea(HGAP5));

	palette.getContentPane().add(p, BorderLayout.SOUTH);

	palette.show();

	return palette;
    }


    class ShowFrameAction extends AbstractAction {
	InternalFrameDemo demo;
	Icon icon;
	
	
	public ShowFrameAction(InternalFrameDemo demo, Icon icon) {
	    this.demo = demo;
	    this.icon = icon;
	}
	
	public void actionPerformed(ActionEvent e) {
	    demo.createInternalFrame(icon,
				     getDemoFrameLayer(),
				     getFrameWidth(),
				     getFrameHeight()
	    );
	}
    }

    public int getFrameWidth() {
	return FRAME_WIDTH;
    }

    public int getFrameHeight() {
	return FRAME_HEIGHT;
    }

    public Integer getDemoFrameLayer() {
	return DEMO_FRAME_LAYER;
    }
    
    class ImageScroller extends JScrollPane {
	
	public ImageScroller(InternalFrameDemo demo, Icon icon, int layer, int count) {
	    super();
	    JPanel p = new JPanel();
	    p.setBackground(Color.white);
	    p.setLayout(new BorderLayout() );
	    
	    p.add(new JLabel(icon), BorderLayout.CENTER);
	    
	    getViewport().add(p);
	}
	
	public Dimension getMinimumSize() {
	    return new Dimension(25, 25);
	}
	
    }

    
}
