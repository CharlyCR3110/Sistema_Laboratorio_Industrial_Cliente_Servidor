package com.cliente.instrumentos.logic;

import com.cliente.instrumentos.logic.async.IListener;
import com.cliente.instrumentos.logic.async.ITarget;
import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.ObjectSocket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteServidorHandler implements IListener {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 12345;
	private static ClienteServidorHandler theInstance;
	public ObjectSocket objectSocketSync;	// socket Sync encargado de enviar y recibir mensajes
	public ObjectSocket objectSocketAsync;	// socket Async encargado de recibir notificaciones
	public ITarget target;

	public static synchronized ClienteServidorHandler instance() {
		if (theInstance == null) theInstance = new ClienteServidorHandler();
		return theInstance;
	}

	public static synchronized IListener instanceListener() {
		if (theInstance == null) theInstance = new ClienteServidorHandler();
		return theInstance;
	}

	private ClienteServidorHandler() {
		connect();
	}

	private void connect(){
		try {
			objectSocketSync = new ObjectSocket(new Socket(SERVER_ADDRESS, SERVER_PORT));
			objectSocketSync.out.writeInt(1);	// 1: Sincrono, 2: Asincrono
			objectSocketSync.out.flush();
			String sid = (String) objectSocketSync.in.readObject();
			System.out.println("SYNCH: " + sid);
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
			System.out.println("Enviando solicitud al servidor: " + commandName);
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
			System.out.println("Esperando respuesta del servidor...");
			return objectSocketSync.in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	@Override
	public void addTarget(ITarget t) {
		this.target = t;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}
}