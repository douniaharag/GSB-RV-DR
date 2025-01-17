/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;


import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.RapportVisite;
import fr.gsb.rv.dr.vues.VueConnexion;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import fr.gsb.rv.dr.PanneauAccueil;
import fr.gsb.rv.dr.PanneauPraticiens;
import fr.gsb.rv.dr.PanneauRapports;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;




/**
 *
 * @author developpeur
 */



/**public class Appli extends Application {
    PanneauPraticiens vuePraticiens = new PanneauPraticiens();
    PanneauRapports vueRapports = new PanneauRapports();
    
    @Override
    public void start(Stage primaryStage) {
        
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 900, 500);
        
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setResizable(false);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        PanneauAccueil vueAccueil = new PanneauAccueil();
        
        
        StackPane stack = new StackPane();
        
        
        stack.getChildren().addAll(vueAccueil,vuePraticiens,vueRapports);
        
        vueAccueil.toFront();
        
        
        
        root.setCenter(stack);
       
       
        
        

        
        
       
   
        
 
       
        
        
        
        
        
        MenuBar barreMenus = new MenuBar();
        Menu menuFichier = new Menu("Fichier");
        
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        
        
        
        
        
        MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
        itemSeDeconnecter.setDisable(true);
        
        SeparatorMenuItem separatorFichier = new SeparatorMenuItem();
        MenuItem itemQuitter = new MenuItem("Quitter");
        
        menuFichier.getItems().add(itemSeConnecter);
        
             
        menuFichier.getItems().add(itemSeDeconnecter);
        menuFichier.getItems().add(separatorFichier);
        menuFichier.getItems().add(itemQuitter);
        itemQuitter.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle( ActionEvent event ) {
                    Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                    alertQuitter.setTitle("Quitter");
                    alertQuitter.setHeaderText("Demande de confirmation");
                    alertQuitter.setContentText("Voulez-vous quitter l'application ?");
                    ButtonType btnOui = new ButtonType("Oui");
               
           

                    ButtonType btnNon = new ButtonType("Non");
          
    

                    alertQuitter.getButtonTypes().setAll(btnOui,btnNon);
                    Optional<ButtonType> reponse = alertQuitter.showAndWait();
                    if(reponse.get() == btnOui){
                        Platform.exit();
                    }
                    else {
                        alertQuitter.close();
                    };
                    
                }
            }
        );
        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X,KeyCombination.CONTROL_DOWN));
        
        
        barreMenus.getMenus().add(menuFichier);
        
        
        
        
        Menu menuRapports = new Menu("Rapports");
         menuRapports.setDisable(true);
        
        MenuItem itemConsulter = new MenuItem("Consulter");
        
        menuRapports.getItems().add(itemConsulter);
        barreMenus.getMenus().add(menuRapports);
        
        
        
         Menu menuPraticiens = new Menu("Praticiens");
         menuPraticiens.setDisable(true);
        MenuItem itemHesitant = new MenuItem("Hésitants");
        
        menuPraticiens.getItems().add(itemHesitant);
        barreMenus.getMenus().add(menuPraticiens);
        
        
        itemSeConnecter.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle( ActionEvent event ) {
                    itemSeConnecter.setDisable(true);
                    menuRapports.setDisable(true);
        
   
        
                }
            }
        );
        
        
        root.setTop(barreMenus);
        
        itemSeConnecter.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle( ActionEvent event ) {
                   
                    
//                    try {
   
//                       Connection conn= (Connection) ConnexionBD.getConnexion();
//                       Statement stmt =(Statement) conn.createStatement();
//                        ResultSet rst=stmt.executeQuery("select v.vis_matricule\n" +
//                        "from Visiteur v inner join Travailler t\n" +
//                        "where v.vis_matricule = t.vis_matricule\n" +
//                       "and t.tra_role = 'Délégué';");
                        
//                       while(rst.next()) {
//                            System.out.println(rst.getString("vis_matricule"));
//                        }
//                    }
//                   catch(Exception e){
//                       JOptionPane.showMessageDialog(null, "Erreur de connexion");
//                    }


//                    try {
//                        Visiteur vis = ModeleGsbRv.seConnecter("c14", "azerty");
//                        System.out.println(vis.toString());
//                    } catch (ConnexionException ex) {
//                        JOptionPane.showMessageDialog(null, "Erreur de connexion");
//                   }
                    
                    
                    
                    try {    
                        VueConnexion vue = new VueConnexion();
                        Optional<Pair<String,String>> reponse = vue.showAndWait();
                        if(reponse.isPresent()){
                            Visiteur vis = ModeleGsbRv.seConnecter(reponse.get().getKey(),reponse.get().getValue() );
                            if(vis != null){
                               
                               Session.ouvrir(vis);
                               

                               primaryStage.setTitle(vis.getNom()+" - GSB-RV-DR");
                               
                               menuRapports.setDisable(false);
                               menuPraticiens.setDisable(false);
                               itemSeConnecter.setDisable(true);
                               itemSeDeconnecter.setDisable(false);
                           }
                           else {
                            JOptionPane.showMessageDialog(null, "Erreur de connexion");
                        }
                           
                            
                        } 
                        
                   }
                    catch (ConnexionException ex) {
                        
                    }
                    

                    
                }
            }
        );
        
        itemSeDeconnecter.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle( ActionEvent event ) {
                    itemSeConnecter.setDisable(false);
                    itemSeDeconnecter.setDisable(true);
                    menuRapports.setDisable(true);
                    menuPraticiens.setDisable(true);

                    
                    Session.fermer();
                    primaryStage.setTitle("GSB-RV-DR");
                    vuePraticiens = new PanneauPraticiens();
                    vueRapports = new PanneauRapports();
                    
                    stack.getChildren().addAll(vuePraticiens,vueRapports);
                    vueAccueil.toFront();
   
        
                }
            }
        );
        

        itemConsulter.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle( ActionEvent event ) {
                    primaryStage.setTitle("[Rapports] " + " " + Session.getSession().getLeVisiteur().getNom()+ " " + Session.getSession().getLeVisiteur().getPrenom());
                    vueRapports.toFront();
   
        
                }
            }
        );

        itemHesitant.setOnAction(
            new EventHandler<ActionEvent>(){
                @Override
                public void handle( ActionEvent event ) {
                    primaryStage.setTitle("[Praticiens] " + " " + Session.getSession().getLeVisiteur().getNom()+ " " + Session.getSession().getLeVisiteur().getPrenom());
                    vuePraticiens.toFront();
                    
                    
   
        
                }
            }
        );
        

        
    }

    
    public static void main(String[] args) {
        launch(args);
        
        
        
        
        
        
        
    }
}
 */


public class Appli extends Application {
    
    // Déclaration et initialisation
    PanneauAccueil vueAccueil = new PanneauAccueil();    
    PanneauRapports vueRapports = new PanneauRapports();
    PanneauPraticiens vuePraticiens = new PanneauPraticiens() ; 
    
    @Override
    public void start(Stage primaryStage) throws ConnexionException {
        //Connexion
        Connection connexion = ConnexionBD.getConnexion();
        // Création de la barre de Menu
        MenuBar barreMenus = new MenuBar();
        //Création du menu Fichier
        Menu menuFichier = new Menu("Fichier");
        //Création des items
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
               
        //Création du menu Rapport
        Menu menuRapport = new Menu("Rapport");
        //Création d'items
        MenuItem itemConsulter = new MenuItem("Consulter");
        
        //Création du menu Praticiens
        Menu menuPraticiens = new Menu("Paticiens");
        //Création d'tems
        MenuItem itemHesitants = new MenuItem("Hésitants");
        
        //Ajout de des items dans le menu
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDeconnecter);
        itemSeDeconnecter.setDisable(true); //désactiver l'item
        menuFichier.getItems().add(itemQuitter);
        
        menuRapport.getItems().add(itemConsulter);
        
        menuPraticiens.getItems().add(itemHesitants);
        
        //Ajout des menus dans la barre de menu
        barreMenus.getMenus().add(menuFichier);
        barreMenus.getMenus().add(menuRapport);
        menuRapport.setDisable(true);// désactiver le menu
        barreMenus.getMenus().add(menuPraticiens);
        menuPraticiens.setDisable(true);// désactiver le menu
         
        //Création d'un root
        BorderPane root = new BorderPane();
        root.setTop(barreMenus);
        
        //Création de pile de panneaux
        StackPane pileP = new StackPane();
        
        //Ajout des 3 panneaux à la pile
        pileP.getChildren().addAll(vueAccueil, vueRapports, vuePraticiens);
        vueAccueil.toFront();
        
        //Possition pile au centre
        root.setCenter(pileP);
        
        //Désactiver les panneaux
        vueAccueil.setVisible(false);
        vueRapports.setVisible(false);
        vuePraticiens.setVisible(false);
        
        //Action sur le bouton SeConnecter
        itemSeConnecter.setOnAction((ActionEvent event) ->{
        /*itemSeConnecter.setOnAction(
            new EventHandler<ActionEvent>(){
                public void handle( ActionEvent event){
                Visiteur visiteur1 = new Visiteur ();
                try {
                    visiteur1 = ModeleGsbRv.seConnecter("c14", "azerty");
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                });
            Session.ouvrir(visiteur1)}};*/
            try{
                VueConnexion vue = new VueConnexion();
                Optional<Pair<String, String>> reponse = vue.showAndWait();
                if(reponse.isPresent()){
                   Visiteur visiteur = ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue());
                   if(visiteur!=null){
                        Session.ouvrir(visiteur);
                        menuRapport.setDisable(false);
                        menuPraticiens.setDisable(false);
                        itemSeDeconnecter.setDisable(false);
                        itemSeConnecter.setDisable(true);
                        primaryStage.setTitle(Session.getSession().getLeVisiteur().getNom() + " " + Session.getSession().getLeVisiteur().getPrenom());
                        vueAccueil.setVisible(true);
                        vuePraticiens.setCritereTri(PanneauPraticiens.CRITERE_COEF_CONFIANCE);                        
                    }
                   else{
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Erreur");
                        alertError.setHeaderText("Erreur dans l'authentification");
                        alertError.setContentText("Cliquer Ok pour revenir sur l'acceuil");
                        alertError.showAndWait();
                    }
                }  
            }
            catch(ConnexionException ex){
                Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Action sur le bouton SeDeconnecter
        itemSeDeconnecter.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event ){
                Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                alertQuitter.setTitle("Demande de confirmation");
                alertQuitter.setContentText("Voulez-vous vous déconnecter?");
                ButtonType btnOui = new ButtonType("Oui");
                ButtonType btnNon = new ButtonType("Non");
                alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
                vuePraticiens.setCritereTri(PanneauPraticiens.CRITERE_COEF_CONFIANCE);
                Optional<ButtonType>reponse = alertQuitter.showAndWait();
                if (reponse.get() == btnOui){   
                    Session.fermer();
                    primaryStage.setTitle("GSB-RV-DR");
                    menuRapport.setDisable(true);
                    menuPraticiens.setDisable(true);
                    itemSeDeconnecter.setDisable(true);
                    itemSeConnecter.setDisable(false);
                    vueAccueil.setVisible(false);
                    vueRapports.setVisible(false);
                    vuePraticiens.setVisible(false);
                }
            }
        });
        //Action sur le bouton Consulter
        itemConsulter.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                /*---> Test de la fonction getRapportVisite() / getVisiteur 
                List<RapportVisite> rapp = new ArrayList<>();
                try {
                    rapp = ModeleGsbRv.getRapportsVisite("a131", 05, 2018);
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(rapp);
                List<Visiteur> visiteurs = new ArrayList<>();
                try {
                    visiteurs = ModeleGsbRv.getVisiteurs();
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(visiteurs);*/
                
                primaryStage.setTitle("[Rapports]" + " " +Session.getSession().getLeVisiteur().getNom() + " "+ Session.getSession().getLeVisiteur().getPrenom());
                vueRapports.toFront();
                vueAccueil.setVisible(false);
                vueRapports.setVisible(true);
                vuePraticiens.setVisible(false);
            }
        });
        //Action sur le bouton Hésitant
        itemHesitants.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                primaryStage.setTitle("[Praticiens]" + " " +Session.getSession().getLeVisiteur().getNom() + " "+ Session.getSession().getLeVisiteur().getPrenom());
                vuePraticiens.toFront();
                try {
                    vuePraticiens.rafraichir();
                } catch (ConnexionException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                }
                vueAccueil.setVisible(false);
                vueRapports.setVisible(false);
                vuePraticiens.setVisible(true);
                                
            }
        });
         //Action sur le bouton Quitter
        /*itemQuitter.setOnAction(
        new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event ){
                Platform.exit();
            }
        });*/
        itemQuitter.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        itemQuitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
            alertQuitter.setTitle("Demande de confirmation");
            alertQuitter.setContentText("Voulez-vous quitter l'application?");
            ButtonType btnOui = new ButtonType("Oui");
            ButtonType btnNon = new ButtonType("Non");
            alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
            Optional<ButtonType>reponse = alertQuitter.showAndWait();
                if (reponse.get() == btnOui){
                    System.exit(0);
                }
            }
        });
        
        
        Scene scene = new Scene(root, 890, 520);
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ConnexionException, SQLException {
        launch(args);
    }

}