package drunkenbear;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
public class Slime extends Turtle{
    public Slime(Grid g, Patch p){
	super(g, p);
        setFriendly(false);
        setMove(1);
	setRange(0);
	setHealth(50);
	setShield(0);
	setDamage(15);
	setMaxHealth(30);
        setXP((int)(10+getLevel()/7));
	try{
	    getStates().add(ImageIO.read(new File("Slime.gif")));
	}
	catch(IOException e){}
	
	try{
	    getStates().add(ImageIO.read(new File("Slime2.gif")));
	}
	catch(IOException e){}
	try{
	    setActive(ImageIO.read(new File("Slime-Active.gif")));
	}
	catch(IOException e){}
        try{
	    setExhausted(ImageIO.read(new File("Slime-Exhausted.gif")));
	}
	catch(IOException e){}
	try{
	    setAttacking(ImageIO.read(new File("Slime-Attacking.gif")));
	}
	catch(IOException e){}
        try{
	    setDefending(ImageIO.read(new File("Slime-Defending.gif")));
	}
	catch(IOException e){}
    }
    public String toString(){
            return "Slime";
    }

}