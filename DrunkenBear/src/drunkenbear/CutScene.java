package drunkenbear;
import java.awt.Font;
import java.awt.image.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.*;
public class CutScene extends JLabel{
    private ImageIcon _sprite;
    private JTextArea _dialogue;
    public CutScene(ImageIcon skill){
        setIcon(skill);
        setVisible(true);
        setOpaque(true);
    }
    public CutScene(Turtle turtle){
        _sprite = new ImageIcon(turtle.getInfoPic());
        setIcon(_sprite);
        String skills = "";
        for (int i = 0; i< turtle.getSkills().size();i++){
            skills+= "\n";
            skills+=turtle.getSkills().get(i);
        }
        if (skills.equals("")) skills = "None";
        String info = ""+turtle.getClass().toString().substring(18)+ "\n" + 
                "Level: " + turtle.getLevel()+ "\n"+
                "Experience: " + turtle.getXP() + "/100" + "\n"+
                "Health: " + turtle.getHealth() + "\n" +
                "Attack: " + turtle.getDamage() + "\n"+
                "Defense: " + turtle.getShield() + "\n" + 
                "Skills: " + skills;
        _dialogue= new JTextArea(info);
        _dialogue.setWrapStyleWord(true);
        _dialogue.setSize(180,500);
        _dialogue.setLineWrap(true);
        _dialogue.setFont(new Font("Arial",0,28));
        _dialogue.setLocation(500,160);
        _dialogue.setOpaque(false);
        _dialogue.setEditable(false);
        _dialogue.setFocusable(false);
        setOpaque(true);
        add(_dialogue);
    }
    public CutScene(BufferedImage sprite, String dialogue){
        _sprite = new ImageIcon(sprite); 
    // setMargin(new Insets(0,0,0,0));
    // setBorderPainted(false);
        //setBorder(null);
        _dialogue = new JTextArea(dialogue);
        _dialogue.setWrapStyleWord(true);
        _dialogue.setSize(700,200);
        _dialogue.setLineWrap(true);
        _dialogue.setFont(new Font("Arial",0,28));
        _dialogue.setLocation(34,580);
        _dialogue.setOpaque(false);
        _dialogue.setEditable(false);
        _dialogue.setFocusable(false);
        setIcon(_sprite);
        setSize(_sprite.getImage().getWidth(null), _sprite.getImage().getHeight(null));
        setOpaque(true);
        add(_dialogue);
  }
}
