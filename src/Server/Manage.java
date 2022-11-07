package Server;

import java.io.*;
import java.net.Socket;

public class Manage implements Runnable
{
    private Socket socket;

    public Manage(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        System.out.print("\nConnesso: "+socket.getInetAddress()+"\n");

        int HP_giocatore,pozione_giocatore,HP_mostro;
        int scelta;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;

        HP_giocatore = (int)(Math.random() * ((20 - 10) + 1)) + 10;
        HP_mostro = (int)(Math.random() * ((15 - 10) + 1)) + 10;
        pozione_giocatore = (int)(Math.random() * ((10 - 5) + 1)) + 5;

        try
        {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(),true);

            while(true)
            {
                printWriter.println(HP_giocatore+" "+pozione_giocatore+" "+HP_mostro);
                printWriter.flush();
                scelta = Integer.parseInt(bufferedReader.readLine());

                switch (scelta)
                {
                    case 1:

                        int danno_giocatore,danno_mostro;

                        danno_giocatore = (int)(Math.random() * 10);
                        danno_mostro = (int)(Math.random() * 10);

                        HP_giocatore -= danno_giocatore;
                        HP_mostro -= danno_mostro;

                        if(HP_giocatore < 0) HP_giocatore = 0;
                        if(HP_mostro < 0) HP_mostro = 0;

                        printWriter.println("combattuto "+HP_giocatore+" "+pozione_giocatore+" "+HP_mostro+" "+danno_giocatore+" "+danno_mostro);

                        break;

                    case 2:

                        int pozione_bevuta;

                        if(HP_giocatore<20)
                        {
                            if(pozione_giocatore > 0 )
                            {
                                pozione_bevuta = (int)((Math.random() * pozione_giocatore)+1);

                                pozione_giocatore -= pozione_bevuta;
                                HP_giocatore += pozione_bevuta;

                                printWriter.println("pozione "+pozione_bevuta+" "+pozione_giocatore+" "+HP_giocatore+" "+HP_mostro);

                            }
                            else printWriter.println("terminata");
                        }

                        else printWriter.println("massimo");

                        break;
                }

                if(HP_giocatore<=0 && HP_mostro<=0)
                {
                    printWriter.println("Pareggio");
                    break;
                }

                else if(HP_giocatore<=0)
                {
                    printWriter.println("Sconfitta");
                    break;
                }

                else if(HP_mostro<=0)
                {
                    printWriter.println("Vittoria");
                    break;
                }

                else printWriter.println("");
            }
        } catch (IOException e) {throw new RuntimeException(e);}

        finally
        {
            try
            {
                if (printWriter!= null) printWriter.close();
                if (bufferedReader != null)
                {
                    bufferedReader.close();
                    System.out.print("\nDisconnessione: "+socket.getInetAddress());
                    socket.close();
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
}