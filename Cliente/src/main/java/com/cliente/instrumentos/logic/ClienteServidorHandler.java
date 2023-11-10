package com.cliente.instrumentos.logic;

import com.compartidos.elementosCompartidos.ObjectSocket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteServidorHandler {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 12345;
	private static ClienteServidorHandler theInstance;
	private static ObjectSocket objectSocketSync;	// socket Sync encargado de enviar y recibir mensajes
	public static synchronized ClienteServidorHandler instance() {
		if (theInstance == null) theInstance = new ClienteServidorHandler();
		return theInstance;
	}

	private ClienteServidorHandler() {
		connect();
	}

	private void connect(){
		try {
			Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			objectSocketSync = new ObjectSocket(socket);
		} catch (Exception e) {
			System.out.println("Error al conectar el socketSync al servidor");
			throw new RuntimeException(e);
		}
	}

	public ClienteServidorHandler getInstance() {
		return theInstance;
	}

	// Método para enviar la solicitud al servidor
	private synchronized void enviarSolicitudAlServidor(String commandName, Object datos) {
		try {
			objectSocketSync.out.writeObject(commandName);
			objectSocketSync.out.writeObject(datos);
			objectSocketSync.out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Método para recibir la respuesta del servidor
	private synchronized Object recibirRespuestaDelServidor() {
		try {
			return objectSocketSync.in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Método para manejar notificaciones en un hilo separado
	public void manejarNotificaciones() {
		new Thread(() -> {
			try {
				while (true) {
					Object notificacion = recibirRespuestaDelServidor();
					// Lógica para manejar la notificación (puedes adaptar según tus necesidades)
					System.out.println("Notificación del servidor: " + notificacion);
				}
			} catch (Exception e) {
				e.printStackTrace(); // Manejar la excepción adecuadamente
			}
		}).start();
	}

	// Método para enviar comandos al servidor y recibir respuestas
	public synchronized Object enviarComandoAlServidor(String commandName, Object datos) {
		try {
			if (commandName.equals("close")) {
				cerrarConexion();
				return null;
			}

			enviarSolicitudAlServidor(commandName, datos);

			int resultCode = (int) recibirRespuestaDelServidor();
			if (resultCode != 1) {
				throw new RuntimeException("Error en ClienteServidorHandler.enviarComandoAlServidor: code: " + resultCode);
			}

			return recibirRespuestaDelServidor();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Agrega un método para cerrar la conexión al cerrar la aplicación
	public void cerrarConexion() {
		try {
			objectSocketSync.out.writeObject("close");
			objectSocketSync.out.flush();
			objectSocketSync.socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}