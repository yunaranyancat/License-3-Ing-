package modele;

import java.util.HashMap;
import java.util.Map;


/**
 * Cette classe permet de définir un utilisateur. Dans ces objets, nous stockons en particulier l'évolution de
 * l'utilisateur dans le QCM courant i.e. la question courate il est en train de répondre.
 */
public class Utilisateur {

    /**
     * Pseudo de l'utilisateur
     */
    String pseudo;
    /**
     * Mot de passe de l'utilisateur
     */
    String motdePasse;
    /**
     * Réponses que l'utilisateur a donné jusqu'à présent pour le questionnaire courant.
     * Integer dénote l'identifiant de la question concernée dans le questionnaire.
     * String représente la réponse donnée pour cette question
     */
    Map<Integer,String> reponsesDonnees;
    /**
     * Identifiant du questionnaire en cours
     */
    int idQuestionnaireEnCours;

    /**
     * Permet de savoir quelle est la question courante de l'utilisateur.
     */
    QuestionReponse questionReponseCourante;



    public Map<Integer, String> getReponsesDonnees() {
        return reponsesDonnees;
    }

    public QuestionReponse getQuestionReponseCourante() {
        return questionReponseCourante;
    }

    public void setQuestionReponseCourante(QuestionReponse questionReponseCourante) {
        this.questionReponseCourante = questionReponseCourante;
    }

    public Utilisateur(String pseudo,String mdp) {
        this.pseudo = pseudo;
        this.motdePasse = mdp;
        reponsesDonnees = new HashMap<>();
        idQuestionnaireEnCours = -1;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Integer getIdQuestionnaireEnCours() {
        return idQuestionnaireEnCours;
    }

    public void setIdQuestionnaireEnCours(Integer idQuestionnaireEnCours) {
        this.idQuestionnaireEnCours = idQuestionnaireEnCours;
    }

    /**
     * Permet de vérifier si le mot de passe en paramètre correspond à celui stocké dans l'objet
     * @param mdp
     * @return true si le mot de passe est OK, false sinon
     */
    public boolean checkPassword(String mdp) {
        return this.motdePasse.equals(mdp);
    }


    /**
     * Permet de calculer le score en fonction des réponses données par l'utilisateur sur le questionnaire en question
     * @param questionnaire
     * @return le score (pourcentage de bonnes réponses)
     */
    public double transmettreReponses(Questionnaire questionnaire) {
        int[] ids = new int[reponsesDonnees.keySet().size()];
        String[] reponses = new String[reponsesDonnees.keySet().size()];
        int i = 0;
        for (int id : reponsesDonnees.keySet()) {
            ids[i]=id;
            reponses[i] = reponsesDonnees.get(id);
            i++;
        }
        return questionnaire.validerReponses(ids,reponses);
    }

    public void validerQuestion(int idQuestion, String reponse) {
        this.reponsesDonnees.put(idQuestion,reponse);
    }

    /**
     * Permet de réinitialiser l'utilisateur (du moins ses paramètres variables)
     */
    public void resetStatus() {
        this.idQuestionnaireEnCours = -1;
        this.questionReponseCourante = null;
        this.reponsesDonnees= new HashMap<>();
    }
}