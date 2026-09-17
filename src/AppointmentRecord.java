import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentRecord {

    static ArrayList<AppointmentRecord> appointment = new ArrayList<>();

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MM/dd/uuuu");

    String patientName;
    String patientAddress;
    String patientContact;
    String services;
    LocalDate date;
    String time;
    String dentist;
    int fee;
    String status;

    public AppointmentRecord(String patientName, String patientAddress, String patientContact, String services,
                             LocalDate date, String time, String dentist, int fee, String status){

        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.patientContact = patientContact;
        this.services = services;
        this.date = date;
        this.time = time;
        this.dentist = dentist;
        this.fee = fee;
        this.status = status;
    }

    public static boolean addAppointment(AppointmentRecord newAppointment){
        for(AppointmentRecord appointment : appointment){
            if(appointment.status.equals("Confirmed") && appointment.date.equals(newAppointment.date)
                    && appointment.time.equals(newAppointment.time) && appointment.dentist.equals(newAppointment.dentist)){
                return false;
            }
        }

        appointment.add(newAppointment);
        return true;
    }

    public static boolean hasConflict(AppointmentRecord editing, LocalDate date, String time, String dentist){
        for(AppointmentRecord appointment: appointment){
            if(appointment == editing) continue;
            if (appointment.status.equals("Confirmed") && appointment.date.equals(date)
                    && appointment.time.equals(time)
                    && appointment.dentist.equals(dentist)){
                return true;
            }
        }
        return false;
    }

    public static void viewAppointment(){
        System.out.println("\n======================================");
        System.out.println("          ALL APPOINTMENTS");
        System.out.println("======================================");

        if(appointment.isEmpty()){
            System.out.println("No appointments found.");
            return;
        }

        for(int i = 0; i < appointment.size(); i++){
            AppointmentRecord a = appointment.get(i);

            System.out.println("\nAppointment #" + (i + 1));
            System.out.println("Patient : " + a.patientName);
            System.out.println("Address : " + a.patientAddress);
            System.out.println("Contact : " + a.patientContact);
            System.out.println();
            System.out.println("Dentist : " + a.dentist);
            System.out.println("Service : " + a.services);
            System.out.println("Date    : " + a.date.format(DATE_FMT));
            System.out.println("Time    : " + a.time);
            System.out.println("Fee     : PHP " + a.fee);
            System.out.println("Status  : " + a.status);
            System.out.println("-------------------------------------");

        }
    }

}
