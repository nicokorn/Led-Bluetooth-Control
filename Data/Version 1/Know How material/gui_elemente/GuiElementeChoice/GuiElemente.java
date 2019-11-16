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

    private Choice colourChoice;
    
    public void init() {
        colourChoice = new Choice();
        
        colourChoice.add("Red");
        colourChoice.add("Blue");
        colourChoice.add("Green");
        colourChoice.add("Yellow");

        add(colourChoice); 
        colourChoice.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        repaint();
    }
    
    public void paint(Graphics g) {
        g.drawString(colourChoice.getSelectedItem(), 200, 200);
    }
}