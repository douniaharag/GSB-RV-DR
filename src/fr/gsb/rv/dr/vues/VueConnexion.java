/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vues;


import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;


/**
 *
 * @author developpeur
 */

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import static javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE;
import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author etudiant
 */
public class VueConnexion extends Dialog<Pair<String,String>> {
    
    private Dialog<Pair<String, String>> dialog = new Dialog<>();
     
    public VueConnexion(){
        Label labelLogin = new Label("Matricule");
        Label labelPwd = new Label("Mot de passe ");
        TextField matricule = new TextField();
        PasswordField mdp = new PasswordField();
        VBox vb = new VBox(15);
        HBox hb = new HBox(42);
        HBox hb2 = new HBox(15);
       
        setTitle("Authentification");
        setHeaderText("Saisir vos données de connexion");
        
        hb.getChildren().addAll(labelLogin,matricule);
        hb2.getChildren().addAll(labelPwd,mdp);
        vb.getChildren().addAll(hb,hb2);
        getDialogPane().setContent(vb);
        
        ButtonType btValider = new ButtonType("Valider",OK_DONE);
        ButtonType btAnnuler = new ButtonType("Annuler",CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(btValider,btAnnuler);
       
       setResultConverter(
        new Callback<ButtonType,Pair<String,String>>(){
            @Override
            public Pair<String,String> call(ButtonType typeBouton){
                if (typeBouton == btValider){
                    return new Pair<String,String>(matricule.getText(),mdp.getText());
                }
                return null;
            }
        });
    }
}