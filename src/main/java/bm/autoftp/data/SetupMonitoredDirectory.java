package bm.autoftp.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.jcraft.jsch.ChannelSftp;

import bm.autoftp.main.FileTransfer;

public class SetupMonitoredDirectory {
	
	static ArrayList<TrackedFile> fileList = new ArrayList<TrackedFile>();

	static ChannelSftp channel;
	
	
	public SetupMonitoredDirectory(){
		
	}
	
	public static MonitoredDirectory setup(MonitoredDirectory dir){
		MonitoredDirectory updated = dir;
		
		getAllFiles(dir.getDirectoryPath());
		updated.setFiles(fileList);
		
		return updated;
	}
	
	private static void getAllFiles(String dir){
		 
		
		 File folder = new File(dir);
	     File[] listOfFiles = folder.listFiles();
		
	     for (File file : listOfFiles){
	    	 if(file.isDirectory()){
	    		 
	    		 getAllFiles(file.getAbsolutePath());
	    		 
	    	 }
	    	 else{
	    		 
	    		 String path = (file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf(File.separator)));
	    		 fileList.add(new TrackedFile(file.getName(),path,
	    				 DateFormatter.getInstance().format(file.lastModified())));
	    		 
	    	 }
	     }	
	     
	}
	
	
	
	/*public static void main(String[] args){
		RemoteServer server = new RemoteServer("192.168.2.16", "FTP");
		MonitoredDirectory monDir = new MonitoredDirectory("D:/testdir/",server);
		
		MonitoredDirectory newDir = setup(monDir);
		
	}*/
}
