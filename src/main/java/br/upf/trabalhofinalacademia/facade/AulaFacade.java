/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.trabalhofinalacademia.facade;

import br.upf.trabalhofinalacademia.entity.AulaEntity;
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
public class AulaFacade extends AbstractFacade<AulaEntity> {
    
    @PersistenceContext(unitName = "TrabalhoAcademiaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public AulaFacade() {
        super(AulaEntity.class);
    }
    
    private List<AulaEntity> entityList;
    
    public List<AulaEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM AulaEntity p order by p.dataAula");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<AulaEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
}
