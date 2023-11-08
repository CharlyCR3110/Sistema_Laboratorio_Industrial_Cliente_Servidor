package com.servidor.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private static List<Thread> threads = new ArrayList<>();
	public static void main(String[] args) {
		System.out.println("Hello world from Server!");
		int port = 12345;

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server is listening on port " + port);
			while (true) {
				System.out.println("Clientes conectados: " + threads.size());
				// Espera a que llegue una conexión entrante
				Socket socket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + socket.getInetAddress());

				// Crea una nueva instancia de ClientHandler para manejar la conexión del cliente
				ClientHandler clientHandler = new ClientHandler(socket, threads);

				// Inicia un nuevo hilo para manejar la conexión del cliente
				Thread thread = new Thread(clientHandler);
				thread.start();

				// Agrega el hilo a la lista de hilos
				threads.add(thread);
			}
		} catch (Exception e) {
			System.out.println("Server exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
