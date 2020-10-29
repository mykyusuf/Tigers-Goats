package tigersgoats;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.*;

public class SimpleCanvas
{

    private JFrame     frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Image      canvasImage;
    private boolean    autoRepaint;
    
   /**
     * Creates and displays a SimpleCanvas of the specified size and background 
     */
    public SimpleCanvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width,height));
        frame.pack();
        Dimension size = canvas.getSize();
        canvasImage = canvas.createImage(size.width,size.height);
        graphic = (Graphics2D) canvasImage.getGraphics();
        graphic.setColor(bgColour);
        graphic.fillRect(0,0,size.width,size.height);
        graphic.setColor(Color.black);
        frame.setVisible(true);
        this.autoRepaint = true;
    }
    
   /**
     * Creates and displays a SimpleCanvas of size 400x400 with the
     * default title "SimpleCanvas" and with white background.
     */
    public SimpleCanvas() {
        this("SimpleCanvas", 400, 400, Color.white);
    }
    
    /** 
     * Draws a line on this SimpleCanvas from x1,y1 to x2,y2 with colour c.
     */
    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        setForegroundColour(c);
        graphic.drawLine(x1, y1, x2, y2);
        if (autoRepaint) canvas.repaint();
    }
    
    /** 
     * Draws a point on this SimpleCanvas at x,y with colour c.
     */
    public void drawPoint(int x, int y, Color c) {
        drawLine(x, y, x, y, c);
    }
    
    /** 
     * Draws a rectangle on this SimpleCanvas with sides parallel to the axes 
     * between x1,y1 and x2,y2 with colour c.
     */
    public void drawRectangle(int x1, int y1, int x2, int y2, Color c) {
        setForegroundColour(c);
        graphic.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        if (autoRepaint) canvas.repaint();
    }
    
    /** 
     * Draws a disc on this SimpleCanvas centred at x,y with radius r with colour c.
     */
    public void drawDisc(int x, int y, int r, Color c) {
        for (int i = x - r; i <= x + r; i++)
            for (int j = y - r; j <= y + r; j++)
                if (Math.pow(i-x, 2) + Math.pow(j-y, 2) <= Math.pow(r, 2)) 
                   drawPoint(i, j, c);
        if (autoRepaint) canvas.repaint();
    }
    
    /** 
     * Draws a circle on this SimpleCanvas centred at x,y with radius r with colour c.
     */
    public void drawCircle(int x, int y, int r, Color c) {
        for (int i = x - r; i <= x + r; i++)
            for (int j = y - r; j <= y + r; j++)
                if (Math.pow(i-x, 2) + Math.pow(j-y, 2) <= Math.pow(r,     2) &&
                    Math.pow(i-x, 2) + Math.pow(j-y, 2) >= Math.pow(r - 5, 2)) 
                   drawPoint(i, j, c);
        if (autoRepaint) canvas.repaint();
    }
    
    /**
     * Writes the String text on this SimpleCanvas at x,y with colour c.
     */
    public void drawString(String text, int x, int y, Color c) {
        setForegroundColour(c);
        graphic.drawString(text, x, y);
        if (autoRepaint) canvas.repaint();
    }
    
    /**
     * Writes the number n on this SimpleCanvas at x,y with colour c.
     */
    public void drawString(int n, int x, int y, Color c) {
        drawString(n + "", x, y, c);
    }
    
    /** 
     * Changes the colour for subsequent drawing on this SimpleCanvas.
     */
    public void setForegroundColour(Color newColour) {
        graphic.setColor(newColour);
    }
    
    /**
     * Gets the colour currently used for drawing on this SimpleCanvas.
     */
    public Color getForegroundColour() {
        return graphic.getColor();
    }
    
    /**
     * Changes the font for subsequent Strings on this SimpleCanvas.
     */
    
    public void setFont(Font newFont) {
        graphic.setFont(newFont);
    }
    
    /**
     * Gets the font currently used for Strings on this SimpleCanvas.
     */
    public Font getFont() {
        return graphic.getFont();
    }
    
    /**
     * Sets the repaint mode to either manual or automatic.
     */
    public void setAutoRepaint(boolean autoRepaint) {
        this.autoRepaint = autoRepaint;
    }
     
     
    /**
     * If this SimpleCanvas does not automatically repaint after each drawing command, 
     * this method can be used to cause a manual repaint.
     */
    public void repaint() {
        canvas.repaint();
    }
    
    /**
     * Causes execution to pause for the specified amount of time.
     * This is usually used to produce animations in an easy manner, 
     * by repeatedly drawing, pausing, and then redrawing an object.
     */
    public void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            System.out.println("Interruption in SimpleCanvas: " + ie);
        }
    }
    
    /**
     * Sets up this SimpleCanvas to respond to mouse input.
     */
    public void addMouseListener(MouseListener ml) {
        canvas.addMouseListener(ml);
    }
    
    /**
     * Sets up this SimpleCanvas to respond to mouse motion input.
     */
    public void addMouseMotionListener(MouseMotionListener mml) {
        canvas.addMouseMotionListener(mml);
    }
    
    class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage,0,0,null);
        }
    }       
}


