package com.cliente.instrumentos.presentation.notificaciones;

import com.cliente.instrumentos.logic.ClienteServidorHandler;

public class NotificacionesController {
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
}
