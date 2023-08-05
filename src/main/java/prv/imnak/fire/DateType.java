package prv.imnak.fire;

public enum DateType {
	
	CREATED("Date Created"),
	MODIFIED("Last Modified"),
	ACCESSED("Last Accessed");
	
	private final String text;
	
	private DateType(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

}
