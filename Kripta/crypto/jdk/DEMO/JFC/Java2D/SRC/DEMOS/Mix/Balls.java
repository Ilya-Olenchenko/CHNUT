/*
 * @(#)Balls.java	1.18 99/09/07
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

package demos.Mix;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import AnimatingControlsSurface;
import CustomControls;



/**
 * Animated color bouncing balls with custom controls.
 */
public class Balls extends AnimatingControlsSurface {

    private static Color colors[] = 
            { Color.red, Color.orange, Color.yellow, Color.green.darker(),
              Color.blue, new Color(75, 00, 82), new Color(238,130,238) };
    private long now, deltaT, lasttime;
    private boolean active;
    protected Ball balls[] = new Ball[colors.length];
    protected boolean clearToggle;
    protected JComboBox combo;


    public Balls() {
        setBackground(Color.white);
        for (int i = 0; i < colors.length; i++) {
            balls[i] = new Ball(colors[i], 30);
        }
        balls[0].isSelected = true;
        balls[3].isSelected = true;
        balls[4].isSelected = true;
        balls[6].isSelected = true;
        setControls(new Component[] { new DemoControls(this) });
    }


    public void reset(int w, int h) {
        if (w > 400 && h > 100) {
            combo.setSelectedIndex(5);
        }
    }


    public void step(int w, int h) {
        if (lasttime == 0) {
            lasttime = System.currentTimeMillis();
        }
        now = System.currentTimeMillis();
        deltaT = now - lasttime;
        active = false;
        for (int i = 0; i < balls.length; i++) {
            if (balls[i] == null) {
                return;
            }
            balls[i].step(deltaT, w, h);
            if (balls[i].Vy > .02 || -balls[i].Vy > .02 ||
                    balls[i].y + balls[i].bsize < h) {
                active = true;
            }
        }
        if (!active) {
            for (int i = 0; i < balls.length; i++) {
                balls[i].Vx = (float)Math.random() / 4.0f - 0.125f;
                balls[i].Vy = -(float)Math.random() / 4.0f - 0.2f;
            }
            clearToggle = true;
        }
    }


    public void render(int w, int h, Graphics2D g2) {
        for (int i = 0; i < balls.length; i++) {
            Ball b = balls[i];
            if (b == null || b.imgs[b.index] == null || !b.isSelected) {
                continue;
            }
            g2.drawImage(b.imgs[b.index], (int) b.x, (int) b.y, this);
        }
        lasttime = now;
    }


    public static void main(String argv[]) {
        createDemoFrame(new Balls());
    }


    static class Ball {
    
        public int bsize;
        public float x, y;
        public float Vx = 0.1f;
        public float Vy = 0.05f;
        public int nImgs = 5;
        public BufferedImage imgs[];
        public int index = (int) (Math.random() * (nImgs-1));
    
        private final float inelasticity = .96f;
        private final float Ax = 0.0f;
        private final float Ay = 0.0002f;
        private final float Ar = 0.9f;
        private final int UP = 0;
        private final int DOWN = 1;
        private int indexDirection = UP;
        private boolean collision_x, collision_y;
        private float jitter;
        private Color color;
        private boolean isSelected;
    
    
        public Ball(Color color, int bsize) {
            this.color = color;
            makeImages(bsize);
        }
    
    
        public void makeImages(int bsize) {
            this.bsize = bsize*2;
            int R = bsize;
            byte[] data = new byte[R * 2 * R * 2];
            int maxr = 0;
            for (int Y = 2 * R; --Y >= 0;) {
                int x0 = (int) (Math.sqrt(R * R - (Y - R) * (Y - R)) + 0.5);
                int p = Y * (R * 2) + R - x0;
                for (int X = -x0; X < x0; X++) {
                    int x = X + 15;
                    int y = Y - R + 15;
                    int r = (int) (Math.sqrt(x * x + y * y) + 0.5);
                    if (r > maxr) {
                        maxr = r;
                    }
                    data[p++] = r <= 0 ? 1 : (byte) r;
                }
            }
    
            imgs = new BufferedImage[nImgs];
    
            int bg = 255;
            byte red[] = new byte[256];
            red[0] = (byte) bg;
            byte green[] = new byte[256];
            green[0] = (byte) bg;
            byte blue[] = new byte[256];
            blue[0] = (byte) bg;
    
            for (int r = 0; r < imgs.length; r++) {
                float b = 0.5f + (float) ((r+1f)/imgs.length/2f);
                for (int i = maxr; i >= 1; --i) {
                    float d = (float) i / maxr;
                    red[i] = (byte) blend(blend(color.getRed(), 255, d), bg, b);
                    green[i] = (byte) blend(blend(color.getGreen(), 255, d), bg, b);
                    blue[i] = (byte) blend(blend(color.getBlue(), 255, d), bg, b);
                }
                IndexColorModel icm = new IndexColorModel(8, maxr + 1,
                            red, green, blue, 0);
                DataBufferByte dbb = new DataBufferByte(data, data.length);
                int bandOffsets[] = {0};
                WritableRaster wr = Raster.createInterleavedRaster(dbb,
                    R*2,R*2,R*2,1, bandOffsets,null);
                imgs[r] = new BufferedImage(icm, wr,icm.isAlphaPremultiplied(),null);
            }
        }
    
    
        private final int blend(int fg, int bg, float fgfactor) {
            return (int) (bg + (fg - bg) * fgfactor);
        }
    
    
        public void step(long deltaT, int w, int h) {
            collision_x = false;
            collision_y = false;
    
            jitter = (float) Math.random() * .01f - .005f;
    
            x += Vx * deltaT + (Ax / 2.0) * deltaT * deltaT;
            y += Vy * deltaT + (Ay / 2.0) * deltaT * deltaT;
            if (x <= 0.0f) {
                x = 0.0f;
                Vx = -Vx * inelasticity + jitter;
                collision_x = true;
            }
            if (x + bsize >= w) {
                x = w - bsize;
                Vx = -Vx * inelasticity + jitter;
                collision_x = true;
            }
            if (y <= 0) {
                y = 0;
                Vy = -Vy * inelasticity + jitter;
                collision_y = true;
            }
            if (y + bsize >= h) {
                y = h - bsize;
                Vx *= inelasticity;
                Vy = -Vy * inelasticity + jitter;
                collision_y = true;
            }
            Vy = Vy + Ay * deltaT;
            Vx = Vx + Ax * deltaT;
    
            if (indexDirection == UP) {
                index++; 
            }
            if (indexDirection == DOWN) {
                --index; 
            }
            if (index+1 == nImgs) {
                indexDirection = DOWN;
            }
            if (index == 0) {
                indexDirection = UP;
            }
        }
    }  // End class Ball



    class DemoControls extends CustomControls implements ActionListener {

        Balls demo;
        JToolBar toolbar;

        public DemoControls(Balls demo) {
            super(demo.name);
            this.demo = demo;
            setBackground(Color.gray);
            add(toolbar = new JToolBar());
            toolbar.setFloatable(false);
            addTool("Clear", true);
            addTool("R", demo.balls[0].isSelected);
            addTool("O", demo.balls[1].isSelected);
            addTool("Y", demo.balls[2].isSelected);
            addTool("G", demo.balls[3].isSelected);
            addTool("B", demo.balls[4].isSelected);
            addTool("I", demo.balls[5].isSelected);
            addTool("V", demo.balls[6].isSelected);
            add(combo = new JComboBox());
            combo.addItem("10");
            combo.addItem("20");
            combo.addItem("30");
            combo.addItem("40");
            combo.addItem("50");
            combo.addItem("60");
            combo.addItem("70");
            combo.addItem("80");
            combo.setSelectedIndex(2);
            combo.addActionListener(this);
        }


        public void addTool(String str, boolean state) {
            JButton b = (JButton) toolbar.add(new JButton(str));
            b.setBackground(state ? Color.green : Color.lightGray);
            b.setSelected(state);
            b.addActionListener(this);
        }


        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JComboBox) {
                int size = Integer.parseInt((String) combo.getSelectedItem());
                for (int i = 0; i < demo.balls.length; i++) {
                    demo.balls[i].makeImages(size);
                }
                return;
            }
            JButton b = (JButton) e.getSource();
            b.setSelected(!b.isSelected());
            b.setBackground(b.isSelected() ? Color.green : Color.lightGray);
            if (b.getText().equals("Clear")) {
                demo.clearSurface = b.isSelected();
            } else {
                int index = toolbar.getComponentIndex(b)-1;
                demo.balls[index].isSelected = b.isSelected();
            }
        }


        public Dimension getPreferredSize() {
            return new Dimension(200,37);
        }


        public void run() {
            try { thread.sleep(999); } catch (Exception e) { return; }
            Thread me = Thread.currentThread();
            ((JButton) toolbar.getComponentAtIndex(2)).doClick();
            while (thread == me) {
                try {
                    thread.sleep(222);
                } catch (InterruptedException e) { return; }
                if (demo.clearToggle) {
                    if (demo.clearSurface) {
                        combo.setSelectedIndex((int) (Math.random()*5));
                    }
                    ((JButton) toolbar.getComponentAtIndex(0)).doClick();
                    demo.clearToggle = false;
                }
            }
            thread = null;
        }
    } // End DemoControls
} // End Balls
