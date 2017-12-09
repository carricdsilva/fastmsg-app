package com.example.fastmsg;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by CarricDesktop on 12/7/2017.
 */

public class CommunicatorThread extends Thread
{
    public int portNumber = 10000;
    public ServerSocket srvSocket;
    public Socket clientSocket1;
    public WriterThread writeTh;
    TextView textView;

    public void run()
    {
        System.out.println("Communicator thread started...also acts as reader thread");

        try
        {
            srvSocket     = new ServerSocket(portNumber, 0, InetAddress.getByName("localhost"));
            System.out.println("Waiting for incoming connection");
            clientSocket1 = srvSocket.accept();

            BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));

            // Start the writer thread in parallel
            writeTh.clientSocket1 = clientSocket1;
            writeTh.start();

            while(true)
            {
                System.out.println("Waiting for message");
                String msg = in.readLine();
                if(null != msg)
                {
                    System.out.println("Got message from remote: length[" + msg.length() + "] msg: [" + msg + "]");

                    msg = "\n" + msg;
                    textView.append(msg);
                }
                else
                {
                    //Communication closed
                    System.out.println("Communication terminated. Cleaning up sockets");
                    in.close();
                    srvSocket.close();
                    clientSocket1.close();
                    break;
                }

            }
        }
        catch(Exception exp)
        {
            System.out.println("Caught exception: " + exp.toString());
        }

        writeTh.writerLock.lock();
        writeTh.toExit = true;
        writeTh.writerCond.signalAll();
        writeTh.writerLock.unlock();

        System.out.println("Exiting Communicator/Reader Thread");
    }
}
