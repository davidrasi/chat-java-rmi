

// Un archivo de interfaz  RMI simple 
import java.rmi.*;

/**
 * Esto es una interfaz remota
 */

public interface InterfazChat extends Remote {


   
   public boolean desconectar(String nombre)
      throws java.rmi.RemoteException;

   public void mostrarConectados()
      throws java.rmi.RemoteException;

   public void mostrarMensaje(String texto)
      throws java.rmi.RemoteException;

   public void abrir(String puerto)
      throws java.rmi.RemoteException;

   public String hablar(String puerto)
      throws java.rmi.RemoteException;

   public void conversacion(String puerto)
      throws java.rmi.RemoteException;
   
   public void pedirHablar(String puerto)
      throws java.rmi.RemoteException;

   public String pedirPuertoLibre(String nombre)
      throws java.rmi.RemoteException;

   public void publicarTexto(String puerto)
      throws java.rmi.RemoteException;

   public void publicarInfo(String puerto)
      throws java.rmi.RemoteException;

public void activarChat()
      throws java.rmi.RemoteException;

   

} //fin interfaz remota
