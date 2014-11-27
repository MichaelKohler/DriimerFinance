package driimerfinance.gui;

public class MainWindowSingleton {
	
	static MainWindow main = null;
	
	private static MainWindow createMainWindow() {
		MainWindow main = new MainWindow();
		return main;
	}
	
	

	/**
	 * Returns the singleton's instance.
	 * 
	 * @return singleton with the menu bar
	 */
	public static MainWindow getMainWindowInstance() {
		if (main != null) {
			return main;
		} else {
			main = createMainWindow();
			return main;
		}
	}

}
