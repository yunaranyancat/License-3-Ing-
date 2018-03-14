package actions;

import modele.Case;
import modele.exceptions.ExceptionLoginDejaPris;

public class Connect extends Environment {

    @Override
    public String execute() {

        try {
            model.connexion(login);
            session.put("login",login);
        } catch (ExceptionLoginDejaPris e) {
            addFieldError("login",getText("connect.errLoginDejaPris"));
            return INPUT;
        }
        //Enregistrer en session le login



        return SUCCESS;
    }

    public Case[][] getPlateau(){return model.getPlateau(login).getMonPlateau();}

    @Override
    public void validate() {
        if(login.length()==0 || login.length()>8){
            addFieldError( "login",getText("connect.errLogin"));
        }
    }
}
