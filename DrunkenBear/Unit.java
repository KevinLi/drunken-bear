import java.util.ArrayList;
public abstract class Unit() implements Turtle{
    private int _movement;
    private int _range;
    private int _health;
    private int _shield;
    private int _maxHealth;
    private ArrayList<Image> _states;
    private int _state;
    private Image _active;
    public int takeDamage(int damage){
	_health -= damage;
	if (_health <= 0){
	    die;
	}
    }
    public void move(){
    }
    public void act(){
	_sprite = _states.get(_state);
	_state++;
	if (_state > _states.size()){
	    _state = 0;
	}
    }
    public int heal(int healing){
	_health += healing;
	if (_health > maxHealth){
	    _health = maxHealth;
	}
    }
}
    