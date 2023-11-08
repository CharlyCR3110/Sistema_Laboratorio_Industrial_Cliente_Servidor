package com.cliente.instrumentos.presentation.notificaciones;

import javax.swing.*;

public class View {
	private JTextPane registro;
	private JPanel panel;
	private JLabel title;

	public View() {
		registro.setEditable(false);
	}

	public void agregarMensaje(String mensaje) {
		registro.setText(registro.getText() + "\n" + mensaje);
	}

	public JPanel getPanel() {
		return panel;
	}

}
