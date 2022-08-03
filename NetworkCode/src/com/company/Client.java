package com.company;

import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;


public class Client {
    public static final int MAKEPORT=6666;
    public static final int CANCELPORT=6667;
    public static void main(String[]args) throws IOException {

        Scanner Reader=new Scanner(System.in);
        System.out.print("Please, Enter your name: ");
        String patientName=Reader.nextLine();

        Socket SocketMake=new Socket("localhost",MAKEPORT);
        Socket SocketCancel=new Socket("localhost",CANCELPORT);

        PrintWriter GetMake = new PrintWriter(SocketMake.getOutputStream(), true);
        PrintWriter GetCancel = new PrintWriter(SocketCancel.getOutputStream(), true);

        Scanner In_Make=new Scanner(SocketMake.getInputStream());
        Scanner In_Cancel=new Scanner(SocketCancel.getInputStream());

        GetMake.println(patientName);
        GetCancel.println(patientName);

        while(true)
        {
            System.out.println("_______________________________________\n");
            System.out.println("|Enter the operation that you want    |\n");
            System.out.println("|        [1] Make an appointment      |\n");
            System.out.println("|        [2] Cancel an appointment    |\n");
            System.out.println("|        [3] Close the program        |\n");
            System.out.println("______________________________________\n");
            int oper=Reader.nextInt();
            System.out.print("Enter doctor ID: ");
            int doctor_Id=Reader.nextInt();
            System.out.print("Enter timeslot: ");
            int timeSlot=Reader.nextInt();
            String message;
            if(oper==1)
            {
                GetMake.println(doctor_Id);
                GetMake.println(timeSlot);
                message=In_Make.nextLine();
            }
            else if(oper==2)
            {
                GetCancel.println(doctor_Id);
                GetCancel.println(timeSlot);
                message=In_Cancel.nextLine();
            }
            else if(oper==3)
            {
                GetMake.println(-1);
                GetCancel.println(-1);
                break;
            }
            else{
                message="Invalid operation";
            }
            System.out.println(message);
        }
        System.out.println("I am exiting now, bye.");
        GetMake.close();
        GetCancel.close();
        In_Make.close();
        In_Cancel.close();
        SocketMake.close();
        SocketCancel.close();
    }
}
