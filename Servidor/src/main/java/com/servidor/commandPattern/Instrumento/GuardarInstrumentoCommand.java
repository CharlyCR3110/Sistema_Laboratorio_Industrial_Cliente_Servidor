package com.servidor.commandPattern.Instrumento;


import com.compartidos.elementosCompartidos.Instrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.InstrumentoDaoController;

import java.util.List;

public class GuardarInstrumentoCommand implements Command<Instrumento> {
	private final InstrumentoDaoController instrumentoDaoController = new InstrumentoDaoController();
	private Instrumento instrumento;
	public GuardarInstrumentoCommand(){}

	@Override
	public void setDatos(Object datos) {
		this.instrumento = (Instrumento) datos;
	}

	@Override
	public int execute() {
		return instrumentoDaoController.guardar(instrumento);
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
