package com.servidor.network;

import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.ObjectSocket;
import com.compartidos.elementosCompartidos.Protocol;
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
public class ClientHandler {
	private Server server;
	private final CommandManager commandManager;
	public ObjectSocket objectSocketSync;
	public ObjectSocket objectSocketAsync;
	boolean continuar = true;
	public ClientHandler(Server server, ObjectSocket objectSocketSync) {
		this.server = server;
		this.objectSocketSync = objectSocketSync;
		this.commandManager = new CommandManager();
	}

	public void start() {
		try {
			System.out.println("Worker atendiendo peticiones...");
			Thread t = new Thread(new Runnable(){
				public void run(){
					listen();
				}
			});
			continuar = true;
			t.start();
		} catch (Exception ex) {
		}
	}

	public void listen() {
		try {
			while (continuar) {
				System.out.println("Clientes conectados: " + server.listaWorkersSize());    // DEBUG

				if (objectSocketSync == null) {
					System.out.println("No hay clientes sincronos");
					return;
				}
				System.out.println("SID: " + objectSocketSync.sid);    // DEBUG
				System.out.println("Esperando comando");
				System.out.println("Momento exacto de inicio: " + System.currentTimeMillis() + "ms");// DEBUG

				// Leer el comando y el objeto
				int protocolCode = objectSocketSync.in.readInt();

				if (protocolCode == Protocol.NORMAL) {
					System.out.println("NORMAL");
				}


				String commandName = (String) objectSocketSync.in.readObject();
				Object datos = objectSocketSync.in.readObject();

				// obtener el comando
				Command<?> command = commandManager.getCommand(commandName);
				if (command == null) {
					objectSocketSync.out.writeObject(-1);
					objectSocketSync.out.writeObject("Comando no encontrado");
					objectSocketSync.out.flush();
					throw new RuntimeException("Comando no encontrado");
				}
				System.out.println("Comando obtenido: " + commandName);

				// Configura los datos del comando con los datos del cliente
				if (datos == null) {
					System.out.println("Datos nulos");
					objectSocketSync.out.writeObject(-1);
					objectSocketSync.out.writeObject("Datos no encontrados");
					objectSocketSync.out.flush();
				}

				System.out.println("DATO NO NULO");
				command.setDatos(datos);

				// ejecutar el comando
				int resultCode;
				try {
					resultCode = command.execute();
				} catch (Exception ex) {
					objectSocketSync.out.writeObject(-1);
					objectSocketSync.out.writeObject(ex.getMessage());
					objectSocketSync.out.flush();
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
				objectSocketSync.out.writeObject(resultCode);
				objectSocketSync.out.writeObject(returnObject);
				objectSocketSync.out.flush();

				// Si el comando se relaciona con una modificación, enviar una notificación a los clientes
				if (commandName.contains("MODIFICAR") || commandName.contains("ELIMINAR") || commandName.contains("GUARDAR")) {
					MensajeAsincrono mensajeAsincrono = new MensajeAsincrono(commandName, "Se ha modificado un elemento");
					server.deliver(mensajeAsincrono);
				}
			}
		} catch (EOFException e) {
			System.out.println("Cliente desconectado. Momento exacto de finalización: " + System.currentTimeMillis() + "ms");// DEBUG
			// eliminar el cliente de la lista de clientes
			server.remove(this);
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


	public void deliver(MensajeAsincrono message) {
		if (objectSocketAsync == null) {
			System.out.println("No hay clientes asincronos");
			return;
		}
		try {
			objectSocketAsync.out.writeInt(Protocol.DELIVER);	// DELIVER CODE
			objectSocketAsync.out.writeObject(message);
			objectSocketAsync.out.flush();
			System.out.println("Mensaje enviado");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
