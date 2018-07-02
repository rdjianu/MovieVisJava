/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

/**
 *
 * @author Radu
 */
public class Movie {
    String title;
    Actor[] actors;
    int releaseYear;
    double rating;
    String[] directors;    
    String[] genres; 
    
    public Movie(String title, String[] genres, String[] directors, Actor[] actors, int year, double rating){
        this.title = title;
        this.genres = genres;
        this.directors = directors;
        this.actors = actors;
        this.releaseYear = year;
        this.rating = rating;
    }
}
