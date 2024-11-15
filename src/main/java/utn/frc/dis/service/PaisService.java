package utn.frc.dis.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import utn.frc.dis.entity.Pais;
import utn.frc.dis.entity.Vino;

public class PaisService {
    private List<Pais> paises;

    public List<Pais> ConseguirVinos(){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Obtener e imprimir datos de Vino
            paises = session.createQuery("from Pais ", Pais.class).list();
            //acá hace un query y del resultado crea una lista de paises

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
            return paises;

        }



    }

}
//pensandolo mejor creo que no hace falta esto
//esto sería para crear una