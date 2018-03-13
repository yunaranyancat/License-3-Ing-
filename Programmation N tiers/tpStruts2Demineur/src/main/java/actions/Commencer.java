package actions;

import modele.Case;
import modele.GestionDemineur;
import modele.Plateau;
import modele.exceptions.BombeException;

public class Commencer extends Environment {

    //private static int DEFAULT_SIZE = 6;
    private Plateau plateau;

    public Plateau getPlateau(){
        return plateau;
    }

    @Override
    public String execute(){
        try {
            Plateau monPlateau = getFacade().getPlateau(login);
            for (int i = 0; i < monPlateau.getMonPlateau().length; i++) {
                for (int j = 0; j < monPlateau.getMonPlateau().length; j++) {
                    monPlateau.decouvrirCase(i, j);
                }
            }
        } catch (BombeException e) {
            e.printStackTrace();
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return SUCCESS;
        }

        return SUCCESS;

    }

    @Override
    public void validate() {
        super.validate();
    }
}
