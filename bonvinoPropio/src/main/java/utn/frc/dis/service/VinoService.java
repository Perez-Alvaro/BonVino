package utn.frc.dis.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import utn.frc.dis.entity.Vino;

import java.util.List;


public class VinoService {
    public VinoService() {
        super();
    }

    private List<Vino> vinos;

    public List<Vino> ConseguirVinos(){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener e imprimir datos de Vino
            vinos = session.createQuery("from Vino", Vino.class).list();
            //acá hace un query y del resultado crea una lista de vinos

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
            return vinos;

        }



    }

}
