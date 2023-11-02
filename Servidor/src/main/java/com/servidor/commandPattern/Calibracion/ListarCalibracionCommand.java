package com.servidor.commandPattern.Calibracion;

import com.compartidos.elementosCompartidos.Calibracion;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.CalibracionDaoController;

import java.util.List;

public class ListarCalibracionCommand implements Command<Calibracion> {

	private final CalibracionDaoController calibracionDaoController = new CalibracionDaoController();
	private Calibracion calibracion;
	private List<Calibracion> calibracionListReturn;
	public ListarCalibracionCommand() {}

	@Override
	public void setDatos(Object datos) {
		this.calibracion = (Calibracion) datos;
	}

	@Override
	public int execute() {
		calibracionListReturn = calibracion.getInstrumento() == null ?
				calibracionDaoController.listar("NO HAY INSTRUMENTO") :
				calibracionDaoController.listar(calibracion.getInstrumento().getSerie());
		return 1;
	}

	@Override
	public Calibracion getReturn() {
		return null;
	}

	@Override
	public List<Calibracion> getListReturn() {
		return calibracionListReturn;
	}
}
