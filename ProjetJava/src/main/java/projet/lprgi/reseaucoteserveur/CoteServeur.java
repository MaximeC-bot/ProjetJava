package projet.lprgi.reseaucoteserveur;

import java.io.*;
import java.sql.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CoteServeur {

    /* A LANCER AVANT ProgrammeClient !!! */
    /* Objectifs: créer livre dans SGBD coté serveur
    *               Donner liste de livres (avec select) à client
    *   CONSOLE ?*/
    public static void main(String[] args){
        System.out.println("Début serveur");
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

        String nomDriver = "com.mysql.cj.jdbc.Driver";
        String BaseDeDonnees= "jdbc:mysql://localhost:3306/projet_java";

        try {
            Class.forName(nomDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-3);
        }

        Connection cnx=null;

        try {
            cnx= DriverManager.getConnection(BaseDeDonnees,"root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-4);
        }


        String requeteSansSelect =
                "Create Table IF NOT EXISTS voiture " +
                        "(" +
                        "ID Integer AUTO_INCREMENT" + ", " +
                        "Marque VARCHAR(20)" + ", " +
                        "Modele VARCHAR(20)" + ", " +
                        "Annee Integer" + ", " +
                        "Kilometrage Integer" + ", " +
                        "Primary Key(ID)" +
                        ")";

        Statement stmt=null;
        try {
            stmt=cnx.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-6);
        }

        try {
            stmt.executeUpdate(requeteSansSelect);
            System.out.println("Bingo ! Table Creee !!!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-7);
        }





        DataInputStream dis=null;
        DataOutputStream exercice=null;
        dis=new DataInputStream(bis);
        exercice=new DataOutputStream(bos);

        String MarqueRecue="";
        String ModeleRecu="";
        int AnneeRecue=0000;
        int KilometrageRecu=0;

        try {

            System.out.println("Reception");
            int length = dis.readInt();
            byte[] MarqueB = new byte[length];
            dis.read(MarqueB);
            MarqueRecue=new String(MarqueB, "UTF-8");
            length = dis.readInt();
            byte[] ModeleB = new byte[length];
            dis.read(ModeleB);
            ModeleRecu=new String(ModeleB, "UTF-8");
            AnneeRecue= dis.readInt();
            KilometrageRecu= dis.readInt();

            System.out.println(
                    "entierRecu = " + MarqueRecue +
                            ",\t doubleRecu = " + ModeleRecu +
                            ",\t "+ AnneeRecue
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

         requeteSansSelect =
                "INSERT INTO voiture (Marque, Modele, Annee, Kilometrage) " +
                        "VALUES (" +
                         "'" + MarqueRecue + "','" + ModeleRecu +"'," + AnneeRecue +","+ KilometrageRecu +
                        ")";

        ResultSet resultSet = null;
        try {
            System.out.println(requeteSansSelect);
            stmt.executeUpdate(requeteSansSelect);
            System.out.println("Insert OK !!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nombreVoitures=0;
        String requeteAvecSelect = "SELECT COUNT(*) FROM voiture;";
        try {
            resultSet = stmt.executeQuery(requeteAvecSelect);
            System.out.println("Select :");
            nombreVoitures=resultSet.getInt("COUNT(*)");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] TableauMarques=new String[nombreVoitures];
        String[] TableauModeles=new String[nombreVoitures];
        int[] TableauAnnees=new int[nombreVoitures];
        int[] TableauKilometrage=new int[nombreVoitures];



        String requeteAvecSelect2 = "SELECT * FROM voiture;";
        try {
            resultSet = stmt.executeQuery(requeteAvecSelect2);
            System.out.println("Select :");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            int i=0;
            while(resultSet.next()){

                TableauMarques[i]=resultSet.getString("Marque");
                TableauModeles[i]=resultSet.getString("Modele");
                TableauAnnees[i]=resultSet.getInt("Annee");
                TableauKilometrage[i]=resultSet.getInt("Kilometrage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        System.out.println("Debut du Renvoi");

        String ipServer="127.0.0.1";

        try {
            MaSocket=new Socket(ipServer, 1235);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        ObjectOutputStream Oos=null;

        try {
            Oos=new ObjectOutputStream(
                    MaSocket.getOutputStream()
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        DataOutputStream dos=null;

        dos=new DataOutputStream(bos);

       try {
            Oos.writeObject(TableauMarques);
            Oos.writeObject(TableauModeles);
            Oos.writeObject(TableauAnnees);
           Oos.writeObject(TableauKilometrage);

           //dos.writeDouble(doubleExport);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            dos.close();
            dis.close();
            Oos.close();
            MaSocket.close();
            monServeurSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

}
