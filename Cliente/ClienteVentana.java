/**
 * @author David Ramírez Sierra 
 * Esta clase representa la Interfaz gráfica además de ser el programa principal del Cliente
 */



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteVentana extends JFrame implements ActionListener {
    
    private static JFrame frameChat;
    private static JPanel panel;
    private static JPanel panel2;
    private static JTextArea chatText;
    private static JTextArea enviarText;
    private static JTextArea participantesText;
    private static JButton enviarButton;
    private static JLabel usernameLabel;
    private static JLabel puertoLabel;
    private static JLabel estadoLabel;
    private static JButton desconectarButton;
    private static JLabel darLabel;
    private JFrame frameEntrar;
    private JPanel panel3;
    private JTextField nombre;
    private JButton entrarButton;

    private int ESTADO = 0; // 0- desconectado, 1- cONectado


    //// RELOJ
    java.util.Calendar calendario;
    int dia, mes, anio, hora, minutos, segundos;
    private javax.swing.JLabel label;
    private JLabel horaLabel, horaConexionLabel;

    //// DATOS MIEMBRO 
    private String username;
    private InterfazChat h;
    private String puerto;

    
    ////////////// CONSTRUCTORES //////////////

    ClienteVentana(boolean estado){
        ventanaEntrar();
        ventanaChat();
        reloj();
    }

    ClienteVentana(){
        
        frameChat.setVisible(true);

    }

    ////////////// MAIN //////////////

    public static void main(String[] args) {

        new ClienteVentana(true);

    }

     ////////////// VENTANAS //////////////

    private void ventanaChat() {
        
        //CREAMOS EL FRAME
        frameChat = new JFrame("CHAT!!!");
        frameChat.setResizable(false);
        frameChat.setSize(700, 450);
        frameChat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frameChat.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {

                if (JOptionPane.showConfirmDialog(frameChat, "Desea realmente salir del CHAT?",
                "CHAT | Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
           { 
            

             try {
                    h.desconectar(puerto);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
                }


            System.exit(0);
           }


            }
        });

        // CREAMOS LOS DOS PANELES Y LOS AÑADIMOS AL FRAME, además les ponemos borde
        panel = new JPanel();
        panel2 = new JPanel();
        frameChat.add(panel);
        frameChat.add(panel2);
        panel2.setBorder(BorderFactory.createTitledBorder(""));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        panel.setSize(700, 60);
        panel.setBackground(Color.LIGHT_GRAY);


        //// CONFIGURACION PARA EL PANEL 2 ////////

        panel2.setLocation(20, 30);
        panel2.setSize(100, 100);
        panel2.setLayout(null);

        // CAMPO DE CONVERSACION
         chatText = new JTextArea(0, 0);
        chatText.setBounds(5, 65, 500, 230);
        chatText.setBorder(BorderFactory.createTitledBorder(""));
        chatText.setEditable(false);
        panel2.add(chatText);

        // CAMPO DE ENVIAR
        enviarText = new JTextArea(0, 0);
        enviarText.setBounds(5, 300, 500, 100);
        enviarText.setBorder(BorderFactory.createTitledBorder(""));
        panel2.add(enviarText);

        // CAMPO DE PARTICIPANTES
        participantesText = new JTextArea(0, 0);
        participantesText.setBounds(510, 65, 185, 230);
        participantesText.setBorder(BorderFactory.createTitledBorder("PARTICIPANTES"));
        participantesText.setEditable(false);
        panel2.add(participantesText);

        // BOTÓN DE ENVIAR
        enviarButton = new JButton("<html><b>ENVIAR</b>");
        enviarButton.setBounds(510, 300, 185, 100);
        enviarButton.setName("ENVIAR");
        panel2.add(enviarButton);
        enviarButton.addActionListener(this);


        //// CONFIGURACION PARA EL PANEL 1 ////////

        // LABEL DE USUARIO
        usernameLabel = new JLabel("-------------");
        panel.add(usernameLabel);
        usernameLabel.setBorder(BorderFactory.createTitledBorder("USUARIO"));

        // LABEL DE PUERTO
        puertoLabel = new JLabel("-------------");
        panel.add(puertoLabel);
        puertoLabel.setBorder(BorderFactory.createTitledBorder("PUERTO"));

        // LABEL DE ESTADO
        estadoLabel = new JLabel("CONECTADO");
        panel.add(estadoLabel);
        estadoLabel.setBorder(BorderFactory.createTitledBorder("ESTADO"));
        estadoLabel.setForeground(Color.GREEN);

        // BOTON PARA DESCONECTARSE
        desconectarButton = new JButton("<html><b>X<b>");
        desconectarButton.setName("X");
        panel.add(desconectarButton);
        desconectarButton.addActionListener(this);
        desconectarButton.setForeground(Color.red);
//
//        // LABEL DE Hora CONEXION
//        horaConexionLabel = new JLabel("------------------");
//        panel.add(horaConexionLabel);
//        horaConexionLabel.setBorder(BorderFactory.createTitledBorder("ULTIMA CONEXIÓN"));
//        horaConexionLabel.setForeground(Color.BLACK);


        // LABEL DE HORA
        horaLabel = new JLabel("-------------");
        panel.add(horaLabel);
        horaLabel.setBorder(BorderFactory.createTitledBorder(""));
        horaLabel.setForeground(Color.GRAY);


        // LABEL DE DAR
        darLabel = new JLabel("[dar]@davidramirezsierra");
        darLabel.setBounds(5, 360, 185, 100);
        darLabel.setForeground(Color.LIGHT_GRAY);
        panel2.add(darLabel);

        // DAMOS VISIBILIDAD AL FRAME CREADO
        //frameChat.setVisible(true);


    }

    private void ventanaEntrar() {
        try {
            
            
            //CREAMOS EL FRAME
            frameEntrar = new JFrame("CHAT | Registrarse");
            frameEntrar.setSize(400, 250);
            frameEntrar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // CREAMOS EL PANEL
            panel3 = new JPanel();
            frameEntrar.add(panel3);
            panel3.setBorder(BorderFactory.createTitledBorder("NOMBRE DE USUARIO"));
            panel3.setSize(100, 100);
            panel3.setBackground(Color.lightGray);

            // CREAMOS EL TITULO
            //JLabel titulo = new JLabel("REGISTRATE");
            //titulo .setForeground(Color.BLACK);
            //panel3.add(titulo);

            // CREAMOS EL CAMPO DE INTRODUCIR EL NOMBRE
            nombre = new JTextField(30);
            panel3.add(nombre);
            nombre.setBorder(BorderFactory.createTitledBorder(""));
            nombre.setBounds(5, 10, 18, 10);

            // CREAMOS BOTON DE ENTRAR
            entrarButton = new JButton("<html><b>ENTRAR</b>");
            panel3.add(entrarButton);
            entrarButton.setName("ENTRAR");
            entrarButton.addActionListener(this);
            
            // DAMOS VISIBILIDAD AL FRAME CREADO
            frameEntrar.setVisible(true);
           
            // HACEMOS LA PRIMERA CONEXION
            
            //Incluye el gestor de seguridad
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }


            String nombreHost = "localhost";
            String numeroPuerto = "1088";
            String registroURL = "rmi://" + nombreHost + ":" + numeroPuerto + "/chat";
            // encontrar el objeto remoto y transmitirlo a un objeto de interfaz
            h = (InterfazChat) Naming.lookup(registroURL);
            System.out.println("Conexión completada|\thost:" + nombreHost + "|\tpuerto:" + numeroPuerto);


        } catch (NotBoundException ex) {
            //Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            //Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    ////////////// ACTION PERFORMED //////////////
    
    public void actionPerformed(ActionEvent e) {

        String accion = ((JButton) e.getSource()).getName();
        //System.out.println("accion: " + accion);

        if (accion.equals("ENTRAR")) {

            if (nombre.getText().length() > 0) {

                username = nombre.getText();
                registrar();

            } else {

                dialogoError("Su nombre debe tener más de 1 letra");

            }

        } else if (accion.equals("ENVIAR")) {

            String texto = enviarText.getText();

            try {

                h.pedirHablar("<" + username + ">:  " + texto);

            } catch (RemoteException ex) {
                Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else if (accion.equals("X")) {

            if (ESTADO == 1) {
                try {
                    h.desconectar(puerto);
                    enviarText.setText("NO ESTÁS CONECTADO");
                    cambiarAspecto();
                    participantesText.setText("--------");
                    ESTADO = 0; // Pasamos a desconectado
                } catch (RemoteException ex) {
                    Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (ESTADO == 0) {
                registrar();
                cambiarAspecto();
                //ESTADO = 1; // Pasamos a conectado
                
                enviarText.setEnabled(true);
                enviarButton.setEnabled(true);
                estadoLabel.setForeground(Color.GREEN);
                desconectarButton.setForeground(Color.RED);

                //ACTUALIZAMOS LA ULTIMA CONEXION
                actualizarUltimaConexion();

            }



        }

    }

    ////////////// METODOS ÚTILES //////////////

    private void registrar(){
          ////////// R·E·G·I·S·T·R·A·R·S·E ////////////////////////////
                
                
                String puertoCrear = username;

                try {
                    puertoCrear = h.pedirPuertoLibre(username); // ---> y que te devuelva el puerto libre por el que se va a hacer la conexion
                } catch (RemoteException ex) {
                    Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
                }

                puerto = puertoCrear;

                if (puerto.equals("ERROR")){
                        System.out.println("NO SE PUEDE ESTABLECER LA CONEXIÓN. SERVIDOR OCUPADO");
                        dialogoError("NO SE PUEDE ESTABLECER LA CONEXIÓN. SERVIDOR OCUPADO");
                       try {
                            Thread.sleep(4000);
                    } catch (InterruptedException ee) {
                             ee.printStackTrace();
                     }

                } else {
                    try {

                        // SE ABRE COMO UN PEQUEÑO SERVIDOR (el cliente se convierte también en servidor)
                        ClienteAuxiliar cl_aux = new ClienteAuxiliar(Integer.parseInt(puertoCrear));
                        h.abrir(puertoCrear);
                        System.out.println("Conexión creada con exito. \n");

                        ESTADO = 1; // Pasamos a conectado

                        // CAMBIAMOS EL ASPECTO
                        frameEntrar.setVisible(false);
                        frameChat.setVisible(true);
                        usernameLabel.setText("---" + nombre.getText() + "---");
                        usernameLabel.setForeground(Color.BLUE);
                        puertoLabel.setText("---"+puerto+"---");
                        enviarText.setEnabled(true);
                        enviarButton.setEnabled(true);
                        estadoLabel.setForeground(Color.GREEN);
                        desconectarButton.setForeground(Color.RED);
                        // HORA DE LA CONEXION
                        actualizarUltimaConexion();

                        // Al haber hecho esto, se crea el nuevo servidor y se habre la ventana del chat

                    } catch (RemoteException ex) {
                        Logger.getLogger(ClienteVentana.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

    }

    private void dialogoError(String mensaje){

        JOptionPane.showMessageDialog(null, mensaje,
                "CHAT | Error", JOptionPane.WARNING_MESSAGE);

    }

    private void actualizarUltimaConexion() {
        // HORA DE LA CONEXION
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);
        String hour = String.format("%02d : %02d : %02d", hora, minutos, segundos);
        estadoLabel.setText("<html><center>" + "CONECTADO: " + " <font size=\"1\">" + hour + "<font>"+"");
    }

    private void cambiarAspecto(){
        
         if (ESTADO == 1){

                // CAMBIAMOS EL ASPECTO
                estadoLabel.setText("DESCONECTADO");
                estadoLabel.setForeground(Color.red);
                desconectarButton.setForeground(Color.GREEN);
                enviarText.setEnabled(false);
                enviarButton.setEnabled(false);

            } else if (ESTADO == 0){

                // CAMBIAMOS EL ASPECTO
                estadoLabel.setText("CONECTADO");
                estadoLabel.setForeground(Color.GREEN);
                desconectarButton.setForeground(Color.RED);
                enviarText.setEnabled(true);
                enviarButton.setEnabled(true);

                //ACTUALIZAMOS LA ULTIMA CONEXION
                //actualizarUltimaConexion();

            }

    }

    ////// METODO PARA EL RELOJ ////

    private void reloj() {
        calendario = new java.util.GregorianCalendar();
        segundos = calendario.get(Calendar.SECOND);
        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                java.util.Date actual = new java.util.Date();
                calendario.setTime(actual);
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = (calendario.get(Calendar.MONTH) + 1);
                anio = calendario.get(Calendar.YEAR);
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);
                segundos = calendario.get(Calendar.SECOND);
                String hour = String.format("%02d : %02d : %02d", hora, minutos, segundos);
                String date = String.format("%02d / %02d / %02d", dia, mes, anio);
                horaLabel.setText("<html><center> <font size=\"2\">" + date + "<br>" + hour +"</font>");
            }
        });
        timer.start();
    }

    ////////////// METODOS QUE USA LA IMPLEMENTACION DE LA INTERFAZ //////////////

    public void actualizarTexto(String texto) {
        chatText.append(texto+"\n");
        enviarText.setText("");
    }

    public void actualizarInfo(String texto) {
        participantesText.setText("");
        participantesText.append(texto+"\n");
    }
    




}
