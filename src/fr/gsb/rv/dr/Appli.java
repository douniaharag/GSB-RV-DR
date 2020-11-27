/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import java.awt.event.InputEvent;
import java.util.Collection;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author developpeur
 */
public class Appli extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        MenuBar barreMenus = new MenuBar();
        

        Menu menuFichier = new Menu ("Fichier") ;
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
         
           
        MenuItem itemSeDéconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
       
        
        menuFichier.getItems().add(itemSeConnecter);
        menuFichier.getItems().add(itemSeDéconnecter);
        menuFichier.getItems().add(itemQuitter);
        barreMenus.getMenus().add(menuFichier);
        
        Menu menuRapports = new Menu ("Rapports") ;
        MenuItem itemConsulter = new MenuItem("Consulter");
        
        menuRapports.getItems().add(itemConsulter);
        barreMenus.getMenus().add(menuRapports);
        
        Menu menuPraticiens = new Menu ("Praticiens") ;
        MenuItem itemHésitants = new MenuItem("Hésitants");
        
        menuPraticiens.getItems().add(itemHésitants);
        barreMenus.getMenus().add(menuPraticiens);
        
        itemSeConnecter.setDisable(false);
        itemSeDéconnecter.setDisable(true);
        itemQuitter.setDisable(false);
        menuRapports.setDisable(true);
        menuPraticiens.setDisable(true);
        
        itemSeConnecter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent event) {
                        itemSeConnecter.setDisable(true);
                        itemSeDéconnecter.setDisable(false);
                        itemQuitter.setDisable(false);
                        menuRapports.setDisable(false);
                        menuPraticiens.setDisable(false);
                      }
                    }); 
        
        
        
        
        itemSeDéconnecter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent event) {
                        itemSeConnecter.setDisable(false);
                        itemSeDéconnecter.setDisable(true);
                        itemQuitter.setDisable(false);
                        menuRapports.setDisable(true);
                        menuPraticiens.setDisable(true);
                      }
                    });  
          
         itemQuitter.setOnAction(
                new EventHandler<ActionEvent>(){
                    public void handle ( ActionEvent event) {
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
        
        BorderPane menu = new BorderPane();
        Scene scene = new Scene(barreMenus , 300 , 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
