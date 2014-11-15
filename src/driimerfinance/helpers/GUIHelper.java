package driimerfinance.helpers;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * GUI related helper methods.
 * 
 * (c) 2014 Driimer Finance
 */
public class GUIHelper {
	
	/**
     * Centeres the frame on the user's screen and sets the size of it to a reasonable size.
     * 
     * @param JFrame to be configured
     * @return void
     */
	public static void centerAndSizeFrame(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		frame.setSize(width / 2, height / 2);
		frame.setLocationRelativeTo(null);
	}

	/**
     * Centers a JFrame on the user's screen.
     * 
     * @param JFrame to be centered
     * @return void
     */
	public static void centerFrame(JFrame frame) {
		frame.setLocationRelativeTo(null);
	}
}
