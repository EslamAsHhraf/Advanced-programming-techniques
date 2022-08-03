package com.company;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class Server extends Thread{
    public static final int MAKEPORT=6666;
    public static final int CANCELPORT=6667;
    public static Hospital hospital;
    private Socket socket;
    public Server(Socket s)
    {
        socket=s;
    }
    public static void main(String[]args) throws FileNotFoundException {
        hospital=new Hospital("doctor.txt");
        new Thread(){
            public void run()
            {
                try {
                    ServerSocket sock=new ServerSocket(MAKEPORT);
                    while(true)
                    {
                        new Server(sock.accept()).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            public void run()
            {
                try {
                    ServerSocket sock=new ServerSocket(CANCELPORT);
                    while(true)
                    {
                        new Server(sock.accept()).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void run()
    {
        try {
            PrintWriter output=new PrintWriter(socket.getOutputStream(),true);
            Scanner input=new Scanner(socket.getInputStream());
            String Name=input.nextLine();
            while(true)
            {
                int docotor_ID=-1;
                docotor_ID= input.nextInt();
                if(docotor_ID==-1)
                {
                    break;
                }
                int Time_Slot=input.nextInt();
                if(socket.getLocalPort()==CANCELPORT)
                {
                    int response=hospital.cancelAppointment(docotor_ID,Time_Slot,Name);
                    if(response==0)
                        output.println("Cancelling the appointment is done successfully (Success)");
                    else if(response==-1)
                        output.println("the doctor id is not found in hospital (Failure)");
                    else if(response==1)
                        output.println("the doctor doesn’t have an appointment at this timeslot (Failure)");
                    else if(response==2)
                        output.println("the timeslot index is out of boundary (Failure)");
                    else if(response==3)
                        output.println("the doctor has an appointment to a different patient name at this timeslot (Failure)");
                }
                else
                {
                    int response=hospital.makeAppointment(docotor_ID,Time_Slot,Name);
                   if(response==0)
                       output.println("Making the appointment is done successfully (Success)");
                   else if(response==-1)
                       output.println("the doctor id is not found in hospital (Failure)");
                   else if(response==1)
                       output.println("the doctor is already busy at this timeslot (Failure)");
                   else if(response==2)
                       output.println("the timeslot index is out of boundary (Failure)");
                }
                hospital.print();
            }
            System.out.println("bye, " + Name);
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
