/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drunkenbear;
import java.util.ArrayList;
import java.awt.image.*;
public abstract class Unit extends Turtle{
    //movement range
    private int _movement;
    public void setMove(int n){
	_movement = n;
    }
    //attack range
    private int _range;
    public void setRange(int n){
	_range = n;
    }
    //current health
    private int _health;
    public void setHealth(int n){
	_health = n;
    }
    //damage-taken modifier
    private int _shield;
    public void setShield(int n){
	_shield = n;
    }
    //base health
    private int _maxHealth;
    public void setMaxHealth(int n){
	_maxHealth = n;
    }
    //damage unit deals on attacks
    private int _damage;
    public void setDamage(int n){
	_damage = n;
    }
    //will cycle through to serve as idle animation
    private ArrayList<BufferedImage> _states;
    public ArrayList<BufferedImage> getStates(){
	return _states;
    }
    //tracker of what image to display
    private int _state;
    //when clicked on, unit will display this pose
    private BufferedImage _active;
    public void setActive(BufferedImage img){
	_active = img;
    }
    //gives it more options
    private boolean active;
    public Unit(Grid g, Patch p){
	super(g, p);
	_states = new ArrayList();
	_state = 0;
	active = false;

    }
    public void takeDamage(int damage){
	_health -= (damage - _shield);
	if (_health <= 0){
	    die();
	}
    }
    public void move(){
	ArrayList<Patch> range = getRange(_range);
	for (int i = 0; i< range.size();i++){
	    range.get(i).setActive();
	}
	
    }
    public void act(){
	System.out.println("Acted");
	if (!active){
	    setImage(_states.get(_state));
	    _state++;
	    if (_state >= _states.size()){
		_state = 0;
	    }
	    System.out.println(_states.size());
	    System.out.println("State: " + _state);
	}
    }
    public void heal(int healing){
	_health += healing;
	if (_health > _maxHealth){
	    _health = _maxHealth;
	}
    }
    public ArrayList<Patch> getRange(int range){
	return null;
    }
}
    