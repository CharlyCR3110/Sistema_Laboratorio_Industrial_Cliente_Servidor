package com.servidor.network;

import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.ObjectSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	ServerSocket serverSocket;
	List<ClientHandler> workers;
	private final int PORT = 12345;
	public Server() {
		try {
			serverSocket = new ServerSocket(PORT);
			workers = Collections.synchronizedList(new ArrayList<ClientHandler>());
			System.out.println("Servidor iniciado...");
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}


	public void run() {
		boolean continuar = true;
		ObjectSocket objectSocketSync = null;
		Socket socket = null;
		ClientHandler clientHandler = null;
		String sid;
		while (continuar) {
			try {
				socket = serverSocket.accept();
				objectSocketSync = new ObjectSocket(socket);
				System.out.println("Conexion Establecida...");
				int type = objectSocketSync.in.readInt();	// 1: Sincrono, 2: Asincrono
				switch (type) {
					case 1:
						sid = socket.getRemoteSocketAddress().toString();
						objectSocketSync.sid = sid;
						System.out.println("SYNCH: " + objectSocketSync.sid);
						clientHandler = new ClientHandler(this, objectSocketSync);
						workers.add(clientHandler);
						System.out.println("Quedan: " + workers.size());
						objectSocketSync.out.writeObject(objectSocketSync.sid);
						break;
					case 2:
						sid = (String) objectSocketSync.in.readObject();
						objectSocketSync.sid = sid;
						System.out.println("ASYNCH: " + objectSocketSync.sid);
						join(objectSocketSync);
						break;
				}
				clientHandler.start();
				objectSocketSync.out.flush();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	public void join(ObjectSocket objectSocketAsync) {
		for (ClientHandler w : workers) {
			if (w.objectSocketSync.sid.equals(objectSocketAsync.sid)) {
				w.objectSocketAsync = objectSocketAsync;
				break;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello world from Server!");
		Server server = new Server();
		server.run();
	}

	public String listaWorkersSize() {
		return String.valueOf(workers.size());
	}

	public void remove(ClientHandler clientHandler) {
		workers.remove(clientHandler);
	}

	public void deliver(MensajeAsincrono message) {
		for (ClientHandler worker : workers) {
			worker.deliver(message);
		}
	}
}
