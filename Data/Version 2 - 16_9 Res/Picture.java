import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * Mithilfe dieser Klasse wird ein Bild in den Speicher gelesen und als BufferedImage zurückgegeben
 */
public class Picture{
    //Deklarationen
    
    //Analyse Bild
    private static BufferedImage image;
    private static int i_height;
    private static int i_width; 
    private static boolean bol_picture_loadet;
    private static int i_pixelvalue;
 
    //File Chooser für das Öffnen eines Bildes
    private static JFileChooser chooser_open_picture;
    private static FileNameExtensionFilter filter;
    private static int i_return_value;
    
    //gui
    private static JFrame gui;
    
    //Array für Farbinformationen der LED Wand
    private static int[][] arr_led_color;
    private static int i_col_count_led;
    private static int i_row_count_led;
    private static int i_segment_width;
    private static int i_segment_height;
    private static int i_segment_width_distance;
    private static int i_segment_height_distance;
    private static int i_segment_x0;
    private static int i_segment_y0;
    private static long i_segment_color;
    private static int i_segment_pixel_counter;
    private static int i;
    private static int j;
    private static int r;
    private static int s;

    /**
     * Konstruktor
     */
    public Picture(JFrame gui){
        this.gui = gui;
    }

    /**
     * open picture
     */
    public void open_picture(){
        //reset old values
        image = null;
        i_width = 0;
        i_height = 0;
        i_pixelvalue = 0;
        bol_picture_loadet = false;
        
        //init new valies
        chooser_open_picture = new JFileChooser();
        filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        chooser_open_picture.setFileFilter(filter);
        i_return_value = chooser_open_picture.showOpenDialog(gui);
        if(i_return_value == JFileChooser.APPROVE_OPTION){
            //Bild laden
            try {
                image = ImageIO.read(new File(chooser_open_picture.getSelectedFile().getAbsolutePath()));   //Bild laden
                i_width = image.getWidth();                                                                 //Anzahl Reihen
                i_height = image.getHeight();                                                               //Anzahl Zeilen
                i_pixelvalue = i_height*i_width;                                                            //Anzahl Pixel
                bol_picture_loadet = true;
            }
            catch (IOException s) {
                System.out.println("Bild konnte nicht geladen werden");
            }
        }
    }
    
    /**
     * gibt Höhe/Zeilenzahl des Bildes zurück
     */
    public int get_picture_height(){
        return i_height;
    }
    
    /**
     * gibt Spaltenzahl/Breite des Bildes zurück
     */
    public int get_picture_width(){
        return i_width;
    }
    
    /**
     * gibt zurück ob das Bild geladen wurde oder nicht
     */
    public boolean get_picture_status(){
        return bol_picture_loadet;
    }
    
    /**
     * gibt Höhe/Zeilenzahl des Bildes zurück
     */
    public BufferedImage get_picture(){
        return image;
    }
    
    /**
     * Diese Methode teilt das geladene Bild in Segmente, aus welchen der durchschnittliche Farbwert bestimmt wird. Die Farbwerte werden in einem Array abgelegt und in der LED Wandvorschau wiedergegeben
     */
    public int[][] get_picture_color(int i_col_count_led, int i_row_count_led, int segment_size){
        //2D Array für die Farbinfos der LED erstellen
        System.out.println("Eingestellte Anzahl Reihen: "+i_row_count_led+", Anzahl Spalten: "+i_col_count_led);
        arr_led_color = null;
        arr_led_color = new int[i_row_count_led][i_col_count_led];
        System.out.println("Array Dim: "+arr_led_color[0].length+", "+arr_led_color.length);
        
        i_segment_width = 0;
        i_segment_height = 0;
        i_segment_x0 = 0;
        i_segment_y0 = 0;
       
        //wird ein out of bound exception innerhalb des bildes geworfen, so wird gewechselt
        try{
            //segmentbreite 100%
            i_segment_width_distance = (int)Math.floor((double)i_width / (double)i_col_count_led);
            //segmentbreite anpassen
            i_segment_width = (int)Math.round(((double)segment_size/100)*(double)i_segment_width_distance);
            System.out.println("Segmenthöhe: "+i_segment_width);
            i_segment_height_distance = i_segment_width_distance;
            //segmenthöhe anpassen
            i_segment_height = (int)Math.round(((double)segment_size/100)*(double)i_segment_height_distance);
            System.out.println("Segmentbreite: "+i_segment_height);

            //mit einer for schlaufe durch alle Segmente schalten und jeweilige Durschnittsfarben bestimmen
            i_segment_y0 = (i_height-i_row_count_led*i_segment_height_distance) / 2;
            System.out.println("y0: "+i_segment_y0+" = ("+i_height+" - "+i_row_count_led+" * "+i_segment_height_distance+") / "+2);
            for(i=0; i<arr_led_color.length; i++){
                i_segment_x0 = 0;
                for(j=0; j<arr_led_color[0].length; j++){
                    arr_led_color[i][j] = get_picture_color_of_segment(i_segment_x0, i_segment_y0, i_segment_width, i_segment_height);
                    //System.out.println("x0, y0: "+i_segment_x0+"  "+i_segment_y0);
                    //System.out.println("seg: "+i_segment_width+"  "+i_segment_height);
                    i_segment_x0 += i_segment_width_distance;
                }
                    i_segment_y0 += i_segment_height_distance;
            }
        }catch(IndexOutOfBoundsException t){
            //segmenthöhe 100%
            i_segment_height_distance = (int)Math.floor((double)i_height / (double)i_row_count_led);
            System.out.println("Distance y: "+i_segment_height_distance+"");
            //segmenthöhe anpassen
            i_segment_height = (int)Math.round(((double)segment_size/100)*(double)i_segment_height_distance);
            System.out.println("Segmentbreite alternativ: "+i_segment_height);
            //segmentbreite 100%
            i_segment_width_distance = i_segment_height_distance;
            //segmentbreite anpassen
            i_segment_width = (int)Math.round(((double)segment_size/100)*(double)i_segment_width_distance);
            System.out.println("Segmenthöhe alternativ: "+i_segment_width);
            
            i_segment_y0 = 0;
            //mit einer for schlaufe durch alle Segmente schalten und jeweilige Durschnittsfarben bestimmen
            for(i=0; i<arr_led_color.length; i++){
                i_segment_x0 = (i_width-i_col_count_led*i_segment_width_distance) / 2;
                for(j=0; j<arr_led_color[0].length; j++){
                    //if(image!=null && s<i_width && r<i_height && (i_segment_x0+i_segment_width)<i_width && (i_segment_height+i_segment_y0)<i_height){
                        arr_led_color[i][j] = get_picture_color_of_segment(i_segment_x0, i_segment_y0, i_segment_width, i_segment_height);
                        //System.out.println("x0, y0: "+i_segment_x0+"  "+i_segment_y0);
                        //System.out.println("seg: "+i_segment_width+"  "+i_segment_height);
                        i_segment_x0 += i_segment_width_distance;
                        //}
                    }
                    i_segment_y0 += i_segment_height_distance;
                }
        }
        
        return arr_led_color;
    }
    
    /**
     * Diese Methode liest aus einem Bildegment die durchschnittliche Farbe
     */
    public int get_picture_color_of_segment(int i_segment_x0, int i_segment_y0, int i_segment_width, int i_segment_height){
        i_segment_color = 0;
        i_segment_pixel_counter = 0;
        //bestimme Durchschnittliche Farbe im gegebenen segment
        for(r=i_segment_y0; r<(i_segment_height+i_segment_y0); r++){
            for(s=i_segment_x0; s<(i_segment_width+i_segment_x0); s++){
                if(image!=null){
                    i_segment_color += image.getRGB(s,r);
                    //System.out.println("Farbwert: "+image.getRGB(s,r)+", x: "+s+", y: "+r);
                    i_segment_pixel_counter++;
                    //System.out.println("pixelcounter: "+i_segment_pixel_counter);
                }
            }
        }
        //Schnitt der Farbenkennzahl
        //System.out.println(i_segment_color+" = "+i_segment_color+" / "+i_segment_pixel_counter);
        i_segment_color = i_segment_color / i_segment_pixel_counter;
        //System.out.println("-----------Farbwert im Schnitt: "+i_segment_color);
        
        return (int)i_segment_color;
    }
}
