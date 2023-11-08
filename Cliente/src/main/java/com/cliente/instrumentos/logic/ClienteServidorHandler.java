package com.cliente.instrumentos.logic;

import com.compartidos.elementosCompartidos.Calibracion;
import com.compartidos.elementosCompartidos.Instrumento;
import com.compartidos.elementosCompartidos.TipoInstrumento;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteServidorHandler {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 12345;
	private static ClienteServidorHandler theInstance;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public static synchronized ClienteServidorHandler instance() {
		if (theInstance == null) theInstance = new ClienteServidorHandler();
		return theInstance;
	}

	private ClienteServidorHandler() {
		try {
			// Crear el socket y los flujos una sola vez al inicio
			socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ClienteServidorHandler getInstance() {
		return theInstance;
	}

	// metodo encargado de enviar el comando al servidor (sirve literalmente para cualquier comando)
	public synchronized Object enviarComandoAlServidor(String commandName, Object datos) {
		try {
			if (commandName.equals("close")) {
				cerrarConexion();
				return null;
			}
			// Enviar el comando y el objeto al servidor
			out.writeObject(commandName);
			out.writeObject(datos);
			out.flush();

			// Esperar la respuesta del servidor
			int resultCode = (int) in.readObject();
			if (resultCode != 1) {
				// El comando no se ejecutó con éxito
				throw new RuntimeException("Error en ClienteServidorHandler.enviarComandoAlServidor: code: " + resultCode + "Object: " + in.readObject());
			}

			// Esperar la respuesta del servidor y devolver el objeto recibido
			return in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Agrega un método para cerrar la conexión al cerrar la aplicación
	public void cerrarConexion() {
		try {
			socket.close();
			out.close();
			in.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}