package views;

import dao.dataDAO;
import model.data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class userView {
    private String email;
    userView(String email){
        this.email=email;
    }
    public void home(){
        do{
            System.out.println("Welcome "+this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc=new Scanner(System.in);
            int ch=Integer.parseInt(sc.nextLine());
            switch (ch){
                case 1 -> {
                    try {
                        List<data> files=dataDAO.getAllFiles(email);
                        System.out.println("ID - File Name");
                        for(data file:files){
                            System.out.println(file.getId()+" - "+file.getFileName());
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter File Path:");
                    String path=sc.nextLine();
                    File f=new File(path);
                    data file=new data(0,f.getName(),path,this.email);
                    try {
                        dataDAO.hideFile(file);
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    List<data> files= null;
                    try {
                        files = dataDAO.getAllFiles(email);
                        System.out.println("ID - File Name");
                        for(data file:files){
                            System.out.println(file.getId()+" - "+file.getFileName());
                        }
                        System.out.println("Enter the ID of File to unhide");
                        int id=Integer.parseInt(sc.nextLine());
                        boolean isVaild=false;
                        for(data file:files){
                            if(file.getId()==id){
                                isVaild=true;
                                break;
                            }
                        }
                        if(isVaild){
                            dataDAO.unhide(id);
                        }else{
                            System.out.println("Wrong ID");
                        }
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        }while(true);
    }
}
