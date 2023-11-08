package com.servidor.network;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
	private static Map<Thread, ObjectOutputStream> clientStreams = new HashMap<>();
	public static void main(String[] args) {
		System.out.println("Hello world from Server!");
		int port = 12345;

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server is listening on port " + port);
			while (true) {
				System.out.println("Clientes conectados: " + clientStreams.size());
				// Espera a que llegue una conexión entrante
				Socket socket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + socket.getInetAddress());

				// Crea una nueva instancia de ClientHandler para manejar la conexión del cliente
				ClientHandler clientHandler = new ClientHandler(socket, clientStreams);

				// Inicia un nuevo hilo para manejar la conexión del cliente
				Thread thread = new Thread(clientHandler);
				thread.start();

				// Agrega el hilo a la lista de hilos
				clientStreams.put(thread, clientHandler.getOutputSteam());
			}
		} catch (Exception e) {
			System.out.println("Server exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
