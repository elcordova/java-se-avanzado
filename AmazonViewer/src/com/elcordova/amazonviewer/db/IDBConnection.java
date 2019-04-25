package com.elcordova.amazonviewer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import static com.elcordova.amazonviewer.db.DataBase.*;

public interface IDBConnection {
    default Connection connectToDB() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL+DB,USER,PASS);
            if (connection != null){
                System.out.println(" Se estableció la conexion :D");
                return connection;
            }

        } catch (Exception e){

        }finally {
            return connection;
        }
    }
}
