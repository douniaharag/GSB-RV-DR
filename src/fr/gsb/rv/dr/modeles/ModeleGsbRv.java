package fr.gsb.rv.dr.modeles;


import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeleGsbRv {
    
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{
        
        // Code de test à compléter
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
       /*  String requete = "select v.vis_matricule, v.vis_nom, v.vis_prenom, t.tra_role, t.jjmmaa " +
                            "from Travailler t  " +
                            "inner join Visiteur v\n" +
                            "on v.vis_matricule = t.vis_matricule" +
                            "where t.tra_role = \"Délégué\"" +
                            "and jjmmaa = (" +
                            "select max(jjmmaa)" +
                            "from Travailler t2" +
                            "where t2.vis_matricule = t.vis_matricule) " +
                            "and v.vis_matricule = \""+matricule+"\" " +
                            "and v.vis_mdp = \""+mdp+"\";" ;*/
       
            String requete = "select vis_nom, vis_prenom "
                + "from Visiteur "
                + "INNER JOIN Travailler "
                + "ON Visiteur.vis_matricule = Travailler.vis_matricule "
                + "where Visiteur.vis_matricule = ? "
                + "AND Travailler.tra_role like '%Délégué%'";      
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
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
}
