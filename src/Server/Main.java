package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    public static void main(String[] args)
    {
        ServerSocket server = null;

        try
        {
            server = new ServerSocket(9999);
            server.setReuseAddress(true);

            System.out.println("The server is running...");

            ExecutorService pool = Executors.newCachedThreadPool();

            while (true) pool.execute(new Manage(server.accept()));

        } catch (IOException e) {throw new RuntimeException(e); }

        finally
        {
            if (server != null)
            {
                try {server.close();}
                catch (IOException e) {e.printStackTrace();}
            }
        }
    }
}
