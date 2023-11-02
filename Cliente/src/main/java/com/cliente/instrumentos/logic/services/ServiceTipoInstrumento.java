package com.cliente.instrumentos.logic.services;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.TipoInstrumento;

import java.util.Collections;
import java.util.List;

public class ServiceTipoInstrumento {
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private static ServiceTipoInstrumento theInstance;
	public static ServiceTipoInstrumento instance(){
		if (theInstance == null) theInstance = new ServiceTipoInstrumento();
		return theInstance;
	}
	private ServiceTipoInstrumento(){
	}
	public static ServiceTipoInstrumento getInstance() {
		return theInstance;
	}

	public int guardar(TipoInstrumento tipoInstrumento) {
		try {
			ClienteServidorHandler.enviarComandoAlServidor(GUARDAR_TIPO_INSTRUMENTO, tipoInstrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.guardarTipoInstrumento: " + e.getMessage());
		}
	}

	public int eliminar(TipoInstrumento tipoInstrumento) {
		try {
			ClienteServidorHandler.enviarComandoAlServidor(ELIMINAR_TIPO_INSTRUMENTO, tipoInstrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.eliminarTipoInstrumento: " + e.getMessage());
		}
	}

	public int modificar(TipoInstrumento tipoInstrumento) {
		try {
			ClienteServidorHandler.enviarComandoAlServidor(MODIFICAR_TIPO_INSTRUMENTO, tipoInstrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.modificarTipoInstrumento: " + e.getMessage());
		}
	}

	public List<TipoInstrumento> listar(TipoInstrumento filter) {
		Object returnObject;
		try {
			returnObject = ClienteServidorHandler.enviarComandoAlServidor(LISTAR_TIPO_INSTRUMENTO, filter);
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.listar: " + e.getMessage());
		}

		if (returnObject instanceof List<?>) {
			// Verificar si la lista contiene objetos del tipo TipoInstrumento
			List<?> lista = (List<?>) returnObject;
			if (!lista.isEmpty() && lista.get(0) instanceof TipoInstrumento) {
				return (List<TipoInstrumento>) returnObject;
			}
		}
		// caso en el que el resultado no es del tipo esperado
		// devolver una lista vacía de TipoInstrumento
		return Collections.emptyList(); // Devuelve una lista vacía en caso de error
	}

		public TipoInstrumento obtener(TipoInstrumento tipoInstrumento) {
		try {
			return (TipoInstrumento) ClienteServidorHandler.enviarComandoAlServidor(OBTENER_TIPO_INSTRUMENTO, tipoInstrumento);
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.obtener: " + e.getMessage());
		}
	}
	public TipoInstrumento getTipoSeleccionado(String tipo) {
		for (TipoInstrumento tipoInstrumento : listar(new TipoInstrumento())) {
			if (tipoInstrumento.getNombre().equals(tipo)) {
				return tipoInstrumento;
			}
		}
		return null;
	}




	// constantes para los comandos
	private static final String GUARDAR_TIPO_INSTRUMENTO = "GUARDAR_TIPO_INSTRUMENTO";
	private static final String ELIMINAR_TIPO_INSTRUMENTO = "ELIMINAR_TIPO_INSTRUMENTO";
	private static final String LISTAR_TIPO_INSTRUMENTO = "LISTAR_TIPO_INSTRUMENTO";
	private static final String MODIFICAR_TIPO_INSTRUMENTO = "MODIFICAR_TIPO_INSTRUMENTO";
	private static final String OBTENER_TIPO_INSTRUMENTO = "OBTENER_TIPO_INSTRUMENTO";
}
