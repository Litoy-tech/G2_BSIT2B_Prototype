import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int choice;

        Staff staff = new Staff("staff", "1234");

        System.out.println("======================================");
        System.out.println("       DENTALCARE APPOINTMENT SYSTEM");
        System.out.println("======================================");
        System.out.println(" [1] Sign-Up");
        System.out.println(" [2] Login");
        System.out.println(" [3] Exit");
        System.out.println("======================================");

        while(true){
            System.out.print("Select: ");

            if(input.hasNextInt()){
                choice = input.nextInt();
                input.nextLine();

                if(choice < 1 || choice > 3){
                    System.out.println("Invalid Choice! Try again");
                }else break;

            }else {
                System.out.println("Invalid Input! Please enter a number.");
                input.nextLine(); // remove invalid input
            }
        }

        if(choice == 1){
            System.out.println("Register soon");

        }else if(choice == 2){

            int attemps = 3;
            boolean loggedIn = false;

            while(attemps > 0) {
                String enteredUsername = "";

                while (enteredUsername.isEmpty()) {
                    System.out.print("Username: ");
                    enteredUsername = input.nextLine();

                    if (enteredUsername.isEmpty()) {
                        System.out.println("Username cannot be empty!");
                    }
                }

                String enteredPassword = "";
                while (enteredPassword.isEmpty()) {
                    System.out.print("Password: ");
                    enteredPassword = input.nextLine();

                    if (enteredPassword.isEmpty()) {
                        System.out.println("Password cannot be empty!");
                    }
                }

                if (staff.authenticate(enteredUsername, enteredPassword)) {
                    System.out.println("Login successful!");
                    loggedIn = true;

                    staff.staffMenu();
                    break;
                }else{
                    attemps--;
                    System.out.println("Invalid username or password!");

                    if(attemps > 0){
                        System.out.println("Attempts remaining: " + attemps);
                    }
                }
            }
        }else{
            System.out.println("Exit...");
            return;
        }
    }
}