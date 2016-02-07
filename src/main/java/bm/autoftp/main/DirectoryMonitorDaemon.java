package bm.autoftp.main;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

import bm.autoftp.data.MonitoredDirectory;
import bm.autoftp.data.RemoteServer;
import bm.autoftp.data.SetupMonitoredDirectory;
import bm.autoftp.data.TrackedFile;

public class DirectoryMonitorDaemon implements Daemon {

	private Thread myThread;
	private boolean stopped = false;
	private boolean lastOneWasATick = false;

	public void init(DaemonContext daemonContext) throws DaemonInitException, Exception {
		/*
		 * Construct objects and initialize variables here. You can access the
		 * command line arguments that would normally be passed to your main()
		 * method as follows:
		 */
		String[] args = daemonContext.getArguments();

		myThread = new Thread() {
			private long lastTick = 0;

			public synchronized void start() {
				DirectoryMonitorDaemon.this.stopped = false;
				super.start();
			}

			public void run() {
				while (!stopped) {
					long now = System.currentTimeMillis();
					if (now - lastTick >= 1000) {
						System.out.println(!lastOneWasATick ? "tick" : "tock");
						lastOneWasATick = !lastOneWasATick;
						lastTick = now;
					}
				}
			}
		};
	}

	public void start() throws Exception {
		myThread.start();
	}

	public void stop() throws Exception {
		stopped = true;
		try {
			myThread.join(1000);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}

	public void destroy() {
		myThread = null;
	}
	
	public static void main(String[] args){
		/*DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		//Test Data
		File file = new File("D:/w/FtpTest/bin/bm/FtpTest/main/dir/test.txt");
		TrackedFile file1 = new TrackedFile("test.txt","D:/w/FtpTest/bin/bm/FtpTest/main/dir/",dateFormat.format(file.lastModified()));
		
		ArrayList<TrackedFile> files = new ArrayList<TrackedFile>();
		files.add(file1);*/
		
		RemoteServer server = new RemoteServer("192.168.2.16", "FTP");
		
		MonitoredDirectory monDir = SetupMonitoredDirectory.setup(new MonitoredDirectory("D:/testdir/",server));
		
		
		FileTransfer trans = new FileTransfer(monDir);
		trans.createRemoteDirectory("D:/testdir/", "FTP/testdir");
		trans.monitor();
		
		
		/*for (TrackedFile file : monDir.getFiles()){
			System.out.println(file.getLastModified());
		}*/
		
		
		
		
		
	}

	
}
