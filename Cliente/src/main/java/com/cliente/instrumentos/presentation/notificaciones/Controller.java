package com.cliente.instrumentos.presentation.notificaciones;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.cliente.instrumentos.logic.async.ITarget;
import com.compartidos.elementosCompartidos.Calibracion;
import com.compartidos.elementosCompartidos.Instrumento;
import com.compartidos.elementosCompartidos.MensajeAsincrono;
import com.compartidos.elementosCompartidos.TipoInstrumento;

import java.util.ArrayList;

public class Controller implements ITarget {
	private View view;
	private Model model;
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();
	private com.cliente.instrumentos.presentation.tipos.Controller tiposController;
	private com.cliente.instrumentos.presentation.instrumentos.Controller instrumentosController;
	private com.cliente.instrumentos.presentation.calibraciones.Controller calibracionesController;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		model.init();
		view.setController(this);
		model.addObserver(view);

		ClienteServidorHandler.instanceListener().addTarget(this);
		ClienteServidorHandler.instanceListener().start();
	}

	public Controller(View view, Model model, com.cliente.instrumentos.presentation.tipos.Controller tiposController, com.cliente.instrumentos.presentation.instrumentos.Controller instrumentosController, com.cliente.instrumentos.presentation.calibraciones.Controller calibracionesController) {
		this.view = view;
		this.model = model;
		this.calibracionesController = calibracionesController;
		this.instrumentosController = instrumentosController;
		this.tiposController = tiposController;

		model.init();
		view.setController(this);
		model.addObserver(view);

		ClienteServidorHandler.instanceListener().addTarget(this);
		ClienteServidorHandler.instanceListener().start();
	}

	public void clear() {
		model.setList(new ArrayList<MensajeAsincrono>());
		model.commit();
	}

	public void stop () {
		ClienteServidorHandler.instanceListener().stop();
	}

	@Override
	public void deliver(MensajeAsincrono message) {
		model.agregar(message);
		model.commit();
		recargarListas();
	}

	private void recargarListas() {
		tiposController.refresh();
		instrumentosController.refresh();
		calibracionesController.refresh();
	}

	public View getView() {
		return view;
	}

	public Model getModel() {
		return model;
	}
}
