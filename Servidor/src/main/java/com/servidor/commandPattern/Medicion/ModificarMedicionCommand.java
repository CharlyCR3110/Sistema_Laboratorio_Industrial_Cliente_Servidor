package com.servidor.commandPattern.Medicion;

import com.compartidos.elementosCompartidos.Medicion;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.MedicionDaoController;

import java.util.List;

public class ModificarMedicionCommand implements Command<Medicion> {
	private final MedicionDaoController medicionDaoController = new MedicionDaoController();
	private Medicion medicion;
	public ModificarMedicionCommand(){}

	@Override
	public void setDatos(Object datos) {
		this.medicion = (Medicion) datos;
	}

	@Override
	public int execute() {
		return medicionDaoController.modificar(medicion);
	}

	@Override
	public Medicion getReturn() {
		return null;
	}

	@Override
	public List<Medicion> getListReturn() {
		return null;
	}
}
