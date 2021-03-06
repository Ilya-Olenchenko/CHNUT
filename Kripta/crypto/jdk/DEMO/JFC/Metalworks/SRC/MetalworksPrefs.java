/*
 * @(#)MetalworksPrefs.java	1.5 99/04/23
 *
 * Copyright (c) 1998, 1999 by Sun Microsystems, Inc. All Rights Reserved.
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

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.metal.*;


/**
  * This is dialog which allows users to choose preferences
  *
 * @version 1.5 04/23/99
  * @author Steve Wilson
  */
public class MetalworksPrefs extends JDialog {

    public MetalworksPrefs(JFrame f) {
        super(f, "Preferences", true);
	JPanel container = new JPanel();
	container.setLayout( new BorderLayout() );

 	JTabbedPane tabs = new JTabbedPane();
	JPanel filters = buildFilterPanel();
	JPanel conn = buildConnectingPanel();
	tabs.addTab( "Filters", null, filters );
	tabs.addTab( "Connecting", null, conn );


	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout ( new FlowLayout(FlowLayout.RIGHT) );
	JButton cancel = new JButton("Cancel");
	cancel.addActionListener(new ActionListener() {
	                       public void actionPerformed(ActionEvent e) {
				   CancelPressed();
			       }});
	buttonPanel.add( cancel );
	JButton ok = new JButton("OK");
	ok.addActionListener(new ActionListener() {
	                       public void actionPerformed(ActionEvent e) {
				   OKPressed();
			       }});
	buttonPanel.add( ok );
	getRootPane().setDefaultButton(ok);

	container.add(tabs, BorderLayout.CENTER);
	container.add(buttonPanel, BorderLayout.SOUTH);
	getContentPane().add(container);
	pack();
	centerDialog();
	UIManager.addPropertyChangeListener(new UISwitchListener(container));
    }

    public JPanel buildFilterPanel() {
	JPanel filters = new JPanel();
	filters.setLayout( new GridLayout(1, 0) );

	JPanel spamPanel = new JPanel();

	spamPanel.setLayout(new ColumnLayout());
	spamPanel.setBorder( new TitledBorder("Spam") );
	ButtonGroup spamGroup = new ButtonGroup();
	JRadioButton file = new JRadioButton("File in Spam Folder");
	JRadioButton delete = new JRadioButton("Auto Delete");
	JRadioButton bomb = new JRadioButton("Reverse Mail-Bomb");
	spamGroup.add(file);
	spamGroup.add(delete);
	spamGroup.add(bomb);   
	spamPanel.add(file);
	spamPanel.add(delete);
	spamPanel.add(bomb);  
	file.setSelected(true);
	filters.add(spamPanel);
	
	JPanel autoRespond = new JPanel();
	autoRespond.setLayout(new ColumnLayout());
	autoRespond.setBorder( new TitledBorder("Auto Response") );

	ButtonGroup respondGroup = new ButtonGroup();
	JRadioButton none = new JRadioButton("None");
	JRadioButton vaca = new JRadioButton("Send Vacation Message");
	JRadioButton thx = new JRadioButton("Send Thank You Message");

	respondGroup.add(none);
	respondGroup.add(vaca);
	respondGroup.add(thx);

	autoRespond.add(none);
	autoRespond.add(vaca);
	autoRespond.add(thx);

	none.setSelected(true);
	filters.add(autoRespond);

	return filters;
    }

    public JPanel buildConnectingPanel() {
	JPanel connectPanel = new JPanel();
	connectPanel.setLayout( new ColumnLayout() );

	JPanel protoPanel = new JPanel();
	JLabel protoLabel = new JLabel ("Protocol");
	JComboBox protocol = new JComboBox();
	protocol.addItem("SMTP");
	protocol.addItem("IMAP");
	protocol.addItem("Other...");
	protoPanel.add(protoLabel);
	protoPanel.add(protocol);

	JPanel attachmentPanel = new JPanel();
	JLabel attachmentLabel = new JLabel ("Attachments");
	JComboBox attach = new JComboBox();
	attach.addItem("Download Always");
	attach.addItem("Ask size > 1 Meg");
	attach.addItem("Ask size > 5 Meg");
	attach.addItem("Ask Always");
	attachmentPanel.add(attachmentLabel);
	attachmentPanel.add(attach);

	JCheckBox autoConn = new JCheckBox("Auto Connect");
	JCheckBox compress = new JCheckBox("Use Compression");
	autoConn.setSelected( true );

	connectPanel.add(protoPanel);
	connectPanel.add(attachmentPanel);
	connectPanel.add(autoConn);
	connectPanel.add(compress);
	return connectPanel;
    }



    protected void centerDialog() {
        Dimension screenSize = this.getToolkit().getScreenSize();
	Dimension size = this.getSize();
	screenSize.height = screenSize.height/2;
	screenSize.width = screenSize.width/2;
	size.height = size.height/2;
	size.width = size.width/2;
	int y = screenSize.height - size.height;
	int x = screenSize.width - size.width;
	this.setLocation(x,y);
    }

    public void CancelPressed() {
        this.setVisible(false);
    }

    public void OKPressed() {
        this.setVisible(false);
    }
  
}

class ColumnLayout implements LayoutManager {

  int xInset = 5;
  int yInset = 5;
  int yGap = 2;

  public void addLayoutComponent(String s, Component c) {}

  public void layoutContainer(Container c) {
      Insets insets = c.getInsets();
      int height = yInset + insets.top;
      
      Component[] children = c.getComponents();
      Dimension compSize = null;
      for (int i = 0; i < children.length; i++) {
	  compSize = children[i].getPreferredSize();
	  children[i].setSize(compSize.width, compSize.height);
	  children[i].setLocation( xInset + insets.left, height);
	  height += compSize.height + yGap;
      }

  }

  public Dimension minimumLayoutSize(Container c) {
      Insets insets = c.getInsets();
      int height = yInset + insets.top;
      int width = 0 + insets.left + insets.right;
      
      Component[] children = c.getComponents();
      Dimension compSize = null;
      for (int i = 0; i < children.length; i++) {
	  compSize = children[i].getPreferredSize();
	  height += compSize.height + yGap;
	  width = Math.max(width, compSize.width + insets.left + insets.right + xInset*2);
      }
      height += insets.bottom;
      return new Dimension( width, height);
  }
  
  public Dimension preferredLayoutSize(Container c) {
      return minimumLayoutSize(c);
  }
   
  public void removeLayoutComponent(Component c) {}

}
