package drunkenbear;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
public class Mage extends Turtle{
    public Mage(Grid g, Patch p){
	super(g, p);
        setFriendly(true);
        setMove(3);
	setRange(2);
	setHealth(60);
	setShield(0);
	setDamage(35);
	setMaxHealth(85);
	try{
	    getStates().add(ImageIO.read(new File("Mage.gif")));
	}
	catch(IOException e){}
	
	try{
	    getStates().add(ImageIO.read(new File("Mage2.gif")));
	}
	catch(IOException e){}
	try{
	    setActive(ImageIO.read(new File("Mage-Active.gif")));
	}
	catch(IOException e){}
	try{
	    setActive(ImageIO.read(new File("Mage-Exhausted.gif")));
	}
	catch(IOException e){}
        
    }
    public String toString(){
            return "Mage";
    }

}