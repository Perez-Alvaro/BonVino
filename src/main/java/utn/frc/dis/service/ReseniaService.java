package utn.frc.dis.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import utn.frc.dis.entity.Resenia;

import java.util.List;

public class ReseniaService {
    private List<Resenia> resenias;

    public List<Resenia> ConseguirResenias() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener e imprimir datos de Resenia
            resenias = session.createQuery("from Resenia", Resenia.class).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
        return resenias;
    }
}
