import business.User;

import java.util.Scanner;

public class Menu {
    userFacade userFacade;

    public Menu(){
        this.userFacade = new userFacade();
    }

    public void mainMenu(){
        System.out.println("1) Registro.");
        System.out.println("2) Login.");
        System.out.println("3) Tabela de utilizadores.");
        System.out.println("4) Remove da BD.");

    }

    public void showMenu(){
        Scanner scanner = new Scanner(System.in);

        int op = scanner.nextInt();
        if(op==1) {
            scanner.nextLine();
            System.out.println("Username:");
            String username = scanner.nextLine();
            System.out.println("Password:");
            String password = scanner.nextLine();
            User u = new User(username,password);
            if(!userFacade.existeUser(u)) userFacade.adicionaUser(u);
            else System.out.println("O utilizador já existe no sistema. Faça login.");
        }
        if(op==2) {
            scanner.nextLine();
            System.out.println("Username:");
            String username = scanner.nextLine();
            System.out.println("Password:");
            String password = scanner.nextLine();
            User u = new User(username,password);
            if(!userFacade.existeUser2(username)){
                System.out.println(username+" não existe no sistema.");
            }
            else if(!userFacade.procuraUser(username).getPassword().equals(password)) System.out.println("Password incorreta.Digite novamente.");
            else System.out.println("Você conectou-se.");
        }
        if(op==3){
            userFacade.showUsers();
        }
        if(op==4){
            scanner.nextLine();
            System.out.println("Username:");
            String username = scanner.nextLine();
            userFacade.removeUser2(username);
        }

    }
}
