package com.servidor.commandPattern.Instrumento;

import com.compartidos.elementosCompartidos.Instrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.InstrumentoDaoController;

import java.util.List;

public class ObtenerInstrumentoCommand implements Command<Instrumento> {
	private final InstrumentoDaoController instrumentoDaoController = new InstrumentoDaoController();
	private Instrumento instrumento;
	private Instrumento instrumentoReturn;
	public ObtenerInstrumentoCommand() {}
	@Override
	public void setDatos(Object datos) {
		this.instrumento = (Instrumento) datos;
	}

	@Override
	public int execute() {
		try {
			this.instrumentoReturn = instrumentoDaoController.obtener(instrumento);
			return 1;
		} catch (Exception e) {
			throw new RuntimeException("ERROR AL OBTENER INSTRUMENTO");
		}
	}

	@Override
	public Instrumento getReturn() {
		return instrumentoReturn;
	}

	@Override
	public List<Instrumento> getListReturn() {
		return null;
	}
}
