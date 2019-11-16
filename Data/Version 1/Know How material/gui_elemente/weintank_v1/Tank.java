/**
 * Die Klasse Tank ist das Model in der Weintank-Demo
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */
public class Tank 
{
    private int fuellstand;     // aktueller Fuellstand in Liter
    private int maxFuellstand;  // max. Fuellstand in Liter

    /**
     *  Konstruktor: Daten des Tanks festlegen
     */
    public Tank (int fuellstand, int maxFuellstand)
    {
        this.fuellstand = fuellstand;
        this.maxFuellstand = maxFuellstand;
    }

    /**
     *  Fuellstand zurueckgeben
     */
    public int getFuellstand ()
    {
        return fuellstand;
    }

    /**
     *  Maximalen Fuellstand zurueckgeben
     */
    public int getMaxFuellstand ()
    {
        return maxFuellstand;
    }

    /**
     * Fuellt den Tank 
     */
    public void fuellen ()
    {
        fuellstand = maxFuellstand;
    }

    /**
     * Aendert den Tankinhalt um einen bestimmten Betrag, dabei muss der
     * maximale Fuellstand beruecksichtigt werden
     */
    public void fuellstandAendernUm (int liter)
    {
        if (fuellstand + liter < maxFuellstand) {
            if (fuellstand + liter > 0){
                fuellstand = fuellstand + liter;
            }
            else {
                fuellstand = 0;
            }
        }
        else {
            fuellstand = maxFuellstand;
        }
    }

    /**
     *  Fuellstand auf einen bestimmten Wert setzen
     */
    public void setFuellstand (int aufLiter)
    {
        if (aufLiter >= 0 && aufLiter <= maxFuellstand){
            fuellstand = aufLiter;
        }
    }

    /**
     * leert den Tank, gibt den letzten Fuellstand zurueck
     */
    public int leeren ()
    {
        int lastFuellstand = fuellstand;
        fuellstand = 0;
        return lastFuellstand;
    }
}