/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.upf.trabalhofinalacademia.controller;

import br.upf.trabalhofinalacademia.entity.PersonalEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@Named(value = "personalController")
@SessionScoped
public class PersonalController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinalacademia.facade.PersonalFacade ejbFacade;
    
    private PersonalEntity personal = new PersonalEntity();
    private List<PersonalEntity> personalList = new ArrayList<>();
    private PersonalEntity selected;

    public PersonalEntity getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalEntity personal) {
        this.personal = personal;
    }

    public List<PersonalEntity> getPersonalList() {
        return ejbFacade.buscarTodos();
    }

    public void setPersonalList(List<PersonalEntity> personalList) {
        this.personalList = personalList;
    }

    public PersonalEntity getSelected() {
        return selected;
    }

    public void setSelected(PersonalEntity selected) {
        this.selected = selected;
    }
    
    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
    
    private void persist(PersonalController.PersistAction persistAction, String successMessage) {
        try {
            switch (persistAction) {
                case CREATE:
                    ejbFacade.createReturn(personal);  
                    break;
                case UPDATE:
                    ejbFacade.edit(selected);            
                    selected = null;                     
                    break;
                case DELETE:
                    ejbFacade.remove(selected);          
                    selected = null;                    
                    break;
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }
    
    public void addSuccessMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }
    
    public void addErrorMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }
    
    public PersonalEntity prepareAdicionar() {
        personal = new PersonalEntity();
        return personal;
    }
    
    public void adicionarPersonal() {
        persist(PersistAction.CREATE,"Personal adicionado com sucesso!");
    }
    
    public void editarPersonal() {
        persist(PersistAction.UPDATE,"Personal editado com sucesso!");
    }
    
    public void deletarPersonal() {
        persist(PersistAction.DELETE, "Personal deletado com sucesso!");
              
    }
}
