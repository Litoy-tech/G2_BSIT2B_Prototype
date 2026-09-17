import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Appointment {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);


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

        System.out.println("\n  ===== SELECT DENTIST =====");
        for (int i = 0; i < Dentist.dentistList.length; i++){
            System.out.println("  [" + (i+1)+ "]" + Dentist.dentistList[i].name);
        }

        int dentistChoice;

        while(true){
            System.out.print("  Select Dentist: ");
            if(input.hasNextInt()){
                dentistChoice = input.nextInt();

                if(dentistChoice >= 1 && dentistChoice <= Dentist.dentistList.length){
                    selectedDentist = Dentist.dentistList[dentistChoice - 1].name;
                    input.nextLine();
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
                System.out.print("Select time slot: ");
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

        selectedServices = Service.NAMES[serviceChoice - 1];
        serviceFee = Service.FEES[serviceChoice - 1];

        System.out.println("======================================");
        System.out.println("          APPOINTMENT SUMMARY");
        System.out.println("======================================");
        System.out.println();
        System.out.println("Patient Name : " + patientName);
        System.out.println("Address      : " + patientAddress);
        System.out.println("Contact No.  : " + patientContact);
        System.out.println();
        System.out.println("Dentist      : " + selectedDentist);
        System.out.println("Service      : " + selectedServices);
        System.out.println("Date         : " + date.format(dateFormat));
        System.out.println("Time         : " + appointmentTime);
        System.out.println("Estimated Fee: PHP " + serviceFee);

        String confirm;
        while(true){
            System.out.print("\nConfirm Appointment? (Y/N): ");
            confirm = input.nextLine();

            if(confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) break;
            System.out.println("Invalid input! Please enter Y or N.");
        }

        if(confirm.equalsIgnoreCase("Y")){

            AppointmentRecord newAppointment = new AppointmentRecord(patientName,
                    patientAddress, patientContact, selectedServices, date, appointmentTime, selectedDentist, serviceFee, "Confirmed");

            if(AppointmentRecord.addAppointment(newAppointment)){
                System.out.println("\nAppointment Confirmed!");
                System.out.println("\n======================================");
                System.out.println("       APPOINTMENT CONFIRMED");
                System.out.println("======================================");
                System.out.println();
                System.out.println("Patient Name : " + patientName);
                System.out.println("Address      : " + patientAddress);
                System.out.println("Contact No.  : " + patientContact);
                System.out.println("Dentist      : " + selectedDentist);
                System.out.println();
                System.out.println("Service      : " + selectedServices);
                System.out.println("Date         : " + date.format(dateFormat));
                System.out.println("Time         : " + appointmentTime);
                System.out.println("Estimated Fee: PHP " + serviceFee);
                System.out.println("Status       : Confirmed");
                System.out.println("======================================");
            }else {
                System.out.println("This schedule is already booked.");
            }
        }else {
            System.out.println("Appointment not confirmed.");
        }

    }

    public void updateAppointment(Scanner input){

        AppointmentRecord record = selectAppointment(input, " to update");
        if(record == null) return;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate today = LocalDate.now();
        LocalDate newDate;
        String newTime = "";

        while(true){
            LocalDate date;
            while(true){
                System.out.print("\nEnter new appointment date (MM/DD/YYYY): ");
                String dateInput = input.nextLine();
                try{
                    date = LocalDate.parse(dateInput, dateFormat);
                    if(date.isBefore(today)){
                        System.out.println("Invalid date! Please choose today or a future date.");
                    }else break;
                }catch(DateTimeParseException e){
                    System.out.println("Invalid Date Format. Please use MM/DD/YYYY");
                }
            }

            System.out.println("\n===== SELECT TIME SLOT =====");
            System.out.println("[1] 8:00 AM  - 10:00 AM");
            System.out.println("[2] 10:00 AM - 12:00 PM");
            System.out.println("[3] 1:00 PM  - 3:00 PM");
            System.out.println("[4] 3:00 PM  - 5:00 PM");

            int timeChoice;
            while(true){
                System.out.print("Select time slot: ");
                if(input.hasNextInt()){
                    timeChoice = input.nextInt();
                    input.nextLine();
                    if(timeChoice >= 1 && timeChoice <= 4) break;
                    System.out.println("Invalid choice! Please select 1-4.");
                }else{
                    System.out.println("Invalid input! Please enter a number.");
                    input.nextLine();
                }
            }

            switch(timeChoice){
                case 1: newTime = "8:00 AM - 10:00 AM"; break;
                case 2: newTime = "10:00 AM - 12:00 PM"; break;
                case 3: newTime = "1:00 PM - 3:00 PM"; break;
                default: newTime = "3:00 PM - 5:00 PM"; break;
            }

            if(AppointmentRecord.hasConflict(record, date, newTime, record.dentist)){
                System.out.println("\n" + record.dentist + " is already booked at this date and time.");
                System.out.println("Please choose a different date or time slot.");
            }else{
                newDate = date;
                break;
            }
        }

        String confirm;
        while(true) {
            System.out.print("\nConfirm Appointment? (Y/N): ");
            confirm = input.nextLine();

            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) break;
            System.out.println("Invalid input! Please enter Y or N.");
        }

        if(confirm.equalsIgnoreCase("Y")){

            record.date = newDate;
            record.time = newTime;

            System.out.println("\n======================================");
            System.out.println("       APPOINTMENT UPDATED");
            System.out.println("======================================");
            System.out.println("Patient : " + record.patientName);
            System.out.println("Dentist : " + record.dentist);
            System.out.println("Service : " + record.services);
            System.out.println("Date    : " + record.date.format(dateFormat));
            System.out.println("Time    : " + record.time);
            System.out.println("======================================");
        }else{
            System.out.println("\nUpdate cancelled. Appointment unchanged.");
        }
    }

    private AppointmentRecord selectAppointment(Scanner input, String actionLabel){
        AppointmentRecord.viewAppointment();

        if(AppointmentRecord.appointments.isEmpty()){
            return null;
        }

        while(true){
            System.out.print("\nEnter Appointment Number " + actionLabel + "(0 to cancel): ");

            if(input.hasNextInt()){
                int choice = input.nextInt();
                input.nextLine();

                if(choice == 0) return null;

                if(choice >= 1 && choice <= AppointmentRecord.appointments.size()){
                    AppointmentRecord record = AppointmentRecord.appointments.get(choice - 1);

                    if(!record.status.equalsIgnoreCase("Confirmed")){
                        System.out.println("That appointment is already " + record.status + ". Please choose another.");
                        continue;
                    }
                    return record;
                }
                System.out.println("Invalid choice! Please select a valid appointment number.");
            }else {
                System.out.println("Invalid input! Please enter a number.");
                input.nextLine();
            }
        }
    }

    public void cancelAppointment(Scanner input){

        AppointmentRecord record = selectAppointment(input, "cancel");
        if(record == null) return;

        System.out.println("\n======================================");
        System.out.println("        CANCEL APPOINTMENT");
        System.out.println("======================================");
        System.out.println("Patient : " + record.patientName);
        System.out.println("Dentist : " + record.dentist);
        System.out.println("Service : " + record.services);
        System.out.println("Date    : " + record.date.format(DATE_FMT));
        System.out.println("Time    : " + record.time);
        System.out.println("======================================");

        String confirm;
        while(true){
            System.out.print("\nAre you sure you want to cancel this appointment? (Y/N): ");
            confirm = input.nextLine();
            if(confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) break;
            System.out.println("Invalid input! Please enter Y or N.");
        }

        if(confirm.equalsIgnoreCase("Y")){
            record.status = "Cancelled";
            System.out.println("\nAppointment cancelled successfully.");
        }else{
            System.out.println("\nCancellation aborted. Appointment remains " + record.status + ".");
        }
    }
}