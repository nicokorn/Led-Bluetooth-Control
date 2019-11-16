/**
 *  FileDialogDemo
 *  Oeffnen einer Dateiauswahl und Anzeige der ausgewaehlten Datei
 *  inklusive dem zugehoerigen Pfad.
 *  
 *  @author huhp, bkrt 
 *  @version 01-MAY-2013
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class FileDialogDemo extends Frame implements ActionListener, WindowListener 
{

    private Button loadButton;
    private FileDialog getNameBox;
    private TextField nameField;

    /**
     *  main-Methode zum Starten des Programms
     */
    public static void main (String [] args) 
    {
        FileDialogDemo demo = new FileDialogDemo();
        demo.setSize(500,400);
        demo.setVisible(true);
    }

    /**
     *  Konstruktor: GUI initialisieren
     */
    public FileDialogDemo () 
    {
        setLayout( new FlowLayout() );
        
        loadButton = new Button("load");
        add(loadButton);
        loadButton.addActionListener(this);
        
        nameField = new TextField(30);
        add(nameField);
        
        addWindowListener(this);
    }

    /**
     *  Button-Ereignis behandeln: FileDialog oeffnen
     */
    public void actionPerformed (ActionEvent evt) 
    {
        String fileName, dirName;
        if (evt.getSource() == loadButton) {
 
            // Dialog anzeigen
            getNameBox = new FileDialog (this, "get Name", FileDialog.LOAD);
            getNameBox.setVisible(true);
            
            // Dialog auswerten
            fileName = getNameBox.getFile();
            dirName = getNameBox.getDirectory();
            nameField.setText(dirName + fileName);
        }
    }

    /**
     *  WindowListener-Methoden
     */
    public void windowClosing (WindowEvent e) {
        System.exit(0);
    }
    public void windowIconified (WindowEvent e) {}
    public void windowOpened (WindowEvent e) { }
    public void windowClosed (WindowEvent e) { }
    public void windowDeiconified (WindowEvent e) { }
    public void windowActivated (WindowEvent e) { }
    public void windowDeactivated (WindowEvent e) { }

}
