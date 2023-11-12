package com.cliente.instrumentos.logic;

import com.cliente.instrumentos.logic.async.IListener;
import com.cliente.instrumentos.logic.async.ITarget;
import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.ObjectSocket;
import com.compartidos.elementosCompartidos.Protocol;

import javax.swing.*;
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ClienteServidorHandler getInstance() {
		return theInstance;
	}

	// Método para enviar la solicitud al servidor
	private synchronized void enviarSolicitudAlServidor(String commandName, Object datos) {
		try {
			// limpiar el buffer de salida
			objectSocketSync.out.reset();
			// enviar los datos
			objectSocketSync.out.writeInt(Protocol.NORMAL);
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
		try {
			objectSocketAsync = new ObjectSocket(new Socket(Protocol.SERVER, Protocol.PORT));	// <- AQUI HAY PROBLEMAS
			objectSocketAsync.sid = objectSocketSync.sid;
			// limpiar el buffer de salida
			objectSocketAsync.out.reset();
			// enviar los datos
			objectSocketAsync.out.writeInt(Protocol.ASYNC);
			objectSocketAsync.out.writeObject(objectSocketAsync.sid);
			objectSocketAsync.out.flush();
		} catch (SocketException e) {
			throw new RuntimeException("Socket Exception: " + e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// Se crea un hilo secundario para escuchar los mensajes asincronos
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				listen();
			}
		});
		continuar = true;
		t.start();
	}

	public void listen() {
		int method;
		while (continuar) {
			try {
				method = objectSocketAsync.in.readInt();
				switch (method) {
					case Protocol.DELIVER:
						try {
							MensajeAsincrono mensajeAsincrono = (MensajeAsincrono) objectSocketAsync.in.readObject();
							this.deliver(mensajeAsincrono);
						} catch (ClassNotFoundException ex) {
							throw new RuntimeException(ex);
						}
						break;
				}
			} catch (Exception e) {
				continuar = false;
				throw new RuntimeException(e);
			}
		}

		// Cerrar el socket y streams
		try {
			objectSocketAsync.socket.shutdownOutput();
			objectSocketAsync.socket.close();
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
		this.startListening();
	}

	@Override
	public void stop() {
		this.stopListening();
		try {
			this.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void disconnect() {
		try {
			objectSocketSync.socket.shutdownOutput();
			objectSocketSync.socket.close();
		} catch (Exception e) {
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