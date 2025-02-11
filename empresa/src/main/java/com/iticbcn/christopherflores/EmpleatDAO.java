package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import com.iticbcn.christopherflores.model.Departament;
import com.iticbcn.christopherflores.model.Empleat;
import com.iticbcn.christopherflores.model.Tasca;

public class EmpleatDAO {
    
    public static void opcionesEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
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
                case "5":
                    insertarTasca(sf, entrada);
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
            
        ---------------
         TABLA EMPLEAT
        ---------------

        1. CREA UN EMPLEAT
        2. ENCUENTRA EMPLEAT
        3. MODIFICA EMPLEAT
        4. ELIMINA EMPLEAT
        5. AÑADIR TASCA A EMPLEAT

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;   
        String nomEmpleat = "";
        String dni = "";
        String correu = "";
        String telefon = "";
        try {
            session = sf.openSession();
            session.beginTransaction();
            if (!DepartamentDAO.muestrasAllDepartaments(session)) {
                printScreen(terminal, "Debe crear un departamento para poder gestionar la tabla Empleat");
                return;
            }
            Departament departament = DepartamentDAO.encuentraDepartamentPorID(session, entrada);
    
            nomEmpleat = solicitaYValidaNombre(entrada);
            dni = solicitaYValidaDNI(entrada);
            correu = solicitaYValidaCorreo(entrada);
            telefon = solicitaYValidaTelefono(entrada);
            
            Empleat empleat = new Empleat(nomEmpleat, dni, correu, telefon, departament);
            session.persist(empleat);
            departament.addEmpleat(empleat); // añade el empleado a la lista de empleados de la clase Departament
            session.getTransaction().commit();
            
            printScreen(terminal, "Empleado registrado con éxito.");
    
        } catch (ConstraintViolationException cve) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            String errorMessage = cve.getMessage();
            
            // Determinar qué campo causó el error (dni, correu, telefon)
            if (errorMessage.contains(dni)) {
                printScreen(terminal, "Error: Ya existe un empleado con el mismo DNI.");
            } else if (errorMessage.contains(correu)) {
                printScreen(terminal, "Error: Ya existe un empleado con el mismo Correo.");
            } else if (errorMessage.contains(telefon)) {
                printScreen(terminal, "Error: Ya existe un empleado con el mismo Teléfono.");
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    } 

    public static void consultaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            if (!muestrasAllEmpleats(session)) return;
            Empleat empleat = encuentraEmpleatPorID(session, entrada);
            
            printScreen(terminal, "\nEmpleado encontrado: " + empleat.toString());
    
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
    
    public static void modificaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        Empleat empleat = null;
        try {
            session = sf.openSession();
            session.beginTransaction();

            if (!muestrasAllEmpleats(session)) return;
    
            empleat = encuentraEmpleatPorID(session, entrada);
            printScreen(terminal, "Empleado encontrado.");
            boolean opcionValida = false;
            String opcion;

            String escogeAtributos ="""

                    ¿Qué atributo desea modificar?
                    1. Nombre
                    2. DNI
                    3. Correo
                    4. Teléfono
                    5. Departamento
                    6. (Modifcar todos)
                    """;
            printScreen(terminal, escogeAtributos);

            while (!opcionValida) {
                System.out.print("Opción: ");
                opcion = entrada.readLine().strip();
                if (opcion.isEmpty()) continue;
                switch (opcion) {
                    case "1":
                        empleat.setNomEmpleat(solicitaYValidaNombre(entrada));
                        opcionValida = true;
                        break;
                    case "2":
                        empleat.setDni(solicitaYValidaDNI(entrada));
                        opcionValida = true;
                        break;
                    case "3":
                        empleat.setCorreu(solicitaYValidaCorreo(entrada));
                        opcionValida = true;
                        break;
                    case "4":
                        empleat.setTelefon(solicitaYValidaTelefono(entrada));
                        opcionValida = true;
                        break;
                    case "5":
                        if (!DepartamentDAO.muestrasAllDepartaments(session)) {
                            printScreen(terminal, "Debe crear un departamento para poder gestionar la tabla Empleat");
                            return;
                        }
                        Departament departament = DepartamentDAO.encuentraDepartamentPorID(session, entrada);
                        empleat.setDepartament(departament);
                        opcionValida = true;
                        break;
                    case "6":
                        empleat.setNomEmpleat(solicitaYValidaNombre(entrada));
                        empleat.setDni(solicitaYValidaDNI(entrada));
                        empleat.setCorreu(solicitaYValidaCorreo(entrada));
                        empleat.setTelefon(solicitaYValidaTelefono(entrada));

                        if (!DepartamentDAO.muestrasAllDepartaments(session)) {
                            printScreen(terminal, "Debe crear un departamento para poder gestionar la tabla Empleat");
                            return;
                        }
                        Departament nuevoDep = DepartamentDAO.encuentraDepartamentPorID(session, entrada);
                        empleat.setDepartament(nuevoDep);
                        opcionValida = true;
                        break;
                    default:
                        printScreen(terminal, "\nOpción incorrecta! Por favor, vuelva a intentarlo.\n");
                }
            }    
            session.merge(empleat);
            session.getTransaction().commit();
            
            printScreen(terminal, "Empleado modificado con éxito.");
    
        } catch (ConstraintViolationException cve) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            String errorMessage = cve.getMessage();
            
            // Determinar qué campo causó el error (dni, correu, telefon)
            if (errorMessage.contains(empleat.getDni())) {
                printScreen(terminal, "Error: Ya existe un empleado con el mismo DNI.");
            } else if (errorMessage.contains(empleat.getCorreu())) {
                printScreen(terminal, "Error: Ya existe un empleado con el mismo Correo.");
            } else if (errorMessage.contains(empleat.getTelefon())) {
                printScreen(terminal, "Error: Ya existe un empleado con el mismo Teléfono.");
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    public static void eliminaEmpleat(SessionFactory sf, BufferedReader entrada) throws IOException {
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();

            if (!muestrasAllEmpleats(session)) return;
    
            Empleat empleat = encuentraEmpleatPorID(session, entrada);
    
            printScreen(terminal, "Empleado encontrado.");
            printScreen(terminal, "Está seguro de que quiere eliminarlo? (y / n): ");

            String respuesta = entrada.readLine();
            if (respuesta.equals("y")) {
                session.remove(empleat);
                printScreen(terminal, "Empleado eliminado con éxito.");
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

    public static void insertarTasca(SessionFactory sf, BufferedReader entrada) throws IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
            if (!muestrasAllEmpleats(session)) return;
            printScreen(terminal, "Seleccione el empleado al que desea añadirle una tarea:");
            Empleat empleat = encuentraEmpleatPorID(session, entrada);
            if (!TascaDAO.muestrasAllTascas(session)) {
                System.out.println("Cree una tarea para poder asignarla a un empleado.");
                return;
            }
            printScreen(terminal, "Seleccione la tarea que desea añadirle al empleado " + empleat.getNomEmpleat());

            Tasca tasca = TascaDAO.encuentraTascaPorID(session, entrada);

            printScreen(terminal, "Insertando tarea....");

            empleat.getTasca().add(tasca);

            printScreen(terminal, "Tarea asignada con éxito.");
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

    /*
     * MÉTODOS COMPLEMENTARIOS
     */

    public static Empleat encuentraEmpleatPorID(Session session, BufferedReader entrada) throws IOException {
        Empleat empleat = null;
        while (empleat == null) {
            System.out.print("Introduzca la ID del empleado que desea obtener: ");
            try {
                Integer id = Integer.parseInt(entrada.readLine());
                empleat = session.find(Empleat.class, id);
    
                if (empleat == null) {
                    System.out.printf("El Empleado con ID %d no existe.%n", id);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: introduzca un número entero.");
            }
        }
        return empleat;
    }
    
    public static boolean muestrasAllEmpleats(Session session) throws InterruptedException, IOException{
        Terminal terminal = TerminalBuilder.builder().dumb(true).build();
        try {
            Query<Empleat> query = session.createQuery("from Empleat", Empleat.class);
            List<Empleat> empleats = query.list();
    
            if (empleats.isEmpty()) {
                printScreen(terminal, "No hay Empleats registrados.");
                return false;
            } else {
                printScreen(terminal, "Emmpleats disponibles:");
                System.out.println();
                for (Empleat empl : empleats) {
                    System.out.println(empl.toString());
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

    // Solicita y valida el nombre
    private static String solicitaYValidaNombre(BufferedReader entrada) throws IOException {
        System.out.print("Nombre> ");
        String nombre = entrada.readLine().strip();

        while (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            System.out.println("Error: El nombre no debe: estar vacío, contener números ni caracteres especiales.");
            System.out.print("Nombre> ");
            nombre = entrada.readLine();
        }
        return nombre;
    }

    // Solicita y valida el DNI
    private static String solicitaYValidaDNI(BufferedReader entrada) throws IOException {
        System.out.print("DNI> ");
        String dni = entrada.readLine().strip();

        while (!dni.matches("^[0-9]{8}[A-Za-z]$")) {
            System.out.println("Error: El DNI debe tener 8 números seguidos de una letra (Ej: 60125478J).");
            System.out.print("DNI> ");
            dni = entrada.readLine();
        }
        return dni;
    }

    // Solicita y valida el correo
    private static String solicitaYValidaCorreo(BufferedReader entrada) throws IOException {
        System.out.print("Correo> ");
        String correo = entrada.readLine().strip();
        String regexCorreo = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        while (!Pattern.matches(regexCorreo, correo)) {
            System.out.println("Error: El correo debe tener el formato correcto (Ej: usuario@dominio.com).");
            System.out.print("Correo> ");
            correo = entrada.readLine();
        }
        return correo;
    }

    // Solicita y valida el teléfono
    private static String solicitaYValidaTelefono(BufferedReader entrada) throws IOException {
        System.out.print("Teléfono> ");
        String telefono = entrada.readLine().strip();

        while (!telefono.matches("^[0-9]{9}$")) {
            System.out.println("Error: El teléfono debe tener exactamente 9 dígitos numéricos (Ej: 684521452).");
            System.out.print("Teléfono> ");
            telefono = entrada.readLine();
        }
        return telefono;
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
