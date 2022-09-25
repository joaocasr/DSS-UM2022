package data;

import java.sql.*;
import java.util.*;
import business.User;

public class DAOUser implements Map<String, User> {
    private static DAOUser singleton = null;

    public DAOUser()  {
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
            Statement statement = con.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "username varchar(30) NOT NULL PRIMARY KEY,"+
                    "password varchar(50) NOT NULL"+
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }

    }

    public static DAOUser getInstance() {
        if(DAOUser.singleton ==null){
            DAOUser.singleton = new DAOUser();
        }
        return DAOUser.singleton;
    }

    public void show(){
        try(Connection con = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME,DAOconfig.PASSWORD)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while(rs.next()){
                System.out.println("Username:"+rs.getString(1)+"->"+ " Password:"+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int size() {
        int n =0;
        try (Connection con = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT (*COUNT) FROM users")) {
                if(rs.next()){
                    n = rs.getInt(1);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r = false;
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT username FROM users")){
            while(rs.next()){
                if(rs.getString(1).equals(key.toString())) r=true;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        User u = (User) value;
        return this.containsValue(u.getUsername());
    }

    @Override
    public User get(Object key) {
        User user = null;
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE username='"+key.toString()+"'")) {
            if(rs.next()){
                user = new User(rs.getString(1),rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User put(String key, User value) {
        User user = null;
        String sql="";
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
        Statement st = con.createStatement()){
        if(this.containsKey(key)){
             sql= "UPDATE users SET password='"+value.getPassword()+"' WHERE username='"+key+"'";
        }else{
             sql= "INSERT INTO users VALUES('"+key+"', '"+value.getPassword()+"')";
        }
        st.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return user;
    }

        @Override
    public User remove(Object key) {
        User u = this.get(key);
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("DELETE FROM users WHERE username='"+u.getUsername()+"'")) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public void putAll(Map<? extends String, ? extends User> m) {
        for( User u : m.values()){
            this.put(u.getUsername(),u);
        }
    }

    @Override
    public void clear() {
            try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
               Statement st = con.createStatement()){
               st.executeUpdate("TRUNCATE users");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NullPointerException(e.getMessage());
            }
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<User> values() {
        Collection<User> allUsers = new HashSet<>();
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
           Statement st = con.createStatement();
           ResultSet rs = st.executeQuery("SELECT * FROM users")) {
            while(rs.next()){
                allUsers.add(new User(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public Set<Entry<String, User>> entrySet() {
        return null;
    }
}
