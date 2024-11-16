package utn.frc.dis.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import utn.frc.dis.entity.Provincia;

import java.util.List;

public class ProvinciaService {
    private List<Provincia> provincias;

    public List<Provincia> ConseguirProvincias() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener e imprimir datos de Provincia
            provincias = session.createQuery("from Provincia", Provincia.class).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
        return provincias;
    }
}
