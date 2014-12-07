package driimerfinance.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * Close the frame. Can be used for ActionListeners on buttons.
 * 
 * (c) 2014 Driimer Finance
*/
public class FrameCloseAction implements ActionListener {
	JFrame frame = null;
	
	/**
	 * Constructor
	 * 
	 * @param frame to close
	 */
	public FrameCloseAction(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.dispose();
	}
}