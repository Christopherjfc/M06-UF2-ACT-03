package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.iticbcn.christopherflores.model.Tasca;

public class TascaDAO {
    
    public static void opcionesTasca(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        boolean confirma = true;
        while (confirma) {
            System.out.print("CJ_HIBERNATE> ");
            String orden = entrada.readLine().strip();
            if (orden.isEmpty()) continue;
            switch (orden) {
                case "1":
                    registraTasca(sf, entrada);
                    muestraOpciones();
                    break;
                case "2":
                    consultaTasca(sf, entrada);
                    muestraOpciones();
                    break;
                case "3":
                    modificaTasca(sf, entrada);
                    muestraOpciones();
                    break;
                case "4":
                    eliminaTasca(sf, entrada);
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

        TABLA TASCA:

        1. CREA UN TASCA
        2. ENCUENTRA TASCA
        3. MODIFICA TASCA
        4. ELIMINA TASCA

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraTasca(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            System.out.print("Título> ");
            String nomTasca = entrada.readLine();
            System.out.print("Descripción> ");
            String descTasca = entrada.readLine();
            Tasca tasca = new Tasca(nomTasca, descTasca);
            session.persist(tasca);
            session.getTransaction().commit();
            System.out.println("Tarea creada con éxito.");
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

    public static void consultaTasca(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            if (!muestrasAllTascas(session)) return;
            Tasca tasca = encuentraTascaPorID(session, entrada);
            System.out.println("\n Tarea encontrada: " + tasca.toString());
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

    public static void modificaTasca(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try{
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllTascas(session)) return;

            Tasca tasca = encuentraTascaPorID(session, entrada);
            boolean opcionValida = false;
            String opcion;

            System.out.println("""

                    ¿Qué atributo desea modificar?
                    1. Título
                    2. Descripción
                    3. (Modifcar todos)
                    """);

            while (!opcionValida) {
                System.out.print("Opción: ");
                opcion = entrada.readLine().strip();
                if (opcion.isEmpty()) continue;
                switch (opcion) {
                    case "1":
                        System.out.print("Nuevo título: ");
                        tasca.setNomTasca(entrada.readLine());    
                        opcionValida = true;
                        break;
                    case "2":
                        System.out.print("Nuevo título: ");
                        tasca.setDescripcio(entrada.readLine());
                        opcionValida = true;
                        break;
                    case "3":
                        System.out.print("Nuevo título: ");
                        tasca.setNomTasca(entrada.readLine()); 
                        System.out.print("Nuevo título: ");
                        tasca.setDescripcio(entrada.readLine());
                        opcionValida = true;
                        break;
                    default:
                        System.out.println("\nOpción incorrecta! Por favor, vuelva a intentarlo.\n");
                }
            }
            session.merge(tasca);
            session.getTransaction().commit();
            
            System.out.println("Tasca modificado con éxito.");
        } catch(HibernateException e){
            if(session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error Hibernate: "+ e.getMessage());
        } catch (Exception e) {
            if(session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperat: "+ e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
    
    public static void eliminaTasca(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            
            if (!muestrasAllTascas(session)) return;

            Tasca tasca = encuentraTascaPorID(session, entrada);
    
            System.out.println("Tarea encontrada.");
            System.out.print("Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(tasca);
            } else {
                System.out.println("Operación cancelada.");
                return;
            }
    
            session.getTransaction().commit();
    
            System.out.println("Tarea eliminada con éxito.");
            
        } catch(HibernateException e){
            if(session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error Hibernate: "+ e.getMessage());
        } catch (Exception e) {
            if(session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperat: "+ e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
    
    
    public static Tasca encuentraTascaPorID(Session session, BufferedReader entrada) throws IOException {
        Tasca tasca = null;
        while (tasca == null) {
            System.out.print("Introduzca la ID de la tarea que desea obtener: ");
            try {
                Integer id = Integer.parseInt(entrada.readLine());
                tasca = session.find(Tasca.class, id);
    
                if (tasca == null) {
                    System.out.printf("La tarea con ID %d no existe.%n", id);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: introduzca un número entero.");
            }
        }
        return tasca;
    }

    public static boolean muestrasAllTascas(Session session) {
        try {
            Query<Tasca> query = session.createQuery("from Tasca", Tasca.class);
            List<Tasca> tascas = query.list();
    
            if (tascas.isEmpty()) {
                System.out.println("No hay tareas registradas.");
                return false;
            } else {
                System.out.println("Tareas disponibles:");
                System.out.println();
                for (Tasca tasca : tascas) {
                    System.out.println(tasca.toString());
                }
                System.out.println();
                return true;
            }
        } catch (HibernateException e) {
            System.out.println("Error Hibernate: " + e.getMessage());
        }
        return false;
    }
}
