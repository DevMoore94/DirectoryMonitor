package bm.autoftp.data;

import java.util.ArrayList;

public class MonitoredDirectory {

	private String directoryPath;
	private ArrayList<TrackedFile> trackedFiles;
	private RemoteServer remoteServer;

	public MonitoredDirectory(String directoryPath, RemoteServer remoteServer) {
		this.directoryPath = directoryPath;
		trackedFiles = new ArrayList<TrackedFile>();
		this.remoteServer = remoteServer;
	}

	public String getDirectoryPath() {
		return directoryPath;
	}

	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	public ArrayList<TrackedFile> getFiles() {
		return trackedFiles;
	}

	public void setFiles(ArrayList<TrackedFile> trackedFiles) {
		this.trackedFiles = trackedFiles;
	}
	
	public void addFile(TrackedFile trackedFile){
		this.trackedFiles.add(trackedFile);
	}
	
	public RemoteServer getRemoteServer() {
		return remoteServer;
	}

	public void setRemoteServer(RemoteServer remoteServer) {
		this.remoteServer = remoteServer;
	}

}
