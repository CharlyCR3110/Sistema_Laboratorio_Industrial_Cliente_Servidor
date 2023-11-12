package com.cliente.instrumentos.logic;

import com.cliente.instrumentos.logic.async.IListener;
import com.cliente.instrumentos.logic.async.ITarget;
import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.ObjectSocket;
import com.compartidos.elementosCompartidos.Protocol;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClienteServidorHandler implements IListener {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 12345;
	private static ClienteServidorHandler theInstance;
	public ObjectSocket objectSocketSync = null;	// socket Sync encargado de enviar y recibir mensajes
	public ObjectSocket objectSocketAsync = null;	// socket Async encargado de recibir notificaciones
	public ITarget target;
	public boolean continuar;

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
			// enviar los datos
			objectSocketSync.out.writeInt(Protocol.SYNC);	// 1: Sincrono, 2: Asincrono
			objectSocketSync.out.flush();
			objectSocketSync.sid = (String) objectSocketSync.in.readObject();
			System.out.println("SYNCH: " + objectSocketSync.sid);
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
			System.out.println("---------kk----------");
			System.out.println("SID: " + objectSocketSync.sid);    // DEBUG
			System.out.println("Enviando solicitud al servidor: " + commandName);
			System.out.println("Datos: " + datos);
			// limpiar el buffer de salida
			objectSocketSync.out.reset();
			// enviar los datos
			objectSocketSync.out.writeInt(Protocol.NORMAL);
			objectSocketSync.out.writeObject(commandName);
			objectSocketSync.out.writeObject(datos);
			objectSocketSync.out.flush();
			System.out.println("Solicitud enviada");
			System.out.println("Datos enviados. Protocolo: " + Protocol.NORMAL + " CommandName: " + commandName + " Datos: " + datos);
			System.out.println("---------kk-----------");
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
			if (resultCode != Protocol.ERROR_NO_ERROR) {
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
			// limpiar el buffer de salida
			objectSocketSync.out.reset();
			// enviar 
			objectSocketSync.out.writeObject("close");
			objectSocketSync.out.flush();
			objectSocketSync.socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void startListening() {
		System.out.println("startListening");
		try {
			objectSocketAsync = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));	// <- AQUI HAY PROBLEMAS
			objectSocketAsync.sid = objectSocketSync.sid;
			// limpiar el buffer de salida
			objectSocketAsync.out.reset();
			// enviar los datos
			objectSocketAsync.out.writeInt(Protocol.ASYNC);
			objectSocketAsync.out.writeObject(objectSocketAsync.sid);
			objectSocketAsync.out.flush();
			System.out.println("ASYNCH: " + objectSocketAsync.sid);
		} catch (SocketException e) {
			System.out.println("SocketException - > startListening: Error al conectar el socketAsync al servidor. Motivo: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("startListening: Error al conectar el socketAsync al servidor");
			throw new RuntimeException(e);
		}

		System.out.println("startListening: Creando hilo secundario");
		// Se crea un hilo secundario para escuchar los mensajes asincronos
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("startListening: Escuchando...");
				listen();
			}
		});
		continuar = true;
		t.start();
	}

	public void listen() {
		int method;
		while (continuar) {
			System.out.println("Escuchandoss...");
			try {
				method = objectSocketAsync.in.readInt();
				System.out.println("Operacion: " + method);
				switch (method) {
					case Protocol.DELIVER:
						try {
							MensajeAsincrono mensajeAsincrono = (MensajeAsincrono) objectSocketAsync.in.readObject();
							this.deliver(mensajeAsincrono);
							System.out.println("Mensaje asincrono recibido: " + mensajeAsincrono.toString());
						} catch (ClassNotFoundException ex) {
							System.out.println("listen: Error al recibir el mensaje asincrono");
							throw new RuntimeException(ex);
						}
						break;
				}
			} catch (Exception e) {
				System.out.println("listen: Error: " + e.getMessage());
				continuar = false;
				throw new RuntimeException(e);
			}
		}

		// Cerrar el socket y streams
		try {
			objectSocketAsync.socket.shutdownOutput();
			objectSocketAsync.socket.close();
		} catch (Exception e) {
			System.out.println("listen: Error al cerrar el socketAsync");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addTarget(ITarget t) {
		this.target = t;
	}

	@Override
	public void start() {
		this.startListening();
	}

	@Override
	public void stop() {
		this.stopListening();
		try {
			this.disconnect();
		} catch (Exception e) {
			System.out.println("stop: Error al desconectar el socketSync");
			throw new RuntimeException(e);
		}
	}

	private void disconnect() {
		try {
			objectSocketSync.socket.shutdownOutput();
			objectSocketSync.socket.close();
		} catch (Exception e) {
			System.out.println("disconnect: Error al desconectar el socketSync");
			throw new RuntimeException(e);
		}
	}

	private void stopListening() {
		continuar = false;
	}

	private void deliver( final MensajeAsincrono message ) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				target.deliver(message);
			}
		});
	}
}