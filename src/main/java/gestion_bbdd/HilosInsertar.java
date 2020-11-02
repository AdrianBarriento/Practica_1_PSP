package gestion_bbdd;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class HilosInsertar extends Thread{
    private Connection conexion;
    private Statement consulta;
    private int registrosXhilo;
    private Semaphore semaforo;


    //variables para llenarlas con datos aleatorios y llenar la bbdd, y clase Faker para generar estos datos
    private String email;
    private int sueldo;
    Faker faker = new Faker();
    public HilosInsertar(Connection conexion, Statement consulta, int registrosXhilo, Semaphore semaforo) {
        this.conexion = conexion;
        this.consulta = consulta;
        this.registrosXhilo = registrosXhilo;
        this.semaforo = semaforo;
    }

    private void insertarRegistro(){

    }
    @Override
    public void run() {
        PreparedStatement preparedStatement = null;
        try {
            semaforo.acquire();
            preparedStatement = conexion.prepareStatement("INSERT INTO empleados (EMAIL, INGRESOS) VALUES (?,?)");
            for (int i=0; i < registrosXhilo; i++){
                email = faker.name().username()+"@gmail.com";
                sueldo = (int) (Math.random()*1000)+10;
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, sueldo);
                preparedStatement.executeUpdate();
            }
            semaforo.release();
            TimeUnit.MILLISECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
