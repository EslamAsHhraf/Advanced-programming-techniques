package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Hospital {
    HashMap<Integer,Doctor>doctors;
    public Hospital(String file) throws FileNotFoundException {
        File myFile = new File(file);
        doctors=new HashMap<Integer,Doctor>();
        Scanner myReader  =new Scanner(myFile);
        while(myReader .hasNext())
        {
            int id=myReader .nextInt();
            String name=myReader .nextLine();
            doctors.put(id,new Doctor(id,name));
        }
    }
    public int makeAppointment(int doctor_id,int timeSlot,String patient_Name)
    {
        if(doctors.containsKey(doctor_id)) {
            return doctors.get(doctor_id).makeAppointment_doctor(timeSlot-1,patient_Name);
        }
        else{
            return -1;
        }
    }
    public int cancelAppointment(int doctor_id,int timeSlot,String patient_Name)
    {
        if(doctors.containsKey(doctor_id)) {
            return doctors.get(doctor_id).cancelAppointment_doctor(timeSlot-1,patient_Name);
        }
        else{
            return -1;
        }
    }
    public void print()
    {
        for(HashMap.Entry<Integer, Doctor> entry:doctors.entrySet())
        {
            System.out.println("Doctor ID: "+entry.getValue().id+", Doctor Name: "+entry.getValue().name);
            boolean[]timeSlots=entry.getValue().timeSlot;
            String[]patients=entry.getValue().patient;
            for(int i=0;i<4;i++)
            {
                if(timeSlots[i])
                {
                    System.out.println("IN TimeSlot | "+(i+1)+" | has patient:  "+patients[i]);
                }
                else
                {
                    System.out.println("IN TimeSlot |  "+(i+1)+" | hasn't patient");
                }

            }
            System.out.println("------------------------");
        }
        System.out.println("********************************************");
    }
}
