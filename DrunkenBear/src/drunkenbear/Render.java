package drunkenbear;

/**
 *
 * @author Jeffrey
 */
import java.util.Random;
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

    public Render(int width, int height, Grid g) {
	Render.title = "Drunken Bear";
	Render.width = width;
	Render.height = height;
	paused = false;
	reset = false;
	locked = new AtomicBoolean();
	locked.set(false);
	_grid = g;
	setFrame();
	scale = 16;
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
    public void setup(){
	//spawnTurtle(5,5, "Warrior");
        //spawnTurtle(0,5,"Mage");
    }
    public Render(){
	this(512, 512);
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
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	_frame.setLocationRelativeTo(null);
	_frame.setVisible(true);
    }
    public void tick(){
	//asks all turtles to perform their action
	if (!locked.get()){
	    System.out.println("Ticking");
	    locked.set(true);
	    _frame.repaint();
	    for (int i = 0; i < width/32; i++){
		for (int j = 0; j < height/32; j++){
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
            System.out.println(_frame.getComponents().length);
	}
        
	locked.set(false);
    }
    public void drawTurtle(Turtle turtle){
	_frame.add(new TurtleDisplay(turtle,scale));
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