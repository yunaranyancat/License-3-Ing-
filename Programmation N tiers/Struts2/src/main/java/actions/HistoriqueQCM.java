package actions;

import modele.Questionnaire;

import java.util.Collection;

public class HistoriqueQCM extends Environment{

    public Collection<Questionnaire> getQcms(){
        return model.getListQuestionnairesFaits((String)session.get("login"));

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
