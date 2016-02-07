package bm.autoftp.data;

public class TrackedFile {
	
	private String name;
	private String filePath;
	private String lastModified;
	
	public TrackedFile(String name,String filePath, String lastModified) {
		this.name = name;
		this.filePath = filePath;
		this.lastModified = lastModified;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastModified() {
		return lastModified;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	
	
	
}
