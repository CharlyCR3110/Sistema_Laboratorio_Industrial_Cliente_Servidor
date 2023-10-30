package com.servidor.commandPattern.Instrumento;

import com.compartidos.elementosCompartidos.Instrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.InstrumentoDaoController;

import java.util.List;
public class ModificarInstrumentoCommand implements Command<Instrumento> {
	private final InstrumentoDaoController instrumentoDaoController = new InstrumentoDaoController();
	private Instrumento instrumento;
	public ModificarInstrumentoCommand(){}

	@Override
	public void setDatos(Object datos) {
		this.instrumento = (Instrumento) datos;
	}

	@Override
	public int execute() {
		try {
			return instrumentoDaoController.modificar(instrumento);
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
