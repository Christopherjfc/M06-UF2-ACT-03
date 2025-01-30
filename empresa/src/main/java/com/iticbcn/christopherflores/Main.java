package com.iticbcn.christopherflores;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iticbcn.christopherflores.model.Departament;
import com.iticbcn.christopherflores.model.Empleat;
import com.iticbcn.christopherflores.model.Empresa;
import com.iticbcn.christopherflores.model.Tasca;

public class Main {
    public static void main(String[] args) {
        Session session = null;
        try (SessionFactory sf = HibernateUtil.getSessionFactory()) {
            try {
                session = sf.openSession();
                session.beginTransaction();

                Empresa empr1 = new Empresa("Christopher S.L", "C76567893", "Calle Joshua");
                Empresa empr2 = new Empresa("Khiara S.L", "K65498712", "Calle Jamilee");

                Departament d1 = new Departament("DAM", empr1);
                Departament d2 = new Departament("DAW", empr1);
                Departament d3 = new Departament("DAW", empr2);

                Empleat empl1 = new Empleat("Christopher", "60094467K", "2023_christopher.flores@iticbcn.cat", "697924823", d3);
                Empleat empl2 = new  Empleat("Khiara", "21354678K", "2023_khiara.cayllahua@iticbcn.cat", "123456789", d3);

                Tasca t1 = new Tasca("Act-03_ORM", "La actividad se cerrará en día 9 de diciembre a las 23:59"); 
                Tasca t2 = new Tasca("Act-04_ORM", "La actividad se cerrará en día 9 de diciembre a las 23:59"); 
                Tasca t3 = new Tasca("Act-05_ORM", "La actividad se cerrará en día 9 de diciembre a las 23:59"); 

                // Se guardan los departamentos a la BBDD
                session.persist(d1);
                session.persist(d2);
                session.persist(d3);

                // Se guardan las tareas a la BBDD para luego asignale a los empleados
                session.persist(t1);
                session.persist(t2);
                session.persist(t3);

                // Se asignan las tareas ya registradas a los empleados 
                empl1.addTasca(t1);
                empl1.addTasca(t2);
                empl2.addTasca(t1);
                empl2.addTasca(t3);

                // Se guardan los Empleados a la BBDD
                session.persist(empl1);
                session.persist(empl2);

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