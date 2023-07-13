package org.umces.umces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class UnixHandler {
	protected String username;
	protected String password;
	protected String hostname;
	protected String rootDirectory;
	protected int port;

	Session mainSession;
	ChannelSftp mainsftpChannel;

	{
		System.err.println("UnixHandler Opened");
	}

	public UnixHandler(String username, String password, String hostname, int port) {
		this.username = username;
		this.password = password;
		this.hostname = hostname;
		this.port = port;
		System.err.println("Unix Credentials Filled!");
	}

	public UnixHandler() {
		System.err.println("Unix Credentials Not Filled!");
	}

	public boolean startConnection(String remoteDirectory) {
		try {
            JSch jsch = new JSch();
			this.mainSession = jsch.getSession(username, hostname, port);
			this.mainSession.setPassword(password);
			this.mainSession.setConfig("StrictHostKeyChecking", "no");
			this.mainSession.connect();

			this.mainsftpChannel = (ChannelSftp) this.mainSession.openChannel("sftp");
			this.mainsftpChannel.connect();
			this.mainsftpChannel.cd(remoteDirectory);
			this.rootDirectory = remoteDirectory;

        } catch (Exception e) {
            e.printStackTrace();
			return false;
        }
		return true;
	}

	public boolean LocaltoRemote(String localFilePath) {
		try {
			Path newPath = Paths.get(localFilePath).toAbsolutePath();
			String remoteFileName = newPath.getFileName().toString();

			this.mainsftpChannel.put(newPath.toString(), remoteFileName);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean disconnectPuTTy() {
		if (this.mainSession != null || this.mainsftpChannel != null) {
			try {
				this.mainsftpChannel.disconnect();
				this.mainSession.disconnect();
				this.mainSession = null;
				this.mainsftpChannel = null;
				return true;
			} catch (Exception e) {
				System.err.println("Looks Like You Where Never Connected To PuTTy");
			}
		}
		return false;
	}
	


	public String executeCommand(String command) {
		StringBuilder output = new StringBuilder();

		try {
			ChannelExec channel = (ChannelExec) this.mainSession.openChannel("exec");
			channel.setCommand(command);
			channel.setInputStream(null);
			channel.setErrStream(System.err);

			InputStream inputStream = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();
	}

	@SuppressWarnings("unchecked")
	public List<String> listFilesInDirectory() {
		List<String> fileList = new ArrayList<>();
		try {
			Vector<ChannelSftp.LsEntry> entries = mainsftpChannel.ls(".");
			for (ChannelSftp.LsEntry entry : entries) {
				if (!entry.getAttrs().isDir()) {
					fileList.add(entry.getFilename());
				}
			}
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return fileList;
	}

	public String readRemoteFile(String fileName) {
	    StringBuilder fileContent = new StringBuilder();
	    try {
	        InputStream inputStream = mainsftpChannel.get(fileName);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

	        String line;
	        while ((line = reader.readLine()) != null) {
	            fileContent.append(line).append("\n");
	        }
	        reader.close();
	        inputStream.close();
	    } catch (SftpException | IOException e) {
	        e.printStackTrace();
	    }
	    return fileContent.toString();
	}

	// Helps locate files in the Unix Directory
	public String getPath(String extendPath) {
		return "/home/" + this.username + "/" + this.rootDirectory + "/" + extendPath;
	}

	public String getPath() {
		return "/home/" + this.username + "/" + this.rootDirectory + "/";
	}

}
