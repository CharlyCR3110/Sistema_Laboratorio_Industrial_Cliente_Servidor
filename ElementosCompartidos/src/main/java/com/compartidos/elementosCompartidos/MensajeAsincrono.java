package com.compartidos.elementosCompartidos;

import java.io.Serializable;

public class MensajeAsincrono implements Serializable {
	private final String mensaje;
	private final String tipo;

	public MensajeAsincrono(String mensaje, String tipo) {
		this.mensaje = mensaje;
		this.tipo = tipo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return mensaje;
	}
}
