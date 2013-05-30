import java.awt.Color;
import java.util.ArrayList;
public class Patch{
    private int _xcor, _ycor;
    private int _var1, _var2, _var3; //quick instance variables
    private ArrayList<Object> _vars; //potential to create unlimited instance variables
    private Color _pcolor;
    private Turtle _turtle;
    private Grid _grid;
    private String _plabel;
    public Patch(Grid grid, int xcor, int ycor){
	_grid = grid;
	_xcor = xcor;
	_ycor = ycor;
	_plabel = "X";
	_vars = new ArrayList();
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
	return _plabel;
    }
}
    
		     