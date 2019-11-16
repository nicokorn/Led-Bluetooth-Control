import java.awt.*;
import java.applet.*;

public class CanvasDemo extends Applet { 
    private MyCanvas canvas = new MyCanvas();

    public void init() { 
        canvas.setBackground(Color.gray); 
        canvas.setForeground(Color.white); 
        canvas.setSize(200,100); 
        add(canvas);
    } 
}