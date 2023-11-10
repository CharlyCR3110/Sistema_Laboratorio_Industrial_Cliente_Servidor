package com.cliente.instrumentos.logic.async;

import com.compartidos.elementosCompartidos.MensajeAsincrono;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EscuchaAsincrona extends Thread {
	private Socket socket;
	private ObjectInputStream inputStream;
	private volatile boolean continuarEscuchando;

	public EscuchaAsincrona(Socket socket, ObjectInputStream inputStream) {
		this.socket = socket;
		this.inputStream = inputStream;
		this.continuarEscuchando = true;
	}

	public void detenerEscucha() {
		this.continuarEscuchando = false;
		System.out.println("EscuchaAsincrona - Deteniendo escucha");
	}

	public void reanudarEscucha() {
		this.continuarEscuchando = true;
		System.out.println("EscuchaAsincrona - Reanudando escucha");
	}

	@Override
	public void run() {
		System.out.println("EscuchaAsincrona - Iniciando escucha");
		try {
			// Abre el flujo de entrada de objetos desde el socket
			while (!socket.isClosed()) {
				if (continuarEscuchando) {
					System.out.println("Escuchando...");
					// verificar que no esté vacío
					Object respuesta = inputStream.readObject();

					System.out.println("EscuchaAsincrona - La respuestas recibida es de tipo: " + respuesta.getClass().getName());

					if (respuesta instanceof String tipo) {
						if (tipo.equals("NOTIFICACION")) {
							System.out.println("Notificación recibida: " + inputStream.readObject().toString());
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error al escuchar en escucha asincrona");
			// Maneja las excepciones según tus necesidades
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al escuchar en escucha asincrona: ClassNotFoundException: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
