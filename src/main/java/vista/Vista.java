package vista;

import gestion_bbdd.GestionarBBDD;

import java.util.InputMismatchException;
import java.util.Scanner;
public class Vista {
    public void menu(){
        Scanner teclado = new Scanner(System.in);
        int numRegistros=0, numHilos=0;
        boolean repetir = true; //variable para pedir otra vez algún dato si el usuario introduce una letra en lugar de un número

        while(repetir){
            try{
                System.out.println("Introduzca el número de registros que desea introducir: ");
                numRegistros = teclado.nextInt();
                System.out.println("Introduzca el número de hilos que desea utilizar: ");
                numHilos = teclado.nextInt();
                repetir = false;
            }catch(InputMismatchException exLetra){
                System.out.println("Porfavor, introduzca un número.");
            }
        }
        new GestionarBBDD().insertarRegistros(numRegistros, numHilos);

    }
}
