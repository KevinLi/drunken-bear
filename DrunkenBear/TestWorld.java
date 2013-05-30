import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.io.*;
public class TestWorld{
    private Graphics2D g;
    private JFrame _world;
    public TestWorld() {
	_world = new JFrame();
	initUI();
    }
    
    public final void initUI(){
	//java.net.URL img = getClass().getResource("Jumper.gif");
	_world = new JFrame();
	_world.setSize(200,300);
	BufferedImage icon = null;
	try{
	    icon = ImageIO.read(new File("Jumper.gif"));
	}catch (IOException e){
	}
	JPanel panel = new JPanel();
	_world.setLayout(new BorderLayout());
	_world.add(panel);
	_world.add(new LoadImage(icon,0,50));
	panel.setLayout(null);
	panel.setVisible(true);
	_world.setVisible(true);
    }

    public static void main(String[] args) {
	TestWorld world = new TestWorld();
	
    }
}