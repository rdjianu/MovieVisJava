/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movievis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author Radu
 */
public class MovieData extends ConnectedItems{
    
    private ArrayList<Actor> actors;
    private ArrayList<Movie> movies;
    
    public MovieData(String actorFile, String movieFile){
        
        BufferedReader reader;
        
        try{
            
            this.actors = new ArrayList<Actor>();
            
            reader = new BufferedReader(new FileReader(actorFile));
            String line = null;
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t");
                
                String name = fields[0].trim();
                String gender = fields[1].trim();
                int birthYear = Integer.parseInt(
                        fields[2].trim().substring(
                                fields[2].trim().length()-4));
                
                int deathYear = -1;
                if (fields[3].trim().length() != 0)
                    deathYear = Integer.parseInt(
                            fields[3].trim().substring(
                                    fields[3].trim().length()-4));
                
                String height = fields[4].trim();
                
                Actor actor = new Actor(name, gender, height, birthYear, deathYear);
                this.actors.add(actor);
            }            
            reader.close();            
            
            
            movies = new ArrayList<Movie>();
            
            reader = new BufferedReader(new FileReader(movieFile));
            line = null;
            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\t");
                
                String title = fields[0].trim();
                int year = Integer.parseInt(fields[1].trim());
                double rating = Double.parseDouble(fields[2].trim());
                String[] genres = fields[3].split(",");
                for (int j=0; j<genres.length; j++)
                    genres[j] = genres[j].trim();
                String[] directors = fields[4].split(",");
                 for (int j=0; j<directors.length; j++)
                    directors[j] = directors[j].trim();               
                String[] actorStrings = fields[5].split(",");
                
                ArrayList<Actor> actors = new ArrayList<Actor>();
                for (int j=0; j<actorStrings.length; j++){
                    System.out.println("Find actor: " + actorStrings[j]);
                    Actor a = findActor(actorStrings[j].trim());
                    if (a  == null)
                        System.out.println("coudlnt find actor " + actorStrings[j]);
                    else 
                        actors.add(a);
                }
                
                if (actors.size() == 0) continue;

                Movie m = new Movie(title, genres, directors, actors.toArray(new Actor[]{}), year, rating);
                movies.add(m);
            }
            
            reader.close();
            
            computeConnections();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private Actor findActor(String actorName){
        for (int i=0; i<actors.size(); i++)
            if (actors.get(i).name.equals(actorName))
                return actors.get(i);
        return null;
    }
    
    public Movie getMovie(int index){
        return movies.get(index);
    }

   
    public double getSimilarity(int movieIndex1, int movieIndex2) {
        Movie m1 = movies.get(movieIndex1);
        Movie m2 = movies.get(movieIndex2);
        
        double ratingSimilarity = 1 - (Math.abs(m1.rating - m2.rating) / 10.);
        
        int sharedGenres = 0;
        for (int i=0; i<m1.genres.length; i++)
            for (int j=0; j<m2.genres.length; j++)
                if (m1.genres[i].equals(m2.genres[j])){
                    sharedGenres++;
                    break;
                }        
        double genreSimilarity = ((double) sharedGenres / Math.max(m1.genres.length, m2.genres.length));
        
        int sharedActors = 0;
        for (int i=0; i<m1.actors.length; i++)
            for (int j=0; j<m2.actors.length; j++)
                if (m1.actors[i].equals(m2.actors[j])){
                    sharedActors++;
                   
                    break;
                }        
        double actorSimilarity = Math.min(1.,Math.sqrt(sharedActors)/2);
        
        double yearSimilarity =  1 - (Math.abs(m1.releaseYear - m2.releaseYear) / 30.);
        if (yearSimilarity < 0) yearSimilarity = 0;
        
      //  System.out.println(actorSimilarity);
        
        return 0.1*ratingSimilarity + 0.4*genreSimilarity + 0.4*actorSimilarity * 0.1*yearSimilarity;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    protected double computeConnection(int index1, int index2) {
        double sim = getSimilarity(index1, index2);
        if (getSimilarity(index1, index2) >= 0.3)
            return sim;
        else return 0;
    }
    
    public void save(String dirPath){
        //save actors
        
       
        
        try{
            FileWriter writer = new FileWriter(dirPath + "/actors.txt");
            
            for (int i=0; i<actors.size(); i++){
                Actor actor = actors.get(i);
                
                
                writer.write(actor.name + "\t" + 
                        actor.gender + "\t" +
                        actor.birthYear + "\t" +
                        (actor.deathYear == -1 ? "" : actor.deathYear) + "\t" +
                        actor.height);
                
                if (i != actors.size()-1)
                    writer.write("\n");
  
            }
            writer.close();
            
            writer = new FileWriter(dirPath + "/movies.txt");
            
            for (int i=0; i<movies.size(); i++){
                Movie movie = movies.get(i);
                
                String genresString = "";
                for (int j=0; j<movie.genres.length; j++)
                    if (j == movie.genres.length-1)
                        genresString += movie.genres[j];
                    else 
                        genresString += movie.genres[j] + ",";
                
                String actorsString = "";
                for (int j=0; j<movie.actors.length; j++)
                    if (j == movie.actors.length-1)
                        actorsString += movie.actors[j].name;
                    else 
                        actorsString += movie.actors[j].name + ",";

                String directorsString = "";
                for (int j=0; j<movie.directors.length; j++)
                    if (j == movie.directors.length-1)
                        directorsString += movie.directors[j];
                    else 
                        directorsString += movie.directors[j] + ",";                
                
                
                
                writer.write( movie.title + "\t" +
                        movie.releaseYear + "\t" + 
                        movie.rating + "\t" +
                        genresString + "\t" + 
                        directorsString + "\t" +
                        actorsString);
                
                if (i != movies.size()-1)
                    writer.write("\n");
                
            }
           writer.close();
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }        
        
        //save movies
    }
    
    
    public int[] selectMovieIndeces(MovieSelector selector){
        ArrayList<Integer> selected = new ArrayList<Integer>();
        for (int i=0; i<movies.size(); i++)
            if (selector.selected(movies.get(i)))
                selected.add(i);
        
        int[] ret = new int[selected.size()];
        for (int i=0; i<ret.length; i++)
            ret[i] = selected.get(i);
        
        return ret;
    }
    
    public Movie[] selectMovies(MovieSelector selector){
        ArrayList<Movie> selected = new ArrayList<Movie>();
        for (int i=0; i<movies.size(); i++)
            if (selector.selected(movies.get(i)))
                selected.add(movies.get(i));
        
        return selected.toArray(new Movie[]{});
    } 
    
    public void removeMovies(MovieSelector selector){
        for (int i=0; i<movies.size(); i++){
            if (selector.selected(movies.get(i))){
                movies.remove(i);
                i--;
            }
        }
        this.computeConnections();
            
    }
}
