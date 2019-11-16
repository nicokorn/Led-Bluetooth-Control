import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.Object;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Klasse zum aufbauen einer Virtuellen LED Wand
 */
public class JPanelLEDPreview extends JPanel{
    //Variablen
    private static int i;
    private static int j;
    private static int s;
    private static double x;
    private static double y;
    private static int i_col;
    private static int i_row;
    private static int i_preview_x_offset;
    private static int i_preview_y_offset;
    private static double i_delta_x;
    private static double i_delta_y;
    private static double i_width;
    private static double i_height;
    private static int i_led_radius;
    private static Graphics2D g2d;
    private static boolean bol_draw;
    private static int[][] arr_led_colors;

    /**
     * Constructor for objects of class LEDWall
     */
    public JPanelLEDPreview(){
        //Panel
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "LED Wand Vorschau"));
        setPreferredSize(new Dimension(702, 430));
        
        //Nullpunkt für LED Wand Vorschau
        i_preview_x_offset = 15;
        i_preview_y_offset = 25;
        i_width = 670;
        i_height = 390;
        
        //init row col
        //i_col = 4;
        //i_row = 4;
        
        bol_draw = false;
    }

    /**
     * 
     */
    public void setLEDRaster(int i_col, int i_row, int[][] arr_led_colors, boolean b_keep_ratio, int i_width_ratio, int i_height_ratio){
        this.i_col = i_col;
        this.i_row = i_row;
        this.arr_led_colors = arr_led_colors;
        
        if(b_keep_ratio){
            i_delta_x = i_width_ratio / i_col;
            i_delta_y = i_height_ratio / i_row;
        }else{
            i_delta_x = i_width / i_col;
            i_delta_y = i_height / i_row;
        }
        
        bol_draw = true;
    }
    
    /**
     * setze LED radius
     */
    public void setLEDRadius(int i_led_radius){
        this.i_led_radius = i_led_radius;
        repaint();
    }
    
    /**
     * gebe LED radius
     */
    public int getLEDRadius(){
        return i_led_radius;
    }
    
    /**
     * Disco Funktion: Array Manipulieren
     */
    public void setColorDance(int i_led_color){
        for(i=0; i<arr_led_colors.length; i++){
            for(j=0; j<arr_led_colors[0].length; j++){
                arr_led_colors[i][j] += i_led_color;
            }
        }
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g2d = (Graphics2D) g;
        //g.drawRect(i_preview_x_offset,i_preview_y_offset,i_width,i_height);
        //LED Raster zeichnen
        int count = 0;
        if(bol_draw){
            y = (int)Math.round(i_delta_y / 2);
            for(i=0; i<arr_led_colors.length; i++){
                x = (int)Math.round(i_delta_x / 2);
                for(j=0; j<arr_led_colors[0].length; j++){
                    //System.out.println("Laufvariable x: "+j+", max: "+arr_led_colors[0].length+"--- Laufvariable y: "+i+", max: "+arr_led_colors.length);
                    //System.out.println("counter: "+count);
                    //System.out.println("x: "+x+", y: "+y);
                    g.setColor(new Color(arr_led_colors[i][j]));
                    g.drawOval((int)(Math.round(i_preview_x_offset+x)-(i_led_radius/2)), (int)(Math.round(i_preview_y_offset+y)-(i_led_radius/2)), i_led_radius, i_led_radius);
                    g.fillOval((int)(Math.round(i_preview_x_offset+x))-(i_led_radius/2), (int)(Math.round(i_preview_y_offset+y)-(i_led_radius/2)), i_led_radius, i_led_radius);
                    x += i_delta_x;
                    count++;
                }
                y += i_delta_y;
            }
            //System.out.println("rows total: "+i_row+", rows gezählt: "+s+",  cols total: "+i_col+", cols gezählt: "+r);
            //System.out.println("rows total in array: "+arr_led_colors[1].length+", cols total in array: "+arr_led_colors[0].length);
            //System.out.println("LED Bild Breite: "+x+", Höhe: "+y);
        }
    }
}
