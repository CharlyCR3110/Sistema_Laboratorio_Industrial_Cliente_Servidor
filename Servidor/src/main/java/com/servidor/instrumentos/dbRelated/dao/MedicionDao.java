package com.servidor.instrumentos.dbRelated.dao;

import com.compartidos.elementosCompartidos.Medicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicionDao {
	Connection connection;

	public MedicionDao(Connection connection) {
		this.connection = connection;
	}

	public int guardar(Medicion medicion, String calibracion_numero) {
		// Consulta SQL para insertar un registro en la tabla de mediciones
		String query = "INSERT INTO mediciones (referencia, medicion, calibracion_numero) VALUES (?, ?, ?)";
		try (PreparedStatement statement2 = connection.prepareStatement(query)) {
			// Setear los valores de la consulta SQL
			statement2.setInt(1, medicion.getReferencia());
			statement2.setInt(2, medicion.getMedicion());
			statement2.setInt(3, Integer.parseInt(calibracion_numero));

			// Ejecutar la consulta SQL y obtener el resultado
			return statement2.executeUpdate();
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			throw new RuntimeException("Error al guardar la medicion");
		}
	}

	public List<Medicion> listar(String calibracion_numero) {
		List<Medicion> mediciones = new ArrayList<>();

		// Consulta SQL para obtener las mediciones de una calibración
		String query = "SELECT * FROM mediciones WHERE calibracion_numero = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Setear el número de la calibración en la consulta SQL
			statement.setInt(1, Integer.parseInt(calibracion_numero));

			// Ejecutar la consulta SQL y obtener el resultado
			try (ResultSet resultSet = statement.executeQuery()) {
				// Recorrer el resultado obtenido
				while (resultSet.next()) {
					// Crear una nueva instancia de Medicion
					Medicion medicion = new Medicion();

					// Llenar los datos de la medicion con los datos obtenidos de la base de datos
					medicion.setNumero(resultSet.getInt("numero"));
					medicion.setReferencia(resultSet.getInt("referencia"));
					medicion.setMedicion(resultSet.getInt("medicion"));

					// Agregar la medicion a la lista de mediciones
					mediciones.add(medicion);
				}
			}
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error al listar las mediciones
			throw new RuntimeException("Error al listar las mediciones de la calibración: " + e.getMessage(), e);
		}
		// Retornar la lista de mediciones
		return mediciones;
	}

	public int modificar (Medicion medicion) {
		String query = "UPDATE mediciones SET referencia = ?, medicion = ? WHERE numero = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			// Setear los valores de la consulta SQL
			statement.setInt(1, medicion.getReferencia());
			statement.setInt(2, medicion.getMedicion());
			statement.setInt(3, medicion.getNumero());

			// Ejecutar la consulta SQL y obtener el resultado
			return statement.executeUpdate();
		} catch (SQLException e) {
			// Lanzar una excepción en caso de que ocurra un error
			throw new RuntimeException(e);
		}
	}
}
