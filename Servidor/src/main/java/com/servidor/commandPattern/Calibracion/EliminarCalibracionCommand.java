package com.servidor.commandPattern.Calibracion;

import com.compartidos.elementosCompartidos.Calibracion;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.CalibracionDaoController;

import java.util.List;

public class EliminarCalibracionCommand implements Command<Calibracion>{
	private final CalibracionDaoController calibracionDaoController = new CalibracionDaoController();
	private Calibracion calibracion;
	public EliminarCalibracionCommand(){}

	@Override
	public void setDatos(Object datos) {
		this.calibracion = (Calibracion) datos;
	}

	@Override
	public int execute() {
		try {
			return calibracionDaoController.eliminar(calibracion.getNumero());
		} catch (Exception e) {
			throw new RuntimeException("ERROR AL ELIMINAR CALIBRACION");
		}
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
