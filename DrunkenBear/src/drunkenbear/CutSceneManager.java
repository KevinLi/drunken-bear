package drunkenbear;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
public class CutSceneManager implements MouseListener {
    private ArrayList<CutScene> cutscene1;
    private ArrayList<CutScene> cutscene2;
    private ArrayList<CutScene> currentCutscene;
    private ArrayList<CutScene> pauseScreen;
    private ArrayList<CutScene> mage3;
    private BufferedImage portrait;
    private String dialogue;
    private Render _render;
    private int pos;
    public CutSceneManager(Render render){
        _render = render;
        cutscene1 = new ArrayList();
        cutscene2 = new ArrayList();
        pauseScreen = new ArrayList();
        mage3 = new ArrayList();
        try{
            portrait = (ImageIO.read(new File("Pause Screen.gif")));
        }catch (Exception e) {}
        pauseScreen.add(new CutScene(portrait, null));
        createCutSceneOne();
        createCutSceneTwo();
        createMageSkills();
        
    }
    public void createCutSceneOne(){
        try{
            portrait = (ImageIO.read(new File("CutSceneWarrior.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue = ("Testing, testing, 1 2 3~!");
        cutscene1.add(new CutScene(portrait,dialogue));
        try{
            portrait = (ImageIO.read(new File("CutSceneWarriorHappy.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue = ("Ah, good, it works!");
        cutscene1.add(new CutScene(portrait,dialogue));
        try{
            portrait = (ImageIO.read(new File("CutSceneMageHappy.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue = ("Don't get too happy! This project is still crap.");
        cutscene1.add(new CutScene(portrait,dialogue));
        try{
            portrait = (ImageIO.read(new File("CutSceneWarriorAngry.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue = ("Ah geez, shut up you critic!");
        cutscene1.add(new CutScene(portrait,dialogue));
        try{
            portrait = ImageIO.read(new File("CutSceneMage.gif"));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue=("Just to clarify, dear player. \n There's nothing you can do yet. At all.");
        cutscene1.add(new CutScene(portrait,dialogue));
        
    }
    public void createCutSceneTwo(){
        try{
            portrait = (ImageIO.read(new File("CutSceneWarrior-Surprised.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue = ("Ack! I thought you said nothing would happen!");
        cutscene2.add(new CutScene(portrait,dialogue));
        try{
            portrait=(ImageIO.read(new File("CutSceneMageHappy.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogue=("Ah, did I? Well, my bad.");
        cutscene2.add(new CutScene(portrait,dialogue));
    }
    public void createMageSkills(){
        ImageIcon skill = null;
        try{
            skill=new ImageIcon("MageUltimate.gif");
        }catch (Exception e){}
        mage3.add(new CutScene(skill));
    }
    public void mageSkill3(){
        _render.setCutScene(true);
        _render.getCSDisplay().add(mage3.get(0));
        System.out.println("A");
        mage3.get(0).addMouseListener(this);
        startCutSceneOne();
        System.out.println("B");
        currentCutscene = mage3;
        pos = 0;
    }
    public void startCutSceneOne(){
        _render.setCutScene(true);
        _render.getCSDisplay().add(cutscene1.get(0));
        cutscene1.get(0).addMouseListener(this);
        currentCutscene = cutscene1;
        pos = 0;
    }
    public void pauseScreen(){
        _render.setCutScene(true);
        _render.getCSDisplay().add(pauseScreen.get(0));
        currentCutscene = pauseScreen;
        pauseScreen.get(0).addMouseListener(this);
        pos = 0;
    }
    public void startCutSceneTwo(){
        _render.setCutScene(true);
        _render.getCSDisplay().add(cutscene2.get(0));
        cutscene2.get(0).addMouseListener(this);
        currentCutscene = cutscene2;
        pos = 0;
    }
    public void nextScene(){
        if (pos < currentCutscene.size()){
            _render.getCSDisplay().add(currentCutscene.get(pos));
            currentCutscene.get(pos).addMouseListener(this);
        }
        else{            
            _render.setCutScene(false);
            _render.setPaused(false);
            _render.drawPatches();
            _render.tick();
            pos = 0;
        }
        _render.getCSDisplay().repaint();
    }
    public CutScene getMage(){
        return mage3.get(0);
    }
    public void mousePressed(MouseEvent e){
        //_render.getCSDisplay().remove(currentCutscene.get(pos));
        _render.getCSDisplay().removeAll();
        pos++;
        nextScene();
    }
    
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        _render.getCSDisplay().remove(currentCutscene.get(pos));
        pos++;
        nextScene();
    }
}