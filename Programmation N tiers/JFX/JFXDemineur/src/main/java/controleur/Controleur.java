package controleur;
import views.Login;
import views.Menu;

public class Controleur {

    private Login login;
    private Menu menu;


    public Controleur(){
        this.login = Login.creerInstance(this);
        this.menu = Menu.creerInstance(this);
    }


}
