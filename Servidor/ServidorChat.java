import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;
import java.lang.Thread;

/**
 *  @author David ramirez Sierra
 * Esta clase representa al objeto servidor de un objeto
 * distribuido de la clase Chat, Representa el servidor.
 */
public class ServidorChat {

    public static void main(String args[]) {

        //Incluye el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }


        //Controla la entrada del servidor
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String numeroPuerto, registroURL, registroNom;
        try {
////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
            int port = 1088; // Se crea un registro rmi en el puerto 1088
            try {
                java.rmi.registry.LocateRegistry.createRegistry(port);

            } catch (java.rmi.RemoteException e) {
                System.out.println("Error en el registry este: " + e.toString());
            }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

            int numeroPuertoRMI = 1088;// En este caso se hace en el puerto 1088

            //1. Se crea el stub
            InterfazChat objExportado = (InterfazChat) new InterfazChatImplServidor();
            //se crea el stub dinamicamente y se asocia al numero de puerto numeroPuertoRMI
            InterfazChat stub = (InterfazChat) UnicastRemoteObject.exportObject(objExportado, numeroPuertoRMI);
        
            //2. Registro del stub
            //se crea un registro en el servidor RMI
            Registry registry = LocateRegistry.getRegistry(numeroPuertoRMI);
            registry.rebind("chat", stub); //para registrarlo o sustituirlo
            //Muestra un mensaje positivo y muestra nombre registros actuales
            System.out.println("Servidor registrado.  El registro contiene actualmente:");
            listarRegistro(registry);
            System.out.println("El ServidorChat esta preparado.");

         
        }// fin try
        catch (Exception re) {
            System.out.println("Excepcion en el main() del  ServidorChat: " + re);
        } // fin catch
    } // fin main

    // Este metodo lista los nombres registrados con el objeto registro
    private static void listarRegistro(String registroURL)
            throws RemoteException, MalformedURLException {
        System.out.println("El Registro " + registroURL + " contiene: ");
        String[] nombres = Naming.list(registroURL);
        for (int i = 0; i < nombres.length; i++) {
            System.out.println(nombres[i]);
        }
    } //fin listarRegistro

    // Este metodo lista los nombres registrados con el objeto registro
    private static void listarRegistro(Registry registry)
            throws RemoteException, MalformedURLException {
        System.out.println("El Registro " + registry.toString() + " contiene: ");
        String[] nombres = registry.list();
        if (nombres.length > 0) {
            for (int i = 0; i < nombres.length; i++) {
                System.out.println(nombres[i]);
            }
        }
    } //fin listarRegistro
} // fin class

