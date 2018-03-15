package views;

import controleur.Controleur;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Menu {

    private Controleur monControleur;

    public void setMonControleur(Controleur monControleur){this.monControleur = monControleur;}


    public static Menu creerInstance(Controleur c){
        URL location = Menu.class.getResource("/views/menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent) fxmlLoader.load();

        }catch (IOException e){
            e.printStackTrace();
        }
        Menu menu = fxmlLoader.getController();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
        menu.setMonControleur(c);
        return menu;


    }
}

