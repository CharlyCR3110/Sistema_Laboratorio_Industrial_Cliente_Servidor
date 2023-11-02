package com.servidor.network;

import com.servidor.commandPattern.Command;
import com.servidor.commandPattern.CommandManager;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//TODO mejorar el manejo de errores
public class ClientHandler implements Runnable{
	private final Socket socket;
	private final CommandManager commandManager;
	public ClientHandler(Socket socket) {
		this.socket = socket;
		this.commandManager = new CommandManager();
	}

	@Override
	public void run() {
		try (
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
		) {
			while (true) {	// si hay datos para leer
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
		}catch (EOFException e) {
			System.out.println("Cliente desconectado. Momento exacto de finalización: " + System.currentTimeMillis() + "ms");// DEBUG
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error en el clienteHandler: " + e.getMessage());	// DEBUG
			e.printStackTrace();
		}
		finally {
			try {
				// Cerrar los flujos y el socket
				socket.close();
				System.out.println("SOCKET CERRADO");
			} catch (IOException e) {
				System.out.println("Error al cerrar el socket: " + e.getMessage());	// DEBUG
				e.printStackTrace();
			}
		}
	}
}
