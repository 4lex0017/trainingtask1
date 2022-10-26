package org.example.database.hibernate;


import org.example.data.FundData;
import org.example.database.FundDataDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("hibernate")
public class FundDataDAOHibernate implements FundDataDAO {
    private final SessionFactory sessionFactory;


    public FundDataDAOHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSessionFactory() {
        return sessionFactory.openSession();
    }

    public void saveFundData(FundData fundData) {
        Transaction transaction = null;
        try (Session session = getSessionFactory()) {
            transaction = session.beginTransaction();
            session.persist(fundData);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public List<FundData> findAllFundData() {

        Transaction transaction = null;
        String fundDataTableName = "FundData";
        try (Session session = getSessionFactory()) {
            return session.createQuery("SELECT a FROM " + fundDataTableName + " a", FundData.class).getResultList();
        }
    }

    @Override
    public void clearTable() {
        // Not needed because config deletes tables
    }

    @Override
    public void generateFundDataTable() {
        //hibernate auto generates Tables
    }

}
