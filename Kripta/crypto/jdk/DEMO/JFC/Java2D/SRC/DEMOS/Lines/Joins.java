/*
 * @(#)Joins.java	1.19 99/09/07
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

package demos.Lines;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import ControlsSurface;
import CustomControls;


/**
 * BasicStroke join types and width sizes illustrated.  Control for
 * rendering a shape returned from BasicStroke.createStrokedShape(Shape).
 */
public class Joins extends ControlsSurface implements ChangeListener {

    protected int joinType = BasicStroke.JOIN_MITER;
    protected float bswidth = 20.0f;
    protected JSlider slider;
    protected JLabel label;


    public Joins() {
        setBackground(Color.white);
        slider = new JSlider(JSlider.VERTICAL, 0, 100, (int)(bswidth*2));
        slider.setPreferredSize(new Dimension(15, 100));
        slider.addChangeListener(this);
        setControls(new Component[] { new DemoControls(this), slider });
        setConstraints(new String[] { BorderLayout.NORTH, BorderLayout.WEST});
    }


    public void stateChanged(ChangeEvent e) {
        // when using these sliders use double buffering, which means
        // ignoring when DemoSurface.imageType = 'On Screen'
        if (getImageType() <= 1) {
            setImageType(2);
        }
        bswidth = (float) slider.getValue() / 2.0f;
        label.setText("width=" + String.valueOf(bswidth));
        label.repaint();
        repaint();
    }    


    public void render(int w, int h, Graphics2D g2) {
        BasicStroke bs = new BasicStroke(bswidth, 
                                    BasicStroke.CAP_BUTT, joinType);
        GeneralPath p = new GeneralPath();
        p.moveTo(- w / 4.0f, - h / 12.0f);
        p.lineTo(+ w / 4.0f, - h / 12.0f);
        p.lineTo(- w / 6.0f, + h / 4.0f);
        p.lineTo(+     0.0f, - h / 4.0f);
        p.lineTo(+ w / 6.0f, + h / 4.0f);
        p.closePath();
        p.closePath();
        g2.translate(w/2, h/2);
        g2.setColor(Color.black);
        g2.draw(bs.createStrokedShape(p));
    }


    public static void main(String s[]) {
        createDemoFrame(new Joins());
    }


    class DemoControls extends CustomControls implements ActionListener {

        Joins demo;
        int joinType[] = { BasicStroke.JOIN_MITER, 
                       BasicStroke.JOIN_ROUND, BasicStroke.JOIN_BEVEL };
        String joinName[] = { "Mitered Join", "Rounded Join", "Beveled Join" };
        JMenu menu;
        JMenuItem menuitem[] = new JMenuItem[joinType.length];
        JoinIcon icons[] = new JoinIcon[joinType.length];
        JToolBar toolbar;


        public DemoControls(Joins demo) {
            super(demo.name);
            this.demo = demo;
            setBackground(Color.gray);
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(2,2,2,2));
            label = new JLabel("width=" + String.valueOf(demo.bswidth));
            Font font = new Font("serif", Font.BOLD, 14);
            label.setFont(font);
            label.setForeground(Color.lightGray);
            add("West", label);
            JMenuBar menubar = new JMenuBar();
            add("East", menubar);
            menu = (JMenu) menubar.add(new JMenu(joinName[0]));
            menu.setFont(font = new Font("serif", Font.PLAIN, 10));
            for (int i = 0; i < joinType.length; i++) {
                icons[i]= new JoinIcon(joinType[i]);
                menuitem[i] = menu.add(new JMenuItem(joinName[i]));
                menuitem[i].setFont(font);
                menuitem[i].setIcon(icons[i]);
                menuitem[i].addActionListener(this);
            } 
            menu.setIcon(icons[0]);
        }


        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < joinType.length; i++) {
                if (e.getSource().equals(menuitem[i])) {
                    demo.joinType = joinType[i];
                    menu.setIcon(icons[i]);
                    menu.setText(joinName[i]);
                    break;
                } 
            }
            demo.repaint();
        }


        public Dimension getPreferredSize() {
            return new Dimension(200,28);
        }


        public void run() {
            try { thread.sleep(999); } catch (Exception e) { return; }
            Thread me = Thread.currentThread();
            while (thread == me) {
                for (int i = 0; i < menuitem.length; i++) {
                    menuitem[i].doClick();
                    for (int k = 10; k < 60; k+=2) {
                        demo.slider.setValue(k);
                        try {
                            thread.sleep(100);
                        } catch (InterruptedException e) { return; }
                    }
                    try {
                        thread.sleep(999);
                    } catch (InterruptedException e) { return; }
                }
            }
            thread = null;
        }


        class JoinIcon implements Icon {
            int joinType;
            public JoinIcon(int joinType) {
                this.joinType = joinType;
            }
    
            public void paintIcon(Component c, Graphics g, int x, int y) {
                ((Graphics2D) g).setRenderingHint(
                     RenderingHints.KEY_ANTIALIASING, 
                     RenderingHints.VALUE_ANTIALIAS_ON);
                BasicStroke bs = new BasicStroke(8.0f, 
                                    BasicStroke.CAP_BUTT, joinType);
                ((Graphics2D) g).setStroke(bs);
                GeneralPath p = new GeneralPath();
                p.moveTo(0, 3);
                p.lineTo(getIconWidth()-2, getIconHeight()/2);
                p.lineTo(0,getIconHeight());
                ((Graphics2D) g).draw(p);
            }
            public int getIconWidth() { return 20; }
            public int getIconHeight() { return 20; }
        } // End JoinIcon class
    } // End DemoControls class
} // End Joins class
