package Frame;
// About Frame
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AFrame extends JFrame {

	public AFrame() {
		JLabel authors = new JLabel(
				"<html>Authors :<br> Piotr B�czkiewicz<br> Tomasz Strza�ka<br>Lucjan Koperkiewicz<br><br><br>Year 2013/2014</html>");
		this.add(authors);
	}

}
