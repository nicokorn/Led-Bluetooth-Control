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

    private List liste;
    
    public void init() {
        setLayout(null);
        liste = new List(5,true);
        
        liste.add("Red");
        liste.add("Blue");
        liste.add("Green");
        liste.add("Yellow");
        liste.add("Grey");

        add(liste); 
        liste.setBounds(50, 10, 80, 100);
        liste.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        repaint();
    }
    
    public void paint(Graphics g) {
        String[] items = liste.getSelectedItems();
        for (int i=0, n=items.length; i<n; i++) {
            g.drawString(items[i], 200, 200 + 20*i);
        }
    }
}