package com.servidor.instrumentos.dbRelated.controller;

import com.servidor.factory.ConnectionFactory;
import com.servidor.instrumentos.dbRelated.dao.InstrumentoDao;


import com.compartidos.elementosCompartidos.Instrumento;
import java.util.List;

public class InstrumentoDaoController {
	private final InstrumentoDao instrumentoDao;
	public InstrumentoDaoController() {
		var factory = new ConnectionFactory();
		this.instrumentoDao = new InstrumentoDao(factory.recuperarConexion());
	}

	public Instrumento obtener(Instrumento e) { return instrumentoDao.obtener(e);	}
	public int guardar(Instrumento instrumento) {
		return instrumentoDao.guardar(instrumento);
	}
	public List<Instrumento> listar() {
		return instrumentoDao.listar();
	}
	public int eliminar(String serie) {
		return instrumentoDao.eliminar(serie);
	}
	public List<Instrumento> listarPorDescripcion(String descripcion) {
		return instrumentoDao.listarPorDescripcion(descripcion);
	}
	public int modificar(Instrumento instrumento) {
		return instrumentoDao.modificar(instrumento);
	}
}
