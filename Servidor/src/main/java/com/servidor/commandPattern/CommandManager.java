package com.servidor.commandPattern;

import com.compartidos.elementosCompartidos.Protocol;
import com.servidor.commandPattern.Medicion.ModificarMedicionCommand;
import com.servidor.commandPattern.TipoInstumento.*;
import com.servidor.commandPattern.Instrumento.*;
import com.servidor.commandPattern.Calibracion.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

	// Atributos finales estáticos para los comandos
	private final static GuardarTipoInstrumentoCommand guardarTipoInstrumentoCommand = new GuardarTipoInstrumentoCommand();
	private final static EliminarTipoInstrumentoCommand eliminarTipoInstrumentoCommand = new EliminarTipoInstrumentoCommand();
	private final static ListarTipoInstrumentoCommand listarTipoInstrumentoCommand = new ListarTipoInstrumentoCommand();
	private final static ModificarTipoInstrumentoCommand modificarTipoInstrumentoCommand = new ModificarTipoInstrumentoCommand();
	private final static ObtenerTipoInstrumentoCommand obtenerTipoInstrumentoCommand = new ObtenerTipoInstrumentoCommand();

	private final static GuardarInstrumentoCommand guardarInstrumentoCommand = new GuardarInstrumentoCommand();
	private final static EliminarInstrumentoCommand eliminarInstrumentoCommand = new EliminarInstrumentoCommand();
	private final static ListarInstrumentoCommand listarInstrumentoCommand = new ListarInstrumentoCommand();
	private final static ModificarInstrumentoCommand modificarInstrumentoCommand = new ModificarInstrumentoCommand();
	private final static ObtenerInstrumentoCommand obtenerInstrumentoCommand = new ObtenerInstrumentoCommand();

	private final static GuardarCalibracionCommand guardarCalibracionCommand = new GuardarCalibracionCommand();
	private final static EliminarCalibracionCommand eliminarCalibracionCommand = new EliminarCalibracionCommand();
	private final static ListarCalibracionCommand listarCalibracionCommand = new ListarCalibracionCommand();
	private final static ModificarCalibracionCommand modificarCalibracionCommand = new ModificarCalibracionCommand();
	private final static ObtenerCalibracionCommand obtenerCalibracionCommand = new ObtenerCalibracionCommand();

	private final static ModificarMedicionCommand modificarMedicionCommand = new ModificarMedicionCommand();
	// Mapa de comandos
	private final Map<String, Command<?>> commandMap;

	public CommandManager() {
		commandMap = new HashMap<>();

		// TipoInstrumento commands
		commandMap.put(Protocol.GUARDAR_TIPO_INSTRUMENTO, guardarTipoInstrumentoCommand);
		commandMap.put(Protocol.ELIMINAR_TIPO_INSTRUMENTO, eliminarTipoInstrumentoCommand);
		commandMap.put(Protocol.LISTAR_TIPO_INSTRUMENTO, listarTipoInstrumentoCommand);
		commandMap.put(Protocol.MODIFICAR_TIPO_INSTRUMENTO, modificarTipoInstrumentoCommand);
		commandMap.put(Protocol.OBTENER_TIPO_INSTRUMENTO, obtenerTipoInstrumentoCommand);

		// Instrumento commands
		commandMap.put(Protocol.GUARDAR_INSTRUMENTO, guardarInstrumentoCommand);
		commandMap.put(Protocol.ELIMINAR_INSTRUMENTO, eliminarInstrumentoCommand);
		commandMap.put(Protocol.LISTAR_INSTRUMENTO, listarInstrumentoCommand);
		commandMap.put(Protocol.MODIFICAR_INSTRUMENTO, modificarInstrumentoCommand);
		commandMap.put(Protocol.OBTENER_INSTRUMENTO, obtenerInstrumentoCommand);

		// Calibracion commands
		commandMap.put(Protocol.GUARDAR_CALIBRACION, guardarCalibracionCommand);
		commandMap.put(Protocol.ELIMINAR_CALIBRACION, eliminarCalibracionCommand);
		commandMap.put(Protocol.LISTAR_CALIBRACION, listarCalibracionCommand);
		commandMap.put(Protocol.MODIFICAR_CALIBRACION, modificarCalibracionCommand);
		commandMap.put(Protocol.OBTENER_CALIBRACION, obtenerCalibracionCommand);

		// Modificar Medicion command
		commandMap.put(Protocol.MODIFICAR_MEDICION, modificarMedicionCommand);
	}

	public Command<?> getCommand(String commandName) {
		return commandMap.get(commandName);
	}
}