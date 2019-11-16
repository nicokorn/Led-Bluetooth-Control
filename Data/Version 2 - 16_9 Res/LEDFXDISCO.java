
/**
 * Diese Klasse beinhaltet eine LED Specialeffect für Simulationen
 */
public class LEDFXDISCO implements Runnable
{
    //Variablen
    private static int s;
    private static JPanelLEDPreview led_wall_preview;
    private static LEDWallGUI led_gui;
    private volatile boolean run = true;
    private static int i_led_color;
    public static int i_refreshrate;
    private static String str_effect;
    
    /**
     * Constructor for objects of class LEDFXDISCO
     */
    public LEDFXDISCO(JPanelLEDPreview led_wall_preview, LEDWallGUI led_gui)
    {
        this.led_wall_preview = led_wall_preview;
        this.led_gui = led_gui;
    }

     /**
     * preview led wall state
     */
    @Override
    public void run(){ 
        s = 0;
        
        switch(str_effect){
            case "Color Dance":         while(run){
                                            if(s < 50){
                                                led_wall_preview.setColorDance(+i_led_color);
                                                led_gui.repaint();
                                                waitProcessing(i_refreshrate);
                                                System.out.println("Zähler: "+s);
                                                s++;
                                            }else{
                                                led_wall_preview.setColorDance(-i_led_color);
                                                led_gui.repaint();
                                                waitProcessing(i_refreshrate);
                                                System.out.println("Else Zähler: "+s);
                                                s++;
                                                if(s > 99){
                                                    System.out.println("reset: "+s);
                                                    s = 0;
                                                }
                                            }
                                        }
                                        break;
            case "Color Wave":          while(run){
                                            //dummy
                                            waitProcessing(i_refreshrate);
                                            System.out.println("waaaave");
                                        }
                                        break;
            default:
        }
    }

     /**
     * stop thread
     */
    public void setEnable(boolean run){ 
        this.run = run;
    }

     /**
     * set refreshrate
     */
    public void setRefreshrate(int i_refreshrate){ 
        this.i_refreshrate = i_refreshrate;
    }
    
     /**
     * set effect
     */
    public void setEffect(String str_effect){ 
        this.str_effect = str_effect;
    }
    
     /**
     * set effect
     */
    public void setLEDFXDISCO(int i_refreshrate, String str_effect){ 
        i_led_color = 1;
        this.i_refreshrate = i_refreshrate;
        this.str_effect = str_effect;
    }
    
    private static void waitProcessing(int iWait){
        try {
            Thread.sleep(iWait);
        }catch(InterruptedException ie) {
        }
    }
}
