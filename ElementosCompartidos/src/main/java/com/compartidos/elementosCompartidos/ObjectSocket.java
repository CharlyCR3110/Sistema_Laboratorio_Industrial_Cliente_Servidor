package com.compartidos.elementosCompartidos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocket {
	public Socket socket;
	public ObjectInputStream in;
	public ObjectOutputStream out;
	public String sid; // Session Id

	public ObjectSocket(Socket socket) throws Exception {
		this.socket = socket;
		this.sid = "";
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			throw new Exception("Error initializing ObjectSocket", e);
		}
	}

	// metodo para cerrar los flujos y el socket
	public void close() {
		try {
			if (out != null) out.close();
			if (in != null) in.close();
			if (socket != null && !socket.isClosed()) socket.close();
		} catch (Exception e) {
			// Log the exception or handle it appropriately
			System.out.println("Error closing ObjectSocket: " + e.getMessage());
		}
	}
}
