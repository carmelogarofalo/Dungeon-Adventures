package Client;

import java.io.*;
import java.net.Socket;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        PrintWriter printWriter;
        BufferedReader br;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        int scelta,x,continua;
        boolean fine = false;
        int HP_giocatore,pozione_giocatore,HP_mostro;
        int numero_round = 1;
        String esito = "";

        while(!fine)
        {
            System.out.print("\n\nDUNGEON ADVENTURES");

            System.out.print("\nInserisci 1 per giocare oppure un altro numero per uscire dal gioco: ");
            scelta = Integer.parseInt(bufferedReader.readLine());

            if(scelta == 1)
            {
                try(Socket socket = new Socket("127.0.0.1",9999))
                {
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    printWriter = new PrintWriter(socket.getOutputStream(), true);

                    String[] message,message2;

                    while(true)
                    {
                        System.out.print("\nInserisci 1 per combattere");
                        System.out.print("\nInserisci 2 per bere la pozione");
                        System.out.print("\nInserisci 3 per uscire dal gioco");
                        System.out.print("\n----> ");
                        x = Integer.parseInt(bufferedReader.readLine());

                        if(x == 3)
                        {
                            System.out.print("\n\nSCONFITTA");

                            br.close();
                            printWriter.close();

                            fine = true;
                            break;
                        }

                        if(x == 1 || x == 2)
                        {
                            printWriter.println(x);
                            message = br.readLine().split(" ");
                            Thread.sleep(100);
                            message2 = br.readLine().split(" ");

                            HP_giocatore = Integer.parseInt(message[0]);
                            pozione_giocatore = Integer.parseInt(message[1]);
                            HP_mostro = Integer.parseInt(message[2]);

                            System.out.print("\n---------------------");

                            System.out.print("\nRound "+numero_round);

                            System.out.print("\n\nHP Giocatore: "+HP_giocatore);
                            System.out.print("\nPozione: "+pozione_giocatore);
                            System.out.print("\nHP Mostro: "+HP_mostro);

                            numero_round+=1;

                            switch (message2[0])
                            {
                                case "combattuto":

                                    System.out.print("\n\n--->Danno giocatore: "+message2[4]);
                                    System.out.print("\n--->Danno mostro: "+message2[5]);

                                    HP_giocatore = Integer.parseInt(message2[1]);
                                    pozione_giocatore = Integer.parseInt(message2[2]);
                                    HP_mostro = Integer.parseInt(message2[3]);

                                    break;

                                case "pozione":

                                    System.out.print("\n\n--->Pozione bevuta: "+message2[1]);

                                    HP_giocatore = Integer.parseInt(message2[3]);
                                    pozione_giocatore = Integer.parseInt(message2[2]);
                                    HP_mostro = Integer.parseInt(message2[4]);

                                    break;

                                case "terminata":

                                    System.out.print("\n\nPozione terminata!!!");

                                    break;

                                case "massimo":

                                    System.out.println("\n\nSalute giocatore gia al massimo");

                                    break;
                            }

                            System.out.print("\n\n---------------------");

                            System.out.print("\n---------------------");

                            System.out.print("\n\nHP Giocatore: "+HP_giocatore);
                            System.out.print("\nPozione: "+pozione_giocatore);
                            System.out.print("\nHP Mostro: "+HP_mostro);

                            System.out.print("\n\n---------------------");

                            Thread.sleep(100);
                            esito = br.readLine();

                            if(esito.equals("Sconfitta"))
                            {
                                System.out.print("\n\nSCONFITTA");

                                br.close();
                                printWriter.close();

                                fine = true;
                                break;
                            }

                            if(esito.equals("Vittoria"))
                            {
                                System.out.print("\n\nVITTORIA");

                                System.out.print("\n\nInserisci 1 se vuoi avviare un'altra partita: ");
                                continua = Integer.parseInt(bufferedReader.readLine());

                                if(continua != 1)
                                {
                                    br.close();
                                    printWriter.close();
                                    fine = true;
                                    break;
                                }
                                break;
                            }

                            if(esito.equals("Pareggio"))
                            {
                                System.out.print("\n\nPAREGGIO");

                                System.out.print("\n\nInserisci 1 se vuoi avviare un'altra partita: ");
                                continua = Integer.parseInt(bufferedReader.readLine());

                                if(continua != 1)
                                {
                                    br.close();
                                    printWriter.close();
                                    fine = true;
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }catch (IOException | InterruptedException e) {throw new RuntimeException(e);}
            }

            else
            {
                fine = true;
                break;
            }
        }
        System.out.print("\nGrazie per aver giocato");
    }
}