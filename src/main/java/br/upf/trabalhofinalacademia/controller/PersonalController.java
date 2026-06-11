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
    
    public PersonalEntity getPersonal(java.lang.Integer id) {
    return ejbFacade.find(id);
    }

    @FacesConverter(forClass = PersonalEntity.class)
    public static class PersonalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonalController controller
                    = (PersonalController) facesContext.getApplication().getELResolver()
                            .getValue(facesContext.getELContext(), null, "personalController");
            return controller.getPersonal(getKey(value));
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
            if (object instanceof PersonalEntity) {
                PersonalEntity o = (PersonalEntity) object;
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
