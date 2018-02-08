package servlet;

import facade.GestionQCM;
import facade.QCMImpl;
import modele.CorrectionQuestion;
import modele.QuestionReponse;
import modele.Questionnaire;
import modele.exceptions.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class Controleur extends HttpServlet {

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        facade = new QCMImpl();
    }

    private GestionQCM facade;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String s = req.getParameter("action");
        switch(s){
            case "login":
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                break;
            case "connect":
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                Integer idQuestionnaire;
                try {
                    facade.connexion(login,password);
                    req.getSession(true).setAttribute("currentuser",login);
                    req.getRequestDispatcher("/WEB-INF/views/menu.html").forward(req,resp);
                } catch (UtilisateurDejaConnecteException e) {
                    req.getRequestDispatcher("/WEB-INF/views/menu.html").forward(req,resp);
                } catch (InformationsSaisiesIncoherentesException e) {
                    req.getSession().setAttribute("message","Erreur! Login ou password incorrect");
                    req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req,resp);
                }
                break;
            case "choixQCM":
                String currentuser = (String)req.getSession(true).getAttribute("currentuser");
                Collection<Questionnaire> listeQcm = facade.getListQuestionnairesNonFaits(currentuser);
                req.setAttribute("listeQcm",listeQcm);
                req.getRequestDispatcher("/WEB-INF/views/choixQCM.jsp").forward(req,resp);
                break;
            case "lancerQcm":
                login = (String)req.getSession().getAttribute("currentuser");
                idQuestionnaire = Integer.parseInt(req.getParameter("idQuestionnaire"));

                try {
                    facade.choixQuestionnaire(login,idQuestionnaire);
                    Questionnaire q = facade.getQuestionnaireById(login,idQuestionnaire);
                    req.getSession().setAttribute("questionnaireEnCours",q);

                    if(facade.hasNext(login,idQuestionnaire)){
                        //lancer le .jsp four aficher la question
                        QuestionReponse qr = facade.next(login,idQuestionnaire);
                        req.setAttribute("laQuestion",qr);
                        req.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(req,resp);
                    }

                } catch (QuestionnaireEnCoursNonTermineException e) {
                    e.printStackTrace();
                }

                break;
            case "repondreQuestion":
                login = (String)req.getSession().getAttribute("currentuser");
                idQuestionnaire = ((Questionnaire)req.getSession().getAttribute("questionnaireEnCours")).getIdQuestionnaire();
                int idQuestion = Integer.parseInt(req.getParameter("idQuestion"));
                String rep = req.getParameter("reponse");
                facade.validerQuestion(login,idQuestion,rep);

                if(facade.hasNext(login,idQuestionnaire)){
                    QuestionReponse qr = facade.next(login,idQuestionnaire);
                    req.setAttribute("laQuestion",qr);
                    req.getRequestDispatcher("/WEB-INF/views/question.jsp").forward(req,resp);

                }else{ //le QCM est terminé
                    try {
                        double score = facade.validerQuestionnaire(login);
                        String libelle = ((Questionnaire)req.getSession().getAttribute("questionnaireEnCours")).getLibelleQuestionnaire();
                        req.getSession().removeAttribute("questionnaireEnCours");
                        req.setAttribute("libelle",libelle);
                        req.setAttribute("score",score);
                        req.getRequestDispatcher("/WEB-INF/views/resultat.jsp").forward(req,resp);

                    } catch (ValidationQuestionnaireException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case "historique": //liste
                login = (String)req.getSession().getAttribute("currentuser");
                facade.getListQuestionnairesFaits(login);
            default:
                break;

        }

    }

}
