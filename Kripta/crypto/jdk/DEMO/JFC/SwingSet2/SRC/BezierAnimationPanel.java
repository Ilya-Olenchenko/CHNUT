/*
 * @(#)BezierAnimationPanel.java	1.7 99/11/08
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
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * BezierAnimationPanel
 *
 * @version 1.7 11/08/99
 * @author Jim Graham
 * @author Jeff Dinkins (removed dynamic setting changes, made swing friendly)
 */
class BezierAnimationPanel extends JPanel implements Runnable {

    Color backgroundColor =  new Color(0,     0, 153);
    Color outerColor      =  new Color(255, 255, 255);
    Color gradientColorA  =  new Color(255,   0, 101);
    Color gradientColorB  =  new Color(255, 255,   0);

    boolean bgChanged = false;

    GradientPaint gradient = null;
    
    public final int NUMPTS = 6;

    float animpts[] = new float[NUMPTS * 2];

    float deltas[] = new float[NUMPTS * 2];

    float staticpts[] = {
	 50.0f,   0.0f,
	150.0f,   0.0f,
	200.0f,  75.0f,
	150.0f, 150.0f,
	 50.0f, 150.0f,
	  0.0f,  75.0f,
    };

    float movepts[] = new float[staticpts.length];

    BufferedImage img;

    Rectangle bounds = null;

    Thread anim;

    /**
     * BezierAnimationPanel Constructor
     */
    public BezierAnimationPanel() {
	addHierarchyListener(
	    new HierarchyListener() {
	       public void hierarchyChanged(HierarchyEvent e) {
		   if(isShowing()) {
		       start();
		   } else {
		       stop();
		   }
	       }
	   }
	);
	setBackground(getBackgroundColor());
    }

    public Color getGradientColorA() {
	return gradientColorA;
    }

    public void setGradientColorA(Color c) {
	if(c != null) {
	    gradientColorA = c;
	}
    }

    public Color getGradientColorB() {
	return gradientColorB;
    }

    public void setGradientColorB(Color c) {
	if(c != null) {
	    gradientColorB = c;
	}
    }

    public Color getOuterColor() {
	return outerColor;
    }

    public void setOuterColor(Color c) {
	if(c != null) {
	    outerColor = c;
	}
    }

    public Color getBackgroundColor() {
	return backgroundColor;
    }

    public void setBackgroundColor(Color c) {
	if(c != null) {
	    backgroundColor = c;
	    setBackground(c);
	    bgChanged = true;
	}
    }

    public void start() {
	Dimension size = getSize();
	for (int i = 0; i < animpts.length; i += 2) {
	    animpts[i + 0] = (float) (Math.random() * size.width);
	    animpts[i + 1] = (float) (Math.random() * size.height);
	    deltas[i + 0] = (float) (Math.random() * 4.0 + 2.0);
	    deltas[i + 1] = (float) (Math.random() * 4.0 + 2.0);
	    if (animpts[i + 0] > size.width / 6.0f) {
		deltas[i + 0] = -deltas[i + 0];
	    }
	    if (animpts[i + 1] > size.height / 6.0f) {
		deltas[i + 1] = -deltas[i + 1];
	    }
	}
	anim = new Thread(this);
	anim.setPriority(Thread.MIN_PRIORITY);
	anim.start();
    }

    public synchronized void stop() {
	anim = null;
	notify();
    }

    public void animate(float[] pts, float[] deltas, int index, int limit) {
	float newpt = pts[index] + deltas[index];
	if (newpt <= 0) {
	    newpt = -newpt;
	    deltas[index] = (float) (Math.random() * 3.0 + 2.0);
	} else if (newpt >= (float) limit) {
	    newpt = 2.0f * limit - newpt;
	    deltas[index] = - (float) (Math.random() * 3.0 + 2.0);
	}
	pts[index] = newpt;
    }

    public void run() {
	Thread me = Thread.currentThread();
	while (getSize().width <= 0) {
	    try {
		anim.sleep(500);
	    } catch (InterruptedException e) {
		return;
	    }
        }
         
	Graphics2D g2d = null;
	Graphics2D BufferG2D = null;
	Graphics2D ScreenG2D = null;
	BasicStroke solid = new BasicStroke(9.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 9.0f);
	GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO);
	int rule = AlphaComposite.SRC_OVER;
	AlphaComposite opaque = AlphaComposite.SrcOver;
	AlphaComposite blend = AlphaComposite.getInstance(rule, 0.9f);
	AlphaComposite set = AlphaComposite.Src;
	int frame = 0;
	int frametmp = 0;
	Dimension oldSize = getSize();
	Shape clippath = null;
	while (anim == me) {
	    Dimension size = getSize();
	    if (size.width != oldSize.width || size.height != oldSize.height) {
		img = null;
		clippath = null;
		if (BufferG2D != null) {
		    BufferG2D.dispose();
		    BufferG2D = null;
		}
		if (ScreenG2D != null) {
		    ScreenG2D.dispose();
		    ScreenG2D = null;
		}
	    }
	    oldSize = size;

	    if (img == null) {
		img = (BufferedImage) createImage(size.width, size.height);
	    }

	    if (BufferG2D == null) {
		BufferG2D = img.createGraphics();
		BufferG2D.setRenderingHint(RenderingHints.KEY_RENDERING,
					   RenderingHints.VALUE_RENDER_DEFAULT);
		BufferG2D.setClip(clippath);
	    }
	    g2d = BufferG2D;

	    float[] ctrlpts;
	    for (int i = 0; i < animpts.length; i += 2) {
		animate(animpts, deltas, i + 0, size.width);
		animate(animpts, deltas, i + 1, size.height);
	    }
	    ctrlpts = animpts;
	    int len = ctrlpts.length;
	    gp.reset();
	    int dir = 0;
	    float prevx = ctrlpts[len - 2];
	    float prevy = ctrlpts[len - 1];
	    float curx = ctrlpts[0];
	    float cury = ctrlpts[1];
	    float midx = (curx + prevx) / 2.0f;
	    float midy = (cury + prevy) / 2.0f;
	    gp.moveTo(midx, midy);
	    for (int i = 2; i <= ctrlpts.length; i += 2) {
		float x1 = (midx + curx) / 2.0f;
		float y1 = (midy + cury) / 2.0f;
		prevx = curx;
		prevy = cury;
		if (i < ctrlpts.length) {
		    curx = ctrlpts[i + 0];
		    cury = ctrlpts[i + 1];
		} else {
		    curx = ctrlpts[0];
		    cury = ctrlpts[1];
		}
		midx = (curx + prevx) / 2.0f;
		midy = (cury + prevy) / 2.0f;
		float x2 = (prevx + midx) / 2.0f;
		float y2 = (prevy + midy) / 2.0f;
		gp.curveTo(x1, y1, x2, y2, midx, midy);
	    }
	    gp.closePath();

	    g2d.setComposite(set);
	    g2d.setBackground(backgroundColor);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_OFF);

	    if(bgChanged || bounds == null) {
		bounds = new Rectangle(0, 0, getWidth(), getHeight());
		bgChanged = false;
	    }
	    // g2d.clearRect(bounds.x-5, bounds.y-5, bounds.x + bounds.width + 5, bounds.y + bounds.height + 5);
	    g2d.clearRect(0, 0, getWidth(), getHeight());

	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setColor(outerColor);
	    g2d.setComposite(opaque);
	    g2d.setStroke(solid);
	    g2d.draw(gp);
	    g2d.setPaint(gradient);

	    if(!bgChanged) {
		bounds = gp.getBounds();
	    } else {
		bounds = new Rectangle(0, 0, getWidth(), getHeight());
		bgChanged = false;
	    }
	    gradient = new GradientPaint(bounds.x, bounds.y, gradientColorA,
					 bounds.x + bounds.width, bounds.y + bounds.height,
					 gradientColorB, true);
	    g2d.setComposite(blend);
	    g2d.fill(gp);

	    if (g2d == BufferG2D) {
		repaint();
	    }
	    ++frame;
	    Thread.yield();
	}
	if (g2d != null) {	
	    g2d.dispose();
	}
    }

    public void paint(Graphics g) {
	synchronized (this) {
	   Graphics2D g2d = (Graphics2D) g;
	   if (img != null) {
	       int imgw = img.getWidth();
	       int imgh = img.getHeight();
	       g2d.setComposite(AlphaComposite.Src);
	       g2d.drawImage(img, null, 0, 0);
	   }
        }
    }
}
