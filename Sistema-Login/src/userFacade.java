import business.User;
import data.DAOUser;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class userFacade implements ITUsers {
    private Map<String, User> allUsers;


    public userFacade() {
        this.allUsers = DAOUser.getInstance();
    }

    @Override
    public void adicionaUser(User u) {
        this.allUsers.put(u.getUsername(),u);
    }

    @Override
    public void removeUser(User u) {
        this.allUsers.remove(u.getUsername());
    }
    public void removeUser2(String u) {
        this.allUsers.remove(u);
    }

    @Override
    public boolean existeUser(User u) {
        return (this.allUsers.containsKey(u.getUsername()) && this.allUsers.get(u.getUsername()).getPassword().equals(u.getPassword()));
    }

    public boolean existeUser2(String u) {
        return this.allUsers.containsKey(u);
    }

    @Override
    public User procuraUser(String user) {
        User u = null;
        u=this.allUsers.values().stream().filter(x->x.getUsername().equals(user)).findFirst().get();
        return u;
    }

    @Override
    public Collection<User> getUsers() {
        return this.allUsers.values().stream().collect(Collectors.toList());
    }

    public void showUsers() {
       DAOUser daoUser = new DAOUser();
       daoUser.show();
    }
}
