package gestion_bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Semaphore;

public class GestionarBBDD {
    public void insertarRegistros(int numRegistros, int numHilos){
        Connection conexionBD;
        Statement consulta;
        int registrosXhilo = numRegistros/numHilos;
        Semaphore semaforo = new Semaphore(1);

        try{
            conexionBD = DriverManager.getConnection("jdbc:mysql://localhost/bbdd_psp_1", "DAM2020_PSP", "DAM2020_PSP");
            consulta = conexionBD.createStatement();
            for(int i = 0; i<numHilos; i++){
                new HilosInsertar(conexionBD, consulta, registrosXhilo, semaforo).start();
            }

        } catch (SQLException exLetra) {
            System.out.println("¡NO SE HA PODIDO CONECTAR CON LA BASE DE DATOS! REINICIE LA APLICACIÓN");
        }
    }
}
