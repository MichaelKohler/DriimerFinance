package driimerfinance.gui;

/**
 * Singleton to return the main window. Since the main window does
 * only exist once, we use a Singleton. A singleton creates a new instance if
 * there is none available yet, if one is available it gets returned. Like that we only
 * have one MainWindow for every use ase.
 * 
 * (C) 2014 Driimer Finance
 *
 */
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
			return main; // return the existing instance
		} else {
			main = createMainWindow(); // create a new instance to be returned
			return main;
		}
	}

}
