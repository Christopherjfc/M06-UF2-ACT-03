package com.iticbcn.christopherflores;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.hibernate.SessionFactory;


public class Main {
    // public static void main(String[] args) {
    // Session session = null;
    // try (SessionFactory sf = HibernateUtil.getSessionFactory()) {
    // try {
    // session = sf.openSession();
    // session.beginTransaction();


    // Empresa empr1 = new Empresa("Christopher S.L", "C76567893", "Calle Joshua");
    // Empresa empr2 = new Empresa("Khiara S.L", "K65498712", "Calle Jamilee");


    // Departament d1 = new Departament("DAM", empr1);
    // Departament d2 = new Departament("DAW", empr1);
    // Departament d3 = new Departament("DAW", empr2);


    // // Se guardan los departamentos a la BBDD
    // session.persist(d1);
    // session.persist(d2);
    // session.persist(d3);


    // Empleat empl1 = new Empleat("Christopher", "60094467K",
    // "2023_christopher.flores@iticbcn.cat", "697924823", d3);
    // Empleat empl2 = new Empleat("Khiara", "21354678K",
    // "2023_khiara.cayllahua@iticbcn.cat", "123456789", d3);


    // Tasca t1 = new Tasca("Act-03_ORM", "La actividad se cerrará en día 9 de
    // diciembre a las 23:59");
    // Tasca t2 = new Tasca("Act-04_ORM", "La actividad se cerrará en día 9 de
    // diciembre a las 23:59");
    // Tasca t3 = new Tasca("Act-05_ORM", "La actividad se cerrará en día 9 de
    // diciembre a las 23:59");


    // // Se guardan las tareas a la BBDD para luego asignale a los empleados
    // session.persist(t1);
    // session.persist(t2);
    // session.persist(t3);


    // // Se asignan las tareas ya registradas a los empleados
    // empl1.addTasca(t1);
    // empl1.addTasca(t2);
    // empl2.addTasca(t1);
    // empl2.addTasca(t3);


    // // Se guardan los Empleados a la BBDD
    // session.persist(empl1);
    // session.persist(empl2);


    // //Creo instáncias de Historic asignandoles Tarea
    // Historic h1 = new Historic(t1, "24/01/2025", "09/02/2025");
    // Historic h2 = new Historic(t2, "30/01/2025", "15/02/2025");
    // Historic h3 = new Historic(t3, "05/02/2025", "23/02/2025");


    // // Se guardan los Historic en la BBDD
    // session.persist(h1);
    // session.persist(h2);
    // session.persist(h3);


    // session.getTransaction().commit();


    // } catch (HibernateException e) {
    // if (session.getTransaction() != null) session.getTransaction().rollback();
    // System.out.println("Error Hibernate: "+ e.getMessage());
    // } catch (Exception e) {
    // if (session.getTransaction() != null) session.getTransaction().rollback();
    // System.out.println("Error inesperat: "+ e.getMessage());
    // }


    // }
    // }


    public static void main(String[] args) throws InterruptedException {
        try (SessionFactory sf = HibernateUtil.getSessionFactory()) {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
                muestraTablas();
                boolean confirma = true;
                String opcion;
                while (confirma) {
                    System.out.print("CJ_HIBERNATE> ");
                    opcion = entrada.readLine().strip();
                    if (opcion.isEmpty()) continue;
                    switch (opcion) {
                        case "1":
                            DepartamentDAO.opcionesDepartament(sf, entrada);
                            muestraTablas();
                            break;
                        case "2":
                            EmpleatDAO.opcionesEmpleat(sf, entrada);
                            muestraTablas();
                            break;
                        case "3":
                            TascaDAO.opcionesTasca(sf, entrada);
                            muestraTablas();
                            break;
                        case "4":
                            HistoricDAO.opcionesHistoric(sf, entrada);
                            muestraTablas();
                            break;
                        case "q":
                            confirma = false;
                            break;
                        default:
                            System.out.println("Opción incorrecta, por favor:");
                            muestraTablas();
                            break;
                    }
                }
                System.out.print("Hasta pronto!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void muestraTablas() {
        System.out.println("""

                Elija que tabla desea gestionar:

                1. DEPARTAMENT
                2. EMPLEAT
                3. TASCA
                4. HISTORIC

                Introduzca "q" para salir del programa.
                """);
    }
}

