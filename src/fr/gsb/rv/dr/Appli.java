/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;


import fr.gsb.rv.dr.entities.Praticien;
import fr.gsb.rv.dr.entities.RapportVisite;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import fr.gsb.rv.dr.vues.VueConnexion;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.application.Application; 
import javafx.geometry.Insets; 
import javafx.scene.Scene; 
import javafx.scene.layout.VBox; 
import javafx.stage.Stage;

import java.awt.*;
import java.io.InputStream;
import static javafx.application.Application.launch;
import javafx.scene.control.SeparatorMenuItem;
import javax.swing.JOptionPane;




/**
 *
 * @author developpeur
 */


public class Appli extends Application {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
   
    }
}
 