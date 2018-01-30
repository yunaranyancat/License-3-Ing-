package modele;


import modele.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe permettant de définir un questionnaire.
 * Un questionnaire est composé
 *  - d'un identifiant
 *  - d'une map associant à un identifiant de question un objet QuestionReponse
 *  - la liste des objets QuestionReponse impliqués dans la map
 *  - Un thème (libelléQuestionnaire)
 */
public class Questionnaire {

    private int idQuestionnaire;
    Map<Integer,QuestionReponse> questionsReponses;
    List<QuestionReponse> questionReponsesList;

    String libelleQuestionnaire;

    public String getLibelleQuestionnaire() {
        return libelleQuestionnaire;
    }

    public void setLibelleQuestionnaire(String libelleQuestionnaire) {
        this.libelleQuestionnaire = libelleQuestionnaire;
    }

    private static int ID_AUTO = 0;

    public Questionnaire() {
        this(null,new ArrayList<>());
    }

    public Questionnaire(String nomQuestionnaire) {
        this(nomQuestionnaire,new ArrayList<>());
    }

    public Questionnaire(String nomQuestionnaire, List<QuestionReponse> questionReponses) {
        this.idQuestionnaire = ID_AUTO;
        this.libelleQuestionnaire = nomQuestionnaire;
        this.questionsReponses = new HashMap<>();
        for(QuestionReponse q : questionReponses) {
            this.questionsReponses.put(q.idQuestion,q);
        }
        questionReponsesList = questionReponses;
        ID_AUTO++;
    }


    public List<QuestionReponse> getQuestionsReponses() {
        return questionReponsesList;

    }


    public int getIdQuestionnaire() {
        return idQuestionnaire;
    }



    public int creerQuestion(String libelleQuestion) {
        QuestionReponse question = new QuestionReponse(libelleQuestion);
        this.questionReponsesList.add(question);
        this.questionsReponses.put(question.getIdQuestion(),question);
        return question.getIdQuestion();
    }

    public void ajouterReponsePossible(int idQuestion, String reponse) throws ReponseDejaProposeeException {
        for (QuestionReponse q : this.questionReponsesList) {
            if (q.getIdQuestion() == idQuestion) {
                q.ajouterReponsePossible(reponse);
            }
        }
    }

    public void ajouterReponseCorrecte(int idQuestion, String reponse) throws ReponsePasProposeeDansLaQuestionException {
        for (QuestionReponse q : this.questionReponsesList) {
            if (q.getIdQuestion() == idQuestion) {
                q.setReponseCorrecte(reponse);
            }
        }

    }


    /**
     * Permet de calculer le score obtenus.
     * @param idsQuestions tableau stockant les identifiants des questions concernées
     * @param reponses tableau stockant les réponses
     *                 En gros ils faut considérer que pour tout i, la réponse reponses[i] correspond à la question
     *                 idsQuestions[i]
     * @return le score obtenu
     */

    public double validerReponses(int[] idsQuestions, String[] reponses) {
        double nbQuestions = getQuestionsReponses().size();

        int nbReponsesCorrectes = 0;
        for (int i = 0; i<idsQuestions.length;i++) {
            if (reponses[i].equals(questionsReponses.get(idsQuestions[i]).getReponseCorrecte())) {
                nbReponsesCorrectes++;
            }
        }

        return (nbReponsesCorrectes*100)/nbQuestions;
    }


    /**
     * Permet de récupérer la question qui suit la question q.
     * @param q
     * @return la question suivante
     * @throws QuestionnaireEnCoursTermineException
     */

    public QuestionReponse getNext(QuestionReponse q) throws QuestionnaireEnCoursTermineException {
        int indice = this.questionReponsesList.indexOf(q);
        indice++;
        if (indice == questionReponsesList.size()) {
            throw new QuestionnaireEnCoursTermineException();
        }
        return questionReponsesList.get(indice);
    }

    public QuestionReponse getQuestionById(int id) throws QuestionNonExistanteException {
        QuestionReponse question = this.questionsReponses.get(id);
        if (question == null) {
            throw new QuestionNonExistanteException();
        }
        return question;

    }
}
