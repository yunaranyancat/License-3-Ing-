package modele;

import modele.exceptions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Cette classe permet de stocker tous les scores qu'on pourrait obtenir sur divers questionnaires par une map.
 * La clé de la map représente l'identifiant du questionnaire en question et la valeur (Double) représente concrètement
 * le score obtenu.
 */
public class ScoresPersos {

    /**
     * Map permettant de stocker les scores obtenus sur différents questionnaires.
     */

    Map<Integer,Double> scoresPerso;


    public ScoresPersos() {
        scoresPerso = new HashMap<>();
    }



    public void ajouterScorePerso(int idQuestionnaire, double pourcentageReussite) throws QuestionnaireDejaFaitException {
        Double score = scoresPerso.get(idQuestionnaire);
        if (score != null)
            throw new QuestionnaireDejaFaitException("Le questionnaire "+idQuestionnaire+ " a déjà été fait !");
        scoresPerso.put(idQuestionnaire,pourcentageReussite);
    }

    public double getScore(int idQuestionnaire) throws QuestionnaireNonFaitException {
        Double score = scoresPerso.get(idQuestionnaire);
        if (score == null) {
            throw new QuestionnaireNonFaitException("Le questionnaire "+idQuestionnaire + "n'a pas été fait");
        }
        return score;
    }


    public Collection<Integer> getIdsQuestionnairesDejaFaits() {
        return scoresPerso.keySet();
    }

    public void effacerScorePerso(int idQuestionnaire) {
        scoresPerso.remove(idQuestionnaire);
    }
}
