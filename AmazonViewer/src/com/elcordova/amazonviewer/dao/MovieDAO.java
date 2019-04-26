package com.elcordova.amazonviewer.dao;

import com.anncode.amazonviewer.model.Movie;
import com.elcordova.amazonviewer.db.IDBConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.elcordova.amazonviewer.db.DataBase.*;

public interface MovieDAO extends IDBConnection {

    default Movie setMovieViewed(Movie movie) {
        if (!movie.getIsViewed()){
            try(Connection connection = connectToDB()) {
                Statement statement = connection.createStatement();
                Timestamp d = new Timestamp(new Date().getTime());
                String query = "INSERT INTO "+TVIEWED +
                        "("+TVIEWED_ID_MATERIAL+","+TVIEWED_ID_ELEMENT+","+TVIEWED_ID_USER+","+TVIEWED_DATE+")" +
                        "VALUES(1,"+movie.getId()+",1,'"+d+"')";
                if (statement.executeUpdate(query)> 0){
                    System.out.println("Se marco en visto");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return movie;
    }

    default ArrayList<Movie> read() {
        ArrayList<Movie> movies = new ArrayList();

        try (Connection connection = connectToDB()) {
            String stringQuery = "SELECT m."+TMOVIE_ID+", m."+TMOVIE_TITLE+", m.genre, m.creator, m.duration, m.year, \n" +
                    "case when v.id_material is null then FALSE else TRUE END as 'visto'  \n" +
                    "FROM movie m\n" +
                    "LEFT JOIN viewed v ON m.id=v.id_element and v.id_material=1";
            PreparedStatement preparedStatement = connection.prepareStatement(stringQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getString(TMOVIE_TITLE),
                        rs.getString(TMOVIE_GENRE),
                        rs.getString(TMOVIE_CREATOR),
                        Integer.valueOf(rs.getString(TMOVIE_DURATION)),
                        Short.valueOf(rs.getString(TMOVIE_YEAR)),
                        rs.getBoolean("visto")
                );
                movie.setId(Integer.valueOf(rs.getString(TMOVIE_ID)));
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer los datos de la DB");
        }

        return movies;
    }

}
