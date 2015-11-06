

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidramirezsierra
 */


public class InterfazChatImplCliente  implements InterfazChat{
    ClienteVentana cv;

    public boolean desconectar(String nombre) throws RemoteException {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarConectados() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mostrarMensaje(String texto) throws RemoteException {
      // System.out.println("Mostrando mensaje??????: "+texto);
    }

    public void abrir(int puerto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void abrir(String puerto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String hablar(String puerto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void conversacion(String texto) throws RemoteException {

    }

    public void pedirHablar(String puerto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publicarTexto(String texto) throws RemoteException {

         cv.actualizarTexto(texto);

    }

    public void activarChat() throws RemoteException {

        cv = new ClienteVentana();

        System.out.println("ACTIVADO");
        
    }

    public void publicarInfo(String texto) throws RemoteException {

        cv.actualizarInfo(texto);


    }

    public String pedirPuertoLibre(String nombre) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }





    


}
