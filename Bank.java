import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable; //Store accounts in a file

class BankAccount implements Serializable {
    private int accNo;
    private String custname;
    private double balAmt;
    private String password;

    BankAccount(String name, int accno, double balamt, String password) {
        this.custname = name;
        this.accNo = accno;
        this.password = password;
        if (balamt >= 0) {
            this.balAmt = balamt;
        } else {
            System.out.println("Invalid amount. balance is 0");
            this.balAmt = 0;
        }
    }

    String getCustName() {
        return custname;
    }

    public void setcustname(String name) {
        this.custname = name;
        // return custname;
    }

    String getpassword() {
        return password;
    }

    public void setpassword(String pass) {
        this.password = pass;
        // return password;
    }

    int getAccno() {
        return accNo;
    }

    double getBalAmt() {
        return balAmt;
    }

    public void deposit(double amt) {
        if (amt > 0) {
            balAmt += amt;
            System.out.println("Amount Rs." + amt + " is deposited");
        } else {
            System.out.println("Given amount is invalid");
        }
    }

    public boolean withdraw(double amt) {
        if (amt > 0 && amt <= balAmt) {
            balAmt -= amt;
            System.out.println("Amount Withdrawen :Rs." + amt);
            return true;
        } else if (amt > balAmt) {
            System.out.println("Insufficient balance to withdraw");
            return false;
        } else {
            System.out.println("INVALID INPUT");
            return false;
        }
    }

    public void transfer(BankAccount receiverAccNo, double transferamt) {
        if (this.withdraw(transferamt)) {
            receiverAccNo.deposit(transferamt);
            System.out.print("to " + receiverAccNo.getCustName());
            System.out.println("amount rs." + transferamt + " is transfered from account " + this.accNo + " to "
                    + receiverAccNo.getAccno());
        } else {
            System.out.println("Transfer failed due to insufficient balance in account " + this.accNo);
        }
    }

}

class Admin {
    static final String adminPas = "admin123";
    static final String adminId = "admin";
}

public class Bank {
    Scanner sc = new Scanner(System.in);
    static int acccnt = 50; // Create a class attribute
    static ArrayList<BankAccount> accounts = new ArrayList<>();

    public static BankAccount findAccount(int accno) {
        for (BankAccount acc : accounts) {
            if (acc.getAccno() == accno) {
                return acc;
            }
        }
        return null;
    }

    static void saveAccounts() {
        try (java.io.FileWriter fw = new java.io.FileWriter("accounts.txt")) {
            for (BankAccount b : accounts) {
                fw.write(
                        b.getAccno() + "," +
                                b.getCustName() + "," +
                                b.getBalAmt() + "," +
                                b.getpassword() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error saving accounts");
        }
    }

    static void loadAccounts() {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("accounts.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int accNo = Integer.parseInt(data[0]);
                String name = data[1];
                double bal = Double.parseDouble(data[2]);
                String pass = data[3];

                accounts.add(new BankAccount(name, accNo, bal, pass));

                if (accNo >= acccnt) {
                    acccnt = accNo + 1;
                }
            }
        } catch (Exception e) {
            // File not found first time — OK
        }
    }

    public static void main(String[] args) {
        loadAccounts();
        system();
        saveAccounts();

    }

    public static void system() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- LOGIN MENU ---\n1.ADMIN login \n2.User login\n3.exit");
            System.out.print("Enter choice: ");
            int mainop = sc.nextInt();
            switch (mainop) {
                case 1:
                    // Admin
                    sc.nextLine();
                    System.out.println("Enter Admin user id: ");
                    String id = sc.nextLine();
                    System.out.println("Enter password: ");
                    String pass = sc.nextLine();
                    if (!id.equals(Admin.adminId) || !pass.equals(Admin.adminPas)) {
                        System.out.println("Invalid Credential");
                        break;
                    }
                    System.out.println("Admin Login Succesful");
                    int opn = 0;
                    while (opn != 4) {
                        System.out.println("\n--- ADMIN MENU ---");
                        System.out.println("1. View All Accounts");
                        System.out.println("2. change user passwords");
                        System.out.println("3. Delete an account");
                        System.out.println("4. Logout");
                        System.out.print("Enter choice: ");
                        opn = sc.nextInt();
                        // sc.nextInt();
                        switch (opn) {
                            case 1:
                                int q = 1;
                                System.out.println("Accounts Lists of Bank");
                                for (BankAccount b : accounts) {
                                    System.out.println(q + " ) \"" + b.getCustName() + "\" Account number: \""
                                            + b.getAccno() + "\" Account Balance\"" + b.getBalAmt() + "\"");
                                    q++;
                                }
                                break;
                            case 2:
                                int a = 0;
                                System.out.println("Enter user account number to change password");
                                int acno = sc.nextInt();
                                sc.nextLine();
                                for (BankAccount b : accounts) {
                                    if (acno == (b.getAccno())) {
                                        System.out.println("Enter new password to change: ");
                                        String nepas = sc.nextLine();
                                        b.setpassword(nepas);
                                        saveAccounts();
                                        System.out.println("Password changed succesfully");
                                        a = 1;
                                        break;
                                    }
                                }
                                if (a == 0) {
                                    System.out.println("Account is unavailed");
                                    break;
                                }
                                break;
                            case 3:
                                System.out.print("Enter an account number to remove: ");
                                int racc = sc.nextInt();
                                for (BankAccount b : accounts) {
                                    if (racc == (b.getAccno())) {
                                        accounts.remove(b);
                                        saveAccounts();
                                        System.out.println("Account is Successfully removed");
                                        break;
                                    }
                                }
                                // System.out.println("");
                                break;
                            case 4:
                                System.out.println("Sucessfully logged out");
                                break;
                            default:
                                System.out.println("-----------------------------");
                                System.out.println("INVALID choice");
                                System.out.println("-----------------------------");
                        }
                    }
                    break;
                // System.out.println("-----------------------------");
                // break;
                case 2:
                    System.out.println("User Account");
                    boolean userlogin = true;
                    while (userlogin) {
                        System.out.println("\n--- USER MAIN MENU ---\n 1.Create account \n 2.Login account \n 3.exit");
                        System.out.println("Enter Choice(1,2,3)");
                        int ch = sc.nextInt();
                        switch (ch) {
                            case 1:
                                System.out.println("-----------------------------");
                                System.out.println("*****Creating Account*****");
                                sc.nextLine();
                                System.out.println("Enter your name: ");
                                String name = sc.nextLine();
                                System.out.println("Enter your password: ");
                                String pass1 = sc.nextLine();
                                BankAccount newacc = new BankAccount(name, acccnt, 0, pass1);
                                accounts.add(newacc);
                                System.out.println("Your Account is created successfully");
                                System.out.println("your acc no is: " + acccnt);
                                acccnt++;
                                saveAccounts();
                                System.out.println("-----------------------------");
                                break;
                            case 2:
                                System.out.println("-----------------------------");
                                System.out.println("Enter Account no.:");
                                int accno = sc.nextInt();
                                sc.nextLine();

                                BankAccount acc = findAccount(accno);
                                if (acc == null) {
                                    System.out.println("Account not found");
                                    break;
                                }
                                System.out.println("Enter password: ");
                                String pw = sc.nextLine();
                                if (!pw.equals(acc.getpassword())) {
                                    System.out.println("INVALID password");
                                    break;
                                }
                                System.out.println("Login is done, Welcome " + acc.getCustName() + "!");
                                boolean login = true;
                                while (login) {
                                    System.out.println("\n--- Bank Menu ---");
                                    System.out.println("1. Deposit money");
                                    System.out.println("2. Withdraw money");
                                    System.out.println("3. Check Balance");
                                    System.out.println("4. Transfer money (Acc to Acc)");
                                    System.out.println("5. Change user name");
                                    System.out.println("6. Change password");
                                    System.out.println("7. Logout");
                                    System.out.print("Enter your choice: ");
                                    int op = sc.nextInt();
                                    switch (op) {
                                        case 1:
                                            System.out.println("Enter amount to deposit");
                                            double damt = sc.nextDouble();
                                            acc.deposit(damt);
                                            saveAccounts();
                                            break;
                                        case 2:
                                            System.out.println("Enter amount to withdraw");
                                            double wamt = sc.nextDouble();
                                            acc.withdraw(wamt);
                                            saveAccounts();
                                            break;
                                        case 3:
                                            System.out.println("Balance amount is rs." + acc.getBalAmt());
                                            break;
                                        case 4:
                                            System.out.println("Enter account number to send");
                                            int raccno = sc.nextInt();
                                            BankAccount racc = findAccount(raccno);
                                            if (racc == null) {
                                                System.out.println("Receiver not FOUND!");
                                                break;
                                            }
                                            System.out.println("Enter amount to send");
                                            double amt = sc.nextDouble();
                                            if (amt <= 0) {
                                                System.out.println("Enter a valid amount");
                                                break;
                                            }
                                            acc.transfer(racc, amt);
                                            saveAccounts();
                                            break;
                                        case 5:
                                            System.out.println("Enter new user name:");
                                            String nam = sc.next();
                                            acc.setcustname(nam);
                                            break;
                                        case 6:
                                            System.out.println("Enter old password");
                                            String oldpass = sc.next();
                                            int l = 3;
                                            for (int i = 0; i < 3; i++) {
                                                if (!oldpass.equals(acc.getpassword())) {
                                                    l = l - 1;
                                                    System.out.println("Enter password correct: ");
                                                    System.out.print("(" + l + ")" + " Attempts left ");
                                                } else {
                                                    break;
                                                }
                                            }
                                            System.out.println("Enter new password: ");
                                            String newpass = sc.next();
                                            acc.setpassword(newpass);
                                            break;
                                        case 7:
                                            System.out.println("Account is logged out");
                                            login = false;
                                            break;
                                        default:
                                            System.out.println("INVALID OPTION");

                                    }
                                }
                                System.out.println("-----------------------------");
                                break;
                            case 3:
                                System.out.println("-----------------------------");
                                System.out.println("Thankyou for using our service");
                                System.out.println("-----------------------------");
                                userlogin = false;
                                break;

                            default:
                                System.out.println("-----------------------------");
                                System.out.println("INVALID choice");
                                System.out.println("-----------------------------");
                        }
                    }
                    break;
                case 3:
                    System.out.println("-----------------------------");
                    System.out.println("Thankyou for using our service");
                    System.out.println("-----------------------------");
                    saveAccounts();
                    sc.close();
                    return;
                default:
                    System.out.println("-----------------------------");
                    System.out.println("INVALID choice");
                    System.out.println("-----------------------------");
            }
        }
    }
}
