package fr.gsb.rv.dr.technique;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author developpeur
 */
public class ConnexionException extends Exception {
    
    @Override
    public String getMessage(){
        return "[Nok] Connexion BD" ;
    }
    
}
