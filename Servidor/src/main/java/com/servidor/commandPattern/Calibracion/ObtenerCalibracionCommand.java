package com.servidor.commandPattern.Calibracion;

import com.compartidos.elementosCompartidos.Calibracion;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.CalibracionDaoController;

import java.util.List;

public class ObtenerCalibracionCommand implements Command<Calibracion> {

	private final CalibracionDaoController calibracionDaoController = new CalibracionDaoController();
	private Calibracion calibracion;
	private Calibracion calibracionReturn;
	public ObtenerCalibracionCommand() {}
	@Override
	public void setDatos(Object datos) {
		this.calibracion = (Calibracion) datos;
	}

	@Override
	public int execute() {
		this.calibracionReturn = calibracionDaoController.obtener(calibracion);
		return 1;
	}

	@Override
	public Calibracion getReturn() {
		return calibracionReturn;
	}

	@Override
	public List<Calibracion> getListReturn() {
		return null;
	}
}