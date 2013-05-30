import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.*;

public class Render extends Canvas implements Runnable, MouseListener,
        MouseMotionListener, ActionListener {

    public static String title;
    public static int width;
    public static int height;
    public static int scale;

    public int fps_now;
    public boolean paused;
    public boolean reset;

    private AtomicBoolean[] _flags;
    private AtomicInteger _turn;
    private Grid _grid;
    private int[] _pixels;
    private BufferedImage _image;
    private long _lastTick;
    private String[] _rules;
    private JFrame _frame;
    private JButton pauseButton;
    private Random random = new Random();
    private int _drawState;

    public Render(int width, int height, Grid g) {
	addMouseListener(this);
	addMouseMotionListener(this);
	Render.title = "Drunken Bear";
	Render.width = width;
	Render.height = height;
	paused = false;
	reset = false;
	
	_grid = g;
	setFrame();
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
	resetButton.setActionCommant("reset");
	resetButton.setFont(new Font("Arial",0,12));
	menuBar.add(resetButton);
	resetButton.addActionListener(this);
	
	menuBar.setPreferredSize(new Dimension(width*scale, 40));
	setPreferredSize(new Dimension(width * scale, height*scale));
	_frame = new JFrame();
	_frame.setJMenuBar(menuBar);
	_frame.setResizable(false);
	_frame.setTitle(title);
	_frame.add(this);
	_frame.pack();
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	_frame.setLocationRelativeTo(null);
	_frame.setVisible(true);
    }
	    
    public Render(){
	this(256, 256);
    }
    public Render(int width, int height){
	this(width,height,new Grid(width, height));
    }
    private void update(){
	for (int i = 0; i < width; i++){
	    for (int j = 0; j < height; j++){
		draw(i, j, color);
	    }
	}
    }
    /*
    public void run(){
	BufferStrategy bs;
	Graphics g;
	bs = getBufferStrategy();
	if (bs == null){
	    createBufferStrategy(1);
	    return;
	}
	g=bs.getDrawGraphics();
	g.drawImage(_image, 0, getWidth(), getHeight(), null);
	g.dispose();
	bs.show();
    }
    */
    public void setGrid(Grid g){
	_grid = g;
    }
}