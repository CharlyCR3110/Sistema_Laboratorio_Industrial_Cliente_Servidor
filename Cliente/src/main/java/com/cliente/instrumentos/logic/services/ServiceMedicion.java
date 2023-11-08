package com.cliente.instrumentos.logic.services;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.Calibracion;
import com.compartidos.elementosCompartidos.Medicion;

public class ServiceMedicion {
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private static ServiceMedicion theInstance;
	public static ServiceMedicion instance(){
		if (theInstance == null) theInstance = new ServiceMedicion();
		return theInstance;
	}
	private ServiceMedicion(){
	}
	public static ServiceMedicion getInstance() {
		return theInstance;
	}

	public int modificar(Medicion medicion) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(MODIFICAR_MEDICION, medicion);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.modificar: " + e.getMessage());
		}
	}

	private static final String MODIFICAR_MEDICION = "MODIFICAR_MEDICION";
}
