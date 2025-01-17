package fr.gsb.rv.dr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import fr.gsb.rv.dr.vues.VueRapport;
import fr.gsb.rv.dr.entities.RapportVisite;
import fr.gsb.rv.dr.entities.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Mois;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;





/**
 *
 * @author developpeur
 */

/**public class PanneauRapports extends Pane {
    
    private ComboBox<Visiteur> cbVisiteurs = new ComboBox<Visiteur>();
    private ComboBox<String> cbMois = new ComboBox<String>();
    private ComboBox<Integer> cbAnnee = new ComboBox<Integer>();
    
    TableView<RapportVisite> tabRapportVisite = new TableView<RapportVisite>();


    public PanneauRapports() {
         super() ;
        
        this.setStyle("-fx-background-color: white");
        
        VBox root = new VBox() ;
        
        root.getChildren().add( new Label("") ) ;
        
        this.getChildren().add( root ) ;
        
        GridPane gp = new GridPane();

       // Changer le nom des comBox
       cbVisiteurs.setPromptText("Visiteur");
       cbMois.setPromptText("Mois");
       cbAnnee.setPromptText("Année");

        // Création du bouton de validation
        
        Button valider = new Button("Valider");
        
       valider.setOnAction( (ActionEvent event)  -> {
            try {
                  if( cbVisiteurs.getValue() != null && cbMois.getValue() != null && cbAnnee.getValue() != null){
                      rafraichir();                    
                  } else {
                        Alert alert = new Alert (Alert.AlertType.ERROR);
                        alert.setTitle("ECHEC DE LA VALIDATION");
                        alert.setHeaderText("La selection est incomplète");
                        alert.setContentText("");
                        alert.showAndWait();
                  }
            } catch (Exception ex) {
                Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            }
        ) ;       

 
        // Récup les visiteurs
         try {
              List<Visiteur> visiteur = ModeleGsbRv.getVisiteurs() ;
              cbVisiteurs.getItems().addAll(visiteur);
         } catch (ConnexionException ex) {
               Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
        };  
        
        //Recup les mois
        try {
            for (Mois unMois : Mois.values()){
                cbMois.getItems().add(unMois.toString());
            }
            
        }
        catch (Exception ex) {
               Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
        };  
        
        // Recup les années
        
        try {
       LocalDate aujourdhui = LocalDate.now();
       int anneeCourante = aujourdhui.getYear();
       cbAnnee.getItems().addAll(anneeCourante,anneeCourante - 1, anneeCourante -2, anneeCourante -3, anneeCourante - 4);
       }
       catch (Exception ex) {
               Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
        }; 
        
         // Création des colonnes de la tb
        TableColumn<RapportVisite, Integer> colNumero = new TableColumn<RapportVisite,Integer>("Numéro"); 
        TableColumn<RapportVisite, String> colNomPraticien = new TableColumn<RapportVisite,String>("Nom praticien"); 
        TableColumn<RapportVisite, String> colVillePraticien = new TableColumn<RapportVisite,String>("Ville"); 
        TableColumn<RapportVisite, LocalDate> colVisite = new TableColumn<RapportVisite,LocalDate>("Visite"); 
        TableColumn<RapportVisite, LocalDate> colRedaction = new TableColumn<RapportVisite,LocalDate>("Rédaction"); 

        
        tabRapportVisite.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        
        colNomPraticien.setCellValueFactory(
             new Callback<CellDataFeatures<RapportVisite,String>,ObservableValue<String>>(){
             @Override
             public ObservableValue<String> call(CellDataFeatures<RapportVisite, String> param) {
                 //System.out.println(  ) :
                 String nom = param.getValue().getLePraticien().getNom();
                return new SimpleStringProperty(nom);
             }
        }
  );
        colVillePraticien.setCellValueFactory(
             new Callback<CellDataFeatures<RapportVisite,String>,ObservableValue<String>>(){
             @Override
             public ObservableValue<String> call(CellDataFeatures<RapportVisite, String> param) {
                String ville = param.getValue().getLePraticien().getVille();
                return new SimpleStringProperty(ville);
             }
        }
  );
        
        colVisite.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        colRedaction.setCellValueFactory(new PropertyValueFactory<>("dateRedaction"));

        
       tabRapportVisite.getColumns().addAll(colNumero,colNomPraticien,colVillePraticien,colVisite,colRedaction);
      
       // Modifier date en format fr
       
       colVisite.setCellFactory(
               colonne -> {   
             return new TableCell<RapportVisite,LocalDate>() {
                 @Override
                 protected void updateItem(LocalDate item, boolean empty){
                     super.updateItem(item, empty);
                     
                     if(empty){
                         setText("");
                     }
                     else {
                         DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                         setText(item.format(formateur)) ;
                         //setText( "Date" ) ;
                     }
                 }
             };
                   
        } 
               
       );
        
        colRedaction.setCellFactory(
               colonne -> {   
             return new TableCell<RapportVisite,LocalDate>() {
                 @Override
                 protected void updateItem(LocalDate item, boolean empty){
                     super.updateItem(item, empty);
                     System.out.println( "> " + item + "*" ) ;
                     if(empty){
                         setText("");
                     }
                     else {
                         DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                         setText(item.format(formateur)) ;
                         //setText( "Date" ) ;
                     }
                 }
             };
                   
        } 
               
       );
        
       // Appliquer un couleur de fond
        
       tabRapportVisite.setRowFactory(

                ligne -> {

                    return new TableRow<RapportVisite>(){

                        @Override

                        protected void updateItem(RapportVisite item, boolean empty){

                            super.updateItem(item, empty);

                            if(item != null ){

                                if( item.isLu() ){

                                    setStyle("-fx-background-color: gold");

                                }

                                else{

                                    setStyle("-fx-background-color: cyan");

                                }

                            }

                        }

                    };

                }

        ); 
       
       // Traiter la sélection d'une ligne
       tabRapportVisite.setOnMouseClicked(
                (MouseEvent event) -> {
                    if( event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 ){
                        int indiceRapport = tabRapportVisite.getSelectionModel().getSelectedIndex();
                        Visiteur leVisiteur = (Visiteur) cbVisiteurs.getSelectionModel().getSelectedItem();
                        RapportVisite rv = tabRapportVisite.getItems().get(indiceRapport);
                        
                        System.out.println( "Sélection : " + rv ) ;
                        
                        try {
                            ModeleGsbRv.setRapportVisiteLu(leVisiteur.getMatricule(), rv.getNumero());
                            
                            this.rafraichir();
                            
                            
                            VueRapport vueRapport = new VueRapport(rv);
                            vueRapport.showAndWait();
                            
                        } catch (ConnexionException ex) {
                            Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );
              
       HBox box = new HBox(20, cbVisiteurs,cbMois,cbAnnee);
       gp.add(box,10,10); 
       root.getChildren().addAll(gp,valider,tabRapportVisite) ;

    }
    
     public void rafraichir() throws ConnexionException{
         //System.out.println(cbVisiteurs.getValue()+ " " + cbMois.getValue()+ " " + cbAnnee.getValue()); 
            Visiteur leVisiteur;
            leVisiteur = (Visiteur) cbVisiteurs.getSelectionModel().getSelectedItem();
            int mois = cbMois.getSelectionModel().getSelectedIndex()+1;
            int annee = (int) cbAnnee.getSelectionModel().getSelectedItem();
            
            System.out.println( "" + leVisiteur.getMatricule() + "," + mois + "," + annee ) ;
            
            List<RapportVisite> rapportVisite = ModeleGsbRv.getRapportsVisite(leVisiteur.getMatricule(),mois,annee);
            ObservableList<RapportVisite> obListe = FXCollections.observableArrayList(rapportVisite);
            
           tabRapportVisite.setItems(obListe);
            
        }
         
     }*/




public class PanneauRapports extends Pane{
    //Déclaration des attributs
    private ComboBox<Visiteur> cbVisiteurs = new ComboBox<Visiteur>();
    private ComboBox<Mois> cbMois = new ComboBox<Mois>();
    private ComboBox<Integer> cbAnnee = new ComboBox<Integer>();
    
    //Création de la tableview
    private TableView<RapportVisite> tabRapports = new TableView<RapportVisite>();
    
    //Création de la liste Observable
    private ObservableList<RapportVisite> rapportsVis = FXCollections.observableArrayList();
    
    public PanneauRapports() {
        //Boîte Vertical qui englobe tout
        VBox vbAll = new VBox();
        vbAll.setStyle("-fx-background-color: white;");
        vbAll.setSpacing(10);
        vbAll.setPadding(new Insets(10, 810, 10, 10));
        //Grille qui prend les3 comboBox
        GridPane grilleCombo = new GridPane();
        grilleCombo.setHgap(10);                //horizontal espace pixel
        grilleCombo.setVgap(10);                //Vertical espace pixel
        //Ajout des comboBox dans la grille
        grilleCombo.add(cbVisiteurs, 0, 0);     
        grilleCombo.add(cbMois, 1, 0);
        grilleCombo.add(cbAnnee, 2, 0);

        //Liste des visiteur dans le comboBox
        List<Visiteur> lesVisiteurs;
        try {
            lesVisiteurs = ModeleGsbRv.getVisiteurs();
            for( Visiteur unVisiteur : lesVisiteurs){
            //Ajout du visiteur dans le comboBoxvisiteurs
            cbVisiteurs.getItems().add(unVisiteur);
        }
        } catch (ConnexionException ex) {
            Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cbVisiteurs.setPromptText("Visiteur");
        
        //Liste Mois
        for(Mois unMois : Mois.values() ){
            cbMois.getItems().add(unMois);
        }
        cbMois.setPromptText("Mois");
        
        //Liste des années
        LocalDate aujourdhui = LocalDate.now();
        int anneeCourante = aujourdhui.getYear();
        for(int i = 0; i < 6; i++){
            cbAnnee.getItems().add(anneeCourante - i);
        }
        cbAnnee.setPromptText("Année");
        
        //Bouton valider
        Button btnValider = new Button("Valider");
        //action de la validité
        btnValider.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle( ActionEvent event ){
                        if(cbVisiteurs.getValue() != null && cbMois.getValue() != null && cbAnnee.getValue() != null){
                            int mois = cbMois.getSelectionModel().getSelectedIndex() + 1;
                            String matricule = cbVisiteurs.getSelectionModel().getSelectedItem().getMatricule();
                            int annee = cbAnnee.getValue();
                            try {
                                List<RapportVisite> listRapp = ModeleGsbRv.getRapportsVisite(matricule, mois, annee);
                                rapportsVis.removeAll(rapportsVis);
                                for(RapportVisite unRapport : listRapp){
                                    rapportsVis.add(unRapport);
                                }
                                rafraichir();

                            } catch (ConnexionException ex) {
                                Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SQLException ex) {
                                Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Erreur");
                        alertError.setHeaderText("La sélection est incomplète");
                        alertError.setContentText("Cliquer Ok pour fermer");
                        alertError.showAndWait();
                        }   
                    }   
                }
        );
        
         //Création des Colonnes tabRapports
        TableColumn<RapportVisite, Integer> colNumero = 
                new TableColumn<RapportVisite, Integer>( "Numéro" );
        TableColumn<RapportVisite, String> colPraticien =
                new TableColumn<RapportVisite, String>( "Praticien" );
        TableColumn<RapportVisite, String> colVille =
                new TableColumn<RapportVisite, String>( "Ville" );
        TableColumn<RapportVisite, LocalDate> colDateVisite =
                new TableColumn<RapportVisite, LocalDate>( "Date Visite" );
        TableColumn<RapportVisite, LocalDate> colRedaction =
                new TableColumn<RapportVisite, LocalDate>( "Rédaction" );
        
        //Valorisation des colonnes
        colNumero.setCellValueFactory( new PropertyValueFactory<>( "rap_num" ));
        colPraticien.setCellValueFactory( new PropertyValueFactory<>( "pra_num" ));
        colVille.setCellValueFactory( new PropertyValueFactory<>( "pra_ville" ));
        colDateVisite.setCellValueFactory( new PropertyValueFactory<>( "rap_date_visite" ));
        colRedaction.setCellValueFactory( new PropertyValueFactory<>( "rap_date_saisie" ));
        
        //Taille des Colonnes
        colNumero.setMinWidth(162);
        colPraticien.setMinWidth(162);
        colVille.setMinWidth(162);
        colDateVisite.setMinWidth(162);
        colRedaction.setMinWidth(162);
        
        //Valoriser les colonnes qui viennes de la classe Praticien (personnalisation)
        colPraticien.setCellValueFactory(
        new Callback<CellDataFeatures<RapportVisite, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String>
                    call( CellDataFeatures<RapportVisite, String> param) {
                        String nom = param.getValue().getPraticien().getNom();
                        return new SimpleStringProperty( nom );
                    }
        });
        colVille.setCellValueFactory(
        param-> {
            String ville = param.getValue().getPraticien().getVille();
            return new SimpleStringProperty( ville );
        });
        
        //Modifier la format date de dateVisite et Redaction
        colDateVisite.setCellFactory(
            colonne ->{
                return new TableCell<RapportVisite, LocalDate>(){
                    @Override
                    protected void updateItem( LocalDate item, boolean empty ){
                        super.updateItem( item, empty );
                        if( empty ){
                            setText("");
                        }
                        else{
                            DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                            setText( item.format(formateur));
                        }
                    }
                };
            });
        
        colRedaction.setCellFactory(
            colonne2 ->{
                return new TableCell<RapportVisite, LocalDate>(){
                    @Override
                    protected void updateItem( LocalDate item, boolean empty ){
                        super.updateItem( item, empty );
                        if( empty ){
                            setText("");
                        }else{
                            DateTimeFormatter formateur2 = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                            setText( item.format(formateur2));   
                        }
                    }
                };
            });
        
        //Ajouter colonne à la tabPraticien
        tabRapports.getColumns().addAll(colNumero, colPraticien, colVille, colDateVisite, colRedaction);
        //Supprimer la  colonne de trop
        tabRapports.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Couleur des Rapport non-lu
        tabRapports.setRowFactory(
        ligne->{
            return new TableRow<RapportVisite>(){
                @Override 
                protected void updateItem( RapportVisite item, boolean empty){
                    super.updateItem(item, empty);
                    if( item!= null){
                        if( item.isRap_lu() ){
                            setStyle( "-fx-background-color: gold" );
                        }
                        else{
                            setStyle( "-fx-background-color: cyan" );
                        }
                    }  
                }
            };
        });
        
        //Traiter la sélection d'1 ligne du tableau
        tabRapports.setOnMouseClicked(
                (MouseEvent event)-> {
                    if( event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 ){
                        int indiceRapport = tabRapports.getSelectionModel().getSelectedIndex();
                        try {
                            if(rapportsVis.get(indiceRapport).isRap_lu() == false ){
                                VueRapport vueRapport = new VueRapport(rapportsVis.get(indiceRapport));
                                Optional<String> resultat = vueRapport.showAndWait();
                                if(resultat.isPresent()){
                                    if(resultat.get()== "valider"){

                                            ModeleGsbRv.setRapportVisite(rapportsVis.get(indiceRapport).getVisiteur().getMatricule(), rapportsVis.get(indiceRapport).getRap_num());
                                            rapportsVis.get(indiceRapport).setRap_lu(true);
                                            rafraichir();
                                        
                                    }
                                }
                            }
                            else{
                                VueRapport vueRapportLu = new VueRapport(rapportsVis.get(indiceRapport));
                                vueRapportLu.showAndWait();
                                }
                        } catch (ConnexionException ex) {
                                            Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (SQLException ex) {
                                            Logger.getLogger(PanneauRapports.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                    }
                }
        );
        
        //Ajout de la grille dans la Vbox et dans le panneau
        vbAll.getChildren().add(grilleCombo);
        vbAll.getChildren().add(btnValider);
        VBox boxTab = new VBox();
        boxTab.getChildren().add(tabRapports);
        boxTab.setPadding(new Insets(0, 5, 0, 0));
        vbAll.getChildren().add(boxTab);
        this.getChildren().add(vbAll);
    }
    
    public void rafraichir() throws ConnexionException, SQLException{
        tabRapports.getItems().clear();
        for(RapportVisite unRapportVis : rapportsVis){
            tabRapports.getItems().add(unRapportVis);
        }
        tabRapports.refresh();
    }
}