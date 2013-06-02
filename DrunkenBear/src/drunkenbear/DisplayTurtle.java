package drunkenbear;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class DisplayTurtle extends JLabel {
    private int xcor;
    private int ycor;
    
  public DisplayTurtle(Turtle turtle) {
    this(new ImageIcon(turtle.getImage()));
  }
  public DisplayTurtle(ImageIcon icon) {
    setIcon(icon);
    // setMargin(new Insets(0,0,0,0));
    setIconTextGap(0);
    // setBorderPainted(false);
    setBorder(null);
    setText(null);
    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
  }

}

