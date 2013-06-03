/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

        add(_dialogue);
  }
}
