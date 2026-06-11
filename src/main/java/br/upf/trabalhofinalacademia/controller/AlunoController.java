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
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
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
    
    public AlunoEntity getAluno(java.lang.Integer id) {
    return ejbFacade.find(id);
    }

    @FacesConverter(forClass = AlunoEntity.class)
    public static class AlunoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlunoController controller
                    = (AlunoController) facesContext.getApplication().getELResolver()
                            .getValue(facesContext.getELContext(), null, "alunoController");
            return controller.getAluno(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AlunoEntity) {
                AlunoEntity o = (AlunoEntity) object;
                return getStringKey(o.getId());
            } else {
                return null;
            }
        }
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
