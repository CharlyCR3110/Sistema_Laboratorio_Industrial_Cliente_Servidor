package com.servidor.commandPattern.Instrumento;

import com.compartidos.elementosCompartidos.Instrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.InstrumentoDaoController;

import java.util.List;

public class EliminarInstrumentoCommand implements Command<Instrumento>{
	private final InstrumentoDaoController instrumentoDaoController = new InstrumentoDaoController();
	private Instrumento instrumento;
	public EliminarInstrumentoCommand(){}

	@Override
	public void setDatos(Object datos) {
		this.instrumento = (Instrumento) datos;
	}

	@Override
	public int execute() {
		try {
			return instrumentoDaoController.eliminar(instrumento.getSerie());
		} catch (Exception e) {
			throw new RuntimeException("ERROR AL ELIMINAR INSTRUMENTO");
		}
	}

	@Override
	public Instrumento getReturn() {
		return null;
	}

	@Override
	public List<Instrumento> getListReturn() {
		return null;
	}
}
