import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
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
            System.out.println("amount rs." + transferamt + " is transfered from " + this.accNo + " to "
                    + receiverAccNo.getAccno());
        } else {
            System.out.println("Transfer failed due to insufficient balance in account " + this.accNo);
        }
    }

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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MAIN MENU ---\n 1.Create account \n 2.Login account \n 3.exit");
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
                    String pass = sc.nextLine();
                    BankAccount newacc = new BankAccount(name, acccnt, 0, pass);
                    accounts.add(newacc);
                    System.out.println("Your Account is created successfully");
                    System.out.println("your acc no is: " + acccnt);
                    acccnt++;
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
                    int op = 1;
                    while ((op <= 5 && op > 0)) {
                        System.out.println("\n--- Bank Menu ---");
                        System.out.println("1. Deposit money");
                        System.out.println("2. Withdraw money");
                        System.out.println("3. Check Balance");
                        System.out.println("4. Transfer money (Acc to Acc)");
                        System.out.println("5. Change user name");
                        System.out.println("6. Change password");
                        System.out.println("7. Logout");
                        System.out.print("Enter your choice: ");
                        op = sc.nextInt();
                        switch (op) {
                            case 1:
                                System.out.println("Enter amount to deposit");
                                double damt = sc.nextDouble();
                                acc.deposit(damt);
                                break;
                            case 2:
                                System.out.println("Enter amount to withdraw");
                                double wamt = sc.nextDouble();
                                acc.withdraw(wamt);
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
                                op = 9;
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