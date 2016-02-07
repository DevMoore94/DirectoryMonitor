package bm.autoftp.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.jcraft.jsch.*;

import bm.autoftp.data.*;

public class FileTransfer {

	private MonitoredDirectory dir;
	private RemoteServer serv;

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	private String SFTPWORKINGDIR = "FTP/testdir";
	private SimpleDateFormat sdf = DateFormatter.getInstance();

	public FileTransfer(MonitoredDirectory dir, RemoteServer serv) 
	{
		this.dir = dir;
		this.serv = serv;

	}

	public FileTransfer() 
	{

	}

	public String getSFTPWORKINGDIR() {
		return SFTPWORKINGDIR;
	}

	public void setSFTPWORKINGDIR(String sFTPWORKINGDIR) {
		SFTPWORKINGDIR = sFTPWORKINGDIR;
	}

	public MonitoredDirectory getDir() {
		return dir;
	}

	public void setDir(MonitoredDirectory dir) {
		this.dir = dir;
	}

	public ChannelSftp getConnection() {
		try {
			JSch jsch = new JSch();

			session = jsch.getSession(serv.getUser(), serv.getHost(), serv.getPort());
			session.setPassword(serv.getPassword());

			java.util.Properties config = new java.util.Properties();

			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();

			channel = session.openChannel("sftp");
			channel.connect();

			System.out.println("Connected!");

			channelSftp = (ChannelSftp) channel;

		} catch (JSchException e) {
			System.out.println(e.getMessage());
		}

		return channelSftp;

	}

	public void createRemoteDirectory(String localDir, String remoteDir) {
		ChannelSftp channel = getConnection();
		try {
			File folder = new File(localDir);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {

				if (file.isDirectory()) {
					channel.mkdir(remoteDir + "/" + file.getName());
					channel.chmod(777, remoteDir + "/" + file.getName());
					createRemoteDirectory(file.getAbsolutePath(), remoteDir + "/" + file.getName());

				} else {
					channel.put(new FileInputStream(file), remoteDir + "/" + file.getName());

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean checkForChanges(TrackedFile trackedFile) {

		File newFile = new File(trackedFile.getFilePath() + "/" + trackedFile.getName());

		if (!sdf.format(newFile.lastModified()).equalsIgnoreCase(trackedFile.getLastModified())) {
			System.out.println(trackedFile.getName() + " Changed.");
			trackedFile.setLastModified(sdf.format(newFile.lastModified()));
			return true;

		} else {

			return false;
		}
	}

	public boolean transfer(TrackedFile file) {
		ChannelSftp channel = getConnection();
		try {
			channel.cd(SFTPWORKINGDIR);
			File f = new File(file.getFilePath(), file.getName());
			String path = f.getPath();
			System.out.println(channel.pwd());
			channel.put(new FileInputStream(f), (f.getName()));
			
			channel.disconnect();
			return true;
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public void monitor() {

		System.out.println("RUNNING......");
		try {
			while (true) {

				for (TrackedFile file : dir.getFiles()) {
					if (this.checkForChanges(file)) {
						// Transfer the file via Apache FTP
						if (transfer(file)) {
							System.out.println(file.getName() + " has been updated on remote directory.");
						} else {
							System.out.println(file.getName() + " has failed to update on remote directory.");
						}
					}
				}
				Thread.sleep(10);
			}
		} catch (InterruptedException e) {
			System.out.print(e.getMessage());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

}
