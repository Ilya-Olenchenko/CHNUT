/*
 * @(#)Rotate.java	1.18 99/09/07
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

package demos.Transforms;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import ControlsSurface;
import CustomControls;


/**
 * Rotate ellipses with controls for increment and emphasis.
 * Emphasis is defined as which ellipses have a darker color and thicker stroke.
 */
public class Rotate extends ControlsSurface {


    protected double increment = 5.0;
    protected int emphasis = 9;


    public Rotate() {
        setBackground(Color.white);
        setControls(new Component[] { new DemoControls(this) });
    }


    public void render(int w, int h, Graphics2D g2) {
        int size = Math.min(w, h);
        float ew = size/4;
        float eh = size-20;
        Ellipse2D ellipse = new Ellipse2D.Float(-ew/2, -eh/2, ew, eh);
        for (double angdeg = 0; angdeg < 360; angdeg+=increment) {
            if (angdeg % emphasis == 0) {
                g2.setColor(Color.gray);
                g2.setStroke(new BasicStroke(2.0f));
            } else {
                g2.setColor(Color.lightGray);
                g2.setStroke(new BasicStroke(0.5f));
            }
            AffineTransform at = AffineTransform.getTranslateInstance(w/2, h/2);
            at.rotate(Math.toRadians(angdeg));
            g2.draw(at.createTransformedShape(ellipse));
        }
        g2.setColor(Color.blue);
        ellipse.setFrame(w/2-10,h/2-10,20,20);
        g2.fill(ellipse);
        g2.setColor(Color.gray);
        g2.setStroke(new BasicStroke(6));
        g2.draw(ellipse);
        g2.setColor(Color.yellow);
        g2.setStroke(new BasicStroke(4));
        g2.draw(ellipse);
        g2.setColor(Color.black);
        g2.drawString("Rotate", 5, 15);
    }


    public static void main(String s[]) {
        createDemoFrame(new Rotate());
    }


    static class DemoControls extends CustomControls implements ActionListener {

        Rotate demo;
        JTextField tf1, tf2;

        public DemoControls(Rotate demo) {
            super(demo.name);
            this.demo = demo;
            setBackground(Color.gray);
            JLabel l = new JLabel("Increment:");
            l.setForeground(Color.black);
            add(l);
            add(tf1 = new JTextField("5.0"));
            tf1.setPreferredSize(new Dimension(30,24));
            tf1.addActionListener(this);
            add(l = new JLabel("  Emphasis:"));
            l.setForeground(Color.black);
            add(tf2 = new JTextField("9"));
            tf2.setPreferredSize(new Dimension(30,24));
            tf2.addActionListener(this);
        }


        public void actionPerformed(ActionEvent e) {
            try { 
                if (e.getSource().equals(tf1)) {
                    demo.increment = Double.parseDouble(tf1.getText().trim());
                    if (demo.increment < 1.0) {
                        demo.increment = 1.0;
                    }
                } else {
                    demo.emphasis = Integer.parseInt(tf2.getText().trim());
                }
                demo.repaint();
            } catch (Exception ex) {}
        }

        public Dimension getPreferredSize() {
            return new Dimension(200,36);
        }

        public void run() {
            Thread me = Thread.currentThread();
            while (thread == me) {
                for (int i = 3; i < 13; i+=3) {
                    try {
                        thread.sleep(4444);
                    } catch (InterruptedException e) { return; }
                    tf1.setText(String.valueOf(i));
                    demo.increment = i;
                    demo.repaint();
                }
            }
            thread = null;
        }
    } // End DemoControls class
} // End Rotate class
