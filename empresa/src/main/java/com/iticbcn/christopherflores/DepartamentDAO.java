package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import com.iticbcn.christopherflores.model.Departament;

public class DepartamentDAO {

    public static void opcionesDepartament(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
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
                    printScreen(terminal, "Opción incorrecta, por favor:");
                    muestraOpciones();
                    break;
            }
        }       
    }


    public static void muestraOpciones() {
        System.out.println("""
            
        -------------------
         TABLA DEPARTAMENT
        -------------------

        1. CREA UN DEPARTAMENT
        2. ENCUENTRA DEPARTAMENT
        3. MODIFICA DEPARTAMENT
        4. ELIMINA DEPARTAMENT

        Introduzca "q" para regresar hacia atrás.
        """);
    }
    
    public static void registraDepartament(SessionFactory sf, BufferedReader entrada) throws IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            Departament departament = new Departament(solicitaYCerificaNomDep(entrada));
            session.persist(departament);
            session.getTransaction().commit();
            printScreen(terminal, "Departamento registrado correctamente.");
        } catch (ConstraintViolationException cve) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error: ya existe un departamento con ese nombre.");
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperat: "+ e.getMessage());
        }finally {
            if (session != null) session.close();
        }
    }

    public static void consultaDepartament(SessionFactory sf, BufferedReader entrada) throws IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            if (!muestrasAllDepartaments(session)) return;
            Departament departament = encuentraDepartamentPorID(session, entrada);

            printScreen(terminal, "El nombre del departamento es: " + departament.getNomDepartament());
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
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllDepartaments(session)) return;
    
            Departament departament = encuentraDepartamentPorID(session, entrada);
            System.out.print("Nuevo ");
            departament.setNomDepartament(solicitaYCerificaNomDep(entrada));
    
            session.merge(departament);
            session.getTransaction().commit();
    
            printScreen(terminal, "Departamento modificado con éxito.");
    
        } catch (ConstraintViolationException cve) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error: ya existe un departamento con ese nombre.");
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public static void eliminaDepartament(SessionFactory sf, BufferedReader entrada) throws IOException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllDepartaments(session)) return;
    
            Departament departament = encuentraDepartamentPorID(session, entrada);
    
            printScreen(terminal, "Departamento encontrado.");

            printScreen(terminal, "Comprobando que el departamento encontrado no esté asociado a un Empleado...");

            if (hayDepEnEmpleado(sf, departament.getIdDepartament()) == 1) {
                printScreen(terminal, "No se puede eliminar el departamento porque tiene empleados asociados.");
                return;
            }

            printScreen(terminal, "Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(departament);
                printScreen(terminal, "Departamento eliminado con éxito.");
            } else {
                printScreen(terminal, "Operación cancelada.");
                return;
            }
    
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
    
    public static boolean muestrasAllDepartaments(Session session) throws IOException, InterruptedException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        try {
            Query<Departament> query = session.createQuery("from Departament", Departament.class);
            List<Departament> departaments = query.list();
            
            if (departaments.isEmpty()) {
                printScreen(terminal, "No hay departamentos registrados.");
                return false;
            } else {
                printScreen(terminal, "Departamentos disponibles:");
                System.out.println();
                for (Departament departament : departaments) {
                    System.out.println(departament.toString());
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
     * Validación de atributos
     */

    public static String solicitaYCerificaNomDep(BufferedReader entrada) throws IOException, InterruptedException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        String nombre = "";
        while (true) {
            System.out.print("Nombre> ");
            nombre = entrada.readLine().strip();
            if (nombre.isBlank()) {
                printScreen(terminal,"Error: el nombre no puede estar vacío");
            }else {
                break;
            }
        }
        return nombre;
    }

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }
        System.out.println();
    }
    
    public static int hayDepEnEmpleado(SessionFactory sf, int idDepartament) {
        Session session = null;
        int resultado = 0;
        try {
            session = sf.openSession();
            String hql = "SELECT COUNT(e) FROM Empleat e WHERE e.departament.idDepartament = :idDepartament";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("idDepartament", idDepartament);
            Long count = query.uniqueResult();
            
            if (count != null && count > 0) {
                resultado = 1; // Hay empleados asociados
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
