package actions;

import modele.exceptions.ExceptionLoginDejaPris;

public class Connect extends Environment {

    @Override
    public String execute() {

        try {
            model.connexion(login);
        } catch (ExceptionLoginDejaPris e) {
            addFieldError("login",getText("connect.errLoginDejaPris"));
            return INPUT;
        }
        //Enregistrer en session le login
        //session.put("login",login);

        return SUCCESS;
    }

    @Override
    public void validate() {
        if(login.length()==0 || login.length()>8){
            addFieldError( "login",getText("connect.errLogin"));
        }
    }
}
