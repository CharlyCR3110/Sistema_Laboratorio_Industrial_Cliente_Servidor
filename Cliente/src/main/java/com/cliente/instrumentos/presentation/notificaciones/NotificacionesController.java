package com.cliente.instrumentos.presentation.notificaciones;

import com.cliente.instrumentos.logic.ClienteServidorHandler;
import com.cliente.instrumentos.logic.async.ITarget;
import com.compartidos.elementosCompartidos.MensajeAsincrono;

public class NotificacionesController implements ITarget {
	private View view;
	private final ClienteServidorHandler clienteServidorHandler = ClienteServidorHandler.instance();

	public NotificacionesController(View view) {
		this.view = view;
		view.agregarMensaje("Conectado al servidor");
	}

	public void agregarMensajeAlRegistro(String mensaje) {
		view.agregarMensaje(mensaje);
	}

	public View getView() {
		return view;
	}

	@Override
	public void deliver(MensajeAsincrono message) {
		view.agregarMensaje(message.getMensaje());
	}
}
