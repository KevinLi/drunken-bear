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
    private boolean special;
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
    private JPanel _glowDisplay;
    private JPanel _patchDisplay;
    private JPanel _cutsceneDisplay;
    private JLayeredPane lpane;
    private ArrayList<Component> images;
    private Component[][] patches;
    private ArrayList<Patch> activePatches;
    private Turtle activeTurtle;
    private Turtle mouseoverTurtle;
    private ArrayList<String> messages;
    private ArrayList<Patch> enemySpawnPoints;
    private int day;
    private int resources;
    private CutSceneManager cutscenes;
    private ArrayList<Turtle> friendlyTurtles;

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
        _grid.setRender(this);
        images = new ArrayList();
        activePatches = new ArrayList();
        cutscenes = new CutSceneManager(this);
        dayTracker = new JLabel();
        resourceTracker = new JLabel();
        spawnAlly = new JComboBox();
        friendlyTurtles = new ArrayList();
        setFrame();
        scale = 48;
        _lastTick = 0;
        messages = new ArrayList();
        enemySpawnPoints = new ArrayList();
        for (int i = 0; i < width / scale; i++) {
            enemySpawnPoints.add(_grid.getPatch(0, i));
            enemySpawnPoints.add(_grid.getPatch(i, 0));
            enemySpawnPoints.add(_grid.getPatch(width / scale - 1, i));
            enemySpawnPoints.add(_grid.getPatch(i, width / scale - 1));
        }
        day = 1;
        setMessages();
        patches = new Component[width / scale][height / scale];
        try {
            _background = ImageIO.read(new File("Background.gif"));
        } catch (IOException e) {
        }
    }
    public ArrayList<Component> getImages(){
        return images;
    }
    public void setMessages() {
        messages.add("Lead me to victory");
        messages.add("What shall I do next?");
        messages.add("Now what?");
        messages.add("Let's go!");
        messages.add("Let's do this!");

    }

    public boolean getSpecial() {
        return special;
    }

    public void setSpecial(boolean input) {
        special = input;
    }

    public JPanel getDisplay() {
        return _display;
    }

    public JPanel getCSDisplay() {
        return _cutsceneDisplay;
    }

    public CutSceneManager getCSManager() {
        return cutscenes;
    }

    public void setCutScene(boolean foo) {
        cutscene = foo;
    }

    public boolean getCutScene() {
        return cutscene;
    }
    //sleep: 1000ms
    //nap: 50ms
    //powernap: 5ms

    public void sleep() {
        long since = System.currentTimeMillis() - _lastTick;
        if (since < 1000) {
            try {
                Thread.sleep(1000 - since);
            } catch (InterruptedException e) {
                return;
            }
        }
        _lastTick = System.currentTimeMillis();
    }

    public void nap() {
        long since = System.currentTimeMillis() - _lastTick;
        if (since < 50) {
            try {
                Thread.sleep(50 - since);
            } catch (InterruptedException e) {
                return;
            }
        }
        _lastTick = System.currentTimeMillis();
    }

    public void powernap() {
        long since = System.currentTimeMillis() - _lastTick;
        if (since < 5) {
            try {
                Thread.sleep(5 - since);
            } catch (InterruptedException e) {
                return;
            }
        }
        _lastTick = System.currentTimeMillis();
    }

    public void wait(double seconds) {
        double startTime = System.currentTimeMillis();
        while (startTime + 1000 * seconds > System.currentTimeMillis()) {
        }
    }

    public void setup() {
        spawnTurtle(7, 7, "Warrior");
        friendlyTurtles.add(_grid.getPatch(7, 7).getTurtle());
        spawnTurtle(8, 8, "Mage");
        friendlyTurtles.add(_grid.getPatch(8, 8).getTurtle());
        spawnTurtle(7, 8, "Slime");
        //cutscenes.startCutSceneOne();
        _display.repaint();
        drawPatches();
    }

    public Component[][] getPatches() {
        return patches;
    }

    public void setPatches(Component[][] input) {
        patches = input;
    }

    public JPanel getPatchDisplay() {
        return _patchDisplay;
    }

    public void drawPatches() {
        for (int i = 0; i < width / scale; i++) {
            for (int j = 0; j < width / scale; j++) {
                if (patches[i][j] != null) {
                    _patchDisplay.remove(patches[i][j]);
                }
            }
        }
        for (int i = 0; i < width / scale; i++) {
            for (int j = 0; j < width / scale; j++) {
                patches[i][j] = _patchDisplay.add(new DisplayImage(_grid.getPatch(i, j).getImage()));
            }
        }
        for (int i = 0; i < width / scale; i++) {
            for (int j = 0; j < width / scale; j++) {
                patches[i][j].setLocation(i * scale, j * scale);
                if (_grid.getPatch(i, j).getActive() || _grid.getPatch(i, j).getAttacking()) {
                    patches[i][j].addMouseListener(this);
                }
            }
        }
        //_patchDisplay.repaint();
    }

    public boolean spawnTurtle(int x, int y, String c) {
        Class turtleClass = null;
        Turtle newTurtle = null;
        String c1 = "drunkenbear." + c;
        try {
            turtleClass = Class.forName(c1);
        } catch (Throwable e) {
        }
        try {
            newTurtle = (Turtle) turtleClass.getDeclaredConstructor(Grid.class, Patch.class).newInstance(_grid, _grid.getPatch(x, y));
        } catch (Throwable e) {
        }
        if (_grid.getPatch(x, y).getTurtle() == null) {
            _grid.getPatch(x, y).setTurtle(newTurtle);
            return true;
        } else {
            return false;
        }
    }

    public Render() {
        this(768, 768);
    }

    public Render(int width, int height) {
        this(width, height, new Grid(width / 32, height / 32));
    }

    public Frame getFrame() {
        return _frame;
    }

    private void setFrame() {
        JMenuBar menuBar = new JMenuBar();
        pauseButton = new JButton("Pause");
        pauseButton.setActionCommand("paused");
        pauseButton.setFont(new Font("Arial", 0, 12));
        pauseButton.setPreferredSize(new Dimension(90, 0));
        menuBar.add(pauseButton);
        pauseButton.addActionListener(this);

        JButton resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.setFont(new Font("Arial", 0, 12));
        menuBar.add(resetButton);
        resetButton.addActionListener(this);

        endTurn = new JButton("End Turn");
        endTurn.setActionCommand("endturn");
        endTurn.setFont(new Font("Arial", 0, 12));
        endTurn.setPreferredSize(new Dimension(90, 0));
        menuBar.add(endTurn);
        endTurn.addActionListener(this);

        menuBar.setPreferredSize(new Dimension(width * scale, 40));
        setPreferredSize(new Dimension(width * scale, height * scale));
        _frame = new JFrame();
        _frame.setJMenuBar(menuBar);
        _frame.setSize(width, height);
        _frame.add(new LoadImage(_background, 0, 0));
        _frame.setResizable(false);
        _frame.setTitle(title);
        _frame.add(this);
        _display = new JPanel();
        _display.setSize(width, height);
        _display.setVisible(true);
        _display.setOpaque(false);
        _patchDisplay = new JPanel();
        _patchDisplay.setSize(width, height);
        _cutsceneDisplay = new JPanel();
        _cutsceneDisplay.setSize(width, height);
        _cutsceneDisplay.setOpaque(false);
        lpane = new JLayeredPane();
        lpane.setPreferredSize(new Dimension(width, height));
        //lpane.setBounds(0,0,width,height);
        lpane.add(_cutsceneDisplay, new Integer(2), 0);
        lpane.add(_display, new Integer(1), 0);
        lpane.add(_patchDisplay, new Integer(0), 0);
        _frame.add(lpane);
        //_frame.setLayout(new FlowLayout());
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setLocationRelativeTo(null);
        _frame.pack();
        _frame.setVisible(true);
    }

    public void startEnemyPhase() {
        if (day % 5 == 2) {
            spawnEnemies((int) (1 + (int) day / 3));
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
        if (day == 2) {
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
        }
        enemyAction();
        tick();
    }

    public void enemyAction() {
        for (int i = 0; i < width / scale; i++) {
            for (int j = 0; j < height / scale; j++) {
                if (_grid.getPatch(i, j).getTurtle() == null) {
                } else {
                    if (_grid.getPatch(i, j).getTurtle().getFriendly() == false && _grid.getPatch(i, j).getTurtle().getExhausted() == false) {
                        Turtle currentEnemy = _grid.getPatch(i, j).getTurtle();
                        activePatches = currentEnemy.getPatchesInRange(currentEnemy.getMove());
                        ArrayList<Turtle> targets;
                        currentEnemy.activate(true);
                        boolean foo = false;
                        for (int k = 0; k < activePatches.size(); k++) {
                            if (activePatches.get(k).getTurtle() == null) {
                                targets = activePatches.get(k).getTurtles4(true);
                                if (targets.size() > 0) {
                                    currentEnemy.moveTo(activePatches.get(k));
                                    currentEnemy.setAttacking(true);
                                    //targets.get(0).setDefending(true);
                                    tick();
                                    int damageDone = currentEnemy.getDamage() - targets.get(0).getShield();
                                    if (damageDone <= 0) {
                                        damageDone = 1;
                                    }
                                    Object[] options = {"Okay."};
                                    int n = JOptionPane.showOptionDialog(_frame,
                                            "" + currentEnemy.getClass().toString().substring(18) + " deals " + damageDone + " damage to " + targets.get(0).getClass().toString().substring(18),
                                            "Combat Reporter",
                                            JOptionPane.YES_NO_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            options,
                                            options[0]);
                                    targets.get(0).takeDamage(damageDone);
                                    if (targets.get(0).getHealth() <= 0) {
                                        int m = JOptionPane.showOptionDialog(_frame,
                                                "" + targets.get(0).getClass().toString().substring(18) + " has died!",
                                                "Combat Reporter",
                                                JOptionPane.YES_NO_CANCEL_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options,
                                                options[0]);
                                    }
                                    //currentEnemy.setExhausted(true);
                                    currentEnemy.setAttacking(false);
                                    foo = true;
                                    break;
                                }
                            }
                        }
                        if (!foo) {
                            if (currentEnemy.getHoming() == true) {
                                Turtle nearestAlly = null;
                                double recordHolder = 100;
                                for (int m = 0; m < friendlyTurtles.size(); m++) {
                                    if (currentEnemy.getPDistance(friendlyTurtles.get(m)) < recordHolder) {
                                        nearestAlly = friendlyTurtles.get(m);
                                        recordHolder = currentEnemy.getPDistance(friendlyTurtles.get(m));
                                    }
                                }
                                recordHolder = 100;
                                Patch closest = currentEnemy.getPatch();
                                for (int k = 0; k < activePatches.size(); k++) {
                                    if (activePatches.get(k).getPDistance(nearestAlly) < recordHolder && activePatches.get(k).getTurtle() == null) {
                                        closest = activePatches.get(k);
                                        recordHolder = activePatches.get(k).getPDistance(nearestAlly);
                                    }
                                }
                                currentEnemy.moveTo(closest);
                            } else {
                                boolean done = false;
                                int giveUp = 20;
                                while (!done && giveUp > 0) {
                                    int n = (int) (Math.random() * activePatches.size());
                                    if (activePatches.get(n).getTurtle() == null) {
                                        done = true;
                                        currentEnemy.moveTo(activePatches.get(n));
                                        giveUp--;
                                    }
                                }
                            }
                        }
                        currentEnemy.setExhausted(true);
                        drawPatches();
                        currentEnemy.activate(false);
                    }
                }
            }
        }
    }

    public void spawnEnemies(int n) {
        Patch spawnPoint;
        int m = (int) (n * .2);
        int failsafe = 42;
        while (n > 0 && failsafe > 0) {
            spawnPoint = enemySpawnPoints.get((int) (Math.random() * enemySpawnPoints.size()));
            if (spawnTurtle(spawnPoint.getX(), spawnPoint.getY(), "Slime")) {
                n--;
            }
            failsafe--;
        }
        while (m > 0 && failsafe > 0) {
            spawnPoint = enemySpawnPoints.get((int) (Math.random() * enemySpawnPoints.size()));
            if (spawnTurtle(spawnPoint.getX(), spawnPoint.getY(), "Wolf")) {
                m--;
            }
            failsafe--;
        }
    }

    public void tick() {
        //asks all turtles to perform their action
        if (!locked.get() && !cutscene) {
            for (int i = 0; i < images.size(); i++) {
                _display.remove(images.get(i));
            }
            System.out.println("Ticking");
            locked.set(true);
            for (int i = 0; i < width / scale; i++) {
                for (int j = 0; j < height / scale; j++) {
                    if (_grid.getPatch(i, j).getTurtle() == null) {
                    } else {
                        _grid.getPatch(i, j).getTurtle().act();
                        drawTurtle(_grid.getPatch(i, j).getTurtle());
                    }
                }
            }


        }
        locked.set(false);
    }

    public void drawTurtle(Turtle turtle) {
        images.add(_display.add(new DisplayTurtle(turtle)));
        images.get(images.size() - 1).setLocation(turtle.getX() * scale, turtle.getY() * scale);
        if (!special){
            images.get(images.size() - 1).addMouseListener(this);
        }
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public void actionPerformed(ActionEvent e) {
        if ("paused".equals(e.getActionCommand()) && !cutscene) {
            if (paused) {
                paused = false;
                pauseButton.setText("Pause");
            } else {
                paused = true;
                pauseButton.setText("Why Pause?");
                cutscenes.pauseScreen();
            }
        }
        if ("endturn".equals(e.getActionCommand())) {
            for (int i = 0; i < width / scale; i++) {
                for (int j = 0; j < height / scale; j++) {
                    if (_grid.getPatch(i, j).getTurtle() == null) {
                    } else {
                        _grid.getPatch(i, j).getTurtle().setExhausted(false);
                    }
                }
            }
            startEnemyPhase();
            day++;
        }
    }

    ;
    public void run() {
    }

    public void setPaused(boolean input) {
        paused = input;
    }

    ;
    public void setGrid(Grid g) {
        _grid = g;
    }

    public Grid getGrid() {
        return _grid;
    }

    public ArrayList<Patch> getActivePatches() {
        return activePatches;
    }

    public void setActivePatches(ArrayList<Patch> input) {
        activePatches = input;
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        if (e.getComponent() instanceof DisplayTurtle) {
            mouseoverTurtle = _grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY()).getTurtle();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (!special){
        if (e.getComponent() instanceof DisplayTurtle && !moving && !attacking) {
            if (_grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY() / scale).getTurtle().getExhausted() == false
                    && _grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY() / scale).getTurtle().getFriendly() == true) {
                moving = true;
                activeTurtle = _grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY() / scale).getTurtle();
                activeTurtle.activate(true);
                int n = -1;
                if (!activeTurtle.getMoved()) {
                    Object[] options = {"Move",
                        "Attack",
                        "Skill",
                        "Info",
                        "Wait"};
                    n = JOptionPane.showOptionDialog(_frame,
                            messages.get((int) (Math.random() * messages.size())),
                            "Choose Action",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[4]);
                } else {
                    Object[] options = {"Attack",
                        "Skill",
                        "Info",
                        "Wait"};
                    n = JOptionPane.showOptionDialog(_frame,
                            messages.get((int) (Math.random() * messages.size())),
                            "Choose Action",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]);
                    //System.out.println(n);
                    n++;
                }

                if (n == 1) {
                    moving = false;
                    attacking = true;
                    activePatches = activeTurtle.getPatchesInRange(activeTurtle.getRange());
                    for (int i = 0; i < activePatches.size(); i++) {
                        activePatches.get(i).setAttacking(true);
                    }
                    drawPatches();
                } else if (n == -1) {
                    activeTurtle.activate(false);
                    moving = false;
                    attacking = false;
                } else if (n == 0) {
                    activePatches = activeTurtle.getPatchesInRadius(activeTurtle.getMove());
                    for (int i = 0; i < activePatches.size(); i++) {
                        activePatches.get(i).setActive(true);
                    }
                    drawPatches();
                } else if (n == 4) {
                    activeTurtle.setExhausted(true);
                    activeTurtle.activate(false);
                    moving = false;
                    attacking = false;
                } else if (n == 3) {
                    System.out.println("Right");
                    cutscenes.getTurtleInfo(activeTurtle);
                    activeTurtle.activate(false);
                    moving = false;
                    attacking = false;
                } else if (n == 2) {
                    if (activeTurtle.getSkills().size() == 0) {
                        Object[] options = {"Okay",
                            "Not Okay"};
                        int m = JOptionPane.showOptionDialog(_frame,
                                "This unit has no learned skills.",
                                "Error",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[1]);

                        if (m == 1) {
                            activeTurtle.gainXP(1000);
                        }
                        activeTurtle.activate(false);
                        moving = false;
                        attacking = false;
                    } else {
                        Object[] options = new Object[activeTurtle.getSkills().size()];
                        for (int j = 0; j < activeTurtle.getSkills().size(); j++) {
                            options[j] = activeTurtle.getSkills().get(j);
                        }
                        int m = -1;
                        m = JOptionPane.showOptionDialog(_frame,
                                "Choose Skill:",
                                "Error",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[options.length - 1]);
                        if (m == -1) {
                            activeTurtle.activate(false);
                            moving = false;
                            attacking = false;
                        } else if (m == 0) {
                            activeTurtle.useSkillOne(this);
                            moving = false;
                            attacking = false;
                        } else if (m == 1) {
                            activeTurtle.useSkillTwo(this);
                            moving = false;
                            attacking = false;
                        } else {
                            activeTurtle.useSkillThree(this);
                            moving = false;
                            attacking = false;
                        }
                    }
                }

            }
        } else if (e.getComponent() instanceof DisplayImage && moving && !attacking) {
            Patch foo = _grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY() / scale);
            activeTurtle.moveTo(foo);
            activeTurtle.setMoved(true);
            activeTurtle.activate(false);
            for (int i = 0; i < activePatches.size(); i++) {
                activePatches.get(i).setActive(false);
            }
            activePatches.clear();
            moving = false;
            tick();
            drawPatches();
        } else if (e.getComponent() instanceof DisplayImage && !moving && attacking) {
            Turtle target = _grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY() / scale).getTurtle();
            if (target == null || (target.getFriendly() == activeTurtle.getFriendly())) {
            } else {
                activeTurtle.setAttacking(true);
                target.setDefending(true);
                tick();
                Object[] options = {"Okay!"};
                int n = JOptionPane.showOptionDialog(_frame,
                        "" + activeTurtle.getClass().toString() + " deals " + (activeTurtle.getDamage() - target.getShield()) + " damage to " + target.getClass().toString(),
                        "Combat Reporter",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                target.takeDamage(activeTurtle.getDamage());
                if (target.getHealth() <= 0) {
                    int m = JOptionPane.showOptionDialog(_frame,
                            "" + target.getClass().toString().substring(18) + " has died! " + activeTurtle.getClass().toString().substring(18) + " gains " + target.getXP() + " experience!",
                            "Combat Reporter",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    activeTurtle.gainXP(target.getXP());
                }
                activeTurtle.setExhausted(true);
                activeTurtle.setAttacking(false);
                target.setDefending(false);
            }
            activeTurtle.activate(false);
            for (int i = 0; i < activePatches.size(); i++) {
                activePatches.get(i).setAttacking(false);
            }
            attacking = false;
            activePatches.clear();
            tick();
            drawPatches();
        } else if (e.getComponent() instanceof DisplayTurtle && !moving && attacking) {
            Turtle target = _grid.getPatch(e.getComponent().getX() / scale, e.getComponent().getY() / scale).getTurtle();
            if (target == null || target.getFriendly() == activeTurtle.getFriendly()) {
            } else {
                activeTurtle.setAttacking(true);
                target.setDefending(true);
                tick();
                Object[] options = {"Okay!"};
                int n = JOptionPane.showOptionDialog(_frame,
                        "" + activeTurtle.getClass().toString().substring(18) + " deals " + (activeTurtle.getDamage() - target.getShield()) + " damage to " + target.getClass().toString().substring(18),
                        "Combat Reporter",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                target.takeDamage(activeTurtle.getDamage());
                if (target.getHealth() <= 0) {
                    int m = JOptionPane.showOptionDialog(_frame,
                            "" + target.getClass().toString().substring(18) + " has died! " + activeTurtle.getClass().toString().substring(18) + " gains " + target.getXP() + " experience!",
                            "Combat Reporter",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    activeTurtle.gainXP(target.getXP());
                }
                activeTurtle.setExhausted(true);
                activeTurtle.setAttacking(false);
                target.setDefending(false);
            }
            activeTurtle.activate(false);
            for (int i = 0; i < activePatches.size(); i++) {
                activePatches.get(i).setAttacking(false);
            }
            attacking = false;
            activePatches.clear();
            tick();
            drawPatches();
        }
    }
    }
}