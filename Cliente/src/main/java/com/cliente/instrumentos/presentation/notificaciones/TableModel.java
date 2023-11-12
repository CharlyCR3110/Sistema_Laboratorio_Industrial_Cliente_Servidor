package com.cliente.instrumentos.presentation.notificaciones;


import com.compartidos.elementosCompartidos.MensajeAsincrono;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel implements javax.swing.table.TableModel {
    List<MensajeAsincrono> rows;
    int[] cols;

    public TableModel(int[] cols, List<MensajeAsincrono> rows){
        this.cols=cols;
        this.rows=rows;
        initColNames();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int col){
        return colNames[cols[col]];
    }

    public Class<?> getColumnClass(int col){
        switch (cols[col]){
            default: return super.getColumnClass(col);
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        MensajeAsincrono mensajeAsincrono = rows.get(row);
		if (cols[col] == MENSAJE) {
			return mensajeAsincrono.getTipo() + " -- " + mensajeAsincrono.getMensaje();
		}
		return "";
	}

    public MensajeAsincrono getRowAt(int row) {
        return rows.get(row);
    }

    public static final int MENSAJE = 0;

    String[] colNames = new String[1];
    private void initColNames(){
        colNames[MENSAJE]= "LOG";
    }

}
