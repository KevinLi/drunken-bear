package drunkenbear;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.*;
import java.util.HashSet;
public abstract class Turtle{
    private Grid _world;
    private Patch _patch;
    private int _direction;
    private BufferedImage _sprite;
    private Object _item;
    private boolean _friendly;
    private int _health;
    private int _xp;
    private int _level;
    private boolean exhausted;
    private BufferedImage _exhaustSprite;
    public void setExhausted(boolean input){
        exhausted = input;
        if (exhausted) _sprite = _exhaustSprite;
    }
    public void setExhausted(BufferedImage input){
        _exhaustSprite = input;
    }
    public int getLevel(){
        return _level;
    }
    public int getXP(){
        return _xp;
    }
    //for friendly units, xp is used to level up
    //for unfriendly units, xp is how much xp it gives to the killer
    public void setXP(int exp){
        _xp = exp;
    }
    public void gainXP(int exp){
        _xp += exp;
        while (_xp > 100){
            _level++; //ding!
            _xp-=100;
            _health+=20;
            _damage++;
            _shield++;
        }
    }
    public boolean getFriendly(){
        return _friendly;
    }
    public void setFriendly(boolean input){
        _friendly = input;
    }
    public void act(){
	if (!active && !exhausted){
	    setImage(_states.get(_state));
	    _state++;
	    if (_state >= _states.size()){
		_state = 0;
	    }
	}
    }
    public ArrayList<Patch> move(){
        active = true;
        return getPatchesInRadius(getMove());
    }
    public void activate(boolean input){
        active = input;
    }
    public boolean getActive(){
        return active;
    }
    public Turtle(Grid g, Patch p){
	_world = g;
	_patch = p;
	_direction = 0;
        _states = new ArrayList();
	_state = 0;
	active = false;
	_level = 1;
        _xp = 0;
    }
    public BufferedImage getImage(){
        if (!active){
            return _sprite;
        }else return _active;
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
	if (newPatch.getTurtle() == null){
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
    public ArrayList<Patch> getPatchesInRadius(int r){
        ArrayList <Patch> ans = new ArrayList();
        ArrayList <Patch> temp = new ArrayList();
        ans.addAll(getPatch().getNeighbors4());
        while (r > 0){
            for (int i = 0; i<ans.size();i++){
                temp.addAll(ans.get(i).getNeighbors4());
            }
            HashSet hs = new HashSet();
            hs.addAll(temp);
            hs.addAll(ans);
            ans.clear();
            temp.clear();
            ans.addAll(hs);
            r--;
            System.out.println(r);
        }
        return ans;
        
    }
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
    public void setHealth(int n){
	_health = n;
    }
    public int getHealth(){
        return _health;
    }
    //damage-taken modifier
    private int _shield;
    public void setShield(int n){
	_shield = n;
    }
    public int getShield(){
        return _shield;
    }
    //base health
    private int _maxHealth;
    public void setMaxHealth(int n){
	_maxHealth = n;
    }
    public int getMaxHealth(){
        return _maxHealth;
    }
    //damage unit deals on attacks
    private int _damage;
    public void setDamage(int n){
	_damage = n;
    }
    public int getDamage(){
        return _damage;
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
    private boolean active;
    public void takeDamage(int damage){
	_health -= (damage - _shield);
	if (_health <= 0){
	    die();
	}
    }
    public void heal(int healing){
	_health += healing;
	if (_health > _maxHealth){
	    _health = _maxHealth;
	}
    }
    public int getRange(){
	return _range;
    }
    public int getMove(){
	return _movement;
    }
}