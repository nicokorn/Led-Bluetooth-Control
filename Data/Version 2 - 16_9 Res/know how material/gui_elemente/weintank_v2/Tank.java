/**
 * Die Klasse Tank ist das Model in der Weintank-Demo
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */
import java.awt.event.*;
import java.beans.*;

public class Tank 
{

    /**
     * Property-Change Unterstuetzung
     */
    public static final String FUELLSTAND_PROPERTY = "fuellstand";
    private PropertyChangeSupport props = new PropertyChangeSupport(this);
    
    /**
     * Weitere Instanzenvariablen
     */
    private int oldFuellstand, fuellstand;  // vorheriger, aktueller Fuellstand in Liter
    private int maxFuellstand;              // max. Fuellstand in Liter
    
    /**
     * Konstruktor
     */
    public Tank (int fuellstand, int maxFuellstand) 
    {
        this.fuellstand = fuellstand;
        this.maxFuellstand = maxFuellstand;
    }

    /**
     * Moeglichkeit, Listener hinzuzufuegen
     */
    public void addPropertyChangeListener (PropertyChangeListener listener) 
    {
        props.addPropertyChangeListener(listener);
    }
    
    /**
     * Werte auslesen
     */
    public int getFuellstand () 
    {
        return fuellstand;
    }
    public int getMaxFuellstand () 
    {
        return maxFuellstand;
    }

    /**
     * Fuellt den Tank und gibt den Fuellstand zurueck
     * 
     * Diese und die folgenden Methoden melden die Aenderung des Fuellstands durch
     * den Aufruf der Methode firePropertyChange an die Listener
     */
    public void fuellen () 
    {
        oldFuellstand = fuellstand;
        fuellstand = maxFuellstand;
        props.firePropertyChange(FUELLSTAND_PROPERTY, oldFuellstand, fuellstand); 
    }

    /**
     * Fuellt den Tank mit liter Litern oder bis zum maximalen Fuellstand
     */
    public void fuellstandAendernUm (int liter) 
    {
        oldFuellstand = fuellstand;
        if (fuellstand + liter < maxFuellstand) {
            if (fuellstand + liter > 0){
                fuellstand = fuellstand + liter;
            }
            else{
                fuellstand = 0;
            }
        }
        else {
            fuellstand = maxFuellstand;
        }
        props.firePropertyChange(FUELLSTAND_PROPERTY, oldFuellstand, fuellstand); 
    }

    /**
     * Setzt den Tank auf aufLiter Liter, falls im erlaubten Bereich
     */
    public void setFuellstand (int aufLiter) 
    {
        oldFuellstand = fuellstand;
        if (aufLiter >= 0 && aufLiter <= maxFuellstand) {
            fuellstand = aufLiter;
        }
        props.firePropertyChange(FUELLSTAND_PROPERTY, oldFuellstand, fuellstand); 
    }

    /**
     * Leert den Tank, gibt den letzten FŸllstand zurŸck
     */
    public int leeren () 
    {
        oldFuellstand = fuellstand;
        fuellstand = 0;
        props.firePropertyChange(FUELLSTAND_PROPERTY, oldFuellstand, fuellstand);
        return oldFuellstand;
    }
}
