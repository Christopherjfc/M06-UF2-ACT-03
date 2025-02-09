package com.iticbcn.christopherflores;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class Main {
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
                        case "0":
                            cargaTablas(sf);
                            muestraTablas();
                            break;
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

                0. (CARGAR TABLAS)
                1. DEPARTAMENT
                2. EMPLEAT
                3. TASCA
                4. HISTORIC

                Introduzca "q" para salir del programa.
                """);
    }

    public static void cargaTablas(SessionFactory sf) {
        Session session = null;
        try {
            session = sf.openSession();
            session.beginTransaction();
    
            // Cargar el script SQL desde resources/db_scripts/insertsTablas.sql
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("db_scripts/insertsTablas.sql");
    
            if (inputStream == null) {
                throw new IOException("No se pudo encontrar el archivo insertsTablas.sql en resources/db_scripts.");
            }
    
            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    
            // Separar las sentencias por punto y coma
            String[] queries = sql.split(";");
    
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    session.createNativeMutationQuery(query).executeUpdate();
                }
            }
    
            session.getTransaction().commit();
            System.out.println("Tablas cargadas con éxito.");
    
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println("Error al cargar las tablas: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }    
}

