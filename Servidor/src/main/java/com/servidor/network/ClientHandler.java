package com.servidor.network;

import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.servidor.commandPattern.Command;
import com.servidor.commandPattern.CommandManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO mejorar el manejo de errores
public class ClientHandler implements Runnable{
	private final Socket socket;
	private final CommandManager commandManager;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Map<Thread, ObjectOutputStream> clientStreams = new HashMap<>();


	public ClientHandler(Socket socket, Map<Thread, ObjectOutputStream> clientStreams) {
		this.socket = socket;
		this.commandManager = new CommandManager();
		this.clientStreams = clientStreams;
		try {
			// Crear los flujos de salida/entrada una sola vez al inicio
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("Clientes conectados: " + clientStreams.size());    // DEBUG
				// Leer el comando y el objeto
				String commandName = (String) in.readObject();
				Object datos = in.readObject();

				// obtener el comando
				Command<?> command = commandManager.getCommand(commandName);
				if (command == null) {
					out.writeObject(-1);
					out.writeObject("Comando no encontrado");
					out.flush();
					throw new RuntimeException("Comando no encontrado");
				}
				System.out.println("Comando obtenido: " + commandName);

				// Configura los datos del comando con los datos del cliente
				if (datos == null) {
					System.out.println("Datos nulos");
					out.writeObject(-1);
					out.writeObject("Datos no encontrados");
					out.flush();
				}

				System.out.println("DATO NO NULO");
				command.setDatos(datos);

				// ejecutar el comando
				int resultCode;
				try {
					resultCode = command.execute();
				} catch (Exception ex) {
					out.writeObject(-1);
					out.writeObject(ex.getMessage());
					out.flush();
					throw new RuntimeException("Error al ejecutar el comando");
				}

				Object returnObject = null;

				// Si el comando tiene un objeto de retorno, obtenerlo
				if (command.getReturn() != null) {
					returnObject = command.getReturn();
				} else if (command.getListReturn() != null) {
					returnObject = command.getListReturn();
				}

				// Enviar el resultado al cliente
				out.writeObject(resultCode);
				out.writeObject(returnObject);
				out.flush();
			}
		} catch (EOFException e) {
			System.out.println("Cliente desconectado. Momento exacto de finalización: " + System.currentTimeMillis() + "ms");// DEBUG
			// eliminar el hilo de la lista de hilos
			clientStreams.remove(Thread.currentThread());
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error en el clienteHandler: " + e.getMessage());    // DEBUG
			e.printStackTrace();
		} finally {
			try {
				// No se puede cerrar el socket porque se cierra la conexión con el cliente
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ObjectOutputStream getOutputSteam() {
		return out;
	}
}
