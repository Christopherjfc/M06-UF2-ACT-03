package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;

import org.hibernate.SessionFactory;

public class TascaDAO {
    
    public static void opcionesTasca(SessionFactory sf, BufferedReader entrada) throws IOException, InterruptedException {
        muestraOpciones();
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

        TABLA TASCA:

        1. CREA UN TASCA
        2. ENCUENTRA TASCA
        3. MODIFICA TASCA
        4. ELIMINA TASCA

        Introduzca "q" para regresar hacia atrás.
        """);
    }

    public static void registraTasca(SessionFactory sf, BufferedReader entrada) throws IOException{
    
    }

    public static void consultaTasca(SessionFactory sf, BufferedReader entrada) throws IOException{
    
    }

    public static void eliminaTasca(SessionFactory sf, BufferedReader entrada) throws IOException {
        
    }
    
    public static void modificaTasca(SessionFactory sf, BufferedReader entrada) throws IOException {
        
    }
}
