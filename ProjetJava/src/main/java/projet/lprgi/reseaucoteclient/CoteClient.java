package projet.lprgi.reseaucoteclient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CoteClient {
    /* Objectifs: héberger un SGBD modifiable à partir de CoteClient
    *                       */

    public static void main(String[] args) {

        System.out.println("Lancement");

        String ipServer = "127.0.0.1";
        int NumPort = 1234;

        Socket maSocket = null;

        try {
            maSocket = new Socket(ipServer, NumPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

       BufferedOutputStream bos = null;

      try {
            bos = new BufferedOutputStream(
                    maSocket.getOutputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        DataOutputStream dos = null;

        dos = new DataOutputStream(bos);

       /* int entierExport = 43712;
        double doubleExport = 3.14; */

        String Marque="Peugeot";
        String Modele="e-208";
        int Annee=2017;
        int Kilometrage=22250;

        try {
            System.out.println("Envoi");
            dos.writeInt(Marque.length());
            dos.writeBytes(Marque);
            dos.writeInt(Modele.length());
            dos.writeBytes(Modele);
            dos.writeInt(Annee);
            dos.writeInt(Kilometrage);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            //dis.close();
            dos.close();
            maSocket.close();
            //monServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }System.out.println("Debut du Serveur");


        ipServer="127.0.0.1";
        NumPort =1235;

        ServerSocket maServSocket2=null;

        try {
            maServSocket2=new ServerSocket(NumPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Socket maSocket2=null;

        try {
            maSocket2 = maServSocket2.accept();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        BufferedInputStream bis=null;

        try {
            bis=new BufferedInputStream(
                    maSocket2.getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

      /*  DataInputStream dis=null;
        dis = new DataInputStream(bis);

        int EntierRecu;
        double DoubleRecu; */
        ObjectInputStream Ois=null;

        String MarqueRecu[];
        String ModeleRecu[];
        int AnneeRecue[];
        int KilometrageRecu[];

        try {
            Ois=new ObjectInputStream(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            MarqueRecu = Ois.readObject();
            DoubleRecu = dis.readDouble();

            System.out.println("Reception réussie");
            System.out.println(
                    "EntierRecu = " + EntierRecu +
                            ",\t DoubleRecu = " + DoubleRecu
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


    }
}
