/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteservidorobjetos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author link
 */
public class Servidor {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Se crea un socket servidor atendiendo a un determinado puerto.
        // Por ejemplo, el 35557.
        ServerSocket socket = new ServerSocket(35557);
        Socket cliente = null; // Socket para atender el cliente
        int numClientes = 0; // Contador de clientes 
        
        boolean salir = false;

        while (!salir) {
            // Se acepata una conexion con un cliente. Esta llamada se queda
            // bloqueada hasta que se arranque el cliente.
            System.out.println("Servidor Listo. Esperando cliente...");
            cliente = socket.accept();
            numClientes ++;
            System.out.println("Conectado con cliente: "+numClientes+" de " + cliente.getInetAddress());
            // setSoLinger() a true hace que el cierre del socket espere a que
            // el cliente lea los datos, hasta un máximo de 10 segundos de espera.
            // Si no ponemos esto, el socket se cierra inmediatamente y si el 
            // cliente no ha tenido tiempo de leerlos, los datos se pierden.
            //cliente.setSoLinger (true, 10);
            
            // Recibimos del cliente, y como nos entra datos es por el input
             ObjectInputStream bufferObjetosEntrada = new ObjectInputStream(cliente.getInputStream());
             Ejemplo datoEntrada = (Ejemplo) bufferObjetosEntrada.readObject();
            datoEntrada.mostrar();
            System.out.println("Recibido del Cliente '" + datoEntrada.toString() + "'");

            // Se prepara un flujo de salida para objetos y un objeto para enviar al output del cliente
            Ejemplo datoSalida = new Ejemplo();
            ObjectOutputStream bufferObjetosSalida = new ObjectOutputStream(cliente.getOutputStream());

            // Se envia el objeto 
            bufferObjetosSalida.writeObject(datoSalida);
            System.out.println("Enviado al Cliente '" + datoSalida.toString() + "'");
        }

        // Se cierra el socket con el cliente.
        // La llamada anterior a setSoLinger() hará
        // que estos cierres esperen a que el cliente retire los datos.
        cliente.close();

        // Se cierra el socket encargado de aceptar clientes. Ya no
        // queremos más. Si queremos procesar más, pues lo comentamos
        socket.close();
    }
}


