package org.umces.umces;

public class Sftpmain {

	public static void main(String[] args) {
		// Set your SSH credentials and server address
		String username = "sosunkunle"; // SSH username
		String password = "APwK#r@7i7H$"; // SSH password
		String host = "sosunkunle-01.al.umces.edu"; // SSH host
		final String remoteDirectory = "SAOfile"; // puTTy Directory (HOME PAGE DIRECTORIES)
		int port = 22; // Default Port

		UnixHandler puTTy = new UnixHandler(username, password, host, port);
		boolean startConnect = puTTy.startConnection(remoteDirectory);

		while (!startConnect) { // Forcefully connect to the unix machine
			System.err.println("Trying To Connect To Unix Machine..");
			startConnect = puTTy.startConnection(remoteDirectory);
		}
		System.out.println("Unix Connected!");

		Swing swing = new Swing(puTTy);
		swing.Settings();
    }
}