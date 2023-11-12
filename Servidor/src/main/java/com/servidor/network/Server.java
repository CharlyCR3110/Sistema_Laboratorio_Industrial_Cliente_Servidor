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
		} catch (IOException ex) {
			ex.printStackTrace();
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
				int type = objectSocketSync.in.readInt();	// 10: Sincrono, 11: Asincrono
				switch (type) {
					case Protocol.SYNC:
						sid = socket.getRemoteSocketAddress().toString();
						objectSocketSync.sid = sid;
						clientHandler = new ClientHandler(this, objectSocketSync);
						workers.add(clientHandler);
						objectSocketSync.out.writeObject(objectSocketSync.sid);
						clientHandler.start();
						break;
					case Protocol.ASYNC:
						sid = (String) objectSocketSync.in.readObject();
						objectSocketSync.sid = sid;
						join(objectSocketSync);
						break;
				}
				objectSocketSync.out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
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
