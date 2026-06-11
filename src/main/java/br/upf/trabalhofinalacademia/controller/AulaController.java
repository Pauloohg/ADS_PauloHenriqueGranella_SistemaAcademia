/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.upf.trabalhofinalacademia.controller;

import br.upf.trabalhofinalacademia.entity.AulaEntity;
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
@Named(value = "aulaController")
@SessionScoped
public class AulaController implements Serializable {

    @EJB
    private br.upf.trabalhofinalacademia.facade.AulaFacade ejbFacade;
    
    private AulaEntity aula = new AulaEntity();
    private List<AulaEntity> aulaList = new ArrayList<>();
    private AulaEntity selected;

    public AulaEntity getAula() {
        return aula;
    }

    public void setAula(AulaEntity aula) {
        this.aula = aula;
    }

    public List<AulaEntity> getAulaList() {
        return ejbFacade.buscarTodos();
    }

    public void setAulaList(List<AulaEntity> aulaList) {
        this.aulaList = aulaList;
    }

    public AulaEntity getSelected() {
        return selected;
    }

    public void setSelected(AulaEntity selected) {
        this.selected = selected;
    }
    
    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
    
    private void persist(AulaController.PersistAction persistAction, String successMessage) {
        try {
            switch (persistAction) {
                case CREATE:
                    ejbFacade.createReturn(aula);  
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
    
    public AulaEntity prepareAdicionar() {
        aula = new AulaEntity();
        return aula;
    }
    
    public void adicionarAula() {
        persist(PersistAction.CREATE, "Aula adicionada com sucesso!");
    }
    
    public void editarAula() {
        persist(PersistAction.UPDATE, "Aula editada com sucesso!");
    }
    
    public void deletarAula() {
        persist(PersistAction.DELETE, "Aula deletada com sucesso!");
    } 
}
