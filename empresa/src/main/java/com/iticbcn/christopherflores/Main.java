package com.iticbcn.christopherflores;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iticbcn.christopherflores.model.Departament;
import com.iticbcn.christopherflores.model.Empleat;
import com.iticbcn.christopherflores.model.Empresa;

public class Main {
    public static void main(String[] args) {
        Session session = null;
        try (SessionFactory sf = HibernateUtil.getSessionFactory()) {
            try {
                session = sf.openSession();
                session.beginTransaction();

                Empresa e = new Empresa("Christopher S.L", "C76567893", "Calle Joshua");
                Empresa r = new Empresa("Khiara S.L", "K65498712", "Calle Jamilee");

                Departament d1 = new Departament("DAM", e);
                Departament d2 = new Departament("DAW", e);
                Departament d3 = new Departament("DAW", r);

                Empleat empl = new Empleat("Christopher", "60094467K", "2023_christopher.flores@iticbcn.cat", "697924823", d3);
                session.persist(d1);
                session.persist(d2);
                session.persist(d3);
                session.persist(empl);
                session.getTransaction().commit();

            } catch (HibernateException e) {
                if (session.getTransaction() != null) session.getTransaction().rollback();
                System.out.println("Error Hibernate: "+ e.getMessage());
            } catch (Exception e) {
                if (session.getTransaction() != null) session.getTransaction().rollback();
                System.out.println("Error inesperat: "+ e.getMessage());
            }

        }
    }
}