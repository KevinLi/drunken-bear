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
        setMove(2);
	setRange(1);
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
	    setExhausted(ImageIO.read(new File("Mage-Exhausted.gif")));
	}
	catch(IOException e){}
        try{
	    setAttacking(ImageIO.read(new File("Mage-Attacking.gif")));
	}
	catch(IOException e){}
	try{
	    setDefending(ImageIO.read(new File("Mage-Defending.gif")));
	}
	catch(IOException e){}
        
    }
    public void learnSkillOne(){
        getSkills().add("Icicle Riser");
    }
    public void learnSkillTwo(){
        getSkills().add("Blood Lightning");
    }
    public void learnSkillThree(){
        getSkills().add("Protometeor");
    }
    public void useSkillOne(Render render){
        
    }
    public void useSkillTwo(Render render){
    }
    public void useSkillThree(Render render){
        render.getCSDisplay();
        int totalXP=0;
        for (int i = 0; i<16;i++){
            for (int j = 0; j<16;j++){
                Turtle currentTurtle = render.getGrid().getPatch(i,j).getTurtle();
                if (currentTurtle != null && currentTurtle.getFriendly()==false){
                    currentTurtle.takeDamage((int)(getDamage()*1.5));
                    if (currentTurtle.getHealth()<=0){
                        totalXP+=currentTurtle.getXP();
                    }
                }
            }
        }
        if (totalXP > 0){}
    }
    public String toString(){
            return "Mage";
    }

}