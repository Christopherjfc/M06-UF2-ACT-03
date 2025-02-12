package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.iticbcn.christopherflores.model.Empleat;
import com.iticbcn.christopherflores.model.Tasca;

public class TascaDAO {
    
    public static void opcionesTasca(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
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
                    case "5":
                    insertarEmpleat(sf, entrada);
                    muestraOpciones();
                    break;
                case "q":
                    confirma = false;
                    break;
                default:
                    printScreen(terminal, "Opción incorrecta, por favor:");
                    muestraOpciones();
                    break;
            }
        }       
    }


    public static void muestraOpciones() {
        System.out.println("""
            
        -------------
         TABLA TASCA
        -------------

        1. CREA UN TASCA
        2. ENCUENTRA TASCA
        3. MODIFICA TASCA
        4. ELIMINA TASCA
        5. AÑADIR EMPLEADO A TASCA

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraTasca(SessionFactory sf, BufferedReader entrada) throws IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            String nomTasca = solicitaYValidaTitulo(entrada);
            String descTasca = solicitaYValidaDescripcion(entrada);
            Tasca tasca = new Tasca(nomTasca, descTasca);
            session.persist(tasca);
            session.getTransaction().commit();
            printScreen(terminal, "Tarea creada con éxito.");
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
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            if (!muestrasAllTascas(session)) return;
            Tasca tasca = encuentraTascaPorID(session, entrada);
            printScreen(terminal, "\nTarea encontrada: " + tasca.toString());
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
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try{
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllTascas(session)) return;

            Tasca tasca = encuentraTascaPorID(session, entrada);
            boolean opcionValida = false;
            String opcion;

            String escogeAtributos ="""

                    ¿Qué atributo desea modificar?
                    1. Título
                    2. Descripción
                    3. (Modifcar todos)
                    """;

            printScreen(terminal, escogeAtributos);
            while (!opcionValida) {
                System.out.print("Opción: ");
                opcion = entrada.readLine().strip();
                if (opcion.isEmpty()) continue;
                switch (opcion) {
                    case "1":
                        tasca.setNomTasca(solicitaYValidaTitulo(entrada));    
                        opcionValida = true;
                        break;
                    case "2":
                        tasca.setDescripcio(solicitaYValidaDescripcion(entrada));
                        opcionValida = true;
                        break;
                    case "3":
                        tasca.setNomTasca(solicitaYValidaTitulo(entrada));    
                        tasca.setDescripcio(solicitaYValidaDescripcion(entrada));
                        opcionValida = true;
                        break;
                    default:
                        printScreen(terminal, "\nOpción incorrecta! Por favor, vuelva a intentarlo.\n");
                }
            }
            session.merge(tasca);
            session.getTransaction().commit();
            
            printScreen(terminal, "Tasca modificado con éxito.");
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
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            
            if (!muestrasAllTascas(session)) return;

            Tasca tasca = encuentraTascaPorID(session, entrada);
    
            printScreen(terminal, "Tarea encontrada.");

            printScreen(terminal, "Comprobando que la tarea encontrada no esté asociada a un Histórico...");

            if (hayTareaEnHistoric(sf, tasca.getIdTasca()) == 1) {
                printScreen(terminal, "No se puede eliminar la tarea porque tiene Históricos asociados.");
                return;
            }

            printScreen(terminal, "Está seguro de que quiere eliminarla? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(tasca);
                printScreen(terminal, "Tarea eliminada con éxito.");
            } else {
                printScreen(terminal, "Operación cancelada.");
                return;
            }
    
            session.getTransaction().commit();
    
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

    public static void insertarEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllTascas(session)) return;
            printScreen(terminal, "Seleccione la tarea a la que desea añadirle una empleado:");
            Tasca tasca = encuentraTascaPorID(session, entrada);
            if (!EmpleatDAO.muestrasAllEmpleats(session)) {
                System.out.println("Cree un empleado para poder asignarla a una Tarea.");
                return;
            }
            printScreen(terminal, "Seleccione el empleado que desea añadirle a la tarea " + tasca.getNomTasca());

            Empleat empleat = EmpleatDAO.encuentraEmpleatPorID(session, entrada);

            printScreen(terminal, "Insertando Empleado....");

            empleat.getTasca().add(tasca);

            printScreen(terminal, "Empleado asignado con éxito.");
            session.getTransaction().commit();
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

    public static boolean muestrasAllTascas(Session session) throws IOException, InterruptedException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        try {
            Query<Tasca> query = session.createQuery("from Tasca", Tasca.class);
            List<Tasca> tascas = query.list();
    
            if (tascas.isEmpty()) {
                printScreen(terminal, "No hay tareas registradas.");
                return false;
            } else {
                printScreen(terminal, "Tareas disponibles:");
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



    /*
     * 
     * VALIDACIÓN DE VARIABLES
     * 
     */

    // Solicita y valida el título
    private static String solicitaYValidaTitulo(BufferedReader entrada) throws IOException {
        System.out.print("Título> ");
        String titulo = entrada.readLine().strip();
    
        while (titulo.isBlank()) {
            System.out.println("Error: El título no puede estar vacío");
            System.out.print("Título> ");
            titulo = entrada.readLine();
        }
        return titulo;
    }

    // Solicita y valida la descripción
    private static String solicitaYValidaDescripcion(BufferedReader entrada) throws IOException {
        System.out.print("Descripción> ");
        String descripcion = entrada.readLine().strip();

        while (descripcion.isBlank()) {
            System.out.println("Error: La descripción no puede estar vacía.");
            System.out.print("Descripción> ");
            descripcion = entrada.readLine();
        }
        return descripcion;
    }

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }
        System.out.println();
    }

    public static int hayTareaEnHistoric(SessionFactory sf, int idTasca) {
        Session session = null;
        int resultado = 0;
        try {
            session = sf.openSession();
            String hql = "SELECT COUNT(h) FROM Historic h WHERE h.tasca.idTasca = :idTasca";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("idTasca", idTasca);
            Long count = query.uniqueResult();
            
            if (count != null && count > 0) {
                resultado = 1; // Hay tareas asociadas
            }
        } catch (HibernateException e) {
            System.out.println("Error Hibernate: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resultado;
    }    
}
