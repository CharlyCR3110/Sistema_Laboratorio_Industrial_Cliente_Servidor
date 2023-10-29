package com.servidor.commandPattern.TipoInstumento;

import com.compartidos.elementosCompartidos.TipoInstrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.TipoInstrumentoDaoController;

import java.util.List;

public class GuardarTipoInstrumentoCommand implements Command<TipoInstrumento> {
	private final TipoInstrumentoDaoController tipoInstrumentoDaoController = new TipoInstrumentoDaoController();
	private TipoInstrumento tipoInstrumento;
	public GuardarTipoInstrumentoCommand() {}
	@Override
	public void setDatos(Object datos) {
		this.tipoInstrumento = (TipoInstrumento) datos;
	}
	@Override
	public int execute() {
		try {
			return tipoInstrumentoDaoController.guardar(tipoInstrumento);
		} catch (RuntimeException e) {
			throw new RuntimeException("ERROR AL GUARDAR TIPO INSTRUMENTO");
		}
	}

	@Override
	public TipoInstrumento getReturn() {
		return null;
	}

	@Override
	public List<TipoInstrumento> getListReturn() {
		return null;
	}
}