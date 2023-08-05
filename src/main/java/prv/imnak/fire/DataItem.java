package prv.imnak.fire;

public class DataItem {

	private final String	path,
							filename;
	
	public DataItem(String path, String filename) {
		this.path = path;
		this.filename = filename;
	}
	
	public String getParent() {
		return path;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public String getPath() {
		return path + "/" + filename;
	}

}
