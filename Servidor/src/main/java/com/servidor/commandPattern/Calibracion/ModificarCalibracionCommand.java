package com.servidor.commandPattern.Calibracion;

import com.compartidos.elementosCompartidos.Calibracion;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.CalibracionDaoController;

import java.util.List;
public class ModificarCalibracionCommand implements Command<Calibracion> {
	private final CalibracionDaoController calibracionDaoController = new CalibracionDaoController();
	private Calibracion calibracion;
	public ModificarCalibracionCommand(){}

	@Override
	public void setDatos(Object datos) {
		this.calibracion = (Calibracion) datos;
	}

	@Override
	public int execute() {
		return calibracionDaoController.modificar(calibracion);
	}

	@Override
	public Calibracion getReturn() {
		return null;
	}

	@Override
	public List<Calibracion> getListReturn() {
		return null;
	}
}
