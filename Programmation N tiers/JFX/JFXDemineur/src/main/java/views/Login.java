package views;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.GestionDemineur;
import modele.exceptions.ExceptionLoginDejaPris;

import java.io.IOException;
import java.net.URL;

public class Login {

    private Controleur monControleur;
    private Stage primaryStage;

    protected GestionDemineur modele =  new GestionDemineur();

    public void setMonControleur(Controleur monControleur){this.monControleur = monControleur;}

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;

    }

    @FXML
    private Button boutonLogin;

    @FXML
    private TextField champLogin;

    @FXML
    private Label labelLogin;

    public static Login creerInstance(Controleur c, Stage primaryStage){
        URL location = Login.class.getResource("/views/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent) fxmlLoader.load();

        }catch (IOException e){
            e.printStackTrace();
        }
        Login login = fxmlLoader.getController();
        primaryStage.setTitle("Page de connexion");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
        login.setMonControleur(c);
        return login;
    }

    public void connexion(ActionEvent actionEvent){
        URL location = Login.class.getResource("/views/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            modele.connexion(this.champLogin.getText());
            root = (Parent) fxmlLoader.load();
            primaryStage.setScene(new Scene(root, 300, 300));

        } catch (ExceptionLoginDejaPris e) {
            e.printStackTrace();
            setMonLabel("Login deja pris!");

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void setMonLabel(String affichage){this.labelLogin.setText(affichage);}


}
