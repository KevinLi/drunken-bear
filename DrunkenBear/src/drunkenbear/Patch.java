/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drunkenbear;

import java.awt.Color;
import java.util.ArrayList;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Patch{
    private int _xcor, _ycor;
    private int _var1, _var2, _var3; //quick instance variables
    private ArrayList<Object> _vars; //potential to create unlimited instance variables
    private Color _pcolor;
    private boolean active;
    private boolean attacking;
    private Turtle _turtle;
    private Grid _grid;
    private String _plabel;
    private BufferedImage _default;
    private BufferedImage _active;
    private BufferedImage _attacking;
    public Patch(Grid grid, int xcor, int ycor){
	_grid = grid;
	_xcor = xcor;
	_ycor = ycor;
	_plabel = "X";
	_vars = new ArrayList();
	active = false;
        try{
	    _default = ImageIO.read(new File("Tile.gif"));
            _active = ImageIO.read(new File("ActiveTile.gif"));
            _attacking = ImageIO.read(new File("AttackTile.gif"));
	}catch (Exception e){}
        
    }
    public BufferedImage getImage(){
        if (active)
            return _active;
        else if (attacking)
            return _attacking;
        else
            return _default;
    }
        
    public Turtle getTurtle(){
	return _turtle;
    }
    public Turtle setTurtle(Turtle input){
	Turtle old = _turtle;
	_turtle = input;
	return old;
    }
    public Turtle removeTurtle(){
	return setTurtle(null);
    }


    public void setPColor(Color color){
	_pcolor = color;
    }
    public void setActive(boolean input){
	active = input;
    }
    public boolean getActive(){
        return active;
    }
    public void deactivate(){
	active = false;
    }
    public boolean getAttacking(){
        return attacking;
    }
    public void setAttacking(boolean input){
        attacking = input;
    }
    public Grid getGrid(){
	return _grid;
    }

    public int getX(){
	return _xcor;
    }
    public int getY(){
	return _ycor;
    }
    public Patch getN(){
	return _grid.getPatch(_xcor, _ycor - 1);
    }
    public Patch getS(){
	return _grid.getPatch(_xcor, _ycor + 1);

    }
    public Patch getW(){
	return _grid.getPatch(_xcor - 1, _ycor);
    }
    public Patch getE(){
	return _grid.getPatch(_xcor + 1, _ycor);
    }
    public Patch getNE(){
	return _grid.getPatch(_xcor + 1, _ycor - 1);
    }
    public Patch getNW(){
	return _grid.getPatch(_xcor - 1, _ycor - 1);

    }
    public Patch getSE(){
	return _grid.getPatch(_xcor + 1, _ycor + 1);
    }
    public Patch getSW(){
	return _grid.getPatch(_xcor - 1, _ycor + 1);
    }
    public ArrayList<Patch> getNeighbors(){
	ArrayList<Patch> neighbors = new ArrayList();
	if (!topEdge()){
	    neighbors.add(getN());
	    if (!leftEdge()){
		neighbors.add(getNW());
	    }
	    if (!rightEdge()){
		neighbors.add(getNE());
	    }
	}
	if (!bottomEdge()){
	    neighbors.add(getS());
	    if (!leftEdge()){
		neighbors.add(getSW());
	    }
	    if (!rightEdge()){
		neighbors.add(getSE());
	    }
	}
	if (!leftEdge()){
	    neighbors.add(getW());
	}
	if (!rightEdge()){
	    neighbors.add(getE());
	}
	return neighbors;
    }
    public ArrayList<Patch> getValidNeighbors4(boolean friendly){
        ArrayList<Patch> neighbors = new ArrayList();
        if (!topEdge()){
            if (getN().getTurtle()==null)
                neighbors.add(getN());
            else{
                if (getN().getTurtle().getFriendly()==friendly)
                    neighbors.add(getN());
            }
            
	}
	if (!bottomEdge()){
	    if (getS().getTurtle()==null)
                neighbors.add(getS());
            else{
                if (getS().getTurtle().getFriendly()==friendly)
                    neighbors.add(getS());
            }
	}
	if (!leftEdge()){
	    if (getW().getTurtle()==null)
                neighbors.add(getW());
            else{
                if (getW().getTurtle().getFriendly()==friendly)
                    neighbors.add(getW());
            }
	}
	if (!rightEdge()){
	    if (getE().getTurtle()==null)
                neighbors.add(getE());
            else{
                if (getE().getTurtle().getFriendly()==friendly)
                    neighbors.add(getE());
            }
	}
	return neighbors;
    }
    public ArrayList<Patch> getNeighbors4(){
        ArrayList<Patch> neighbors = new ArrayList();
        if (!topEdge()){
                neighbors.add(getN());
	}
	if (!bottomEdge()){
                neighbors.add(getS());
	}
	if (!leftEdge()){
                neighbors.add(getW());
	}
	if (!rightEdge()){
                neighbors.add(getE());
	}
	return neighbors;
    }
    public ArrayList<Turtle> getTurtles4(boolean friendly){
        ArrayList<Turtle> neighbors = new ArrayList();
        if (!topEdge()){
            if (getN().getTurtle()!=null)
            if (getN().getTurtle().getFriendly()==friendly){
                    neighbors.add(getN().getTurtle());
            }
            
	}
	if (!bottomEdge()){
            if (getS().getTurtle()!=null)
            if (getS().getTurtle().getFriendly()==friendly){
                    neighbors.add(getS().getTurtle());
            }
	}
	if (!leftEdge()){
            if (getW().getTurtle()!=null)
            if (getW().getTurtle().getFriendly()==friendly){
                    neighbors.add(getW().getTurtle());
            }
	}
	if (!rightEdge()){
            if (getE().getTurtle()!=null)
            if (getE().getTurtle().getFriendly()==friendly){
                    neighbors.add(getE().getTurtle());
            }
	}
        return neighbors;
    }
    public boolean topEdge(){
	return _ycor==0;
    }
    public boolean bottomEdge(){
	return _ycor==(_grid.getHeight()-1);
    }
    public boolean leftEdge(){
	return _xcor==0;
    }
    public boolean rightEdge(){
	return _xcor==(_grid.getWidth()-1);
    }
		
    public String getLabel(){
	return _plabel;
    }
    public String setLabel(String plabel){
	String old = _plabel;
	_plabel = plabel;
	return old;
    }
    public String toString(){
        if (_turtle == null){
	return _plabel;
        }
        else
            return "O";
    }
    public double distance(Patch target){
	return Math.sqrt(Math.pow(getX() - target.getX(),2) + 
			 Math.pow(getY() - target.getY(),2));
    }
}
    
		    
