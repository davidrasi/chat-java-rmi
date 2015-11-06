/**
 * @author David Ramírez Sierra 
 * 
 * Esta clase implementa la interfaz remota 
 * InterfazChat desde la parte del Servidor
 */



import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InterfazChatImplServidor implements InterfazChat {

  private final int MAXIMO_USUARIOS = 10;

  private ArrayList<String> puertosConectados = new ArrayList<String>();
  private ArrayList<String> puertosLibres = new ArrayList<String>();
  private boolean inicio = false;
  private String info = "";
  Map<String, String> nombreMap = new HashMap<String, String>();


   public InterfazChatImplServidor() throws RemoteException {
      super( );

   }

    public boolean desconectar(String puerto) throws RemoteException {

        if (nombreMap.containsKey(puerto)) {
            String usuario_que_se_marcha = nombreMap.get(puerto);

            puertosConectados.remove("rmi://" + "localhost" + ":" + puerto + "/chat");
            nombreMap.remove(puerto);
            puertosLibres.add(puerto);
            mostrarConectados();
            
            InterfazChat h_Z = null;

            for (int i = 0; i < puertosConectados.size(); i++) {
                try {
                    h_Z = (InterfazChat) Naming.lookup(puertosConectados.get(i));
                    h_Z.mostrarMensaje("Se ha ido del chat: " + usuario_que_se_marcha);
                    h_Z.publicarTexto("\t" + usuario_que_se_marcha + " se ha marchado del Chat");
                    h_Z.publicarInfo(info);
                } catch (NotBoundException ex) {
                    Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
                }


            }



            return true;
        } else {
            return false;
        }
    }

    public void mostrarConectados() throws RemoteException {

        System.out.println("USUARIOS CONECTADOS: "+puertosConectados.size());
        info = "";

        Iterator it = nombreMap.keySet().iterator();
        while (it.hasNext()) {
            String key =  (String) it.next();
            System.out.println("Puerto: " + key + " | Nombre: " + nombreMap.get(key));
            info = info+"\nUsuario: "+nombreMap.get(key);
        }

        System.out.print("PUERTOS LIBRES: "+puertosLibres.size() +"--> ");
        
      
        for(int i = 0; i<puertosLibres.size(); i++){
            System.out.print(puertosLibres.get(i)+" ");

        }
        System.out.println();



    }

   

    public void mostrarMensaje(String texto) throws RemoteException {
    }

    public void abrir(String puerto) throws RemoteException {
        try {
            

            String nombreHost = "localhost"; 
            String registroURL_Z = puerto;
            registroURL_Z = "rmi://" + nombreHost + ":" + registroURL_Z + "/chat";

            puertosConectados.add(registroURL_Z);
            
            // encontrar el objeto remoto y transmitirlo a un objeto de interfaz
            InterfazChat h_Z = null;
            mostrarConectados();
            h_Z = (InterfazChat) Naming.lookup(registroURL_Z);
            h_Z.activarChat();
             
            
            for (int i = 0; i < puertosConectados.size(); i++) {

                h_Z = (InterfazChat) Naming.lookup(puertosConectados.get(i));
                h_Z.mostrarMensaje("Se ha añadido al chat: "+puerto);
                h_Z.publicarTexto("\t"+nombreMap.get(puerto)+" Ha entrado en el chat !!!!");
                h_Z.publicarInfo(info);

            }

        } catch (NotBoundException ex) {
            Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String hablar(String texto) throws RemoteException {
        InterfazChat h_Z;
        for (int i = 0; i < puertosConectados.size(); i++) {
            try {

                h_Z = (InterfazChat) Naming.lookup(puertosConectados.get(i));
                h_Z.mostrarMensaje("CHAT|\t"+texto);

                h_Z.conversacion(texto);
                               
               return "CHAT|\t"+texto;
                
            } catch (NotBoundException ex) {
                Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return "error";
    }

    public void conversacion(String puerto) throws RemoteException {
        
    }

    
    public void pedirHablar(String texto) throws RemoteException {

        InterfazChat h_Z;
        
        for (int i = 0; i < puertosConectados.size(); i++) {
            try {
               
                h_Z = (InterfazChat) Naming.lookup(puertosConectados.get(i));
                h_Z.publicarTexto(texto);

            } catch (NotBoundException ex) {
                Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(InterfazChatImplServidor.class.getName()).log(Level.SEVERE, null, ex);
            }

          }
        

        
    }

    public void publicarTexto(String puerto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void activarChat() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void publicarInfo(String puerto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String pedirPuertoLibre(String nombre) throws RemoteException {

                if (puertosLibres.isEmpty() && inicio == false) {
                    inicio = true;
                    puertosLibres.add("1075");
                    puertosLibres.add("1076");
                    puertosLibres.add("1077");
                    puertosLibres.add("1078");
                }

                String puerto="";

                if (inicio == true && !puertosLibres.isEmpty()) 
                {
                    puerto = puertosLibres.get(0);
                    nombreMap.put(puertosLibres.get(0), nombre);
                    puertosLibres.remove(0);

                }else if (inicio == true && puertosLibres.isEmpty())
                {
                    puerto = "ERROR";
                    System.out.println("ERROR: NO SE PERMITEN MÁS CONEXIONES");

                }
               
           return puerto;
           
    }

    



    



    
} // fin clase
