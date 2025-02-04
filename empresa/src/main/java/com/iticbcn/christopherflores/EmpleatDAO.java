package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iticbcn.christopherflores.model.Departament;
import com.iticbcn.christopherflores.model.Empleat;

public class EmpleatDAO {
    
    public static void opcionesEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        boolean confirma = true;
        while (confirma) {
            System.out.print("CJ_HIBERNATE> ");
            String orden = entrada.readLine().strip();
            if (orden.isEmpty()) continue;
            switch (orden) {
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
    
            Departament departament = encuentraDepartamentPorID(session, entrada);
    
            System.out.print("Nombre> ");
            String nomEmpleat = entrada.readLine();
            System.out.print("DNI> ");
            String dni = entrada.readLine();
            System.out.print("Correo> ");
            String correu = entrada.readLine();
            System.out.print("telefon> ");
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
    
    }

    public static void eliminaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException {
        
    }
    
    public static void modificaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException {
        
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
}
