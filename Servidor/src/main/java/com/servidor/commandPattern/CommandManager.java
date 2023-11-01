package com.servidor.commandPattern;

import com.servidor.commandPattern.TipoInstumento.*;
import com.servidor.commandPattern.Instrumento.*;

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

	// Mapa de comandos
	private final Map<String, Command<?>> commandMap;

	public CommandManager() {
		commandMap = new HashMap<>();

		commandMap.put("GUARDAR_TIPO_INSTRUMENTO", guardarTipoInstrumentoCommand);
		commandMap.put("ELIMINAR_TIPO_INSTRUMENTO", eliminarTipoInstrumentoCommand);
		commandMap.put("LISTAR_TIPO_INSTRUMENTO", listarTipoInstrumentoCommand);
		commandMap.put("MODIFICAR_TIPO_INSTRUMENTO", modificarTipoInstrumentoCommand);
		commandMap.put("OBTENER_TIPO_INSTRUMENTO", obtenerTipoInstrumentoCommand);

		commandMap.put("GUARDAR_INSTRUMENTO", guardarInstrumentoCommand);
		commandMap.put("ELIMINAR_INSTRUMENTO", eliminarInstrumentoCommand);
		commandMap.put("LISTAR_INSTRUMENTO", listarInstrumentoCommand);
		commandMap.put("MODIFICAR_INSTRUMENTO", modificarInstrumentoCommand);
		commandMap.put("OBTENER_INSTRUMENTO", obtenerInstrumentoCommand);

		//DEBUG
		System.out.println("CommandManager: initialized");
	}

	public Command<?> getCommand(String commandName) {
		//DEBUG
		System.out.println("CommandManager: getCommand(" + commandName + ")");
		return commandMap.get(commandName);
	}
}