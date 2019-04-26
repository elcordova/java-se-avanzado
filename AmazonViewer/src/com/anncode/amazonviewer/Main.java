package com.anncode.amazonviewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.anncode.amazonviewer.model.*;
import com.anncode.util.AmazonUtil;
import com.elcordova.makereport.Report;


/**
 * <h1>Amazon Viewer</h1>
 * Amazon viewer es un programa que permite visualizar Peliculas, Series (y sus respectivos capitulos),
 * libros y revistas.
 * <p>
 * ademas de genera reportes
 *
 * @author ecordova
 * @version 1.1
 * @since 2019
 */
public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        showMenu();
    }

    public static void showMenu() {
        int exit = 0;
        do {

            System.out.println("BIENVENIDOS AMAZON VIEWER");
            System.out.println("");
            System.out.println("Selecciona el número de la opción deseada");
            System.out.println("1. Movies");
            System.out.println("2. Series");
            System.out.println("3. Books");
            System.out.println("4. Magazines");
            System.out.println("5. Report");
            System.out.println("6. Report Today");
            System.out.println("0. Exit");

            //Leer la respuesta del usuario
            int response = AmazonUtil.validateUserResponseMenu(0, 6);

            switch (response) {
                case 0:
                    //salir
                    exit = 0;
                    break;
                case 1:
                    showMovies();
                    break;
                case 2:
                    showSeries();
                    break;
                case 3:
                    showBooks();
                    break;
                case 4:
                    showMagazines();
                    break;
                case 5:
                    makeReport();
                    exit = 1;
                    break;
                case 6:
                    //Date date = new Date();
                    makeReport(new Date());
                    exit = 1;
                    break;

                default:
                    System.out.println();
                    System.out.println("....¡¡Selecciona una opción!!....");
                    System.out.println();
                    exit = 1;
                    break;
            }


        } while (exit != 0);
    }

    static ArrayList<Movie> movies = new ArrayList();

    public static void showMovies() {
        movies = Movie.makeMoviesList();
        int exit = 1;

        do {
            System.out.println();
            System.out.println(":: MOVIES ::");
            System.out.println();

            AtomicInteger atomicInteger = new AtomicInteger(1);
            movies.forEach(movie ->System.out.println(atomicInteger.getAndIncrement()+".-"+movie.getTitle() + " Visto: " + movie.isViewed()) );

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            int response = AmazonUtil.validateUserResponseMenu(0, movies.size());

            if (response == 0) {
                exit = 0;
                showMenu();
                break;
            }
            if (response > 0) {
                Movie movieSelected = movies.get(response - 1);
                movieSelected.view();
            }


        } while (exit != 0);

    }

    static ArrayList<Serie> series = Serie.makeSeriesList();

    public static void showSeries() {
        int exit = 1;

        do {
            System.out.println();
            System.out.println(":: SERIES ::");
            System.out.println();

            for (int i = 0; i < series.size(); i++) { //1. Serie 1
                System.out.println(i + 1 + ". " + series.get(i).getTitle() + " Visto: " + series.get(i).isViewed());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            int response = AmazonUtil.validateUserResponseMenu(0, series.size());

            if (response == 0) {
                exit = 0;
                showMenu();
            }

            if (response > 0) {
                showChapters(series.get(response - 1).getChapters());
            }


        } while (exit != 0);
    }

    public static void showChapters(ArrayList<Chapter> chaptersOfSerieSelected) {
        int exit = 1;

        do {
            System.out.println();
            System.out.println(":: CHAPTERS ::");
            System.out.println();


            for (int i = 0; i < chaptersOfSerieSelected.size(); i++) { //1. Chapter 1
                System.out.println(i + 1 + ". " + chaptersOfSerieSelected.get(i).getTitle() + " Visto: " + chaptersOfSerieSelected.get(i).isViewed());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            int response = AmazonUtil.validateUserResponseMenu(0, chaptersOfSerieSelected.size());

            if (response == 0) {
                exit = 0;
            }


            if (response > 0) {
                Chapter chapterSelected = chaptersOfSerieSelected.get(response - 1);
                chapterSelected.view();
            }
        } while (exit != 0);
    }

    static ArrayList<Book> books = Book.makeBookList();

    public static void showBooks() {
        int exit = 1;

        do {
            System.out.println();
            System.out.println(":: BOOKS ::");
            System.out.println();

            for (int i = 0; i < books.size(); i++) { //1. Book 1
                System.out.println(i + 1 + ". " + books.get(i).getTitle() + " Leído: " + books.get(i).isReaded());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            int response = AmazonUtil.validateUserResponseMenu(0, books.size());

            if (response == 0) {
                exit = 0;
                showMenu();
            }

            if (response > 0) {
                Book bookSelected = books.get(response - 1);
                bookSelected.view();
            }

        } while (exit != 0);
    }

    public static void showMagazines() {
        ArrayList<Magazine> magazines = Magazine.makeMagazineList();
        int exit = 0;
        do {
            System.out.println();
            System.out.println(":: MAGAZINES ::");
            System.out.println();

            for (int i = 0; i < magazines.size(); i++) { //1. Book 1
                System.out.println(i + 1 + ". " + magazines.get(i).getTitle());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            int response = AmazonUtil.validateUserResponseMenu(0, 0);

            if (response == 0) {
                exit = 0;
                showMenu();
            }


        } while (exit != 0);
    }

    public static void makeReport() {

        Report report = new Report();
        report.setNameFile("reporte");
        report.setExtension("txt");
        StringBuilder contentReport = new StringBuilder();
        contentReport.append(":: VISTOS ::\n");

        movies.stream()
                .filter(movie -> movie.getIsViewed())
                .forEach(m -> contentReport.append(m.toString() + "\n"));

//        Predicate<Serie> chaptersSerie = serie -> serie.getIsViewed();
        Consumer<Serie> serieEach = serie -> {
            ArrayList<Chapter> chapters = serie.getChapters();
            chapters.stream()
                    .filter(chapter -> chapter.getIsViewed())
                    .forEach(chapter -> contentReport.append(chapter.toString() + "\n"));
        };
        series.stream().forEach(serieEach);

        books.stream()
                .filter(book -> book.getIsReaded())
                .forEach(b -> contentReport.append(b.toString() + "\n"));

        report.setContent(contentReport.toString());
        report.makeReport();
        System.out.println("Reporte Generado");
        System.out.println();
    }

    public static void makeReport(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-h-m-s-S");
        String dateString = df.format(date);
        Report report = new Report();

        report.setNameFile("reporte" + dateString);
        report.setExtension("txt");


        SimpleDateFormat dfNameDays = new SimpleDateFormat("E, W MMM Y");
        dateString = dfNameDays.format(date);
        StringBuilder contentReport = new StringBuilder();
        contentReport.append(":: VISTOS :: \n Date: " + dateString + "\n\n\n");
        Predicate<Film> isViewed = movie -> movie.getIsViewed();
        movies.stream().filter(isViewed).forEach(movie -> contentReport.append(movie.toString() + "\n"));
        Consumer<Serie> serieEach = serie -> {
            ArrayList<Chapter> chapters = serie.getChapters();
            chapters.stream()
                    .filter(isViewed)
                    .forEach(chapter -> contentReport.append(chapter.toString() + "\n"));
        };
        series.stream().forEach(serieEach);
        books.stream().filter(book -> book.getIsReaded()).forEach(book->contentReport.append(book.toString() + "\n"));
        report.setContent(contentReport.toString());
        report.makeReport();

        System.out.println("Reporte Generado");
        System.out.println();
    }

}















