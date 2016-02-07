package bm.autoftp.data;

public class RemoteServer {
	private String host;
	private String directory;
	private String user;
	private String password;
	private int port;
	
	public RemoteServer(String address, String directory, String user,String password,int port) {
		this.host = address;
		this.directory = directory;
		this.user = user;
		this.password = password;
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
}
