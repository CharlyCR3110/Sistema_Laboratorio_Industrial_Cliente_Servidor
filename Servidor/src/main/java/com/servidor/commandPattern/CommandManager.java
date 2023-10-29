package com.servidor.commandPattern;

import com.servidor.commandPattern.TipoInstumento.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
	private final Map<String, Command<?>> commandMap;

	public CommandManager() {
		commandMap = new HashMap<>();

		// Commands para TipoInstrumento
		commandMap.put("GUARDAR_TIPO_INSTRUMENTO", new GuardarTipoInstrumentoCommand());
		commandMap.put("ELIMINAR_TIPO_INSTRUMENTO", new EliminarTipoInstrumentoCommand());
		commandMap.put("LISTAR_TIPO_INSTRUMENTO", new ListarTipoInstrumentoCommand());
		commandMap.put("MODIFICAR_TIPO_INSTRUMENTO", new ModificarTipoInstrumentoCommand());
		commandMap.put("OBTENER_TIPO_INSTRUMENTO", new ObtenerTipoInstrumentoCommand());

		//DEBUG
		System.out.println("CommandManager: initialized");
	}

	public Command<?> getCommand(String commandName) {
		//DEBUG
		System.out.println("CommandManager: getCommand(" + commandName + ")");
		return commandMap.get(commandName);
	}
}