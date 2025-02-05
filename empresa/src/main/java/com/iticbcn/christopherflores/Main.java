package com.iticbcn.christopherflores;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

                1. DEPARTAMENT
                2. EMPLEAT
                3. TASCA
                4. HISTORIC

                Introduzca "q" para salir del programa.
                """);
    }
}

