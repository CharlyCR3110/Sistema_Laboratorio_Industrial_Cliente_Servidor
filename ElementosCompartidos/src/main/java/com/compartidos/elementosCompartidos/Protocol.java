package com.compartidos.elementosCompartidos;

public class Protocol {
	public static final String SERVER = "localhost";
	public static final int PORT = 12345;

	public static final int SYNC = 10;
	public static final int ASYNC = 11;
	public static final int DELIVER=12;	// async
	public static final int NORMAL=13;	// sync
	public static final int DISCONNECT=14;
	public static final int ERROR_NO_ERROR=1;
	public static final int ERROR_ERROR=0;

	// 									-------- MENSAJES --------
	// TipoInstrumento
	public static final String GUARDAR_TIPO_INSTRUMENTO = "GUARDAR_TIPO_INSTRUMENTO";
	public static final String ELIMINAR_TIPO_INSTRUMENTO = "ELIMINAR_TIPO_INSTRUMENTO";
	public static final String LISTAR_TIPO_INSTRUMENTO = "LISTAR_TIPO_INSTRUMENTO";
	public static final String MODIFICAR_TIPO_INSTRUMENTO = "MODIFICAR_TIPO_INSTRUMENTO";
	public static final String OBTENER_TIPO_INSTRUMENTO = "OBTENER_TIPO_INSTRUMENTO";

	// Instrumento
	public static final String GUARDAR_INSTRUMENTO = "GUARDAR_INSTRUMENTO";
	public static final String ELIMINAR_INSTRUMENTO = "ELIMINAR_INSTRUMENTO";
	public static final String LISTAR_INSTRUMENTO = "LISTAR_INSTRUMENTO";
	public static final String MODIFICAR_INSTRUMENTO = "MODIFICAR_INSTRUMENTO";
	public static final String OBTENER_INSTRUMENTO = "OBTENER_INSTRUMENTO";

	// Calibracion
	public static final String GUARDAR_CALIBRACION = "GUARDAR_CALIBRACION";
	public static final String ELIMINAR_CALIBRACION = "ELIMINAR_CALIBRACION";
	public static final String LISTAR_CALIBRACION = "LISTAR_CALIBRACION";
	public static final String MODIFICAR_CALIBRACION = "MODIFICAR_CALIBRACION";
	public static final String OBTENER_CALIBRACION = "OBTENER_CALIBRACION";

	// Medicion
	public static final String MODIFICAR_MEDICION = "MODIFICAR_MEDICION";

	// Close
	public static final String CLOSE = "CLOSE";

}
