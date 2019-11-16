/**
 * Klasse TankView: View im MVC-Beispiel
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

class TankView extends Canvas implements PropertyChangeListener 
{
    /**
     * Instanzenvariable
     */
    private Tank tank;

    /**
     * Konstruktor
     * @param   model   Referenz zum Model
     * @param   breite  Breite der Darstellung
     * @param   hoehe   Hoehe der Darstellung
     * @return  - 
     */
    TankView (Tank model, int breite, int hoehe)
    {
        tank = model;
        // Beim Tank als PropertyChangeListener anmelden, damit die  
        // Darstellung des Tanks bei Aenderungen aktualisiert wird
        tank.addPropertyChangeListener(this);
        this.setSize(new Dimension(breite, hoehe));
    }

    /** 
     * Zeichnet einen Tank mit Dimension 50 x 150 an der Position xPos, yPos,
     * der max. FŸllstand ist 'maxStand', der aktuelle FŸllstand 'fuellstand'
     */
    public void paint (Graphics g)
    {
        int fuellhoehe = 0;
        g.setColor(Color.black);
        g.drawRect(0,0, getWidth()-1, getHeight()-1);
        g.setColor(Color.red);
        fuellhoehe = getHeight()*tank.getFuellstand()/tank.getMaxFuellstand();
        g.fillRect(1, getHeight()-fuellhoehe+1, getWidth()-2, fuellhoehe-2);
    }

    /**
     * Event Handler: 
     * Die TankView ist Listener des Models und wird ueber diese Methode 
     * ueber Aenderungen am Model informiert; wenn dies geschieht, muss der
     * Tank neu gezeichnet werden
     */
    public void propertyChange(PropertyChangeEvent e)
    {
        this.repaint();
    }
}