package com.iticbcn.christopherflores;

import java.io.BufferedReader;
import java.io.IOException;

import org.hibernate.SessionFactory;

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
                    System.out.println("Opción incorrecta, por favor:");
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
  
    }

    public static void consultaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException{
  
    }
    
    public static void eliminaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException {
        
    }
    
    public static void modificaHistoric(SessionFactory sf, BufferedReader entrada) throws IOException {
        
    }
}
