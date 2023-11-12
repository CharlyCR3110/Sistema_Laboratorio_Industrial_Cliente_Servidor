package com.cliente.instrumentos.logic.services;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.Calibracion;
import com.compartidos.elementosCompartidos.Protocol;

import java.util.Collections;
import java.util.List;

public class ServiceCalibracion {
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private static ServiceCalibracion theInstance;
	public static ServiceCalibracion instance(){
		if (theInstance == null) theInstance = new ServiceCalibracion();
		return theInstance;
	}
	private ServiceCalibracion(){
	}
	public ServiceCalibracion getInstance() {
		return theInstance;
	}

	public int guardar(Calibracion calibracion) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(Protocol.GUARDAR_CALIBRACION, calibracion);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.guardar: " + e.getMessage());
		}
	}

	public int eliminar(Calibracion calibracion) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(Protocol.ELIMINAR_CALIBRACION, calibracion);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.eliminar: " + e.getMessage());
		}
	}

	public int modificar(Calibracion calibracion) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(Protocol.MODIFICAR_CALIBRACION, calibracion);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.modificar: " + e.getMessage());
		}
	}

	public List<Calibracion> listar(Calibracion filter) {
		Object returnObject;
		try {
			returnObject = clienteServidorHandler.enviarComandoAlServidor(Protocol.LISTAR_CALIBRACION, filter);
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceCalibracions.listar: " + e.getMessage());
		}

		if (returnObject instanceof List<?>) {
			// Verificar si la lista contiene objetos del tipo Calibracion
			List<?> lista = (List<?>) returnObject;
			if (!lista.isEmpty() && lista.get(0) instanceof Calibracion) {
				return (List<Calibracion>) returnObject;
			}
		}
		// caso en el que el resultado no es del tipo esperado
		// devolver una lista vacía de Calibracion
		return Collections.emptyList(); // Devuelve una lista vacía en caso de error
	}

	public Calibracion obtener(Calibracion calibracion) {
		try {
			return (Calibracion) clienteServidorHandler.enviarComandoAlServidor(Protocol.OBTENER_CALIBRACION, calibracion);
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.obtener: " + e.getMessage());
		}
	}
}
