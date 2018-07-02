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
public class Actor {
    String name;
    String gender;
    int birthYear;
    int deathYear;
    String height;
    
    
    public Actor(String name, String gender, String height, int birthYear, int deathYear){
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.name.equals(((Actor)obj).name))
            return true;
        return false;
    }
    
    
}
