public class Main {
    public static void main(String [] args){
        Menu m = new Menu();
        try {
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }

    }
}
