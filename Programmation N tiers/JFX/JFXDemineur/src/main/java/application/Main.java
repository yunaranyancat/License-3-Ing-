package application;

import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;
import modele.GestionDemineur;

public class Main extends Application {


    public void start(Stage stage) throws Exception {

        Controleur monControleur = new Controleur();
    }

    public static void main(String[] args){ launch(args); }
}
