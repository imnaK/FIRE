package prv.imnak.fire;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;

/*
 * TODO ideas
 * 
 * search options
 * 	text in file
 * 
 * filter options
 *	filter by size
 */

public class Application {
	
	@SuppressWarnings("unused")
	public static String			APPLICATION_NAME = "FIRE",
									APPLICATION_NAME_EXTENDED = "Find Recursively",
									VERSION = "Unknown";
	public static Frame					appFrame;
	public static BufferedImage			APPLICATION_ICON;
	private static final Preferences	prefs = Preferences.userNodeForPackage(Application.class);
	private static final String			LAST_OPEN_SEARCH_DIRECTORY = "last_open_search_directory",
										CASE_SENSITIVE_SEARCH = "case_sensitive_search",
										SHOW_FULL_PATH = "show_full_path",
										REGULAR_EXPRESSION_SEARCH = "regular_expression_search";
	public static ArrayList<DataItem>	filesFound = new ArrayList<>();
	
	public static void main(String[] args) {
		try {
			new Application();
		} catch (IOException ex) {
			//noinspection CallToPrintStackTrace
			ex.printStackTrace();
		}
	}
	
	public Application() throws IOException {

		Properties properties = new Properties();
		properties.load(Application.class.getClassLoader().getResourceAsStream(".properties"));
		VERSION = properties.getProperty("version");

		Application.loadUserPreferences();
		loadIcons();
		startFrame();
	}
	
	public static void loadListData() {
		final String[] paths = new String[Application.filesFound.size()];
		final String[] filenames = new String[Application.filesFound.size()];
		for (int i = 0; i < Application.filesFound.size(); ++i) {
			paths[i] = Application.filesFound.get(i).getPath();
			filenames[i] = Application.filesFound.get(i).getFilename();
		}
		final String[] data = Search.isShowFullPath ? paths : filenames;
		Application.appFrame.listFilesFound.setListData(data);
	}
	
	/**
	 * Loads the last user preferences, saved with the Preference API.
	 */
	public static void loadUserPreferences() {
		Search.selectedSearchDirectory = new File(prefs.get(LAST_OPEN_SEARCH_DIRECTORY, ""));
		Search.isCaseSensitiveSearch = prefs.getBoolean(Application.CASE_SENSITIVE_SEARCH, false);
		Search.isShowFullPath = prefs.getBoolean(Application.SHOW_FULL_PATH, false);
		Search.isRegularExpressionSearch = prefs.getBoolean(Application.REGULAR_EXPRESSION_SEARCH, false);
	}
	
	/**
	 * Saves the current user preferences, saved with the Preference API.
	 * 
	 * @return "true" if success, else "false"
	 */
	@SuppressWarnings("UnusedReturnValue")
	public static boolean saveUserPreferences() {
		try {
			final String last_open_search_directory = Search.selectedSearchDirectory.getAbsolutePath();
			prefs.put(LAST_OPEN_SEARCH_DIRECTORY, last_open_search_directory);
			prefs.putBoolean(CASE_SENSITIVE_SEARCH, Search.isCaseSensitiveSearch);
			prefs.putBoolean(SHOW_FULL_PATH, Search.isShowFullPath);
			prefs.putBoolean(REGULAR_EXPRESSION_SEARCH, Search.isRegularExpressionSearch);
			prefs.flush();
		} catch (BackingStoreException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Loads the Icons. Should run before startFrame()
	 * or else it will throw errors for not finding
	 * icons it needs.
	 */
	private void loadIcons() {
		try {
			Application.APPLICATION_ICON = ImageIO.read(Objects.requireNonNull(Application.class.getClassLoader().getResource("img/logo.png")));
		} catch (IOException ex) {
			//noinspection CallToPrintStackTrace
			ex.printStackTrace();
		}
	}

	/**
	 * Sets the Look and Feel of the Frame and starts the Frame.
	 */
	private void startFrame() {
		FlatLightLaf.setup();
		
		try {
			// set look and feel
			UIManager.setLookAndFeel(new FlatAtomOneDarkIJTheme());
			
			// create actual frame and show it
			Application.appFrame = new Frame();
			Application.appFrame.setVisible(true);
			Application.appFrame.tfSearchForString.requestFocus();
		} catch (Exception ex) {
			//noinspection CallToPrintStackTrace
			ex.printStackTrace();
		}
	}

	/**
	 * Performs the searching process.
	 * @param searchPattern The Search Pattern, either searching
	 * with regular expressions, or the path should contain that string
	 */
	public static void performSearch(final String searchPattern) {
		filesFound.clear();
		Search search = new Search(Search.selectedSearchDirectory.toPath(), searchPattern);
		search.start();
	}

	/**
	 * This function opens the Windows explorer and
	 * selects the file specified.
	 */
	public static void tryOpenInExplorer() {
		File file = new File(Application.filesFound.get(Application.appFrame.listFilesFound.getSelectedIndex()).getParent());
		try {
			Desktop.getDesktop().open(file);
		} catch (IllegalArgumentException | IOException ex) {
			//noinspection CallToPrintStackTrace
			ex.printStackTrace();
		}
	}
	
	/**
	 * Opens an item from the list specified in the
	 * default application form the system.
	 * If it errors, it will open the explorer instead
	 * and selects the item for you.
	 */
	public static void tryOpenSelectedListItem() {
		if (Application.appFrame.listFilesFound.getModel().getSize() > 0 && !Application.appFrame.listFilesFound.isSelectionEmpty()) {
			File file = new File(Application.filesFound.get(Application.appFrame.listFilesFound.getSelectedIndex()).getPath());
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException ex) {
				tryOpenInExplorer();
			}
		}
	}
	
	/**
	 * Saves the user preferences and exits the frame
	 * and application.
	 */
	public static void exitApplication() {
		Application.saveUserPreferences();
		System.exit(0);
	}
}
