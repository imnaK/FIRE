package main;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JList;
import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerContrastIJTheme;

public class Application {
	
	public static final String	APPLICATION_NAME = "FIRE",
								APPLICATION_NAME_EXTENDED = "Find Recursively",
								VERSION = "v0.5 Alpha";
	
	public Frame appFrame;
	public static BufferedImage APPLICATION_ICON;
	public static File selectedSearchDirectory;
	public static final Preferences prefs = Preferences.userNodeForPackage(Application.class);
	public static final String	LAST_OPEN_SEARCH_DIRECTORY = "last_open_search_directory",
								CASE_SENSITIVE_SEARCH = "case_sensitive_search",
								REGULAR_EXPRESSION_SEARCH = "regular_expression_search";
	public static boolean	caseSensitiveSearch,
							regularExpressionSearch,
							inSearchProcess = false;
	
	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		Application.loadUserPreferences();
		loadIcons();
		startFrame();
	}
	
	/**
	 * Loads the last user preferences, saved with the Preference API.
	 */
	public static void loadUserPreferences() {
		Application.selectedSearchDirectory = new File(prefs.get(LAST_OPEN_SEARCH_DIRECTORY, ""));
		Application.caseSensitiveSearch = prefs.getBoolean(Application.CASE_SENSITIVE_SEARCH, false);
		Application.regularExpressionSearch = prefs.getBoolean(Application.REGULAR_EXPRESSION_SEARCH, false);
	}
	
	/**
	 * Saves the current user preferences, saved with the Preference API.
	 * 
	 * @return "true" if success, else "false"
	 */
	public static boolean saveUserPreferences() {
		try {
			prefs.put(LAST_OPEN_SEARCH_DIRECTORY, Application.selectedSearchDirectory.getAbsolutePath());
			prefs.putBoolean(CASE_SENSITIVE_SEARCH, caseSensitiveSearch);
			prefs.putBoolean(REGULAR_EXPRESSION_SEARCH, regularExpressionSearch);
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
			Application.APPLICATION_ICON = loadIcon(new File("logo.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws IOException Throws if specified name and extention
	 * of icon file could not be found.
	 * "src/img/" is the default directory for the search.
	 */
	private BufferedImage loadIcon(File file) throws IOException {
		return ImageIO.read(Application.class.getResource("/img/" + file.getName()));
	}
	
	/**
	 * Sets the Look and Feel of the Frame and starts the Frame.
	 */
	private void startFrame() {
		try {
			// set look and feel
			UIManager.setLookAndFeel(new FlatMaterialDarkerContrastIJTheme());
			
			// create actual frame and show it
			appFrame = new Frame();
			appFrame.setVisible(true);
		} catch (Exception ex) {
			System.err.println("Error creating frame. Most likely a FlatLaf dependency problem.");
		}
	}

	/**
	 * Takes path as root and searchPattern to compare.
	 * Returns a list of the found files recursively.
	 * 
	 * @param path The root path from where
	 * the recursive search should start
	 * @param searchPattern The Search Pattern, eighter searching
	 * with regular expressions, or the path should contain that string
	 * @return String Array with found files
	 */
	public static String[] getRecursiveFileListFromPath(final Path path, final String searchPattern) {
		ArrayList<String> filesFound = new ArrayList<String>();
		
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
				@Override
				public FileVisitResult visitFile(Path currentPath, BasicFileAttributes attrs) throws IOException {
					boolean searchHit = false;
					
					if (Application.regularExpressionSearch) {
						Pattern pattern = Application.caseSensitiveSearch ? Pattern.compile(searchPattern) : Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
						Matcher matcher = pattern.matcher(currentPath.toString());
						searchHit = matcher.find();
					} else {						
						searchHit = Application.caseSensitiveSearch ? currentPath.toString().contains(searchPattern) : currentPath.toString().toLowerCase().contains(searchPattern.toLowerCase());
					}
					if (searchHit)
						filesFound.add(currentPath.toString());
					
					return FileVisitResult.CONTINUE;
				}
				
				@Override
				public FileVisitResult visitFileFailed(Path currentPath, IOException ex1) {
					return FileVisitResult.SKIP_SUBTREE;
				}
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return filesFound.toArray(new String[filesFound.size()]);
	}

	/**
	 * Performs the searching process.
	 * 
	 * TODO extract to thread, so the whole
	 * application wont froze in place and time.
	 * 
	 * @param list List of paths(Strings)
	 * @param searchPattern The Search Pattern, eighter searching
	 * with regular expressions, or the path should contain that string
	 */
	public static void performSearch(JList<String> list, final String searchPattern) {
		Application.inSearchProcess = true;
		
		list.setListData(getRecursiveFileListFromPath(Application.selectedSearchDirectory.toPath(), searchPattern));
		
		Application.inSearchProcess = false;
	}

	/**
	 * This function opens the windows explorer and
	 * selects the file specified.
	 * 
	 * @param file The file to select in the explorer
	 */
	public static void tryOpenInExplorer(File file) {
		try {
			final String[] command = {"explorer.exe", "/select,", file.getAbsolutePath()};
			Runtime.getRuntime().exec(command, null, null);
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}
	}
	
	/**
	 * Opens an item from the list specified in the
	 * default application form the system.
	 * If it errors, it will open the explorer instead
	 * and selectes the item for you.
	 * 
	 * @param list The list of filepaths(Strings) to
	 * get the selected from
	 */
	public static void tryOpenSelectedListItem(JList<String> list) {
		if (list.getModel().getSize() > 1 && !list.isSelectionEmpty()) {	
			File file = new File(list.getSelectedValue());
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException ex1) {
				tryOpenInExplorer(file);
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
