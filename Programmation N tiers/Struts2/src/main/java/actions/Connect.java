package actions;


import modele.exceptions.InformationsSaisiesIncoherentesException;
import modele.exceptions.UtilisateurDejaConnecteException;

public class Connect extends Environment {

    @Override
    public String execute() {

        try {
            model.connexion(login,password);
        } catch (UtilisateurDejaConnecteException e) {
            addFieldError("login",getText("connect.errLoginConnected"));
            return INPUT;
        } catch (InformationsSaisiesIncoherentesException e) {
            addFieldError("login",getText("connect.errLoginOrPassword"));
            addFieldError("password",getText("connect.errLoginOrPassword"));
            return INPUT;
        }
        //Enregistrer en session le login
        session.put("login",login);


        return SUCCESS;
    }

    @Override
    public void validate() {
        if(login.length()==0){
            addFieldError("login",getText("connect.errLogin"));
        }
        if(password.length()==0){
            addFieldError("password",getText("connect.errPassword"));
        }
    }
}
