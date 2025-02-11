package com.iticbcn.christopherflores;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.SessionFactory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        try (SessionFactory sf = HibernateUtil.getSessionFactory()) {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in))) {
                muestraTablas();
                Terminal terminal = TerminalBuilder.builder().dumb(true).build();
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
                            printScreen(terminal, "Opción incorrecta, por favor:");
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

    private static void printScreen(Terminal terminal, String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            terminal.writer().print(c);
            terminal.flush();
            Thread.sleep(10);
        }
        System.out.println();
    }
}

