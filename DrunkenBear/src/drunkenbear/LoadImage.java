/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drunkenbear;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class LoadImage extends Component{
    private BufferedImage _img;
    private int xcor;
    private int ycor;
    public LoadImage(BufferedImage img, int x, int y){
	_img = img;
	xcor = x;
	ycor = y;
    }
    public void paint(Graphics g){
	g.drawImage(_img, 0, 0,null);
    }
    public Dimension getPreferredSize(){
	if (_img == null){
	    return new Dimension(768,768);
	}
	else {
	    return new Dimension(_img.getWidth(null), _img.getHeight(null));
	}
    }
}