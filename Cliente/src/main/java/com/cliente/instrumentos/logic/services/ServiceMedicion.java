package com.cliente.instrumentos.logic.services;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.compartidos.elementosCompartidos.Medicion;
import com.compartidos.elementosCompartidos.Protocol;

public class ServiceMedicion {
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private static ServiceMedicion theInstance;
	private ServiceMedicion(){
	}
	public static ServiceMedicion getInstance() {
		if (theInstance == null) theInstance = new ServiceMedicion();
		return theInstance;
	}

	public int modificar(Medicion medicion) {
		try {
			clienteServidorHandler.enviarComandoAlServidor(Protocol.MODIFICAR_MEDICION, medicion);
			return 1;
		} catch (Exception e) {
			// Manejar errores aquí
			e.printStackTrace();
			throw new RuntimeException("Error en ServiceInstrumentos.modificar: " + e.getMessage());
		}
	}
}
