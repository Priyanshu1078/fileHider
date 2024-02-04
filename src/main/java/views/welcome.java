package views;

import java.sql.SQLException;
import java.util.Scanner;
import dao.userDAO;
import model.user;
import service.generateOTP;
import service.sendOTPService;
import service.userService;

public class welcome {
    public void welcomeScreen(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to the App");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to SignUp");
        System.out.println("Press 0 to exit");
        int choice=Integer.parseInt(sc.nextLine());
        switch (choice){
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void signUp() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter name:");
        String name=sc.nextLine();
        System.out.println("Enter email");
        String email=sc.nextLine();
        String genOTP=generateOTP.getOTP();
        sendOTPService.sendOTP(email,genOTP);
        System.out.println("Enter the OTP: ");
        String otp=sc.nextLine();
        if(otp.equals(genOTP)){
            user user=new user(name,email);
            int res=userService.saveUser(user);
            switch (res){
                case 0 -> System.out.println("User Registered");
                case 1 -> System.out.println("User already exist");
            }
        }else{
            System.out.println("Wrong OTP");
        }
    }

    private void login() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter email:");
        String email=sc.nextLine();
        try{
            if(userDAO.isExist(email)){
                String genOTP=generateOTP.getOTP();
                sendOTPService.sendOTP(email,genOTP);
                System.out.println("Enter the OTP: ");
                String otp=sc.nextLine();
                if(otp.equals(genOTP)){
                    new userView(email).home();
                }else{
                    System.out.println("Wrong OTP");
                }
            }else{
                System.out.println("User not found");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
