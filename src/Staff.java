import java.util.Scanner;

public class Staff {
    private String userName;
    private String password;

    public Staff(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public boolean authenticate(String enteredUsername, String enteredPassword){
        return userName.equals(enteredUsername) && password.equals(enteredPassword);
    }

    public void staffMenu(){
        int choice;

        Scanner input = new Scanner(System.in);

        while(true){
            System.out.println("======================================");
            System.out.println("             STAFF MENU");
            System.out.println("======================================");
            System.out.println("[1] View Dental Services");
            System.out.println("[2] Manage Appointment");
            System.out.println("[3] Exit");
            System.out.println("======================================");

            System.out.print("Select: ");
            choice = input.nextInt();

            switch (choice){
                case 1:
                    Service service = new Service();
                    service.services();
                    System.out.println();
                    break;
                case 2:
                    managePatient(input);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public void managePatient(Scanner input){
        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("        MANAGE APPOINTMENT");
            System.out.println("======================================");
            System.out.println("[1] Add Appointment");
            System.out.println("[2] View Appointments");
            System.out.println("[3] Update Appointment");
            System.out.println("[4] Cancel Appointment");
            System.out.println("[5] Back");
            System.out.println("======================================");

            while(true){
                System.out.print("Select: ");
                if(input.hasNextInt()){
                    choice = input.nextInt();
                    input.nextLine();
                    if(choice < 1 && choice > 5) {
                        System.out.println("Invalid Choice! Try again");
                    }else{
                        break;
                    }
                }else{
                    System.out.println("Invalid Input! Please enter a number.");
                    input.nextLine();
                }
            }

            switch (choice){
                case 1:
                    Apointment book = new Apointment();
                    book.bookApointment(input);
                    break;
                case 2:
                    AppointmentRecord.viewAppointment();
                    System.out.println("\n\n");
                    break;
                case 3:
                    Apointment update = new Apointment();
                    update.updateAppointment(input);
                    break;
            }

        }while(choice != 5);
    }
}



