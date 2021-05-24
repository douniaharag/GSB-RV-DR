package fr.gsb.rv.dr;

import java.io.InputStream;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author developpeur
 */




public class PanneauAccueil extends Pane{
    public PanneauAccueil() {
        this.setStyle("-fx-alignment: center; -fx-background-color: blue;");
        ImageView img = new ImageView("/images/download.png");
        
        img.setFitHeight(225);
        img.setFitWidth(300);
        img.setX(300);
        img.setY(125);
        this.getChildren().add(img);
        

    }
    
}
