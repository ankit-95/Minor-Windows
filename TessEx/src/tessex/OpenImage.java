/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tessex;
 
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;
 
/**
 *
 * @author lenovo
 */
public class OpenImage extends Canvas {
    static String path="";public static  JFrame f;
    public OpenImage open;
    OpenImage(){}
    OpenImage(String path,OpenImage image){
    this.path=path;
    f=new JFrame();
    f.add(image);
    f.setVisible(true);
    f.setSize(800,400);
    
    }
   
    public void paint(Graphics g) {  
        Toolkit t=Toolkit.getDefaultToolkit();  
        java.awt.Image i=t.getImage(path);  
        g.drawImage(i, 120,100,this);  
    }
   
}