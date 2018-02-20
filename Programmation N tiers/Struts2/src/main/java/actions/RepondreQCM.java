package actions;

import modele.QuestionReponse;

public class RepondreQCM extends Environment{

    private int idQuestionnaire;
    private QuestionReponse questionCourante;

    public QuestionReponse getQuestionCourante(){
        return questionCourante;
    }

    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }

    public void setIdQuestionnaire(int idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }



    @Override
    public String execute() throws Exception {
        session.put("idQuestionnaire",idQuestionnaire);
        model.choixQuestionnaire((String)session.get("login"),idQuestionnaire);
        questionCourante = model.next((String)session.get("login"),idQuestionnaire);
        return SUCCESS;
    }

    @Override
    public void validate() {
    }
}
