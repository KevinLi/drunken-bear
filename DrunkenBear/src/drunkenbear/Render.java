package drunkenbear;

import drunkenbear.*;

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


public class Render extends Canvas implements ActionListener, MouseListener {

    public static String title;
    public static int width;
    public static int height;
    public static int scale;

    public int fps_now;
    public boolean paused;
    public boolean reset;
    public boolean cutscene;
    public boolean moving;
    private boolean attacking;

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
    private JButton endTurn;
    private JLabel dayTracker;
    private JLabel resourceTracker;
    private JComboBox spawnAlly;
    private Random random = new Random();
    private int _drawState;
    private JPanel _display;
    private JPanel _patchDisplay;
    private JLayeredPane lpane;
    private ArrayList<Component> images;
    private Component[][] patches;
    private ArrayList<Patch> activePatches;
    private Turtle activeTurtle;
    private Turtle mouseoverTurtle;
    private ArrayList<String> messages;
    private ArrayList<Turtle> enemies;
    private ArrayList<Patch> enemySpawnPoints;
    private int day;
    private int resources;
    private CutSceneManager cutscenes;

    
    public Render(int width, int height, Grid g) {
	Render.title = "Drunken Bear";
	Render.width = width;
	Render.height = height;
        cutscene = false;
	paused = false;
        moving = false;
        attacking = false;
	reset = false;
	locked = new AtomicBoolean();
	locked.set(false);
	_grid = g;
        images = new ArrayList();
        activePatches = new ArrayList();
        cutscenes = new CutSceneManager(this);
        dayTracker = new JLabel();
        resourceTracker = new JLabel();
        spawnAlly = new JComboBox();
	setFrame();
	scale = 48;
	_lastTick = 0;
        enemies = new ArrayList();
        messages = new ArrayList();
        enemySpawnPoints = new ArrayList();
        for (int i =0; i<width/scale; i++){
            enemySpawnPoints.add(_grid.getPatch(0,i));
            enemySpawnPoints.add(_grid.getPatch(i,0));
            enemySpawnPoints.add(_grid.getPatch(width/scale - 1,i));
            enemySpawnPoints.add(_grid.getPatch(i,width/scale-1));
        }
        day = 1;
        setMessages();
        patches = new Component[width/scale][height/scale];
	try{
	    _background = ImageIO.read(new File("Background.gif"));
	}
	catch (IOException e){}
    }
    public void setMessages(){
        messages.add("Lead me to victory");
        messages.add("What shall I do next?");
        messages.add("Now what?");
        messages.add("Let's go!");
        messages.add("Let's do this!");

    }
    public JPanel getDisplay(){
        return _display;
    }
    public void setCutScene(boolean foo){
        cutscene = foo;
    }
    //sleep: 1000ms
    //nap: 50ms
    //powernap: 5ms
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
    public void nap(){
	long since = System.currentTimeMillis() - _lastTick;
	if (since < 50){
	    try {
		Thread.sleep(50 - since);
	    }
	    catch (InterruptedException e){
		return;
	    }
	}
	_lastTick = System.currentTimeMillis();
    }
    public void powernap(){
        long since = System.currentTimeMillis() - _lastTick;
	if (since < 5){
	    try {
		Thread.sleep(5 - since);
	    }
	    catch (InterruptedException e){
		return;
	    }
	}
	_lastTick = System.currentTimeMillis();
    }
    public void setup(){
	spawnTurtle(7,7, "Warrior");
        spawnTurtle(8,8,"Mage");
        spawnTurtle(7,8,"Slime");
        cutscenes.startCutSceneOne();
        _display.repaint();
        drawPatches();
    }
    public void drawPatches(){
        for (int i = 0; i < width/scale; i++){
            for (int j = 0; j< width/scale;j++){
                    if (patches[i][j]!=null)
                        _patchDisplay.remove(patches[i][j]);
            }
        }
        for (int i = 0; i < width/scale; i++){
            for (int j = 0; j< width/scale;j++){
                    patches[i][j]=_patchDisplay.add(new DisplayImage(_grid.getPatch(i,j).getImage()));
            }
        }
        for (int i = 0; i < width/scale; i++){
            for (int j = 0; j< width/scale;j++){
                    patches[i][j].setLocation(i*scale,j*scale);
                    if (_grid.getPatch(i,j).getActive()||_grid.getPatch(i,j).getAttacking()){
                        patches[i][j].addMouseListener(this);
                    }
            }
        }
        //_patchDisplay.repaint();
    }
    public boolean spawnTurtle(int x, int y, String c){
        Class turtleClass = null;
        Turtle newTurtle = null;
        String c1 = "drunkenbear." + c;
        try{
            turtleClass = Class.forName(c1);
        }catch(Throwable e){}
        try{
            newTurtle = (Turtle)turtleClass.getDeclaredConstructor(Grid.class,Patch.class).newInstance(_grid,_grid.getPatch(x,y));
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
	pauseButton.setActionCommand("paused");
	pauseButton.setFont(new Font("Arial", 0, 12));
	pauseButton.setPreferredSize(new Dimension(90, 0));
	menuBar.add(pauseButton);
	pauseButton.addActionListener(this);
	
	JButton resetButton = new JButton("Reset");
	resetButton.setActionCommand("reset");
	resetButton.setFont(new Font("Arial",0,12));
	menuBar.add(resetButton);
	resetButton.addActionListener(this);
	
        endTurn = new JButton("End Turn");
        endTurn.setActionCommand("endturn");
        endTurn.setFont(new Font("Arial", 0, 12));
	endTurn.setPreferredSize(new Dimension(90, 0));
	menuBar.add(endTurn);
	endTurn.addActionListener(this);
        
        menuBar.setPreferredSize(new Dimension(width*scale, 40));
	setPreferredSize(new Dimension(width * scale, height*scale));
	_frame = new JFrame();
	_frame.setJMenuBar(menuBar);
	_frame.setSize(width,height);
        _frame.add(new LoadImage(_background,0,0));
	_frame.setResizable(false);
	_frame.setTitle(title);
	_frame.add(this);
        _display = new JPanel();
        _display.setSize(width,height);
        _display.setVisible(true);
        _display.setOpaque(false);
        _patchDisplay = new JPanel();
        _patchDisplay.setSize(width,height);
        lpane = new JLayeredPane();
        lpane.setPreferredSize(new Dimension(width,height));
        //lpane.setBounds(0,0,width,height);
        lpane.add(_display,new Integer(1),0);
        lpane.add(_patchDisplay,new Integer(0),0);
        _frame.add(lpane);
        //_frame.setLayout(new FlowLayout());
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	_frame.setLocationRelativeTo(null);
	_frame.pack();
        _frame.setVisible(true);
    }
    public void startEnemyPhase(){
        if (day%3==0){
            spawnEnemies((int) (1 + day/3));
            Object[] options = {"Okay"};
                    int n = JOptionPane.showOptionDialog(_frame,
                    "Slimes have appeared!",
                    "Enemy Reporter",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
        }
        if (day == 3){
            enemies.get(0).activate(true);
            enemies.get(1).activate(false);
            Object[] options = {"Panick!"};
                    int n = JOptionPane.showOptionDialog(_frame,
                    "Slimes are looking at Warrior hungrily!",
                    "Enemy Reporter",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            
            cutscenes.startCutSceneTwo();
            enemies.get(0).activate(true);
            enemies.get(1).activate(false);
        }
        enemyAction();
        tick();
    }
    public void enemyAction(){
        for (int i = 0; i<enemies.size();i++){
            Turtle currentEnemy = enemies.get(i);
            activePatches = currentEnemy.getPatchesInRange(currentEnemy.getMove());
            ArrayList<Turtle> targets;
            currentEnemy.activate(true);
            boolean foo = false;
            for (int j = 0; j < activePatches.size();j++){
                if (activePatches.get(j).getTurtle()==null){
                    System.out.println("foo");
                    targets = activePatches.get(j).getTurtles4(true);
                    if (targets.size()>0){
                        currentEnemy.moveTo(activePatches.get(j));
                        currentEnemy.setAttacking(true);
                        //targets.get(0).setDefending(true);
                        tick();
                        Object[] options = {"Okay!"};
                            int n = JOptionPane.showOptionDialog(_frame,
                            ""+currentEnemy.getClass().toString()+" deals "+(currentEnemy.getDamage()-targets.get(0).getShield())+" damage to "+targets.get(0).getClass().toString(),
                            "Combat Reporter",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                        targets.get(0).takeDamage(currentEnemy.getDamage());
                       //currentEnemy.setExhausted(true);
                       currentEnemy.setAttacking(false);
                       foo = true;
                       break;
                       //targets.get(0).setDefending(false);
                    }
                    
                }
            }
            if (!foo){
                Patch closest = currentEnemy.getPatch();
                double recordHolder=100;
                for (int j = 0; j < activePatches.size();j++){
                    if (activePatches.get(j).distance(_grid.getPatch(7,7))<recordHolder && activePatches.get(j).getTurtle()==null){
                        closest = activePatches.get(j);
                        recordHolder = activePatches.get(j).distance(_grid.getPatch(7,7));
                    }
                }
                currentEnemy.moveTo(closest);
            }
            drawPatches();
            currentEnemy.activate(false);
        }
    }
    public void spawnEnemies(int n){
        Patch spawnPoint;
        int failsafe = 42;
        while (n > 0 && failsafe > 0){
            spawnPoint= enemySpawnPoints.get((int) (Math.random()*enemySpawnPoints.size()));
            if (spawnTurtle(spawnPoint.getX(),spawnPoint.getY(),"Slime")){
                enemies.add(spawnPoint.getTurtle());
                n--;
            }
            failsafe--;
        }
    }
    public void tick(){
	//asks all turtles to perform their action
	if (!locked.get()&& !cutscene){
            for (int i = 0; i<images.size(); i++){
                _display.remove(images.get(i));
            }
            System.out.println("Ticking");
	    locked.set(true);
	    for (int i = 0; i < width/scale; i++){
		for (int j = 0; j < height/scale; j++){
		    if (_grid.getPatch(i,j).getTurtle()==null){
		    }
		    else{
			_grid.getPatch(i,j).getTurtle().act();
			drawTurtle(_grid.getPatch(i,j).getTurtle());
		    }
		}
	    }
            

	}
	locked.set(false);
    }
    public void drawTurtle(Turtle turtle){
        //_display.paintComponent(new TurtleDisplay(turtle,scale));
        //add(turtle.getImage());
        images.add(_display.add(new DisplayTurtle(turtle)));
        images.get(images.size()-1).setLocation(turtle.getX()*scale,turtle.getY()*scale);
        images.get(images.size()-1).addMouseListener(this);
    }
    public void actionPerformed(ActionEvent e){
        startEnemyPhase();
        if ("paused".equals(e.getActionCommand())){
            if (paused){
                paused = false;
                pauseButton.setText("Pause");
            } else{
                paused = true;
                pauseButton.setText("Unpause");
            }
        }
        if ("endturn".equals(e.getActionCommand())){
            for (int i = 0; i < width/scale; i++){
		for (int j = 0; j < height/scale; j++){
		    if (_grid.getPatch(i,j).getTurtle()==null){
		    }
		    else{
			_grid.getPatch(i,j).getTurtle().setExhausted(false);
		    }
		}
	    }
        }
        day++;
    };
    public void run(){};
    public void setGrid(Grid g){
	_grid = g;
    }
    public void mousePressed(MouseEvent e){
        
    }
    
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getComponent() instanceof DisplayTurtle){
            mouseoverTurtle = _grid.getPatch(e.getComponent().getX()/scale,e.getComponent().getY()).getTurtle();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
       if (e.getComponent() instanceof DisplayTurtle && !moving && !attacking){
           if (_grid.getPatch(e.getComponent().getX()/scale, e.getComponent().getY()/scale).getTurtle().getExhausted() == false &&
                   _grid.getPatch(e.getComponent().getX()/scale, e.getComponent().getY()/scale).getTurtle().getFriendly() == true){
               moving = true;
               activeTurtle = _grid.getPatch(e.getComponent().getX()/scale, e.getComponent().getY()/scale).getTurtle();
               activeTurtle.activate(true);
               int n = -1;
               if (!activeTurtle.getMoved()){
                    Object[] options = {"Move",
                    "Attack",
                    "Wait"};
                    n = JOptionPane.showOptionDialog(_frame,
                    messages.get((int)(Math.random()*messages.size())),
                    "Choose Action",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
               }else{
                   Object[] options = {"Attack",
                    "Wait"};
                    n = JOptionPane.showOptionDialog(_frame,
                    messages.get((int)(Math.random()*messages.size())),
                    "Choose Action",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
                    //System.out.println(n);
                    if (n==0) n=1;
                    else if (n==1) n=2;
               }
               
               if (n==1){
                   moving = false;
                   attacking = true;
                   activePatches = activeTurtle.getPatchesInRange(activeTurtle.getRange());
                   for (int i = 0; i<activePatches.size();i++){
                       activePatches.get(i).setAttacking(true);
                   }
                   drawPatches();
               } else if (n == -1){
                   activeTurtle.activate(false);
                   moving = false;
                   attacking = false;
               } else if (n == 0){
                    activePatches = activeTurtle.getPatchesInRadius(activeTurtle.getMove());
                    for (int i = 0; i < activePatches.size();i++){
                        activePatches.get(i).setActive(true);
                    }
                    drawPatches();
               } else if (n == 2){
                   activeTurtle.setExhausted(true);
                   activeTurtle.activate(false);
                   moving = false;
                   attacking = false;
               }
               
           }
       }
       else if (e.getComponent() instanceof DisplayImage && moving && !attacking){
           Patch foo = _grid.getPatch(e.getComponent().getX()/scale,e.getComponent().getY()/scale);
           System.out.println(foo.getX()+ ", " + foo.getY());
           activeTurtle.moveTo(foo);
           activeTurtle.setMoved(true);
           activeTurtle.activate(false);
           for (int i = 0; i<activePatches.size();i++){
               activePatches.get(i).setActive(false);
           }
           activePatches.clear();
           moving = false;
           tick();
           drawPatches();
       }
       else if (e.getComponent() instanceof DisplayImage && !moving && attacking){
           Turtle target = _grid.getPatch(e.getComponent().getX()/scale,e.getComponent().getY()/scale).getTurtle();
           if (target == null || (target.getFriendly() == activeTurtle.getFriendly())){
           }
           else{
               activeTurtle.setAttacking(true);
               target.setDefending(true);
               tick();
               Object[] options = {"Okay!"};
                    int n = JOptionPane.showOptionDialog(_frame,
                    ""+activeTurtle.getClass().toString()+" deals "+(activeTurtle.getDamage()-target.getShield())+" damage to "+target.getClass().toString(),
                    "Combat Reporter",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
               target.takeDamage(activeTurtle.getDamage());
               activeTurtle.setExhausted(true);
               activeTurtle.setAttacking(false);
               target.setDefending(false);
           }
           activeTurtle.activate(false);
           for (int i = 0; i<activePatches.size();i++){
               activePatches.get(i).setAttacking(false);
           }
           attacking = false;
           activePatches.clear();
           tick();
           drawPatches();
       }
       else if (e.getComponent() instanceof DisplayTurtle && !moving && attacking){
           Turtle target = _grid.getPatch(e.getComponent().getX()/scale,e.getComponent().getY()/scale).getTurtle();
           if (target == null || target.getFriendly() == activeTurtle.getFriendly()){
           }
           else{
               activeTurtle.setAttacking(true);
               target.setDefending(true);
               tick();
               Object[] options = {"Okay!"};
                    int n = JOptionPane.showOptionDialog(_frame,
                    ""+activeTurtle.getClass().toString()+" deals "+(activeTurtle.getDamage()-target.getShield())+" damage to "+target.getClass().toString(),
                    "Combat Reporter",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
               target.takeDamage(activeTurtle.getDamage());
               activeTurtle.setExhausted(true);
               activeTurtle.setAttacking(false);
               target.setDefending(false);
           }
           activeTurtle.activate(false);
           for (int i = 0; i<activePatches.size();i++){
               activePatches.get(i).setAttacking(false);
           }
           attacking = false;
           activePatches.clear();
           tick();
           drawPatches();
       }
       
    }
}