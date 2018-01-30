package servlet;

import facade.GestionQCM;
import facade.QCMImpl;
import modele.exceptions.InformationsSaisiesIncoherentesException;
import modele.exceptions.UtilisateurDejaConnecteException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                req.getRequestDispatcher("/WEB-INF/views/login.html").forward(req,resp);
                break;
            case "connect":
                String login = req.getParameter("login");
                String password = req.getParameter("password");
                try {
                    facade.connexion(login,password);
                    req.getSession(true).setAttribute("currentuser",login);
                    //aller au menu
                    req.getRequestDispatcher("/WEB-INF/views/menu.html").forward(req,resp);
                } catch (UtilisateurDejaConnecteException e) {
                    //aller au menu
                    req.getRequestDispatcher("/WEB-INF/views/menu.html").forward(req,resp);
                } catch (InformationsSaisiesIncoherentesException e) {
                    //aller à la page de login
                    req.getRequestDispatcher("/WEB-INF/views/login.html").forward(req,resp);
                }
                break;
            case "choixquesionnaire":
                //Obtenir la liste des questionnaires pour l'utilisateur actuel
                String cutrentuser = (String)req.getSession(true).getAttribute("currentuser");
                break;


        }

    }

}
