package com.cliente.instrumentos.presentation.acercaDe;

import javax.swing.*;
import java.awt.*;

public class View {
	public View() {
		agregarElTextoAcercaDe();
	}
	private JPanel panel;
	private JLabel acercaDeLabel;

	// Metodo para agregar la informacion necesaria en la pestana de acerca de, se utiliza HTML para darle formato (MUY BASICO)
	private void agregarElTextoAcercaDe() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<style>");
		sb.append("body {");
		sb.append("    font-family: 'Arial', sans-serif;");
		sb.append("    background-color: #F4F4F4;");
		sb.append("    color: #333333;");
		sb.append("    text-align: center;");
		sb.append("}");
		sb.append("h1 {");
		sb.append("    font-size: 36px;");
		sb.append("    text-transform: uppercase;");
		sb.append("    color: #007BFF;");
		sb.append("}");
		sb.append("p {");
		sb.append("    font-size: 20px;");
		sb.append("    line-height: 1.5;");
		sb.append("}");
		sb.append("a {");
		sb.append("    color: #007BFF;");
		sb.append("    text-decoration: none;");
		sb.append("}");
		sb.append("</style>");
		sb.append("<body>");
		sb.append("<h1>Sylab: Sistema de Laboratorios</h1>");
		sb.append("<p>Version 2.0</p>");
		sb.append("<p>Copyright (C) 2023</p>");
		sb.append("<p>Carlos Gonzalez Garita</p>");
		sb.append("<h1>Contacto</h1>");
		sb.append("<p>carlosgarita3110@gmail.com</p>");

		// Cargar la imagen desde los recursos
		sb.append("<img src=\"" + getClass().getResource("/icon.png") + "\">");

		sb.append("</body>");
		sb.append("</html>");
		acercaDeLabel.setText(sb.toString());
	}

	public Component getPanel() {
		return panel;
	}
}
