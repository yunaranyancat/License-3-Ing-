package actions;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionQCM;
import facade.QCMImpl;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public abstract class Environment extends ActionSupport implements ApplicationAware,SessionAware {


    protected GestionQCM model;
    protected Map session;

    protected String login;
    protected String password;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setApplication(Map<String, Object> map) {
        //gerer la gestion du QCM(la facade)
        if(!map.containsKey("model")){
            map.put("model",new QCMImpl());
        }

        model = (GestionQCM)map.get("model");
    }

    @Override
    public void setSession(Map<String, Object> map) {
        session = map;
    }

}
