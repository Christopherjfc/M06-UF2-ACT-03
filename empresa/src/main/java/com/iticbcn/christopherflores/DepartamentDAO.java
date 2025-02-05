package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.iticbcn.christopherflores.model.Departament;

public class DepartamentDAO {

    public static void opcionesDepartament(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        boolean confirma = true;
        String opcion;
        while (confirma) {
            System.out.print("CJ_HIBERNATE> ");
            opcion = entrada.readLine().strip();
            if (opcion.isEmpty()) continue;
            switch (opcion) {
                case "1":registraDepartament(sf, entrada);
                    muestraOpciones();
                    break;
                case "2":
                    consultaDepartament(sf, entrada);
                    muestraOpciones();
                    break;
                case "3":
                    modificaDepartament(sf, entrada);
                    muestraOpciones();
                    break;
                case "4":
                    eliminaDepartament(sf, entrada);
                    muestraOpciones();
                    break;
                case "q":
                    confirma = false;
                    break;
                default:
                    System.out.println("Opción incorrecta, por favor:");
                    muestraOpciones();
                    break;
            }
        }       
    }


    public static void muestraOpciones() {
        System.out.println("""

        TABLA DEPARTAMENT:

        1. CREA UN DEPARTAMENT
        2. ENCUENTRA DEPARTAMENT
        3. MODIFICA DEPARTAMENT
        4. ELIMINA DEPARTAMENT

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraDepartament(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            System.out.print("Nombre Departamento> ");
            String nomDep = entrada.readLine();
            Departament departament = new Departament(nomDep);
            session.persist(departament);
            session.getTransaction().commit();
            System.out.println("Departamento registrado correctamente.");
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error Hibernate: "+ e.getMessage());
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperat: "+ e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public static void consultaDepartament(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            muestrasAllDepartaments(session);
            Departament departament = encuentraDepartamentPorID(session, entrada);

            System.out.println("\n El nombre del departamento es: " + departament.getNomDepartament());

        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error Hibernate: "+ e.getMessage());
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperat: "+ e.getMessage());
        }finally {
            if (session != null) session.close();
        }
    }

    public static void modificaDepartament(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();

            muestrasAllDepartaments(session);
    
            Departament departament = encuentraDepartamentPorID(session, entrada);
            System.out.println("Para salir escriba 'q'");
            System.out.print("Nuevo nombre del departamento: ");
            String nuevoNombre = entrada.readLine();
            if (nuevoNombre.equals("q")) return;
            departament.setNomDepartament(nuevoNombre);
    
            session.merge(departament);
            session.getTransaction().commit();
    
            System.out.println("Departamento modificado con éxito.");
    
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error Hibernate: " + e.getMessage());
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public static void eliminaDepartament(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();

            muestrasAllDepartaments(session);
    
            Departament departament = encuentraDepartamentPorID(session, entrada);
    
            System.out.println("Departamento encontrado.");
            System.out.print("Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(departament);
            } else {
                System.out.println("Operación cancelada.");
                return;
            }
    
            session.getTransaction().commit();
    
            System.out.println("Departamento eliminado con éxito.");
    
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error Hibernate: " + e.getMessage());
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public static Departament encuentraDepartamentPorID(Session session, BufferedReader entrada) throws IOException {
        Departament departament = null;
        while (departament == null) {
            System.out.print("Introduzca la ID del departamento que desea obtener: ");
            try {
                Integer id = Integer.parseInt(entrada.readLine());
                departament = session.find(Departament.class, id);
    
                if (departament == null) {
                    System.out.printf("El departamento con ID %d no existe.%n", id);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: introduzca un número entero.");
            }
        }
        return departament;
    }

    public static void muestrasAllDepartaments(Session session) {
        try {
            Query<Departament> query = session.createQuery("from Departament", Departament.class);
            List<Departament> departaments = query.list();
    
            if (departaments.isEmpty()) {
                System.out.println("No hay departamentos registrados.");
            } else {
                System.out.println("Departamentos disponibles:");
                System.out.println();
                for (Departament d : departaments) {
                    System.out.println("ID: " + d.getIdDepartament() + " | Nombre: " + d.getNomDepartament());
                }
                System.out.println();
            }
        } catch (HibernateException e) {
            System.out.println("Error Hibernate: " + e.getMessage());
        }
    }
}
