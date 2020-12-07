/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author developpeur
 */

public class PanneauAccueil extends Pane{
     public PanneauAccueil() {
        Label labelAccueil = new Label("Accueil");
        
        VBox vBox = new VBox();
        vBox.getChildren().add(labelAccueil);
      

        vBox.setPadding(new Insets(15, 12, 15, 12));
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: white;");
        this.getChildren().add(vBox);

            
}

   
}