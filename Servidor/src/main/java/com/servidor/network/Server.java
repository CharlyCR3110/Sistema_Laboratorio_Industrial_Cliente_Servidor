package com.servidor.network;

import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.ObjectSocket;
import com.compartidos.elementosCompartidos.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
	ServerSocket serverSocket;
	List<ClientHandler> workers;
	public Server() {
		try {
			serverSocket = new ServerSocket(Protocol.PORT);
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
				int type = objectSocketSync.in.readInt();	// 10: Sincrono, 11: Asincrono
				switch (type) {
					case Protocol.SYNC:
						sid = socket.getRemoteSocketAddress().toString();
						objectSocketSync.sid = sid;
						System.out.println("SYNCH: " + objectSocketSync.sid);
						clientHandler = new ClientHandler(this, objectSocketSync);
						workers.add(clientHandler);
						System.out.println("Quedan: " + workers.size());
						objectSocketSync.out.writeObject(objectSocketSync.sid);
						clientHandler.start();
						break;
					case Protocol.ASYNC:
						System.out.println("ASYNCH");
						sid = (String) objectSocketSync.in.readObject();
						objectSocketSync.sid = sid;
						System.out.println("ASYNCH: " + objectSocketSync.sid);
						join(objectSocketSync);
						break;
				}
				objectSocketSync.out.flush();
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	public void join(ObjectSocket objectSocketAsync) {
		System.out.println("JOIN");
		for (ClientHandler w : workers) {
			if (w.objectSocketSync.sid.equals(objectSocketAsync.sid)) {
				w.objectSocketAsync = objectSocketAsync;
				System.out.println("JOIN: " + w.objectSocketAsync.sid);
				System.out.println("Son los out iguales: " + (w.objectSocketSync.out == w.objectSocketAsync.out));
				System.out.println("Son los in iguales: " + (w.objectSocketSync.in == w.objectSocketAsync.in));
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
