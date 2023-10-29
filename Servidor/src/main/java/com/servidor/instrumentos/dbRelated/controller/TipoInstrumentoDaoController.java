package com.servidor.instrumentos.dbRelated.controller;

import com.servidor.factory.ConnectionFactory;
import com.servidor.instrumentos.dbRelated.dao.TipoInstrumentoDao;
import com.compartidos.elementosCompartidos.TipoInstrumento;

import java.util.List;

public class TipoInstrumentoDaoController {
	private final TipoInstrumentoDao tipoInstrumentoDao;
	public TipoInstrumentoDaoController() {
		var factory = new ConnectionFactory();
		this.tipoInstrumentoDao = new TipoInstrumentoDao(factory.recuperarConexion());
	}

	public TipoInstrumento obtener(TipoInstrumento e) {
			return tipoInstrumentoDao.obtener(e);	}
	public int guardar(TipoInstrumento tipoInstrumento) {
		return tipoInstrumentoDao.guardar(tipoInstrumento);
	}
	public List<TipoInstrumento> listar() {
		return tipoInstrumentoDao.listar();
	}
	public int eliminar(String codigo) {
		return tipoInstrumentoDao.eliminar(codigo);
	}
	public List<TipoInstrumento> listarPorNombre(String nombre) {
		return tipoInstrumentoDao.listarPorNombre(nombre);
	}
	public int modificar(TipoInstrumento tipoInstrumento) {
		return tipoInstrumentoDao.modificar(tipoInstrumento);
	}
}