package drunkenbear;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Render extends Canvas implements ActionListener {

    public static String title;
    public static int width;
    public static int height;
    public static int scale;

    public int fps_now;
    public boolean paused;
    public boolean reset;

    //private AtomicBoolean[] _flags;
    private AtomicBoolean locked;
    private AtomicInteger _turn;
    private Grid _grid;
    private int[] _pixels;
    private BufferedImage _background;
    private long _lastTick;
    private String[] _rules;
    private JFrame _frame;
    private JButton pauseButton;
    private Random random = new Random();
    private int _drawState;
    private JPanel _display;
    private ArrayList<Component> images;

    public Render(int width, int height, Grid g) {
	Render.title = "Drunken Bear";
	Render.width = width;
	Render.height = height;
	paused = false;
	reset = false;
	locked = new AtomicBoolean();
	locked.set(false);
	_grid = g;
        images = new ArrayList();
	setFrame();
	scale = 48;
	_lastTick = 0;
	try{
	    _background = ImageIO.read(new File("Background.gif"));
	    System.out.println("Background successfully loaded.");
	}
	catch (IOException e){}
    }
    public void sleep(){
	long since = System.currentTimeMillis() - _lastTick;
	if (since < 1000){
	    try {
		Thread.sleep(1000 - since);
	    }
	    catch (InterruptedException e){
		return;
	    }
	}
	_lastTick = System.currentTimeMillis();
    }
    
    public void setup(){
	spawnTurtle(0,0, "Warrior");

        spawnTurtle(0,1,"Mage");
    }
    //Attempts to create a turtle at the x-y coordinate given
    //returns true if the patch is empty
    public boolean spawnTurtle(int x, int y, String c){
        Class turtleClass = null;
        Turtle newTurtle = null;
        String c1 = "drunkenbear." + c;
        try{
            turtleClass = Class.forName(c1);
        }catch(Throwable e){}
        try{
            newTurtle = (Turtle)turtleClass.getDeclaredConstructor(Grid.class,Patch.class).newInstance(_grid,_grid.getPatch(x,y));
            System.out.println("success!");
        }catch(Throwable e){}
	if (_grid.getPatch(x,y).getTurtle() == null){
	    _grid.getPatch(x,y).setTurtle(newTurtle);
	    return true;
	}
	else
	    return false;
    }
    
    public Render(){
	this(768, 768);
    }
    public Render(int width, int height){
	this(width,height,new Grid(width/32, height/32));
    }
    private void setFrame(){
	JMenuBar menuBar = new JMenuBar();
	pauseButton = new JButton("Pause");
	pauseButton.setActionCommand("pause");
	pauseButton.setFont(new Font("Arial", 0, 12));
	pauseButton.setPreferredSize(new Dimension(90, 0));
	menuBar.add(pauseButton);
	pauseButton.addActionListener(this);
	
	JButton resetButton = new JButton("Reset");
	resetButton.setActionCommand("reset");
	resetButton.setFont(new Font("Arial",0,12));
	menuBar.add(resetButton);
	resetButton.addActionListener(this);
	
	menuBar.setPreferredSize(new Dimension(width*scale, 40));
	setPreferredSize(new Dimension(width * scale, height*scale));
	_frame = new JFrame();
	_frame.setJMenuBar(menuBar);
	_frame.setSize(width,height);
	_frame.setResizable(false);
	_frame.setTitle(title);
	_frame.add(this);
	_frame.add(new LoadImage(_background,0,0));
	_frame.pack();
        _display = new JPanel();
        _display.setSize(width,height);
        _display.setVisible(true);
        _frame.setContentPane(_display);
        //_frame.setLayout(new FlowLayout());
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	_frame.setLocationRelativeTo(null);
	_frame.setVisible(true);
    }
    public void tick(){
	//asks all turtles to perform their action
	if (!locked.get()){
            for (int i = 0; i<images.size(); i++){
                _display.remove(images.get(i));
            }
            System.out.println("Ticking");
	    locked.set(true);
	    for (int i = 0; i < width/scale; i++){
		for (int j = 0; j < height/scale; j++){
                    //repaint(.5,i*16,j*16,16,16)
		    if (_grid.getPatch(i,j).getTurtle()==null){
		    }
		    else{
			System.out.println("Found turtle!");
                        System.out.println(i+","+j);
			_grid.getPatch(i,j).getTurtle().act();
			drawTurtle(_grid.getPatch(i,j).getTurtle());
		    }

		    
		}
	    }
            
            _display.repaint();

	}
        
	locked.set(false);
    }
    public void drawTurtle(Turtle turtle){
        //_display.paintComponent(new TurtleDisplay(turtle,scale));
        //add(turtle.getImage());
        images.add(_display.add(new DisplayTurtle(turtle)));
        images.get(images.size()-1).setLocation(turtle.getX()*scale,turtle.getY()*scale);
    }
    private void update(){
	for (int i = 0; i < width; i++){
	    for (int j = 0; j < height; j++){
	    }
	}
    }
    public void actionPerformed(ActionEvent e){};
    public void run(){};
    public void setGrid(Grid g){
	_grid = g;
    }
}