/**
 * ScrollbarValues
 * 
 * @author bkrt 
 * @version 18-MAR-2011
 */
import java.awt.*; 
import java.applet.Applet; 
import java.awt.event.*;

public class GuiElemente extends Applet implements ItemListener {

    private Checkbox cola; 
    private int currentX = 1; 
    private int currentY = 5;
    
    public void init() { 
        cola = new Checkbox("Cola"); 
        add(cola); 
        cola.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        repaint();
    }
    
    public void paint(Graphics g) {
        if (cola.getState()) {
            g.drawString("On", 200, 40);
            g.setColor(Color.green);
            g.fillOval(230, 30, 15, 15);
        } else {
            g.drawString("Off", 200, 40);
            g.setColor(Color.red);
            g.fillOval(230, 30, 15, 15);        
        }
    }
}