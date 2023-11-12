package com.servidor.utils;

import com.compartidos.elementosCompartidos.MensajeAsincrono;

public class MensajeCreator {
	public static MensajeAsincrono formatearMensaje(String commandName, Object datos) {
		String mensaje = "";
		String tipo = "";

		if (commandName.contains("MODIFICAR")) {
			mensaje = "Se ha modificado un elemento de tipo: ";
		} else if (commandName.contains("ELIMINAR")) {
			mensaje = "Se ha eliminado un elemento de tipo: ";
		} else if (commandName.contains("GUARDAR")) {
			mensaje = "Se ha agregado un un elemento de tipo: ";
		}


		// tipo de dato
		if (datos.getClass().getSimpleName().equals("TipoInstrumento")) {
			mensaje += "Tipo de instrumento";
			tipo = "Tipo Instrumento";
		} else if (datos.getClass().getSimpleName().equals("Instrumento")) {
			mensaje += "Instrumento";
			tipo = "Instrumento";
		} else if (datos.getClass().getSimpleName().equals("Calibracion")) {
			mensaje += "Calibración";
			tipo = "Calibracion";
		} else if (datos.getClass().getSimpleName().equals("Medicion")) {
			mensaje += "Medición";
			tipo = "Medicion";
		}

		return new MensajeAsincrono(mensaje, tipo);
	}
}
