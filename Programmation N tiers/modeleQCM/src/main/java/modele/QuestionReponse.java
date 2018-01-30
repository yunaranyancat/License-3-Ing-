package modele;

import modele.exceptions.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe permettant de décrire ce qu'est une question à proprement parler.
 * Un objet QuestionReponse est composé
 *  - d'une question (String)
 *  - d'une liste de réponses possibles (List<String>)
 *  - d'une seule réponse correcte (String)
 *  L'identifiant est géré automatiquement à la création d'un objet.
 */
public class QuestionReponse {


    private static int AUTO_ID_QUESTION = 0;
    int idQuestion;
    String question;
    List<String> reponsesPossibles;
    String reponseCorrecte;

    public QuestionReponse(String question, List<String> reponsesPossible, String reponseCorrecte) {
        idQuestion = AUTO_ID_QUESTION;
        AUTO_ID_QUESTION++;
        this.question = question;
        this.reponsesPossibles = reponsesPossible;
        this.reponseCorrecte = reponseCorrecte;
    }


    public QuestionReponse(String question) {
        this(question,new ArrayList<String>(),null);
    }

    public void ajouterReponsePossible(String reponse) throws ReponseDejaProposeeException {
        if (reponsesPossibles.contains(reponse)) {
            throw new ReponseDejaProposeeException();
        }
        else {
            this.reponsesPossibles.add(reponse);
        }
    }



    public int getIdQuestion() {
        return idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getReponsesPossibles() {
        return reponsesPossibles;
    }

    public void setReponsesPossibles(List<String> reponsesPossibles) {
        this.reponsesPossibles = reponsesPossibles;
    }
    public void addReponsesPossibles(String reponsePossible) {
        this.reponsesPossibles.add(reponsePossible);
    }

    public String getReponseCorrecte() {
        return reponseCorrecte;
    }

    public void setReponseCorrecte(String reponseCorrecte) throws ReponsePasProposeeDansLaQuestionException {
        if (!reponsesPossibles.contains(reponseCorrecte)) {
            throw new ReponsePasProposeeDansLaQuestionException();
        }
        this.reponseCorrecte = reponseCorrecte;
    }



}
