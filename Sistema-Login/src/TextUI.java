import business.User;

import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    // O modelUser tem a 'lógica de negócio'.
    private userFacade modelUser;

    // Menus da aplicação
    private Menu menu;

    // Scanner para leitura
    private Scanner scin;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {
        // Criar o menu
        ArrayList<String> opcoesMenu = new ArrayList<>();
        opcoesMenu.add("Registar");
        opcoesMenu.add("Login");

        this.menu = new Menu(" Racing Manager",opcoesMenu);
        this.menu.setHandler(1, this::registaUser);
        this.menu.setHandler(2, this::loginUser);


        this.modelUser = new userFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        this.menu.run();
    }


    public void registaUser(){
        System.out.println("Username:");
        String username = scin.nextLine();
        System.out.println("Password:");
        String password = scin.nextLine();
        User u = new User(username,password);
        if(!this.modelUser.existeUser(u)) this.modelUser.adicionaUser(u);
        else System.out.println("O utilizador já existe no sistema. Faça login.");
    }

    public void loginUser(){
        System.out.println("Username:");
        String username = scin.nextLine();
        System.out.println("Password:");
        String password = scin.nextLine();
        User u = new User(username,password);
        if(!this.modelUser.existeUser2(username)){
            System.out.println(username+" não existe no sistema.");
        }
        else if(!this.modelUser.procuraUser(username).getPassword().equals(password)) System.out.println("Password incorreta.Digite novamente.");
        else System.out.println("Você conectou-se.");

    }
}
