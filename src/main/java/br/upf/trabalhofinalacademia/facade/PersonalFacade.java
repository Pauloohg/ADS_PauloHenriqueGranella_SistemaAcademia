/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.trabalhofinalacademia.facade;

import br.upf.trabalhofinalacademia.entity.PersonalEntity;
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
public class PersonalFacade extends AbstractFacade<PersonalEntity> {
    
    @PersistenceContext(unitName = "TrabalhoAcademiaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public PersonalFacade() {
        super(PersonalEntity.class);
    }
    
    private List<PersonalEntity> entityList;
    
    public List<PersonalEntity> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM PersonalEntity p order by p.nome");
            if (!query.getResultList().isEmpty()) {
                entityList = (List<PersonalEntity>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }
    
    public PersonalEntity buscarPorEmail(String email, String senha) {
        PersonalEntity personal = new PersonalEntity();
        try {
            Query query = getEntityManager()
                    .createQuery("SELECT p FROM PersonalEntity p WHERE p.email = :email AND p.senha = :senha");
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            if (!query.getResultList().isEmpty()) {
                personal = (PersonalEntity) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return personal;
    }
}
