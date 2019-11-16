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
 * Write a description of class JPanelPicture here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JPanelPicture extends JPanel{
    // instance variables - replace the example below with your own
    private static JPanel jpanel_picture;
    private static Picture picture;
    private static JLabel lbl_bild_einfuegen;

    //Analyse Bild
    private static BufferedImage image;
    private static Graphics2D g2d;
    private static int i_height;
    private static int i_width; 
    private static double d_height;
    private static double d_width;
    
    //Vorschau Bild
    private static double d_scale_factor_width;
    private static double d_scale_factor_height;
    private static double d_scale_factor_definitiv;
    private static int i_preview_x;
    private static int i_preview_y;
    private static boolean bol_picture_loadet;
    
    /**
     * Constructor for objects of class JPanelPicture
     */
    public JPanelPicture(){
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Bildvorschau"));
        setPreferredSize(new Dimension(702, 385));

        //label in Fenstermitte mit Anweisung
        lbl_bild_einfuegen = new JLabel("Um ein Bild für die LED Wand zu laden, klicken Sie auf Bild Öffnen", SwingConstants.CENTER);
        add(lbl_bild_einfuegen);
        
        setVisible(true);
    }
    
    /**
     * Zeige Bild
    */
    public void loadPicture(Picture picture){
        this.picture = picture;
        image = null;
        if(picture != null){
            image = picture.get_picture();
            lbl_bild_einfuegen.setVisible(false);
            System.out.println("Picture height: "+(double)picture.get_picture_height()+", and width: "+(double)picture.get_picture_width());
        }
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        
        //zeichne Feldrahmen
        //g.drawRect(5,10,690,360);
        
        //zeichne Bild
        if(picture != null) {
            //Bild Skalierung
            d_height = (double)picture.get_picture_height();
            d_width = (double)picture.get_picture_width();
            d_scale_factor_width = 640 / d_width; 
            d_scale_factor_height = 360 / d_height; 
            //Zeichnen des Bildes im Vorschaufenster und passe es optimal ein
            if((int)(d_height*d_scale_factor_width) > 360){
                i_preview_x = 5+345-((int)(d_width*d_scale_factor_height)/2);
                i_preview_y = 15;
                g.drawImage(image, i_preview_x, i_preview_y, (int)(d_width*d_scale_factor_height), (int)(d_height*d_scale_factor_height), this);
                d_scale_factor_definitiv = d_scale_factor_height;
                System.out.println("width Anpassung Bild: "+(int)(d_width*d_scale_factor_height));
            }else{
                i_preview_x = 30;
                i_preview_y = 15+180-((int)(d_height*d_scale_factor_width)/2);
                g.drawImage(image, i_preview_x, i_preview_y , (int)(d_width*d_scale_factor_width), (int)(d_height*d_scale_factor_width), this);
                d_scale_factor_definitiv = d_scale_factor_width;
                System.out.println("height Anpassung Bild: "+(int)(d_height*d_scale_factor_width));
            }
            
            //Zeichne Rahmen im Bild um zu zeigen, was auf der LED Wall gezeigt werden soll
            if((int)(d_height*d_scale_factor_width) > 360){  
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(3));
                g.drawRect(i_preview_x,(int)((390-d_width*d_scale_factor_height*0.6)/2),(int)(d_width*d_scale_factor_height),(int)(d_width*d_scale_factor_height*0.6));
                System.out.println("width Anpassung Rahmen: "+(int)(d_width*d_scale_factor_height));
            }else{
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(3));
                g.drawRect((int)((700-1.8*d_height*d_scale_factor_width)/2),i_preview_y,(int)(1.8*d_height*d_scale_factor_width),(int)(d_height*d_scale_factor_width));
                System.out.println("height Anpassung Rahmen: "+(int)(d_height*d_scale_factor_width));
            }
        }     
    }
}
