package com.servidor.network;

import com.compartidos.elementosCompartidos.ObjectSocket;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
		while (continuar) {
			try {
				socket = serverSocket.accept();
				objectSocketSync = new ObjectSocket(socket);
				System.out.println("Conexion Establecida...");
				ClientHandler worker = new ClientHandler(this, objectSocketSync);
				workers.add(worker);
				System.out.println("Quedan: " + workers.size());
				worker.start();
			} catch (Exception ex) {
				System.out.println(ex);
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
}
