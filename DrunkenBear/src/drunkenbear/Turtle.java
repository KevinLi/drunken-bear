/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drunkenbear;

/**
 *
 * @author Jeffrey
 */
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
public abstract class Turtle{
    private Grid _world;
    private Patch _patch;
    private int _direction;
    private BufferedImage _sprite;
    public void act(){};
    public void move(){};
    public Turtle(Grid g, Patch p){
	_world = g;
	_patch = p;
	_direction = 0;
	
    }
    
    public BufferedImage getImage(){
	return _sprite;
    }
    public void setImage(BufferedImage img){
	_sprite = img;
    }
    public int getDirection(){
	return _direction;
    }
    public int getX(){
	return getPatch().getX();
    }
    public int getY(){
	return getPatch().getY();
    }
    public Patch getPatch(){
	return _patch;
    }
    public Grid getGrid(){
	return _world;
    }
    public void moveTo(int x, int y){
	moveTo(_world.getPatch(x,y));
    }
    public void die(){
	_patch.removeTurtle();
    }
    public void moveTo(Patch newPatch){
	if (newPatch.getTurtle().equals(null)){
	    _patch.removeTurtle();
	    _patch = newPatch;
	//does this overwrite _patch, or cause the patch at _patch to become a
	//copy of newPatch?
	    _patch.setTurtle(this);
	}
    } 
    public double distance(Patch target){
	return Math.sqrt(Math.pow(getPatch().getX() - target.getX(),2) + 
			 Math.pow(getPatch().getY() - target.getY(),2));
    }
    public double distance(Turtle target){
	return distance(target.getPatch());
    }
	
}