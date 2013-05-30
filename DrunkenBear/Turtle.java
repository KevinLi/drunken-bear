import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
public abstract class Turtle(){
    private Grid _world;
    private Patch _patch;
    private int _direction;
    private Image _sprite;
    public void act();
    public void move();
    public Turtle(Grid g, Patch p){
	_world = g;
	_patch = p;
	_direction = 0;
	
    }
    public int getDirection(){
	return _direction;
    }
    public Patch getPatch(){
	return _patch;
    }
    public Grid getGrid(){
	return _world;
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
	return distance(target.getPatch);
    }
	
}