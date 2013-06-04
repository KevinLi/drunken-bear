package drunkenbear;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

public class TurtleDisplay extends Component {
	private Turtle _turtle;
	private int xcor;
	private int ycor;
	private int _scale;
	private BufferedImage _img;

	public TurtleDisplay(Turtle turtle, int scale) {
		_turtle = turtle;
		xcor = turtle.getX();
		ycor = turtle.getY();
		_scale = scale;
		_img = _turtle.getImage();
	}

	public void paint(Graphics g) {
		g.drawImage(_img, xcor * _scale, ycor * _scale, null);
	}

	public Dimension getPreferredSize() {
		if (_turtle.getImage() == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(32, 32);
		}
	}
}
