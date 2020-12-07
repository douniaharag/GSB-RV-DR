/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.vues.PanneauAccueil;
import fr.gsb.rv.dr.vues.PanneauPraticiens;
import fr.gsb.rv.dr.vues.PanneauRapports;
import fr.gsb.rv.dr.vues.VueConnexion;
import java.awt.event.InputEvent;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.swing.JOptionPane;


/**
 *
 * @author developpeur
 */
public class Appli extends Application {
    
   // Visiteur visiteur = new Visiteur("OB001", "BOUAICHI", "Oumayma");
    
    
    //Creer les 3 panneaux
     PanneauRapports vueRapports = new PanneauRapports();
     PanneauPraticiens vuePraticiens = new PanneauPraticiens();
     PanneauAccueil vueAccueil = new PanneauAccueil();
                        
        
    
    @Override
    public void start(Stage primaryStage) throws ConnexionException {
        // teste de la classe Connexion BD 
       // Connection connexion = ConnexionBD.getConnexion();
   
       
           
        //Creation de la barre d menus 
        
        MenuBar barreMenus = new MenuBar();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root , 700 , 400);
        root.setTop(barreMenus);
       
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.show();
        
        //Creer une pile
        
        StackPane pile = new StackPane(); 
    
        //Ajouter les 3 panneaux a la pile
 
        
        pile.getChildren().addAll(vueRapports  , vuePraticiens , vueAccueil );
      
        
       vueAccueil.setVisible(true);
       vueRapports.setVisible(false);
       vuePraticiens.setVisible(false);

        
        //Positionner Accueil en premier
        
        vueAccueil.toFront();
   
        
        //Positionner la pille au centre
             root.setCenter(pile);
      
     
        
         //Menu Fichier 
        Menu menuFichier = new Menu ("Fichier") ;
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
         
        MenuItem itemSeDéconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
       
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDéconnecter);
        menuFichier.getItems().add(itemQuitter);
        barreMenus.getMenus().add(menuFichier);
        
        //Menu Rapport
        Menu menuRapports = new Menu ("Rapports") ;
        MenuItem itemConsulter = new MenuItem("Consulter");
        
        menuRapports.getItems().add(itemConsulter);
        barreMenus.getMenus().add(menuRapports);
        
         //Menu Praticiens
        Menu menuPraticiens = new Menu ("Praticiens") ;
        MenuItem itemHésitants = new MenuItem("Hésitants");
        
        menuPraticiens.getItems().add(itemHésitants);
        barreMenus.getMenus().add(menuPraticiens);
        
        // au lancement de l'application la session est fermée
        Session.fermer();
        
        itemSeConnecter.setDisable(false);
        itemSeDéconnecter.setDisable(true);
        itemQuitter.setDisable(false);
        menuRapports.setDisable(true);
        menuPraticiens.setDisable(true);
        
        
         // function de l'item seConnecter
               itemSeConnecter.setOnAction((ActionEvent event) -> {
            try {
                VueConnexion vue = new VueConnexion();
                Optional<Pair<String, String>> response = vue.showAndWait();
                if (response.isPresent()){
                    Visiteur visiteur = ModeleGsbRv.seConnecter(response.get().getKey(), response.get().getValue());
                    if (visiteur != null){
                        Session.ouvrir(visiteur);
                        itemSeDéconnecter.setDisable(false);
                        itemSeConnecter.setDisable(true);
                        itemConsulter.setDisable(false);
                        itemHésitants.setDisable(false);
                        menuRapports.setDisable(false);
                        menuPraticiens.setDisable(false);
                        
                       
                       // vueAccueil.setVisible(true);
                        
                        
                        primaryStage.setTitle(Session.getSession().getLeVisiteur().getNom()+ " " + Session.getSession().getLeVisiteur().getPrenom());
                        
                    }else {
                    Alert alert = new Alert (Alert.AlertType.ERROR);
                    alert.setTitle ("Erreur");
                    alert.setHeaderText("Connexion annulée :");
                    alert.setContentText("Matricule ou mot de passe incorrecte!");
                    alert.showAndWait();
                }
                }
            } catch (ConnexionException ex) {
                Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex); 
            }
        });
              
        
         // function de l'item SeDéconnecter
        itemSeDéconnecter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent event) {
                        Session.fermer();
                        itemSeConnecter.setDisable(false);
                        itemSeDéconnecter.setDisable(true);
                        itemQuitter.setDisable(false);
                        menuRapports.setDisable(true);
                        menuPraticiens.setDisable(true);
                        primaryStage.setTitle("GSB-RV-DR");
                        vueAccueil.setVisible(true);
                        vueRapports.setVisible(false);
                        vuePraticiens.setVisible(false);

                      }
                    });  
        
        // function de l'item  Quitter
         itemQuitter.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
         itemQuitter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle ( ActionEvent event) {
                        Session.fermer(); ; 
                        Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION) ;
                        alertQuitter.setTitle("Quitter");
                        alertQuitter.setHeaderText("Demande de confirmation");
                        alertQuitter.setContentText("Voulez-vus quitter l'application");
                        ButtonType btnOui = new ButtonType("Oui") ;
                        ButtonType btnNon = new ButtonType("Non") ;
                        alertQuitter.getButtonTypes().setAll(btnOui , btnNon);
                        Optional<ButtonType> reponse = alertQuitter.showAndWait();
                        if(reponse.get() == btnOui ){
                            Platform.exit();
                        }
                    }

              }
        );
         
         
         // function de l'item  Consulter
         itemConsulter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent event) {
                        primaryStage.setTitle("[Rapports] " + " " + Session.getSession().getLeVisiteur().getNom()+ " " + Session.getSession().getLeVisiteur().getPrenom());
                        vueAccueil.setVisible(false);
                        vueRapports.setVisible(true);
                        vuePraticiens.setVisible(false);
                    }
                }
         );
         
         // function de l'item  Hésitants
         itemHésitants.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent event) {
                        primaryStage.setTitle("[Praticiens] " + "" + Session.getSession().getLeVisiteur().getNom()+ " " + Session.getSession().getLeVisiteur().getPrenom());
                        vueAccueil.setVisible(false);
                        vueRapports.setVisible(false);
                        vuePraticiens.setVisible(true);

  
                    }
                }
         );
         
         
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
