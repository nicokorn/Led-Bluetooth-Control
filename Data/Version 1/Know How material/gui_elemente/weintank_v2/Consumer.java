/**
 * Klasse Consumer: Beeinflusst Model im MVC-Beispiel
 * 
 * @author huhp
 * @author bkrt (etwas angepasst)
 * @version 14-APR-2011
 */
import java.util.Timer;
import java.util.TimerTask;

public class Consumer 
{

    /**
     * Instanzenvariablen
     */
    private Tank tank;
    private int liter = 1;
    private Timer timer;

    /**
     * Konstruktor
     */
    public Consumer (Tank t, int seconds, int menge) 
    {
        tank = t;
        liter = menge;
        timer = new Timer();
        timer.schedule(new ConsumerTask(), seconds*1000, seconds*1000);
    }

    /**
     * Innere Klasse, die run-Methode zur Verfuegung stellt 
     * (TimerTask implementiert Runnable)
     */
    class ConsumerTask extends TimerTask {
        public void run() {
            tank.fuellstandAendernUm (-liter);       
        }
    }

}
