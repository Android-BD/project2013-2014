package Frame;
// About Frame
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * About Frame
 * @author Piotr B�czkiewicz
 *
 */
public class AFrame extends JFrame {
/**
 * @see AFrame window with information about the authors
 */
	public AFrame() {
		super("About");
		JLabel authors = new JLabel(
				"<html>Authors :<br> Piotr B�czkiewicz<br> Tomasz Strza�ka<br>Lucjan Koperkiewicz<br><br><br>Year 2013/2014</html>");
		this.add(authors);
	}

}
