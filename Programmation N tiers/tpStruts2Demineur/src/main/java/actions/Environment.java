package actions;

import com.opensymphony.xwork2.ActionSupport;
import modele.GestionDemineur;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public abstract class Environment extends ActionSupport implements ApplicationAware,SessionAware {

    protected GestionDemineur model;
    protected Map session;

    protected String login;;

    public void setLogin(String login){this.login = login;}

    @Override
    public void setApplication(Map<String, Object> map) {
        if(!map.containsKey("model")){
            map.put("model",new GestionDemineur());
        }

        model = (GestionDemineur) map.get("model");
    }

    @Override
    public void setSession(Map<String, Object> session) {

    }
}
