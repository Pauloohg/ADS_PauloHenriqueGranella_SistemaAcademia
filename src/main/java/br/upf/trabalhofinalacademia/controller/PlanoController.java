/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.upf.trabalhofinalacademia.controller;

import br.upf.trabalhofinalacademia.entity.PlanoEntity;
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
@Named(value = "planoController")
@SessionScoped
public class PlanoController implements Serializable {
    
    @EJB
    private br.upf.trabalhofinalacademia.facade.PlanoFacade ejbFacade;
    
    private PlanoEntity plano = new PlanoEntity();
    private List<PlanoEntity> planoList = new ArrayList<>();
    private PlanoEntity selected;

    public PlanoEntity getPlano() {
        return plano;
    }

    public void setPlano(PlanoEntity plano) {
        this.plano = plano;
    }

    public List<PlanoEntity> getPlanoList() {
        return ejbFacade.buscarTodos();
    }

    public void setPlanoList(List<PlanoEntity> planoList) {
        this.planoList = planoList;
    }

    public PlanoEntity getSelected() {
        return selected;
    }

    public void setSelected(PlanoEntity selected) {
        this.selected = selected;
    }
    
    public PlanoEntity getPlano(java.lang.Integer id) {
        return ejbFacade.find(id);
    }


    @FacesConverter(forClass = PlanoEntity.class)
    public static class PlanoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlanoController controller
                    = (PlanoController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "planoController");
            return controller.getPlano(getKey(value));
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
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PlanoEntity) {
                PlanoEntity o = (PlanoEntity) object;
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
    
    private void persist(PlanoController.PersistAction persistAction, String successMessage) {
        try {
            switch (persistAction) {
                case CREATE:
                    ejbFacade.createReturn(plano);  
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
    
    public PlanoEntity prepareAdicionar() {
        plano = new PlanoEntity();
        return plano;
    }
    
    public void adicionarPlano() {
        persist(PersistAction.CREATE, "Plano adicionado com sucesso!");
    }
    
    public void editarPlano() {
        persist(PersistAction.UPDATE, "Plano editado com sucesso!");
    }
    
    public void deletarPlano() {
        persist(PersistAction.DELETE, "Plano deletado com sucesso!");
    }    
}
