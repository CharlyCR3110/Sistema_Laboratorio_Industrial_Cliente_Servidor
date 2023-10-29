package com.servidor.commandPattern.TipoInstumento;

import com.compartidos.elementosCompartidos.TipoInstrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.TipoInstrumentoDaoController;

import java.util.List;

public class ModificarTipoInstrumentoCommand implements Command<TipoInstrumento> {
	private TipoInstrumentoDaoController tipoInstrumentoDaoController = new TipoInstrumentoDaoController();
	private TipoInstrumento tipoInstrumento;
	public ModificarTipoInstrumentoCommand() {}
	@Override
	public void setDatos(Object datos) {
		this.tipoInstrumento = (TipoInstrumento) datos;
	}
	@Override
	public int execute() {
		try {
			return tipoInstrumentoDaoController.modificar(tipoInstrumento);
		} catch (Exception e) {
			throw new RuntimeException("ERROR AL MODIFICAR TIPO INSTRUMENTO");
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
