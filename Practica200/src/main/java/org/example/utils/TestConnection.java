package org.example.utils;

import org.example.utils.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        try {
            Connection connection =
                    DatabaseConnection.getInstance().getConnection();

            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexion correcta con MariaDB");
                System.out.println("Base de datos: "
                        + connection.getCatalog());
            }

        } catch (Exception e) {
            System.out.println("No se pudo conectar con MariaDB");
            e.printStackTrace();
        }
    }
}
