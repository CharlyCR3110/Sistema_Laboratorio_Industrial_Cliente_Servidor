package com.compartidos.elementosCompartidos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocket {
	public Socket socket;
	public ObjectInputStream in;
	public ObjectOutputStream out;
	public String sid; // Session Id

	public ObjectSocket(Socket socket) {
		this.socket = socket;
		this.sid = "";
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
