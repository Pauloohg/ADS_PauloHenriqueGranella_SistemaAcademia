/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.upf.trabalhofinalacademia.controller;

import br.upf.trabalhofinalacademia.entity.AlunoEntity;
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
@Named(value = "alunoController")
@SessionScoped
public class AlunoController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinalacademia.facade.AlunoFacade ejbFacade;
    
    private AlunoEntity aluno = new AlunoEntity();
    private List<AlunoEntity> alunoList = new ArrayList<>();
    private AlunoEntity selected;

    public AlunoEntity getAluno() {
        return aluno;
    }

    public void setAluno(AlunoEntity aluno) {
        this.aluno = aluno;
    }

    public List<AlunoEntity> getAlunoList() {
        return ejbFacade.buscarTodos();
    }

    public void setAlunoList(List<AlunoEntity> alunoList) {
        this.alunoList = alunoList;
    }

    public AlunoEntity getSelected() {
        return selected;
    }

    public void setSelected(AlunoEntity selected) {
        this.selected = selected;
    }
        
    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }
    
    private void persist(AlunoController.PersistAction persistAction, String successMessage) {
        try {
            switch (persistAction) {
                case CREATE:
                    ejbFacade.createReturn(aluno);  
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
    
    public AlunoEntity prepareAdicionar() {
        aluno = new AlunoEntity();
        return aluno;
    }
    
    public void adicionarAluno() {
        persist(PersistAction.CREATE, "Aluno adicionado com sucesso!");
    }
    
    public void deletarAluno() {
        persist(PersistAction.DELETE, "Aluno deletado com sucesso!");
    }
    
    public void editarAluno() {
        persist(PersistAction.UPDATE, "Aluno editado com sucesso!");
    }
}
