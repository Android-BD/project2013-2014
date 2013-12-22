package Frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import Wifi.Connect;

//Menu Frame
public class MFrame extends JFrame {

	public MFrame(Connect connect) {
		super();

		// -------------------------MenuBar---------------------
		this.setTitle("MultiPlay");
		MenuBar mb = new MenuBar();
		Menu file = new Menu("File");
		Menu help = new Menu("Help");
		Menu options = new Menu("Options");
		mb.add(file);
		mb.add(help);
		mb.add(options);
		MenuItem exit = new MenuItem("Exit");
		MenuItem about = new MenuItem("About");
		MenuItem helps = new MenuItem("Help");
		MenuItem controllers = new MenuItem("Controllers");
		MenuItem connects = new MenuItem("Connect");
		help.add(about);
		help.add(helps);
		options.add(controllers);
		options.add(connects);

		// ----------------------- settings controllers-------------------
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);

		// Connect info
		JPanel kon = new JPanel();
		mainPanel.add(kon);
		JLabel con = new JLabel("Your IP: " + connect.getIP() + "    "
				+ "Port: " + connect.getport());
		kon.add(con);

		// ----------------------------Help Frame----------------------
		helps.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HFrame helpframe = new HFrame();
				helpframe.setSize(500, 500);
				helpframe.setVisible(true);
			}
		});

		// -----------------------About Frame-----------------------
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AFrame helpframe = new AFrame();
				helpframe.setSize(500, 500);
				helpframe.setVisible(true);
			}

		});
		file.add(exit);
		setMenuBar(mb);
		// -----------------------EXIT Frame-----------------------

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JDialog.setDefaultLookAndFeelDecorated(true);
				int response = JOptionPane
						.showConfirmDialog(null, "Do you want to exit?",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
					System.out.println("No button clicked");
				} else if (response == JOptionPane.YES_OPTION) {
					System.out.println("Yes button clicked");
					System.exit(0);
				} else if (response == JOptionPane.CLOSED_OPTION) {
					System.out.println("JOptionPane closed");
				}
			}

		});
		// X Button
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
				int response = JOptionPane
						.showConfirmDialog(null, "Do you want to exit?",
								"Confirm", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {
					System.out.println("No button clicked");
				} else if (response == JOptionPane.YES_OPTION) {
					System.out.println("Yes button clicked");
					System.exit(0);
				} else if (response == JOptionPane.CLOSED_OPTION) {
					System.out.println("JOptionPane closed");
				}
			}
		});

	}
}
