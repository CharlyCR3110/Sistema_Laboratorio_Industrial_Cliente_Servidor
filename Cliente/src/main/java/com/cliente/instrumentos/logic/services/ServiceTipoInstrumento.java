package com.cliente.instrumentos.logic.services;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.Protocol;
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
			clienteServidorHandler.enviarComandoAlServidor(Protocol.GUARDAR_TIPO_INSTRUMENTO, tipoInstrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.guardarTipoInstrumento: " + e.getMessage());
		}
	}

	public int eliminar(TipoInstrumento tipoInstrumento) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(Protocol.ELIMINAR_TIPO_INSTRUMENTO, tipoInstrumento);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceTipoInstrumentos.eliminarTipoInstrumento: " + e.getMessage());
		}
	}

	public int modificar(TipoInstrumento tipoInstrumento) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(Protocol.MODIFICAR_TIPO_INSTRUMENTO, tipoInstrumento);
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
			returnObject = clienteServidorHandler.enviarComandoAlServidor(Protocol.LISTAR_TIPO_INSTRUMENTO, filter);
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
			return (TipoInstrumento) clienteServidorHandler.enviarComandoAlServidor(Protocol.OBTENER_TIPO_INSTRUMENTO, tipoInstrumento);
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
}
