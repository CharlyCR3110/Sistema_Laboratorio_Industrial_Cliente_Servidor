package com.cliente.instrumentos.logic.services;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.Instrumento;

import java.util.Collections;
import java.util.List;

public class ServiceInstrumento {
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private static ServiceInstrumento theInstance;
	public static ServiceInstrumento instance(){
		if (theInstance == null) theInstance = new ServiceInstrumento();
		return theInstance;
	}
	private ServiceInstrumento(){
	}
	public static ServiceInstrumento getInstance() {
		return theInstance;
	}

	public int guardar(Instrumento Instrumento) {
		try {
			ClienteServidorHandler.enviarComandoAlServidor(GUARDAR_INSTRUMENTO, Instrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.guardarInstrumento: " + e.getMessage());
		}
	}

	public int eliminar(Instrumento instrumento) {
		try {
			ClienteServidorHandler.enviarComandoAlServidor(ELIMINAR_INSTRUMENTO, instrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.eliminarInstrumento: " + e.getMessage());
		}
	}

	public int modificar(Instrumento instrumento) {
		try {
			ClienteServidorHandler.enviarComandoAlServidor(MODIFICAR_INSTRUMENTO, instrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.modificarInstrumento: " + e.getMessage());
		}
	}

	public List<Instrumento> listar(Instrumento filter) {
		Object returnObject;
		try {
			returnObject = ClienteServidorHandler.enviarComandoAlServidor(LISTAR_INSTRUMENTO, filter);
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.listar: " + e.getMessage());
		}

		if (returnObject instanceof List<?>) {
			// Verificar si la lista contiene objetos del tipo Instrumento
			List<?> lista = (List<?>) returnObject;
			if (!lista.isEmpty() && lista.get(0) instanceof Instrumento) {
				return (List<Instrumento>) returnObject;
			}
		}
		// caso en el que el resultado no es del tipo esperado
		// devolver una lista vacía de Instrumento
		return Collections.emptyList(); // Devuelve una lista vacía en caso de error
	}

	public Instrumento obtener(Instrumento instrumento) {
		try {
			return (Instrumento) ClienteServidorHandler.enviarComandoAlServidor(OBTENER_INSTRUMENTO, instrumento);
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.obtener: " + e.getMessage());
		}
	}


	// constantes para los comandos
	private static final String GUARDAR_INSTRUMENTO = "GUARDAR_INSTRUMENTO";
	private static final String ELIMINAR_INSTRUMENTO = "ELIMINAR_INSTRUMENTO";
	private static final String LISTAR_INSTRUMENTO = "LISTAR_INSTRUMENTO";
	private static final String MODIFICAR_INSTRUMENTO = "MODIFICAR_INSTRUMENTO";
	private static final String OBTENER_INSTRUMENTO = "OBTENER_INSTRUMENTO";
}
