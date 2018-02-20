package actions;

import modele.Questionnaire;

import java.util.Collection;

public class ChoixQCM extends Environment{

    public Collection<Questionnaire> getQcms(){
        return model.getListQuestionnairesNonFaits((String) session.get("login"));

    }
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public void validate() {
        super.validate();
    }
}
