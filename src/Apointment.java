import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Apointment {

    public void bookApointment(Scanner input){

        String patientName = "", patientAddress = "", patientContact = "";
        String selectedDentist = "";
        String selectedServices = "";
        String appointmentTime = "";
        String appointmentDate = "";
        int serviceFee;

        System.out.println("\n======================================");
        System.out.println("          BOOK AN APPOINTMENT");
        System.out.println("======================================");

        System.out.println("\\n  ===== SELECT DENTIST =====");
        for (int i = 0; i < Dentist.dentistList.length; i++){
            System.out.println("  [" + (i+1)+ "]" + Dentist.dentistList[i].name);
        }

        int dentistChoice;

        while(true){
            System.out.println("  Select Dentist: ");
            if(input.hasNext()){
                dentistChoice = input.nextInt();

                if(dentistChoice >= 1 && dentistChoice <= Dentist.dentistList.length){
                    selectedDentist = Dentist.dentistList[dentistChoice - 1].name;
                    break;
                }else{
                    System.out.println("Invalid choice! Please select a valid dentist.");
                }
            }else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate today = LocalDate.now();
        LocalDate date;
        int timeChoice;

        while(true){
            while (true){
                System.out.println("\n===== DATE =====");
                System.out.print("Enter appointment date (MM/DD/YYYY): ");
                appointmentDate = input.nextLine();
                try{
                    date = LocalDate.parse(appointmentDate, dateFormat);
                    if(date.isBefore(today)){
                        System.out.println("Invalid date! Please choose today or a future date.");
                    }else break;
                }catch (DateTimeParseException e){
                    System.out.println("Invalid Date Format. Please use MM/DD/YYYY");
                }
            }

            System.out.println("\n===== SELECT TIME SLOT =====");
            System.out.println("[1] 8:00 AM  - 10:00 AM");
            System.out.println("[2] 10:00 AM - 12:00 PM");
            System.out.println("[3] 1:00 PM  - 3:00 PM");
            System.out.println("[4] 3:00 PM  - 5:00 PM");

            while (true){
                System.out.println("Select time slot: ");
                if(input.hasNextInt()){
                    timeChoice = input.nextInt();
                    input.nextLine();

                    if(timeChoice >= 1 && timeChoice <= 4){
                        if (timeChoice == 1) appointmentTime = "8:00 AM - 10:00 AM";
                        else if (timeChoice == 2) appointmentTime = "10:00 AM - 12:00 PM";
                        else if (timeChoice == 3) appointmentTime = "1:00 PM - 3:00 PM";
                        else appointmentTime = "3:00 PM - 5:00 PM";
                        break;
                    }else{
                        System.out.println("Invalid choice! Please select 1-4.");
                    }
                }else {
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }
            if(AppointmentRecord.hasConflict(null, date, appointmentTime, selectedDentist)){
                System.out.println("\n" + selectedDentist + " is already booked at this date and time.");
                System.out.println("Please choose a different date or time slot.");
            }else break;
        }

        System.out.println("\n===== PATIENT INFORMATION =====");
        System.out.print("Enter client name: ");
        patientName = input.nextLine();
        while (patientName.trim().isEmpty()) {
            System.out.println("Client name cannot be empty.");
            System.out.print("Enter client name: ");
            patientName = input.nextLine();
        }

        System.out.print("Enter client address: ");
        patientAddress = input.nextLine();
        while (patientAddress.trim().isEmpty()) {
            System.out.println("Client address cannot be empty.");
            System.out.print("Enter client address: ");
            patientAddress = input.nextLine();
        }

        System.out.print("Enter client contact #: ");
        patientContact = input.nextLine();
        while (patientContact.trim().isEmpty()) {
            System.out.println("Client contact # cannot be empty.");
            System.out.print("Enter client contact #: ");
            patientContact = input.nextLine();
        }

        new Service().services();

        int serviceChoice;

        while(true){
            System.out.print("Select service: ");
            if(input.hasNextInt()){
                serviceChoice = input.nextInt();
                input.nextLine();
                if(serviceChoice >= 1 && serviceChoice <= Service.NAMES.length){
                    break;
                }else{
                    System.out.println("Invalid choice! Please select 1-" + Service.NAMES.length);
                }
            }else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }
    }
}
