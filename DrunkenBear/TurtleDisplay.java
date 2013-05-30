package drunkenbear.gui;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

public class TurtleDisplay{
    private Class c1;
    private String imageFilename;
    private static final String imageExtension = ".gif";
    private Map<String, Image> icon = new HashMap<String,Image>();
    
    public TurtleDisplay(Class c2) throws IOException{
	c1 = c2;
	imageFilename = c1.getName().replace('.','/');
	URL url = c1.getClassLoader().getResource(imageFilename = imageExtension);
	if (url == null)
	    throw new FileNotFoundException(imageFilename + imageExtension + " not found.");
	icon.put("", ImageIO.read(url));
    }
    public void draw(Object obj, Component comp, Graphics2D g2){
	Image sprite = icon.get("");
	int width = sprite.getWidth(null);
	int height = sprite.getHeight(null);
	int size = Math.max(width,height);
	g2.scale(1.0/size, 1.0/size);
	g2.clip(new Rectangle(-width / 2, -height/2, width, height));
	g2.drawImage(sprite,-width/2, - height/2, null);
    }
}