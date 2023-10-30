package com.servidor.commandPattern.Instrumento;

import com.compartidos.elementosCompartidos.Instrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.InstrumentoDaoController;

import java.util.List;

public class ListarInstrumentoCommand implements Command<Instrumento> {

	private final InstrumentoDaoController instrumentoDaoController = new InstrumentoDaoController();
	private Instrumento instrumento;
	private List<Instrumento> instrumentoListReturn;
	public ListarInstrumentoCommand() {}

	@Override
	public void setDatos(Object datos) {
		this.instrumento = (Instrumento) datos;
	}

	@Override
	public int execute() {
		try {
			if (instrumento != null && instrumento.getDescripcion() != null && !instrumento.getDescripcion().isEmpty()) {
				instrumentoListReturn = instrumentoDaoController.listarPorDescripcion(instrumento.getDescripcion());
			} else {
				instrumentoListReturn = instrumentoDaoController.listar();
			}
			return 1;
		} catch (Exception e) {
			throw new RuntimeException("ERROR AL LISTAR INSTRUMENTO");
		}
	}

	@Override
	public Instrumento getReturn() {
		return null;
	}

	@Override
	public List<Instrumento> getListReturn() {
		return instrumentoListReturn;
	}
}
