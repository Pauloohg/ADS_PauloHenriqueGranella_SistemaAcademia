/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.trabalhofinalacademia.facade;

import br.upf.trabalhofinalacademia.entity.PlanoEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@Stateless
public class PlanoFacade extends AbstractFacade<PlanoEntity> {
    
    @PersistenceContext(unitName = "TrabalhoAcademiaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public PlanoFacade() {
        super(PlanoEntity.class);
    }
    
    private List<PlanoEntity> entityList;
    
    public List<PlanoEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM PlanoEntity p order by p.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<PlanoEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
    
}
