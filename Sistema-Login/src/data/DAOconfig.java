package data;

public class DAOconfig {
    static final String USERNAME = "joao";                       // Actualizar
    static final String PASSWORD = "joao";                       // Actualizar
    private static final String DATABASE = "usersdb";          // Actualizar
    private static final String DRIVER = "jdbc:mariadb";        // Usar para MariaDB jdbc:mysql://
    //private static final String DRIVER = "jdbc:mysql";        // Usar para MySQL
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE;
}
