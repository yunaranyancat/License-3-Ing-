package facade;

import modele.*;
import modele.exceptions.*;

import java.util.Collection;
import java.util.List;

public interface AdminQCM {


    void connexion(String pseudo, String mdp) throws UtilisateurDejaConnecteException, InformationsSaisiesIncoherentesException;


    /**
     *
     * @param pseudo
     * @return true si pseudo est déclaré comme administrateur
     */
    boolean isAdmin(String pseudo);

    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param titre
     * @return l'identifiant du Questionnaire créé
     */
    int ajouterQuestionnaire(String pseudo, String titre) throws IsNotAllowedException;

    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param idQuestionnaire
     * @param libelleQuestion
     * @return Ajoute une question au questionnaire identifié par idQuestionnaire
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    int ajouterQuestion(String pseudo, int idQuestionnaire, String libelleQuestion)  throws IsNotAllowedException;


    /**
     *
     * @param pseudo
     * @param idQuestionnaire
     * @param idQuestion
     * @param reponse
     * @throws IsNotAllowedException
     * @throws ReponseDejaProposeeException
     */

    void ajouterReponsePossibleAQuestion(String pseudo, int idQuestionnaire, int idQuestion, String reponse)  throws IsNotAllowedException, ReponseDejaProposeeException;

    /**
     *
     * @param pseudo
     * @param idQuestionnaire
     * @param idQuestion
     * @param reponseReelle
     * @throws IsNotAllowedException
     * @throws ReponsePasProposeeDansLaQuestionException
     */
    void ajouterReponseReelle(String pseudo, int idQuestionnaire, int idQuestion, String reponseReelle)  throws IsNotAllowedException, ReponsePasProposeeDansLaQuestionException;


    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param idQuestionnaire
     * @return retourne le questionnaire ayant pour identifiant idQuestionnaire
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    Questionnaire getQuestionnaireEnPreparationById(String pseudo, int idQuestionnaire)  throws IsNotAllowedException;

    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param idQuestionnaire
     * @param id
     * @return la question réponse du questionnaire idFormulaire et ayant pour identifiant id
     * @throws QuestionNonExistanteException
     * @throws IsNotAllowedException (pseudo n'est pas un administrateur)
     */
    QuestionReponse getQuestionReponseById(String pseudo, int idQuestionnaire, int id)  throws IsNotAllowedException, QuestionNonExistanteException;


    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @return la liste complète des questionnaires de l'application
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    Collection<Questionnaire> getListeQuestionnairesEnPreparation(String pseudo)  throws IsNotAllowedException;

    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param idQuestionnaire ID du questionnaire à mettre en production
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    void mettreEnProductionQuestionnaire(String pseudo, int idQuestionnaire)  throws IsNotAllowedException;

    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @return la liste complète des demandes à rejouer des questionnaires déjà fait
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    List<DemandeARejouer> getListeDemandeARejouer(String pseudo)  throws IsNotAllowedException;


    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param demandeur le pseudo de l'utilisateur qui fait la demande
     * @param idQuestionnaire l'id du questionnaire à rejouer
     * @return la liste complète des demandes à rejouer maj
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    List<DemandeARejouer> accepterDemandeARejouer(String pseudo, String demandeur, int idQuestionnaire)  throws IsNotAllowedException;

    /**
     *
     * @param pseudo pseudo correspondant à un administrateur
     * @param demandeur le pseudo de l'utilisateur qui fait la demande
     * @param idQuestionnaire l'id du questionnaire à rejouer
     * @return la liste complète des demandes à rejouer maj
     * @throws IsNotAllowedException : pseudo n'est pas un administrateur
     */
    List<DemandeARejouer> refuserDemandeARejouer(String pseudo, String demandeur, int idQuestionnaire)  throws IsNotAllowedException;

    void deconnexion(String pseudo);

}
