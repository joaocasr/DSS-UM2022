import business.User;

import java.util.Collection;

public interface ITUsers {
    void adicionaUser(User u);
    void removeUser(User u);
    boolean existeUser(User u);
    User procuraUser(String user);
    Collection<User> getUsers();
}
