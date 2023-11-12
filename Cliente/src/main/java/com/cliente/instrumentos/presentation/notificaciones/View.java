package com.cliente.instrumentos.presentation.notificaciones;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
	private JTextPane registro;
	private JPanel panel;
	private JButton clean;
	private JTable notificacionesTable;
	private Controller controller;
	public View() {
		clean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controller.clear();
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void update(Observable updatedModel, Object properties) {
		int props = (int) properties;
		if ((props & Model.LIST) == Model.LIST) {
			int[] cols = {TableModel.MENSAJE};
			notificacionesTable.setModel(new TableModel(cols, controller.getModel().getList()));
			notificacionesTable.setRowHeight(40);
			notificacionesTable.setIntercellSpacing(new Dimension(10, 10));
			notificacionesTable.setShowGrid(false);
			TableColumnModel columnModel = notificacionesTable.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(100);
		}
		this.panel.revalidate();
	}
}
