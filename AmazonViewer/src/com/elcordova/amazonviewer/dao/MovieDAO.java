package com.elcordova.amazonviewer.dao;

import com.anncode.amazonviewer.model.Movie;
import com.elcordova.amazonviewer.db.IDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.elcordova.amazonviewer.db.DataBase.*;

public interface MovieDAO extends IDBConnection {

    default Movie setMovieViwed(Movie movie) {
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
