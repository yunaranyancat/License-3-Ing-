package modele;


/**
 * Permet de représenter une correction pour une question donnée
 */
public class CorrectionQuestion {

    /**
     * Question
     */
    String question;

    /**
     * Réponse exacte attendue
     */
    String reponseAttendue;
    /**
     * Réponse donnée par l'utilisateur
     */
    String reponseDonnee;

    public CorrectionQuestion(String question, String reponseAttendue, String reponseDonnee) {
        this.question = question;
        this.reponseAttendue = reponseAttendue;
        this.reponseDonnee = reponseDonnee;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponseAttendue() {
        return reponseAttendue;
    }

    public String getReponseDonnee() {
        return reponseDonnee;
    }
}
