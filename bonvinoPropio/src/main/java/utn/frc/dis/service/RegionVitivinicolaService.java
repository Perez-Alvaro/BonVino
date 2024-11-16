package utn.frc.dis.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import utn.frc.dis.entity.RegionVitivinicola;

import java.util.List;

public class RegionVitivinicolaService {
    private List<RegionVitivinicola> regiones;

    public List<RegionVitivinicola> ConseguirRegiones() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener e imprimir datos de RegionVitivinicola
            regiones = session.createQuery("from RegionVitivinicola", RegionVitivinicola.class).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
        }
        return regiones;
    }
}
