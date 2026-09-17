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
            System.out.println("[2] View Appointments");
            System.out.println("[3] Manage Appointment");
            System.out.println("[4] Exit");
            System.out.println("======================================");

            System.out.print("Select: ");
            choice = input.nextInt();

            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }

    }
}
