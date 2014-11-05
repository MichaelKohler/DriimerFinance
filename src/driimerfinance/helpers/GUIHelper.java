package driimerfinance.helpers;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * ...
 * 
 * (c) 2014 Driimer Finance
 */
public class GUIHelper {
	public static void centerAndSizeFrame(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		frame.setSize(width / 2, height / 2);
		frame.setLocationRelativeTo(null);
	}

	public static void centerFrame(JFrame frame) {
		frame.setLocationRelativeTo(null);
	}
}
