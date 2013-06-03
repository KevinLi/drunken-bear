/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ArrayList;
import javax.imageio.ImageIO;
public class CutSceneManager implements MouseListener {
    private ArrayList<BufferedImage> portraits;
    private ArrayList<String> dialogues;
    private ArrayList<CutScene> cutscene1;
    private Render _render;
    private int pos;
    public CutSceneManager(Render render){
        _render = render;
        portraits = new ArrayList();
        dialogues = new ArrayList();
        cutscene1 = new ArrayList();
        try{
            portraits.add(ImageIO.read(new File("CutSceneWarrior.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogues.add("Testing, testing, 1 2 3~!");
        cutscene1.add(new CutScene(portraits.get(0),dialogues.get(0)));
        try{
            portraits.add(ImageIO.read(new File("CutSceneWarriorHappy.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogues.add("Ah, good, it works!");
        cutscene1.add(new CutScene(portraits.get(1),dialogues.get(1)));
        try{
            portraits.add(ImageIO.read(new File("CutSceneMageHappy.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogues.add("Don't get too happy! This project is still crap.");
        cutscene1.add(new CutScene(portraits.get(2),dialogues.get(2)));
        try{
            portraits.add(ImageIO.read(new File("CutSceneWarriorAngry.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogues.add("Ah geez, shut up you critic!");
        cutscene1.add(new CutScene(portraits.get(3),dialogues.get(3)));
        try{
            portraits.add(ImageIO.read(new File("CutSceneMage.gif")));
	}catch (Exception e){System.out.println("Boo!");}
        dialogues.add("Just to clarify, dear player. \n There's nothing you can do yet. At all.");
        cutscene1.add(new CutScene(portraits.get(4),dialogues.get(4)));
    }
    public void startCutSceneOne(){
        _render.setCutScene(true);
        _render.getDisplay().add(cutscene1.get(0));
        cutscene1.get(0).addMouseListener(this);
        pos = 0;
    }
    public void nextScene(){
        if (pos < cutscene1.size()){
            _render.getDisplay().add(cutscene1.get(pos));
            cutscene1.get(pos).addMouseListener(this);
        }
        else{
            _render.setCutScene(false);
            _render.drawPatches();
            
        }
        _render.getDisplay().repaint();
    }
    public void mousePressed(MouseEvent e){
        _render.getDisplay().remove(cutscene1.get(pos));
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
        _render.getDisplay().remove(cutscene1.get(pos));
        pos++;
        nextScene();
    }

            
        
}

