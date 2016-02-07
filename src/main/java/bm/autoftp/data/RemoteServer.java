package bm.autoftp.data;

public class RemoteServer {
	private String address;
	private String directory;
	
	public RemoteServer(String address, String directory) {
		this.address = address;
		this.directory = directory;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	
}
