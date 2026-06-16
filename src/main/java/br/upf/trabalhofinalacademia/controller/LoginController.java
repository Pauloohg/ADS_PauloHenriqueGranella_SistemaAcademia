package br.upf.trabalhofinalacademia.controller;

import br.upf.trabalhofinalacademia.entity.PersonalEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private br.upf.trabalhofinalacademia.facade.PersonalFacade ejbFacade;

    public LoginController() {
    }

    private PersonalEntity personal;

    public void prepareAutenticarPersonal() {
        personal = new PersonalEntity();
    }

    @PostConstruct
    public void init() {
        prepareAutenticarPersonal();
    }

    public String validarLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        PersonalEntity personalDB = ejbFacade.buscarPorEmail(
            personal.getEmail(), personal.getSenha());

        if (personalDB != null && personalDB.getId() != null) {
            session.setAttribute("pessoaLogada", personalDB);
            return "/admin/personal.xhtml?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Falha no Login!",
                "Email ou senha incorreto!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    public PersonalEntity getPersonal() { return personal; }
    public void setPersonal(PersonalEntity personal) { this.personal = personal; }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }
}