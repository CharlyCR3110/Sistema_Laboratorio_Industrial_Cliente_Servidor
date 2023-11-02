package com.cliente.instrumentos.logic;

import com.compartidos.elementosCompartidos.Instrumento;

public class Mediator {
	private com.cliente.instrumentos.presentation.instrumentos.Controller instrumentosController;
	private com.cliente.instrumentos.presentation.calibraciones.Controller calibracionesController;
	public Mediator(com.cliente.instrumentos.presentation.instrumentos.Controller instrumentosController, com.cliente.instrumentos.presentation.calibraciones.Controller calibracionesController) {
		this.instrumentosController = instrumentosController;
		this.calibracionesController = calibracionesController;
	}

	public void setInstrumentoSeleccionado() {
		Instrumento instrumentoSeleccionado = instrumentosController.getSelected();
		calibracionesController.instrumentoSeleccionadoCambiado(instrumentoSeleccionado);
	}

}
