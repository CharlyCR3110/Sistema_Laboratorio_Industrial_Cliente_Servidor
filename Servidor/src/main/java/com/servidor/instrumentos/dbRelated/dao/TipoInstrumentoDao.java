package com.servidor.instrumentos.dbRelated.dao;

import com.compartidos.elementosCompartidos.TipoInstrumento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoInstrumentoDao {
	private Connection connection;

	public TipoInstrumentoDao(Connection connection) {
		this.connection = connection;
	}

	public boolean tieneDuplicados(TipoInstrumento tipoInstrumento) {
		boolean r = false;
		// Consulta SQL para verificar si existe un registro con el mismo codigo
		String query = "SELECT * FROM tipo_instrumentos WHERE codigo = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Setear los valores de la consulta SQL
			statement.setString(1, tipoInstrumento.getCodigo());

			// Ejecutar la consulta SQL y obtener el resultado
			try (ResultSet resultSet = statement.executeQuery()) {
				// Si existe un registro con el mismo codigo, setear el resultado a true
				if (resultSet.next()) {
					// Obtener el numero de registros
					int rows = resultSet.getInt(1);

					// Si el numero de registros es mayor a 0, entonces existe un registro con el mismo codigo
					if (rows > 0) {
						r = true;
					}
				}
			}
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			throw new RuntimeException("Error al verificar duplicados: " + e.getMessage());
		}

		return r;
	}

	public int guardar(TipoInstrumento tipoInstrumento) {
		// comprueba si ya existe un TipoInstrumento con el mismo codigo
		if (tieneDuplicados(tipoInstrumento)) {
			return -1;
		}

		// Consulta SQL para insertar un TipoInstrumento
		String query = "INSERT INTO tipo_instrumentos (codigo, nombre, unidad) VALUES (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			// settear los valor
			statement.setString(1, tipoInstrumento.getCodigo());
			statement.setString(2, tipoInstrumento.getNombre());
			statement.setString(3, tipoInstrumento.getUnidad());

			// Ejecutar la consulta y guardar el numero de filas afectadas
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 1) {
				return 1;
			} else {
				throw new RuntimeException("No se pudo guardar el TipoInstrumento");
			}
		} catch (SQLIntegrityConstraintViolationException e){
			throw new RuntimeException("No se guardo el TipoInstrumento. Motivo: El código ya está en uso");
		} catch (SQLException e) {
			throw new RuntimeException("Error al guardar el TipoInstrumento: Motivo: " + e.getMessage());
		}
	}

	public List<TipoInstrumento> listar(String nombre) {
		List<TipoInstrumento> r = new ArrayList<>();

		// Query SQL para listar los TipoInstrumentos
		String query = "SELECT * FROM tipo_instrumentos";

		// Verificar si el nombre no es nulo ni está vacío, y si no lo está, agregar un WHERE a la consulta SQL
		if (nombre != null && !nombre.isEmpty()) {
			query += " WHERE nombre LIKE ?";
		}

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// setear el valor del nombre si no es nulo o vacío
			if (nombre != null && !nombre.isEmpty()) {
				// Utilizar % para indicar que el nombre puede contener el valor en cualquier posición
				statement.setString(1, "%" + nombre + "%");
			}

			// Ejecutar la consulta SQL y obtener el resultado
			try (ResultSet resultSet = statement.executeQuery()) {
				// Agregar los tipos a la lista
				while (resultSet.next()) {
					r.add(new TipoInstrumento(
							resultSet.getString("codigo"),
							resultSet.getString("nombre"),
							resultSet.getString("unidad")
					));
				}
			}
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			if (nombre == null || nombre.isEmpty()) {
				throw new RuntimeException("Error al listar los TipoInstrumentos: " + e.getMessage());
			} else {
				throw new RuntimeException("Error al listar los TipoInstrumentos con nombre " + nombre + ": " + e.getMessage());
			}
		}
		// Retornar la lista de tipos
		return r;
	}

	public int eliminar(String codigo) {
		String query = "DELETE FROM tipo_instrumentos WHERE codigo = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Setear los valores de la consulta SQL
			statement.setString(1, codigo);

			// Ejecutar la consulta SQL y obtener el resultado
			return statement.executeUpdate();
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			throw new RuntimeException("Error al eliminar el TipoInstrumento con código " + codigo + ": " + e.getMessage());
		}
	}

	public int modificar(TipoInstrumento tipoInstrumento) {
		// Consulta SQL para modificar un TipoInstrumento
		String query = "UPDATE tipo_instrumentos SET nombre = ?, unidad = ? WHERE codigo = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// settear los valores
			statement.setString(1, tipoInstrumento.getNombre());
			statement.setString(2, tipoInstrumento.getUnidad());
			statement.setString(3, tipoInstrumento.getCodigo());

			// Ejecutar la consulta y guardar el numero de filas afectadas
			int rowsAffected = statement.executeUpdate();

			// Retornar el número de filas afectadas
			return rowsAffected;
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			throw new RuntimeException("Error al modificar el TipoInstrumento: " + e.getMessage());
		}
	}

	public TipoInstrumento obtener(TipoInstrumento tipoInstrumento) {
		String query = "SELECT * FROM tipo_instrumentos WHERE codigo = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Setear los valores de la consulta SQL
			statement.setString(1, tipoInstrumento.getCodigo());
			// Ejecutar la consulta SQL y obtener el resultado
			try (ResultSet resultSet = statement.executeQuery()) {
				// Si existe un registro con el mismo codigo, setear el resultado a true
				if (resultSet.next()) {
					return new TipoInstrumento(
							resultSet.getString("codigo"),
							resultSet.getString("nombre"),
							resultSet.getString("unidad")
					);
				}
			}
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			throw new RuntimeException("Error al obtener el TipoInstrumento: " + e.getMessage());
		}
		return null;
	}
}