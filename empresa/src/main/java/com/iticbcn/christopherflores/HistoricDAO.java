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

import com.iticbcn.christopherflores.model.Historic;
import com.iticbcn.christopherflores.model.Tasca;

public class HistoricDAO {
    
    public static void opcionesHistoric(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        boolean confirma = true;
        while (confirma) {
            System.out.print("CJ_HIBERNATE> ");
            String orden = entrada.readLine().strip();
            if (orden.isEmpty()) continue;
            switch (orden) {
                case "1":
                    registraHistoric(sf, entrada);
                    muestraOpciones();
                    break;
                case "2":
                    consultaHistoric(sf, entrada);
                    muestraOpciones();
                    break;
                case "3":
                    modificaHistoric(sf, entrada);
                    muestraOpciones();
                    break;
                case "4":
                    eliminaHistoric(sf, entrada);
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

        ----------------
         TABLA HISTORIC
        ----------------

        1. CREA UN HISTORIC
        2. ENCUENTRA HISTORIC
        3. MODIFICA HISTORIC
        4. ELIMINA HISTORIC

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraHistoric(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            printScreen(terminal, "Introduce la ID de una tarea para integrarla al Histórico:");
            if (!TascaDAO.muestrasAllTascas(session)) {
                printScreen(terminal, "Debe crear una Tarea para poder gestionar la tabla Historic.");
                return;
            }
            Tasca tasca = TascaDAO.encuentraTascaPorID(session, entrada);
            System.out.println("formato: (dd/mm/yyyy o dd-mm-yyyy)");
            String fechaInicio = solicitaYValidaFecha(entrada, "fecha inicio> ");
            String fechaFinal = solicitaYValidaFecha(entrada, "fecha final> ");
            
            Historic historic = new Historic(fechaInicio, fechaFinal, tasca);
            session.persist(historic);
            tasca.addhsitoric(historic);
            session.getTransaction().commit();
            printScreen(terminal, "Histórico creada con éxito.");
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

    public static void consultaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            if (!muestrasAllHistorics(session)) return;
            Historic historic = encuentraHistoricPorID(session, entrada);
            printScreen(terminal, "\nHistórico encontrado: " + historic.toString());
    
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
    
    public static void modificaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try{
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllHistorics(session)) return;
    
            Historic historic = encuentraHistoricPorID(session, entrada);
            boolean opcionValida = false;
            String opcion;
    
            String escogeAtributos ="""
    
                    ¿Qué atributo desea modificar?
                    1. Fecha de inicio
                    2. Fecha final
                    3. Tasca
                    4. (Modifcar todos)
                    """;
            printScreen(terminal, escogeAtributos);
    
            while (!opcionValida) {
                System.out.print("Opción: ");
                opcion = entrada.readLine().strip();
                if (opcion.isEmpty()) continue;
                switch (opcion) {
                    case "1":
                        System.out.print("Fecha de inicio> ");
                        historic.setDataInici(entrada.readLine());    
                        opcionValida = true;
                        break;
                    case "2":
                        System.out.print("Fecha final> ");
                        historic.setDataFi(entrada.readLine());
                        opcionValida = true;
                        break;
                    case "3":
                        System.out.print("Ingresa la ID de Tasca que desea reemplazar:");
                        TascaDAO.muestrasAllTascas(session);
                        Tasca tasca = TascaDAO.encuentraTascaPorID(session, entrada);
                        historic.setTasca(tasca);
                        opcionValida = true;
                        break;
                    case "4":
                        System.out.print("Fecha de inicio> ");
                        historic.setDataInici(entrada.readLine());   
                        System.out.print("Fecha final> ");
                        historic.setDataFi(entrada.readLine());
                        System.out.print("Ingresa la ID de Tasca que desea reemplazar:");
                        TascaDAO.muestrasAllTascas(session);
                        Tasca nuevaTasca = TascaDAO.encuentraTascaPorID(session, entrada);
                        historic.setTasca(nuevaTasca);
                        opcionValida = true;
                        break;
                    default:
                       printScreen(terminal, "\nOpción incorrecta! Por favor, vuelva a intentarlo.\n");
                }
            }
            session.merge(historic);
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

    public static void eliminaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            
            if (!muestrasAllHistorics(session)) return;

            Historic historic = encuentraHistoricPorID(session, entrada);
    
            printScreen(terminal, "Histórico encontrado.");
            printScreen(terminal, "Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(historic);
            } else {
                printScreen(terminal, "Operación cancelada.");
                return;
            }
    
            session.getTransaction().commit();
    
            printScreen(terminal, "Hitórico eliminado con éxito.");
            
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
    
    public static Historic encuentraHistoricPorID(Session session, BufferedReader entrada) throws IOException {
        Historic historic = null;
        while (historic == null) {
            System.out.print("Introduzca la ID de la histórico que desea obtener: ");
            try {
                Integer id = Integer.parseInt(entrada.readLine());
                historic = session.find(Historic.class, id);
    
                if (historic == null) {
                    System.out.printf("La histórico con ID %d no existe.%n", id);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: introduzca un número entero.");
            }
        }
        return historic;
    }

    public static boolean muestrasAllHistorics(Session session)  throws IOException, InterruptedException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        try {
            Query<Historic> query = session.createQuery("from Historic", Historic.class);
            List<Historic> historics = query.list();
    
            if (historics.isEmpty()) {
                printScreen(terminal, "No hay históricos registrados.");
                return false;
            } else {
                printScreen(terminal, "Históricos disponibles:");
                System.out.println();
                for (Historic historic : historics) {
                    System.out.println(historic.toString());
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



    // Solicita y valida el fecha
    private static String solicitaYValidaFecha(BufferedReader entrada, String tipoFecha) throws IOException {
        String fecha = "";
        
        while (true) {
            System.out.print(tipoFecha);
            fecha = entrada.readLine();
            if (fecha.matches("^([0-2][0-9]|3[01])[-/](0[1-9]|1[0-2])[-/]\\d{4}$")) {
                break;
            } else {
                System.out.println("Error: Fecha no válida. Debe ser en formato dd/mm/yyyy o dd-mm-yyyy.");
            }
        }
        return fecha;
    }

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }
        System.out.println();
    }
}
