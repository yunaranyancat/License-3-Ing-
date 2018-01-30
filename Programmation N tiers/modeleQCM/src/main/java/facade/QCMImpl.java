package facade;

import modele.exceptions.*;
import modele.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class QCMImpl implements GestionQCM, AdminQCM {

    Map<String, ScoresPersos> scoresPersosMap;
    Map<Integer, Questionnaire> questionnaires;
    List<String> pseudosConnectes;
    Map<String, Utilisateur> utilisateursMap;
    Map<String,Integer> questionnairesEnCours;

    Map<Integer,Questionnaire> questionnairesEnPreparation = new HashMap<>();

    List<String> administrateurs = new ArrayList<>();

    List<DemandeARejouer> demandesRejouer;
    Map<String,Map<Integer,Map<Integer,String>>> historiqueDesReponses;

    public QCMImpl() {
        scoresPersosMap = new HashMap<>();
        questionnaires = new HashMap<>();
        pseudosConnectes = new ArrayList<>();
        questionnairesEnCours = new HashMap<>();
        utilisateursMap = new HashMap<>();
        demandesRejouer = new ArrayList<>();
        historiqueDesReponses = new HashMap<>();

        this.initialiserDAO();

    }

    private void initialiserDAO() {
        this.utilisateursMap.put("yohan", new Utilisateur("yohan", "yohan"));
        this.utilisateursMap.put("fred", new Utilisateur("fred", "fred"));

        this.administrateurs.add("yohan");

        /**
         * Mathématiques
         */
        QuestionReponse questionReponse1 = new QuestionReponse("1+1", Arrays.asList("2", "3", "4", "5"), "2");
        QuestionReponse questionReponse2 = new QuestionReponse("12+35", Arrays.asList("12", "38", "47", "52"), "47");
        QuestionReponse questionReponse3 = new QuestionReponse("1980+37", Arrays.asList("2017", "2018", "2019", "2020"), "2017");
        QuestionReponse questionReponse4 = new QuestionReponse("0*3+1*3", Arrays.asList("2", "3", "4", "5"), "3");


        /**
         * Mathématiques moins drôles
         */
        QuestionReponse questionReponse11 = new QuestionReponse("12*12", Arrays.asList("122", "136", "144", "156"), "144");
        QuestionReponse questionReponse12 = new QuestionReponse("12*35", Arrays.asList("410", "420", "510", "520"), "420");
        QuestionReponse questionReponse13 = new QuestionReponse("16*16", Arrays.asList("276", "266", "256", "246"), "256");


        List<QuestionReponse> questions1 = new ArrayList<QuestionReponse>(Arrays.asList(questionReponse1, questionReponse2, questionReponse3,questionReponse4));

        List<QuestionReponse> questions2 = new ArrayList<QuestionReponse>(Arrays.asList(questionReponse11, questionReponse12, questionReponse13));




        Questionnaire questionnaire1 = new Questionnaire("Maths pour les nuls", questions1);
        Questionnaire questionnaire2 = new Questionnaire("Maths : level up", questions2);
        questionnairesEnPreparation.put(questionnaire1.getIdQuestionnaire(), questionnaire1);
        questionnairesEnPreparation.put(questionnaire2.getIdQuestionnaire(), questionnaire2);
        try {
            mettreEnProductionQuestionnaire("yohan", questionnaire1.getIdQuestionnaire());
            mettreEnProductionQuestionnaire("yohan", questionnaire2.getIdQuestionnaire());

            int id = this.ajouterQuestionnaire("yohan","français");
            int idQuestion = questionnairesEnPreparation.get(id).creerQuestion("quelle est la couleur du cheval blanc ?");
        } catch (IsNotAllowedException e) {
            e.printStackTrace();
        }

        // réponses aléatoires de fred :
        try {
        connexion("fred","fred");
        final int idQuestionnaire = questionnaire2.getIdQuestionnaire();
        choixQuestionnaire("fred", idQuestionnaire);

        while (hasNext("fred",idQuestionnaire)) {
            QuestionReponse questionReponse = next("fred", idQuestionnaire);
            List<String> reponses = questionReponse.getReponsesPossibles();
            String reponse = reponses.get(ThreadLocalRandom.current().nextInt(reponses.size()));
            validerQuestion("fred", questionReponse.getIdQuestion(), reponse);
        }
        validerQuestionnaire("fred");
        deconnexion("fred");

        } catch (QuestionnaireEnCoursNonTermineException e) {
            e.printStackTrace();
        } catch (InformationsSaisiesIncoherentesException e) {
            e.printStackTrace();
        } catch (UtilisateurDejaConnecteException e) {
            e.printStackTrace();
        } catch (ValidationQuestionnaireException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void connexion(String pseudo, String motDePasse) throws UtilisateurDejaConnecteException, InformationsSaisiesIncoherentesException {
        if (pseudosConnectes.contains(pseudo))
            throw new UtilisateurDejaConnecteException();

        Utilisateur u = this.utilisateursMap.get(pseudo);



        if (u == null) {
            throw new InformationsSaisiesIncoherentesException();
        }
        if (u.checkPassword(motDePasse)) {
            this.pseudosConnectes.add(pseudo);
            return;
        }
        throw new InformationsSaisiesIncoherentesException();
    }

    @Override
    public void choixQuestionnaire(String pseudo, int id) throws QuestionnaireEnCoursNonTermineException {
        Integer choisi = questionnairesEnCours.get(pseudo);

        if (choisi != null)
            throw new QuestionnaireEnCoursNonTermineException();

        questionnairesEnCours.put(pseudo,id);
        Utilisateur u = utilisateursMap.get(pseudo);
        u.setIdQuestionnaireEnCours(id);
        return;
    }

    @Override
    public List<Questionnaire> getListQuestionnairesNonFaits(String pseudo) {
        Collection<Integer> questionnairesDejaFaits = scoresPersosMap.get(pseudo)==null ? new ArrayList<>() : scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits();

        Collection<Integer> questionnairesPotentiels = new HashSet<>(questionnaires.keySet());
        questionnairesPotentiels.removeAll(questionnairesDejaFaits);

        List<Questionnaire> collect = questionnairesPotentiels.stream().map(x -> questionnaires.get(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<Questionnaire> getListQuestionnairesFaits(String pseudo) {
        Collection<Integer> questionnairesDejaFaits = scoresPersosMap.get(pseudo)==null ? new ArrayList<>() : scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits();;
        return questionnairesDejaFaits.stream().map(questionnaires::get).collect(Collectors.toList());
    }



    @Override
    public double validerQuestionnaire(String pseudo) throws ValidationQuestionnaireException {
        if (this.questionnairesEnCours.get(pseudo) == null  ) {
            throw new ValidationQuestionnaireException();
        }
        Questionnaire questionnaire = this.questionnaires.get(this.questionnairesEnCours.get(pseudo));
        double score =this.utilisateursMap.get(pseudo).transmettreReponses(questionnaire);

        ScoresPersos scoresPersos = this.scoresPersosMap.get(pseudo);
        if (scoresPersos == null) {
            scoresPersos = new ScoresPersos();
            this.scoresPersosMap.put(pseudo,scoresPersos);
        }
        try {
            scoresPersos.ajouterScorePerso(questionnaire.getIdQuestionnaire(),score);
        } catch (QuestionnaireDejaFaitException e) {
            throw new ValidationQuestionnaireException("Le questionnaire "+questionnaire.getIdQuestionnaire()+" a déjà été fait par "+pseudo);
        }
        this.questionnairesEnCours.remove(pseudo);


        Utilisateur u = this.utilisateursMap.get(pseudo);
        Map<Integer,Map<Integer,String>> mapsDesReponsesPourPseudo = this.historiqueDesReponses.get(pseudo);
        if (mapsDesReponsesPourPseudo == null) {
            mapsDesReponsesPourPseudo = new HashMap<>();
            this.historiqueDesReponses.put(pseudo,mapsDesReponsesPourPseudo);
        }

        mapsDesReponsesPourPseudo.put(u.getIdQuestionnaireEnCours(),u.getReponsesDonnees());

        u.resetStatus();
        return score;
    }

    @Override
    public ScoresPersos getScoresPersos(String pseudo) {
        ScoresPersos scoresPersos = this.scoresPersosMap.get(pseudo);
        if (scoresPersos == null) {
            scoresPersos = new ScoresPersos();
            this.scoresPersosMap.put(pseudo, scoresPersos);
        }
        return scoresPersos;
    }

    @Override
    public void deconnexion(String pseudo) {
        this.pseudosConnectes.remove(pseudo);
    }

    @Override
    public QuestionReponse next(String pseudo, int idQuestionnaire) {
        Utilisateur u = this.utilisateursMap.get(pseudo);
        QuestionReponse next = null;
        if (u.getIdQuestionnaireEnCours() == idQuestionnaire) {
            if (u.getQuestionReponseCourante()==null) {
                next = this.questionnaires.get(idQuestionnaire).getQuestionsReponses().get(0);
            }
            else {
                if (hasNext(pseudo,idQuestionnaire)) {
                    try {
                        next = this.questionnaires.get(idQuestionnaire).getNext(u.getQuestionReponseCourante());
                    } catch (QuestionnaireEnCoursTermineException e) {
                        return null;
                    }
                }
                else {
                    return null;
                }
            }
        }
        u.setQuestionReponseCourante(next);
        return next;
    }


    @Override
    public void validerQuestion(String pseudo, int idQuestion, String reponse)  {
        this.utilisateursMap.get(pseudo).validerQuestion(idQuestion,reponse);
    }

    @Override
    public boolean hasNext(String pseudo, int idQuestionnaire)  {
        Utilisateur u = utilisateursMap.get(pseudo);
        if (u.getIdQuestionnaireEnCours() != null && u.getQuestionReponseCourante() == null) {
            return true;
        }
        else
        {
            if (u.getIdQuestionnaireEnCours() == idQuestionnaire) {
                try {
                    this.questionnaires.get(idQuestionnaire).getNext(u.getQuestionReponseCourante());
                    return true;
                } catch (QuestionnaireEnCoursTermineException e) {
                    return false;
                }
            }
            else
                return false;
        }

    }

    @Override
    public Collection<CorrectionQuestion> getReponsesDonneesPourQuestionnaire(String pseudo, int id) {
        if (this.scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits().contains(id)) {
            Map<Integer,String> reponses = historiqueDesReponses.get(pseudo).get(id);
            Questionnaire q = this.questionnaires.get(id);
            Collection<CorrectionQuestion> resultat = new ArrayList<>();
            for (QuestionReponse qr : q.getQuestionsReponses()) {
                CorrectionQuestion correctionQuestion = new CorrectionQuestion(qr.getQuestion(),qr.getReponseCorrecte(),reponses.get(qr.getIdQuestion()));
                resultat.add(correctionQuestion);
            }
            return resultat;
        }
        else
            return null;
    }

    @Override
    public Double getScore(String pseudo, int idQuestionnaire) throws QuestionnaireNonFaitException {
        return scoresPersosMap.get(pseudo).getScore(idQuestionnaire);
    }

    @Override
    public Questionnaire getQuestionnaireById(String pseudo, int idQuestionnaire) {
        return this.questionnaires.get(idQuestionnaire);
    }

    @Override
    public QuestionReponse getQuestionForAQuestionnaireById(String pseudo, int idQuestionnaire, int idQuestion) {

        if (this.scoresPersosMap.get(pseudo).getIdsQuestionnairesDejaFaits().contains(idQuestionnaire)) {
            try {
                return questionnaires.get(idQuestionnaire).getQuestionById(idQuestion);
            } catch (QuestionNonExistanteException e) {
                return null;
            }
        }
        else
            return null;
    }


    @Override
    public boolean isAdmin(String pseudo) {
        return administrateurs.contains(pseudo);
    }

    @Override
    public int ajouterQuestionnaire(String pseudo, String titre) throws IsNotAllowedException {
        if (isAdmin(pseudo)) {
            Questionnaire questionnaire = new Questionnaire(titre);
            this.questionnairesEnPreparation.put(questionnaire.getIdQuestionnaire(),questionnaire);
            return questionnaire.getIdQuestionnaire();
        }
        throw new IsNotAllowedException();
    }

    @Override
    public int ajouterQuestion(String pseudo, int idQuestionnaire, String libelleQuestion)  throws IsNotAllowedException{
        if (isAdmin(pseudo)) {
            Questionnaire questionnaire = this.questionnairesEnPreparation.get(idQuestionnaire);
            int id = questionnaire.creerQuestion(libelleQuestion);
            return id;
        }
        throw new IsNotAllowedException();
    }

    @Override
    public void ajouterReponsePossibleAQuestion(String pseudo, int idQuestionnaire, int idQuestion, String reponse)  throws IsNotAllowedException, ReponseDejaProposeeException {
        if (isAdmin(pseudo)) {
            Questionnaire questionnaire = this.questionnairesEnPreparation.get(idQuestionnaire);
            questionnaire.ajouterReponsePossible(idQuestion,reponse);
            return;
        }
        throw new IsNotAllowedException();

    }

    @Override
    public void ajouterReponseReelle(String pseudo, int idQuestionnaire, int idQuestion, String reponseReelle)  throws IsNotAllowedException, ReponsePasProposeeDansLaQuestionException {
        if (isAdmin(pseudo)) {
            Questionnaire questionnaire = this.questionnairesEnPreparation.get(idQuestionnaire);
            questionnaire.ajouterReponseCorrecte(idQuestion,reponseReelle);
            return;
        }
        throw new IsNotAllowedException();
    }

    @Override
    public Questionnaire getQuestionnaireEnPreparationById(String pseudo, int idQuestionnaire)  throws IsNotAllowedException{
        if (isAdmin(pseudo)) {
            return this.questionnairesEnPreparation.get(idQuestionnaire);
        }
        throw new IsNotAllowedException();
    }

    @Override
    public QuestionReponse getQuestionReponseById(String pseudo, int idQuestionnaire, int id)  throws IsNotAllowedException, QuestionNonExistanteException {
        if (isAdmin(pseudo)) {
            Questionnaire questionnaire = this.questionnairesEnPreparation.get(idQuestionnaire);
            QuestionReponse q = questionnaire.getQuestionById(id);
            return q;
        }
        throw new IsNotAllowedException();
    }

    @Override
    public Collection<Questionnaire> getListeQuestionnairesEnPreparation(String pseudo)  throws IsNotAllowedException{
        if (isAdmin(pseudo)) {
            return this.questionnairesEnPreparation.values();
        }
        throw new IsNotAllowedException();
    }

    @Override
    public void mettreEnProductionQuestionnaire(String pseudo, int idQuestionnaire)  throws IsNotAllowedException {
        if (isAdmin(pseudo)) {
            Questionnaire questionnaire = this.questionnairesEnPreparation.get(idQuestionnaire);
            if (questionnaire != null) {
                this.questionnaires.put(questionnaire.getIdQuestionnaire(),questionnaire);
                this.questionnairesEnPreparation.remove(idQuestionnaire);
                return;
            }
            return;
        }
        throw new IsNotAllowedException();
    }

    @Override
    public void demandeRejouerFormulaire(String pseudo, int idQuestionnaire) throws QuestionnaireNonFaitException, DemandeARejouerDejaEffectuee {
        ScoresPersos scoresPersos = scoresPersosMap.get(pseudo);
        if (scoresPersos==null || (!scoresPersos.getIdsQuestionnairesDejaFaits().contains(idQuestionnaire))) {
            throw new QuestionnaireNonFaitException(""+idQuestionnaire);
        }
        DemandeARejouer demande = new DemandeARejouer(pseudo,idQuestionnaire);
        if (demandesRejouer.contains(demande)){
            throw new DemandeARejouerDejaEffectuee();
        }
        demandesRejouer.add(demande);
    }

    @Override
    public List<DemandeARejouer> getListeDemandeARejouer(String pseudo) throws IsNotAllowedException {
        if (!isAdmin(pseudo)) {
            throw new IsNotAllowedException();
        }
        return demandesRejouer;
    }

    @Override
    public List<DemandeARejouer> accepterDemandeARejouer(String pseudo, String demandeur, int idQuestionnaire) throws IsNotAllowedException {
        if (!isAdmin(pseudo)) {
            throw new IsNotAllowedException();
        }
        DemandeARejouer demande = new DemandeARejouer(demandeur,idQuestionnaire);
        demandesRejouer.remove(demande);
        getScoresPersos(demandeur).effacerScorePerso(idQuestionnaire);
        return demandesRejouer;
    }

    @Override
    public List<DemandeARejouer> refuserDemandeARejouer(String pseudo, String demandeur, int idQuestionnaire) throws IsNotAllowedException {
        if (!isAdmin(pseudo)) {
            throw new IsNotAllowedException();
        }
        DemandeARejouer demande = new DemandeARejouer(demandeur,idQuestionnaire);
        demandesRejouer.remove(demande);
        return demandesRejouer;
    }
}
