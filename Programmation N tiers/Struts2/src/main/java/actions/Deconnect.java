package actions;

public class Deconnect extends Environment {

    @Override
    public String execute() throws Exception {
        model.deconnexion((String) session.get("login"));
        session.remove("login");
        addFieldError("login",getText("connect.decoOk"));

        return SUCCESS;
    }

    @Override
    public void validate() {
    }
}
