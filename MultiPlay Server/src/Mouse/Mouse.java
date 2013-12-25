package Mouse;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class Mouse {

	public void run(int x, int y) {
		try {
			Robot robot = new Robot();
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x1 = (int) b.getX();
			int y1 = (int) b.getY();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			if (!(x1 + x > d.getWidth()))
				robot.mouseMove(x1 + x, y1);
			if (!(y1 + y > d.getHeight()))
				robot.mouseMove(x1, y1 + y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void click(int pm) {
		try {
			Robot robot = new Robot();
			switch (pm) {
			case (1):
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				break;
			case (2):
				robot.mousePress(InputEvent.BUTTON2_MASK);
				robot.mouseRelease(InputEvent.BUTTON2_MASK);
				break;
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
