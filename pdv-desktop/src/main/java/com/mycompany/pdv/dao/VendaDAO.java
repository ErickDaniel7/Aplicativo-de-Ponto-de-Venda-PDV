package com.mycompany.pdv.dao;

import com.mycompany.pdv.modelos.Venda;
import com.mycompany.pdv.dao.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class VendaDAO {

    EntityManager em = JPAUtil.getEntityManager();

    public VendaDAO() {

    }

    public void inserir(Venda e) {
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(e);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }


}
