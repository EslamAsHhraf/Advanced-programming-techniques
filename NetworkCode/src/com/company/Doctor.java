package com.company;

public class Doctor {
    int id;
    String name;
    boolean []timeSlot;
    String[]patient;
    Doctor(int ID, String Name){
        this.id=ID;
        this.name=Name;
        timeSlot=new boolean[4];
        patient=new String[4];
    }
    public int makeAppointment_doctor(int indx,String patient_Name)
    {
        if(indx>3||indx<0)
        {
            return 2;
        }
        if(!timeSlot[indx])
        {
            timeSlot[indx]=true;
            patient[indx]=patient_Name;
            return 0;
        }
        return 1;
    }
    public int cancelAppointment_doctor(int index,String patientName)
    {
        if(index>3||index<0)
        {
            return 2;
        }
        if(timeSlot[index])
        {
            if(!patientName.equals(patient[index]))
                return 3;
            timeSlot[index]=false;
            patient[index]="";
            return 0;
        }
        return 1;
    }
}
