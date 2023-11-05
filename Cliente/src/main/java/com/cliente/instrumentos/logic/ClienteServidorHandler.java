package com.cliente.instrumentos.logic;

import com.compartidos.elementosCompartidos.Calibracion;
import com.compartidos.elementosCompartidos.Instrumento;
import com.compartidos.elementosCompartidos.TipoInstrumento;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClienteServidorHandler {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 12345;
	private static ClienteServidorHandler theInstance;
	public static synchronized ClienteServidorHandler instance() {
		if (theInstance == null) theInstance = new ClienteServidorHandler();
		return theInstance;
	}
	private ClienteServidorHandler(){}
	public ClienteServidorHandler getInstance() {
		return theInstance;
	}

	// metodo encargado de enviar el comando al servidor (sirve literalmente para cualquier comando)
	public static Object enviarComandoAlServidor(String commandName, Object datos) {

		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

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
}
