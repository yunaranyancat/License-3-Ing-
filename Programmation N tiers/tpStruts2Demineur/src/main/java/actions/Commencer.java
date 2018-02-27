package actions;

import modele.Plateau;

public class Commencer extends Environment {

    public Plateau getPlateau(){
        return model.getPlateau(login);
    }

    @Override
    public String execute() throws Exception {
        return super.execute();
    }

    @Override
    public void validate() {
        super.validate();
    }
}
