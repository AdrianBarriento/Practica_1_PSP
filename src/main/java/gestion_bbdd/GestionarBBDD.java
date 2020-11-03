package gestion_bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestionarBBDD {
    public void insertarRegistros(int numRegistros, int numHilos){
        Connection conexionBD;
        int registrosXhilo = numRegistros/numHilos, diferencia =0;
        //así calcularemos si se queda algún registro fuera en caso de que el número de registros no sea divisible entre el número de hilos pedidos. Diferencia está inicializada a 0 por si la operación es divisible.
        if((numRegistros%numHilos)!=0){
            diferencia = numRegistros-(registrosXhilo*numHilos);
        }
        //conectamos a la base de datos e insertamos tantos registros en x hilos
        try{
            conexionBD = DriverManager.getConnection("jdbc:mysql://localhost/bbdd_psp_1", "DAM2020_PSP", "DAM2020_PSP");
            for(int i = 0; i<numHilos; i++){
                //con esta condición controlamos que cuando llegue al último hilo, le sume la diferencia al número de registros que debe insertar
                if(i==numHilos-1){
                    new HilosInsertar(conexionBD, registrosXhilo+diferencia).start();
                }else{
                    new HilosInsertar(conexionBD, registrosXhilo).start();
                }
            }
        } catch (SQLException exBBDD) {
            System.out.println("¡NO SE HA PODIDO CONECTAR CON LA BASE DE DATOS! REINICIE LA APLICACIÓN");
        }
    }
}
