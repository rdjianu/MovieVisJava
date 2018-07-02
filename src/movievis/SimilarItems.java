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
public interface SimilarItems {

    public int getCount();
    public double getSimilarity(int i, int j);
}
