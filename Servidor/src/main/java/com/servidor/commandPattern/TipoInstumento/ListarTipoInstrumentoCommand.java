package com.servidor.commandPattern.TipoInstumento;

import com.compartidos.elementosCompartidos.TipoInstrumento;
import com.servidor.commandPattern.Command;
import com.servidor.instrumentos.dbRelated.controller.TipoInstrumentoDaoController;

import java.util.List;

public class ListarTipoInstrumentoCommand implements Command<TipoInstrumento> {
	private final TipoInstrumentoDaoController tipoInstrumentoDaoController = new TipoInstrumentoDaoController();
	private TipoInstrumento tipoInstrumento;
	private List<TipoInstrumento> tipoInstrumentoListReturn;
	public ListarTipoInstrumentoCommand() {}
	@Override
	public void setDatos(Object datos) {
		this.tipoInstrumento = (TipoInstrumento) datos;
	}

	@Override
	public int execute() {
		try {
			if (tipoInstrumento != null && tipoInstrumento.getNombre() != null && !tipoInstrumento.getNombre().isEmpty()) {
				tipoInstrumentoListReturn = tipoInstrumentoDaoController.listarPorNombre(tipoInstrumento.getNombre());
			} else {
				tipoInstrumentoListReturn = tipoInstrumentoDaoController.listar();
			}
			return 1;
		} catch (Exception e) {
			throw new RuntimeException("ERROR AL LISTAR TIPO INSTRUMENTO");
		}
	}

	@Override
	public TipoInstrumento getReturn() {
		return null;
	}

	@Override
	public List<TipoInstrumento> getListReturn() {
		return tipoInstrumentoListReturn;
	}
}
