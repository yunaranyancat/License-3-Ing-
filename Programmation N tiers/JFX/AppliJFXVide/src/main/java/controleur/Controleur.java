package controleur;

import views.MaVue;

/**
 * Created by YohanBoichut on 10/11/15.
 */
public class Controleur {


    private MaVue maVue;


    public Controleur() {
        this.maVue = MaVue.creerInstance(this);
    }

}
