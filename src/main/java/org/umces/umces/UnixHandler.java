package org.umces.umces;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class UnixHandler {
	protected String username;
	protected String password;
	protected String hostname;
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

}
