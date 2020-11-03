package gestion_bbdd;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HilosInsertar extends Thread{
    private final Connection conexion;
    private final int registrosXhilo;

    //instanciamos la clase Faker que sirve para generar datos aleatorios
    Faker faker = new Faker();

    public HilosInsertar(Connection conexion,  int registrosXhilo) {
        this.conexion = conexion;
        this.registrosXhilo = registrosXhilo;
    }

    @Override
    public void run() {
        PreparedStatement preparedStatement;
        try {
            //insertamos mediante un preparedstatement los registros necesarios en la base de datos, a través de un  hilo
            preparedStatement = conexion.prepareStatement("INSERT INTO empleados (EMAIL, INGRESOS) VALUES (?,?)");
            for (int i=0; i < registrosXhilo; i++){
                String email = faker.name().username() + "@gmail.com";
                int sueldo = (int) (Math.random() * 1000) + 10;
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, sueldo);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("No se han podido insertar los datos");
        }
    }
}
