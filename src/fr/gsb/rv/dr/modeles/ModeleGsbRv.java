package fr.gsb.rv.dr.modeles;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author developpeur
 */
import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.entities.RapportVisite ;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ModeleGsbRv {
    
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select v.vis_matricule , v.vis_nom , v.vis_prenom , t.tra_role , t.jjmmaa  "
                    + "from Visiteur v "
                    + "inner join Travailler t  "
                    + "on t.vis_matricule=v.vis_matricule  "
                    + "where tra_role='Délégué' "
                    + "and jjmmaa=(select max(jjmmaa) "
                    + "from Travailler t1 "
                    + "where t.vis_matricule=t1.vis_matricule "
                    + "and v.vis_matricule = ? "
                    + "and v.vis_mdp =  ?)";    
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setString( 2 , mdp );
            ResultSet resultat = requetePreparee.executeQuery() ;
            if( resultat.next() ){
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultat.getString( "vis_nom" ));
                visiteur.setPrenom( resultat.getString( "vis_prenom" )) ;
                
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
}
     
     public static List<Praticien> getPraticiensHesitants() throws ConnexionException {
       
        Connection connexion = ConnexionBD.getConnexion() ;

        String requete = "SELECT p.pra_num, pra_nom, pra_ville, pra_coefnotoriete, rap_date_visite, rap_coeff_confiance\r\n"
    			+ "FROM (SELECT pra_num ,MAX(rap_num) as num FROM RapportVisite   GROUP BY pra_num) m\r\n"
    			+ "INNER JOIN RapportVisite  r\r\n"
    			+ "ON m.pra_num = r.pra_num and r.rap_num = m.num\r\n"
    			+ "INNER JOIN Praticien p\r\n"
    			+ "ON p.pra_num = r.pra_num;";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
    
           
            System.out.println( requetePreparee.toString() ) ;
           
            List<Praticien> praticiens = new ArrayList<Praticien>() ;
           
            ResultSet resultat = requetePreparee.executeQuery() ;
                      
            //System.out.println( "1" ) ;
           
            while ( resultat.next() ){
                Praticien praticien = new Praticien() ;
                praticien.setNumero( resultat.getInt( "pra_num" ) );
                praticien.setNom( resultat.getString("pra_nom") );
                praticien.setVille( resultat.getString("pra_ville") );
                praticien.setCoefNotoriete( resultat.getFloat("pra_coefnotoriete"));
                praticien.setDateDerniereVisite( resultat.getDate("rap_date_visite").toLocalDate());
                praticien.setDernierCoefConfiance( resultat.getInt("rap_coeff_confiance") );
                             
                praticiens.add(praticien);
            }
            requetePreparee.close() ;
            return praticiens ;
        }
       
        catch( Exception e ){
            System.out.println( "Pb connexion BD" ) ;
            return null ;    
        }
    
      }
     
     public static List<Visiteur> getVisiteurs() throws ConnexionException {
       
        Connection connexion = ConnexionBD.getConnexion() ;

        String requete ="select vis_matricule, vis_nom, vis_prenom  from Visiteur;";

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
    
           
            System.out.println( requetePreparee.toString() ) ;
           
            List<Visiteur> visiteurs = new ArrayList<Visiteur>() ;
           
            ResultSet resultat = requetePreparee.executeQuery() ;
                      
            //System.out.println( "1" ) ;
           
            while ( resultat.next() ){
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( resultat.getString( "vis_matricule" ) );
                visiteur.setNom( resultat.getString("vis_nom") );
                visiteur.setPrenom( resultat.getString("vis_prenom") );
                      
                visiteurs.add(visiteur);
            }
            requetePreparee.close() ;
            return visiteurs ;
        }
       
        catch( Exception e ){
            System.out.println( "Pb connexion BD" ) ;
            return null ;    
        }
    
      }
     
     public static List<RapportVisite> getRapportsVisite(String matricule, int mois, int annee) throws ConnexionException {
       
        Connection connexion = ConnexionBD.getConnexion() ;

        String requete = "select * \n" +
                            "from RapportVisite r, Motif_visite m, Praticien p \n"   +
                            "where p.pra_num = r.pra_num \n" +
                            "and m.motif_num  = r.motif_num  \n" +
                            "and vis_matricule = ? \n" +
                            "and month(r.rap_date_visite) = ? \n" +
                            "and year(r.rap_date_visite) = ? \n"
                ;

        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
    
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setInt(2 , mois );
            requetePreparee.setInt( 3 , annee );
           
            System.out.println( requetePreparee.toString() ) ;
           
            List<RapportVisite> rapportsVisite = new ArrayList<RapportVisite>() ;
           
            ResultSet resultat = requetePreparee.executeQuery() ;
                      
            //System.out.println( "1" ) ;
           
            while ( resultat.next() ){
                RapportVisite rapportVisite = new RapportVisite() ;
                rapportVisite.setNumero( resultat.getInt( "rap_num" ) );
                rapportVisite.setCoefConfiance( resultat.getInt("rap_coeff_confiance") );
                rapportVisite.setDateVisite(resultat.getDate("rap_date_visite").toLocalDate());
                rapportVisite.setDateRedaction(resultat.getDate("rap_date_saisie").toLocalDate());
                rapportVisite.setBilan( resultat.getString( "rap_bilan" ) );
                rapportVisite.setMotif( resultat.getString( "motif_libelle" ) );
                rapportVisite.setLu( resultat.getBoolean( "rap_lu" ) );
                
                
                Praticien praticien = new Praticien() ;
                praticien.setNumero( resultat.getInt( "pra_num" ) );
                praticien.setNom( resultat.getString("pra_nom") );
                praticien.setVille( resultat.getString("pra_ville") );
                praticien.setCoefNotoriete( resultat.getFloat("pra_coefnotoriete"));
                //praticien.setDateDerniereVisite( resultat.getDate("rap_date_visite").toLocalDate());
                //praticien.setDernierCoefConfiance( resultat.getInt("rap_coef_confiance") );
                
                rapportVisite.setLePraticien( praticien );
                      
                rapportsVisite.add(rapportVisite);
                
                System.out.println( "-> " + rapportVisite ) ;
                
            }
            requetePreparee.close() ;
            return rapportsVisite ;
        }
       
        catch( Exception e ){
            System.out.println( "Pb connexion BD" ) ;
            return null ;    
        }
    
      }
     
     public static void setRapportVisiteLu(String matricule, int numRapport)  {
       
        

        String requete ="update RapportVisite" +
                        " set rap_lu = 1" + 
                        " where rap_num = ?" +
                        " and vis_matricule = ?" ;

        try {
            Connection connexion = ConnexionBD.getConnexion() ;
            System.out.println( "1 " + requete ) ;
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            System.out.println( "2 " + requetePreparee.toString()) ;
            requetePreparee.setInt( 1 , numRapport );
            System.out.println( "3 " + requetePreparee.toString()) ;
            requetePreparee.setString( 2 , matricule );
            System.out.println( "4 " + requetePreparee.toString()) ;
           
            System.out.println( requetePreparee.toString() ) ;
           
            requetePreparee.executeUpdate() ;
            //requetePreparee.execute() ;
                      
            requetePreparee.close() ;
        }
       
        catch( Exception e ){
            System.out.println( "Pb connexion BD" ) ;
        }
    
      }
}