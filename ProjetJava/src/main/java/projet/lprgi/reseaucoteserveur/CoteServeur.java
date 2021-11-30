package projet.lprgi.reseaucoteserveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CoteServeur {

    /* A LANCER AVANT ProgrammeClient !!! */
    /* Objectifs: créer livre dans SGBD coté serveur
    *               Donner liste de livres (avec select)
    *   CONSOLE ?*/
    public static void main(String[] args){
        int numeroPort=1234;

        ServerSocket monServeurSocket=null;

        try {
            monServeurSocket=new ServerSocket(numeroPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Socket MaSocket=null;
        try {
            MaSocket=monServeurSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        BufferedInputStream bis=null;
        BufferedOutputStream bos=null;
        try {
            bis=new BufferedInputStream(
                    MaSocket.getInputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        /*
        DataInputStream dis=null;
        DataOutputStream exercice=null;
        dis=new DataInputStream(bis);
        exercice=new DataOutputStream(bos);

        int entierRecu;
        double doubleRecu;

        int entierEnvoye;
        double doubleEnvoye;

        try {
            entierRecu=dis.readInt();
            doubleRecu=dis.readDouble();

            entierRecu=entierRecu*-1;
            doubleRecu=doubleRecu*-1;

            System.out.println(
                    "entierRecu= "+ entierRecu + " et doubleRecu= "+ doubleRecu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dis.close();
            MaSocket.close();
            monServeurSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        } */

    }

}
