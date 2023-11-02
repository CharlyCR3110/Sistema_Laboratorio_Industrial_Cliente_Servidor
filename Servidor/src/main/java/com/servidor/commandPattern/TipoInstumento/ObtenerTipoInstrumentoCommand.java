package com.servidor.commandPattern.TipoInstumento;

import com.compartidos.elementosCompartidos.TipoInstrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.TipoInstrumentoDaoController;

import java.util.List;

public class ObtenerTipoInstrumentoCommand implements Command<TipoInstrumento> {
	private final TipoInstrumentoDaoController tipoInstrumentoDaoController = new TipoInstrumentoDaoController();
	private TipoInstrumento tipoInstrumento;
	private TipoInstrumento tipoInstrumentoReturn;

	public ObtenerTipoInstrumentoCommand() {}
	@Override
	public void setDatos(Object datos) {
		this.tipoInstrumento = (TipoInstrumento) datos;
	}

	@Override
	public int execute() {
		this.tipoInstrumentoReturn = tipoInstrumentoDaoController.obtener(tipoInstrumento);
		return 1;
	}

	@Override
	public TipoInstrumento getReturn() {
		return tipoInstrumentoReturn;
	}

	@Override
	public List<TipoInstrumento> getListReturn() {
		return null;
	}
}
