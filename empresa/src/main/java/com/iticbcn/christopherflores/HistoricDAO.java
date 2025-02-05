package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.iticbcn.christopherflores.model.Historic;
import com.iticbcn.christopherflores.model.Tasca;

public class HistoricDAO {
    
    public static void opcionesHistoric(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
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
                    System.out.println("\nOpción incorrecta, por favor:");
                    muestraOpciones();
                    break;
            }
        }       
    }


    public static void muestraOpciones() {
        System.out.println("""

        TABLA HISTORIC:

        1. CREA UN HISTORIC
        2. ENCUENTRA HISTORIC
        3. MODIFICA HISTORIC
        4. ELIMINA HISTORIC

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraHistoric(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            System.out.println("Introduce la ID de una tarea para integrarla al Histórico:");
            if (!TascaDAO.muestrasAllTascas(session)) return;
            Tasca tasca = TascaDAO.encuentraTascaPorID(session, entrada);
            System.out.print("Fecha de inicio (xx/xx/xxxx)> ");
            String fechaInicio = entrada.readLine();
            System.out.print("Fecha final (xx/xx/xxxx)> ");
            String fechaFinal = entrada.readLine();
            
            Historic historic = new Historic(fechaInicio, fechaFinal, tasca);
            
            session.persist(historic);
            session.getTransaction().commit();
            System.out.println("Histórico creada con éxito.");
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

    public static void consultaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            if (!muestrasAllHistorics(session)) return;
            Historic historic = encuentraHistoricPorID(session, entrada);
    
            System.out.println("\n Histórico encontrado: " + historic.toString());
    
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
    
    public static void modificaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try{
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllHistorics(session)) return;
    
            Historic historic = encuentraHistoricPorID(session, entrada);
            boolean opcionValida = false;
            String opcion;
    
            System.out.println("""
    
                    ¿Qué atributo desea modificar?
                    1. Fecha de inicio
                    2. Fecha final
                    3. Tasca
                    4. (Modifcar todos)
                    """);
    
            while (!opcionValida) {
                System.out.print("Opción: ");
                opcion = entrada.readLine().strip();
                if (opcion.isEmpty()) continue;
                switch (opcion) {
                    case "1":
                        System.out.print("Fecha de inicio (xx/xx/xxxx)> ");
                        historic.setDataInici(entrada.readLine());    
                        opcionValida = true;
                        break;
                    case "2":
                        System.out.print("Fecha final (xx/xx/xxxx)> ");
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
                        System.out.print("Fecha de inicio (xx/xx/xxxx)> ");
                        historic.setDataInici(entrada.readLine());   
                        System.out.print("Fecha final (xx/xx/xxxx)> ");
                        historic.setDataFi(entrada.readLine());
                        System.out.print("Ingresa la ID de Tasca que desea reemplazar:");
                        TascaDAO.muestrasAllTascas(session);
                        Tasca nuevaTasca = TascaDAO.encuentraTascaPorID(session, entrada);
                        historic.setTasca(nuevaTasca);
                        opcionValida = true;
                        break;
                    default:
                        System.out.println("\nOpción incorrecta! Por favor, vuelva a intentarlo.\n");
                }
            }
            session.merge(historic);
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

    public static void eliminaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            
            if (!muestrasAllHistorics(session)) return;

            Historic historic = encuentraHistoricPorID(session, entrada);
    
            System.out.println("Histórico encontrado.");
            System.out.print("Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(historic);
            } else {
                System.out.println("Operación cancelada.");
                return;
            }
    
            session.getTransaction().commit();
    
            System.out.println("Hitórico eliminado con éxito.");
            
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

    public static boolean muestrasAllHistorics(Session session) {
        try {
            Query<Historic> query = session.createQuery("from Historic", Historic.class);
            List<Historic> historics = query.list();
    
            if (historics.isEmpty()) {
                System.out.println("No hay históricos registradas.");
                return false;
            } else {
                System.out.println("Históricos disponibles:");
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

}
