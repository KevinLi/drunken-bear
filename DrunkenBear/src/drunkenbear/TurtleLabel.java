package drunkenbear;

import java.awt.Font;
import java.awt.image.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.*;

public class TurtleLabel extends JLabel {
	private ImageIcon _box;
	private JTextArea _info;

	public TurtleLabel(BufferedImage box, Turtle turtle) {
		_box = new ImageIcon(box);
		_info = new JTextArea();
		_info.setWrapStyleWord(true);
		_info.setSize(700, 200);
		_info.setLineWrap(true);
		_info.setFont(new Font("Arial", 0, 14));
		_info.setLocation(34, 580);
		_info.setOpaque(false);
		_info.setEditable(false);
		_info.setFocusable(false);
		setIcon(_box);
		setSize(_box.getImage().getWidth(null), _box.getImage().getHeight(null));
		String tclass = turtle.getClass().toString();
		String hp = "" + turtle.getHealth() + "/" + turtle.getMaxHealth();
		String defense = "" + turtle.getShield();
		String attack = "" + turtle.getDamage();

	}
}
