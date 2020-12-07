package fr.gsb.rv.dr.modeles;


import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.Visiteur;
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
        
        // Code de test à compléter
        
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
            requetePreparee.setString(2, mdp);
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
                
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                visiteur.setPrenom(resultat.getString( "vis_prenom" ) ) ;
                
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
    
    
        public static List<Praticien> getPraticiensHesitants() throws ConnexionException{

        Connection connexion = ConnexionBD.getConnexion() ;

        List<Praticien> praticiens = new ArrayList<Praticien>();

        String requete = "select p.pra_num,p.pra_nom,p.pra_ville,p.pra_coefnotoriete,max(r.rap_date_visite) as date,r.rap_coef_confiance " 
                + "from Praticien p "
                + "inner join RapportVisite r "
                + "on p.pra_num = r.pra_num "
                + "group by p.pra_num";


        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;

            ResultSet resultat = requetePreparee.executeQuery() ;

            if( resultat.next() ){
               do{
                Praticien praticien = new Praticien() ;
                praticien.setNumero(resultat.getString("pra_num") );
                praticien.setNom(resultat.getString("pra_nom") );
                praticien.setVille(resultat.getString("pra_ville") );
                praticien.setCoefNotoriete(Float.valueOf(resultat.getString("pra_coefnotoriete")) );                
                praticien.setDateDernierVisite(Date.valueOf(resultat.getString("date")).toLocalDate());
                praticien.setDernierCoefConfiance(Integer.valueOf(resultat.getString("rap_coef_confiance")));


                praticiens.add(praticien);
               }while(resultat.next() == true);

               return praticiens;

            }
            else {
                return null ;
            }

        }
        catch( Exception e ){
            return null ;
        }
        }
}

