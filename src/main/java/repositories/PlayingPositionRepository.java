package repositories;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayingPositionRepository {

    // Recuperar el id correspondiente al nombre
    public Integer idPositionByNombre (String positionName) {
        Integer positionId = 0;

        // ? se usa para meter variables de código en los preparedStatment del conector jdbc que estamos usando
        String sql = "SELECT position_id FROM playing_position WHERE name = ?";

        // Instanciamos conexion
        DBConnection dbConnection = new DBConnection();

        // try con recuersos, instanciamos conexion y el statement, asi aseguramos que se cierre al terminar
        try (
                Connection connection = dbConnection.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
            ) {

            // asignamos variable a la sentencia
            preparedStatement.setString(1,positionName);

            // El .executeQuery nos devuelve el numero de de resultados
            ResultSet resultSet = preparedStatement.executeQuery();

            // Y aqui lo filtramos a como queramos.
            if (resultSet.next()){
                positionId = resultSet.getInt("position_id");
            }

        } catch (SQLException e) {
            // Devolviendo un 0 el servicio gestionara la excepcion (que no deberia producirse porque ya hemos gestionado)
            positionId = null;
        }
        return positionId;
    }
}
