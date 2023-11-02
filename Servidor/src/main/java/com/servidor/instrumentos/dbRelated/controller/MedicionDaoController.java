package com.servidor.instrumentos.dbRelated.controller;

import com.servidor.factory.ConnectionFactory;
import com.servidor.instrumentos.dbRelated.dao.MedicionDao;

import com.compartidos.elementosCompartidos.Medicion;

import java.util.List;

public class MedicionDaoController {
	private final MedicionDao medicionDao;
	public MedicionDaoController() {
		var factory = new ConnectionFactory();
		this.medicionDao = new MedicionDao(factory.recuperarConexion());
	}

	public int guardar(Medicion medicion, String calibracion_numero) {
		return medicionDao.guardar(medicion, calibracion_numero);
	}

	public List<Medicion> listar(String calibracion_numero) {
		return medicionDao.listar(calibracion_numero);
	}

	public int modificar(Medicion medicion) {
		return medicionDao.modificar(medicion);
	}
}
