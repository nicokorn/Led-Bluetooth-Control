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

    private CheckboxGroup c;
    private Checkbox cola, fanta; 
    private int currentX = 1; 
    private int currentY = 5;
    
    public void init() {
        c = new CheckboxGroup();
        cola = new Checkbox("Cola", c, false); 
        add(cola); 
        cola.addItemListener(this);
        fanta = new Checkbox("Fanta", c, true); 
        add(fanta); 
        fanta.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        repaint();
    }
    
    public void paint(Graphics g) {
        if (cola.getState()) {
            g.drawString("Cola", 200, 40);
        } else {
            g.drawString("Fanta", 200, 40);
        }
    }
}