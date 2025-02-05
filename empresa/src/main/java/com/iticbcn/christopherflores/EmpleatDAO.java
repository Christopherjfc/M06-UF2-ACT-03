package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.iticbcn.christopherflores.model.Departament;
import com.iticbcn.christopherflores.model.Empleat;

public class EmpleatDAO {
    
    public static void opcionesEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        boolean confirma = true;
        String opcion;
        while (confirma) {
            System.out.print("CJ_HIBERNATE> ");
            opcion = entrada.readLine().strip();
            if (opcion.isEmpty()) continue;
            switch (opcion) {
                case "1":
                    registraEmpleat(sf, entrada);
                    muestraOpciones();
                    break;
                case "2":
                    consultaEmpleat(sf, entrada);
                    muestraOpciones();
                    break;
                case "3":
                    modificaEmpleat(sf, entrada);
                    muestraOpciones();
                    break;
                case "4":
                    eliminaEmpleat(sf, entrada);
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

        TABLA EMPLEAT:

        1. CREA UN EMPLEAT
        2. ENCUENTRA EMPLEAT
        3. MODIFICA EMPLEAT
        4. ELIMINA EMPLEAT

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            DepartamentDAO.muestrasAllDepartaments(session);
            Departament departament = DepartamentDAO.encuentraDepartamentPorID(session, entrada);
    
            System.out.print("Nombre> ");
            String nomEmpleat = entrada.readLine();
            System.out.print("DNI> ");
            String dni = entrada.readLine();
            System.out.print("Correo> ");
            String correu = entrada.readLine();
            System.out.print("teléfono> ");
            String telefon = entrada.readLine();
            
            Empleat empleat = new Empleat(nomEmpleat, dni, correu, telefon, departament);
            session.persist(empleat);
            session.getTransaction().commit();
    
            System.out.println("Empleado registrado con éxito.");
    
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

    public static void consultaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException{
        Session session = null;
        try {
            session = sf.openSession();
            muestrasAllEmpleats(session);
            Empleat empleat = encuentraEmpleatPorID(session, entrada);
    
            System.out.println("\n Empleado encontrado: " + empleat.toString());
    
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

    
    public static void modificaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();

            muestrasAllEmpleats(session);
    
            Empleat empleat = encuentraEmpleatPorID(session, entrada);
            System.out.println("Empleado encontrado.");
            
            boolean opcionValida = false;
            String opcion;

            System.out.println("""

                    ¿Qué atributo desea modificar?
                    1. Nombre
                    2. DNI
                    3. Correo
                    4. Teléfono
                    5. Departamento
                    6. (Modifcar todos)
                    """);
    
            while (!opcionValida) {
                System.out.print("Opción: ");
                opcion = entrada.readLine().strip();
                if (opcion.isEmpty()) continue;
                switch (opcion) {
                    case "1":
                        System.out.print("Nuevo nombre: ");
                        empleat.setNomEmpleat(entrada.readLine());
                        opcionValida = true;
                        break;
                    case "2":
                        System.out.print("Nuevo DNI: ");
                        empleat.setDni(entrada.readLine());
                        opcionValida = true;
                        break;
                    case "3":
                        System.out.print("Nuevo correo: ");
                        empleat.setCorreu(entrada.readLine());
                        opcionValida = true;
                        break;
                    case "4":
                        System.out.print("Nuevo teléfono: ");
                        empleat.setTelefon(entrada.readLine());
                        opcionValida = true;
                        break;
                    case "5":
                        DepartamentDAO.muestrasAllDepartaments(session);
                        Departament departament = DepartamentDAO.encuentraDepartamentPorID(session, entrada);
                        empleat.setDepartament(departament);
                        opcionValida = true;
                        break;
                    case "6":
                        System.out.print("Nuevo nombre: ");
                        empleat.setNomEmpleat(entrada.readLine());
                        System.out.print("Nuevo DNI: ");
                        empleat.setDni(entrada.readLine());
                        System.out.print("Nuevo correo: ");
                        empleat.setCorreu(entrada.readLine());
                        System.out.print("Nuevo teléfono: ");
                        empleat.setTelefon(entrada.readLine());

                        DepartamentDAO.muestrasAllDepartaments(session);
                        Departament nuevoDep = DepartamentDAO.encuentraDepartamentPorID(session, entrada);
                        empleat.setDepartament(nuevoDep);
                        opcionValida = true;
                        break;
                    default:
                        System.out.println("\nOpción incorrecta! Por favor, vuelva a intentarlo.\n");
                }
            }   
            session.merge(empleat);
            session.getTransaction().commit();
    
            System.out.println("Empleado modificado con éxito.");
    
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

    public static void eliminaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();

            muestrasAllEmpleats(session);
    
            Empleat empleat = encuentraEmpleatPorID(session, entrada);
    
            System.out.println("Empleado encontrado.");
            System.out.print("Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(empleat);
            } else {
                System.out.println("Operación cancelada.");
                return;
            }
    
            session.getTransaction().commit();
    
            System.out.println("Empleado eliminado con éxito.");
    
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

    /*
     * MÉTODOS COMPLEMENTARIOS
     */

    public static Empleat encuentraEmpleatPorID(Session session, BufferedReader entrada) throws IOException {
        Empleat empleat = null;
        while (empleat == null) {
            System.out.print("Introduzca la ID del departamento que desea obtener: ");
            try {
                Integer id = Integer.parseInt(entrada.readLine());
                empleat = session.find(Empleat.class, id);
    
                if (empleat == null) {
                    System.out.printf("El departamento con ID %d no existe.%n", id);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: introduzca un número entero.");
            }
        }
        return empleat;
    }
    
    public static void muestrasAllEmpleats(Session session) {
        try {
            Query<Empleat> query = session.createQuery("from Empleat", Empleat.class);
            List<Empleat> empleats = query.list();
    
            if (empleats.isEmpty()) {
                System.out.println("No hay Empleats registrados.");
            } else {
                System.out.println("Emmpleats disponibles:");
                System.out.println();
                for (Empleat empl : empleats) {
                    System.out.println(empl.toString());
                }
                System.out.println();
            }
        } catch (HibernateException e) {
            System.out.println("Error Hibernate: " + e.getMessage());
        }
    }
}
