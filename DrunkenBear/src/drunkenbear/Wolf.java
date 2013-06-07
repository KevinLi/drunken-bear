package drunkenbear;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
public class Wolf extends Turtle{
    public Wolf(Grid g, Patch p){
	super(g, p);
        setFriendly(false);
        setMove(2);
	setRange(0);
	setHealth(65);
	setShield(0);
	setDamage(18);
	setMaxHealth(65);
        setHoming(true);
        setXP((int)(10+getLevel()*3));
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
    public Wolf(Grid g, Patch p, int level){
        this(g,p);
        setLevel(level);
        setHealth(getHealth()+level*3);
        setDamage(getDamage()+(int)(level*1.8));
        setShield(getShield()+level);
        setXP(getXP()+level*3);
    }
    public String toString(){
            return "Wolf";
    }

}