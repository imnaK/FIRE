package prv.imnak.fire;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search extends Thread {

	private final Path		path;
	private final String	searchPattern;
	
	public static boolean	isSearching = false,
							isCaseSensitiveSearch = false,
							isRegularExpressionSearch = false,
							isShowFullPath = false,
							filterByDate = false;
	public static long		filterStartDate = 0L,
							filterEndDate = System.currentTimeMillis();
	public static File		selectedSearchDirectory;
	public static DateType	dateType;
	
	public Search(Path path, String searchPattern) {
		this.path = path;
		this.searchPattern = searchPattern;
	}

	public void run() {
		isSearching = true;
		
		Application.appFrame.btnOpenFile.setEnabled(false);
		Application.appFrame.btnOpenInExplorer.setEnabled(false);
		
		Application.appFrame.btnChangeSearchPath.setEnabled(!isSearching);
		Application.appFrame.btnOpenOptionsDialog.setEnabled(!isSearching);
		Application.appFrame.btnStartSearchProcess.setEnabled(!isSearching);
		Application.appFrame.tfSearchForString.setEnabled(!isSearching);
		
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() { 
				@Override
				public FileVisitResult visitFile(Path currentPath, BasicFileAttributes attrs) throws IOException {
					boolean searchHit;
					
					if (isRegularExpressionSearch) {
						Pattern pattern = isCaseSensitiveSearch ? Pattern.compile(searchPattern) : Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
						Matcher matcher = pattern.matcher(currentPath.toString());
						searchHit = matcher.find();
					} else {						
						searchHit = isCaseSensitiveSearch ? currentPath.toString().contains(searchPattern) : currentPath.toString().toLowerCase().contains(searchPattern.toLowerCase());
					}
					
					if (searchHit && filterByDate) {
						BasicFileAttributes attr = Files.readAttributes(currentPath, BasicFileAttributes.class);
						String m;
						switch (dateType) {
							case CREATED: m = attr.creationTime().toString(); break;
							case MODIFIED: m = attr.lastModifiedTime().toString(); break;
							case ACCESSED: m = attr.lastAccessTime().toString(); break;
							
							default: return FileVisitResult.CONTINUE;
						}
						try {
							String[] dateParts = m.split("T")[0].split("-");
							Collections.reverse(Arrays.asList(dateParts));
							long n = Util.DATE_FORMAT.parse(String.join(".", dateParts)).getTime();
							searchHit = filterStartDate <= n && n <= filterEndDate;
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					if (searchHit)
						Application.filesFound.add(new DataItem(currentPath.getParent().toString(), currentPath.getFileName().toString()));
					
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

		Application.loadListData();
		
		isSearching = false;
		Application.appFrame.btnChangeSearchPath.setEnabled(!isSearching);
		Application.appFrame.btnOpenOptionsDialog.setEnabled(!isSearching);
		Application.appFrame.btnStartSearchProcess.setEnabled(!isSearching);
		Application.appFrame.tfSearchForString.setEnabled(!isSearching);
		
		Application.appFrame.tfSearchForString.requestFocus();
	}

}
