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
}
