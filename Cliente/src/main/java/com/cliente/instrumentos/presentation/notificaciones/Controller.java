package com.cliente.instrumentos.presentation.notificaciones;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.cliente.instrumentos.logic.async.ITarget;
import com.compartidos.elementosCompartidos.MensajeAsincrono;

import java.util.ArrayList;

public class Controller implements ITarget {
	private View view;
	private Model model;
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		model.init();
		view.setController(this);
		model.addObserver(view);

		ClienteServidorHandler.instanceListener().addTarget(this);
		ClienteServidorHandler.instanceListener().start();
		System.out.println("Not: ClienteServidorHandler.instanceListener().start();");
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
	}

	public View getView() {
		return view;
	}

	public Model getModel() {
		return model;
	}
}
