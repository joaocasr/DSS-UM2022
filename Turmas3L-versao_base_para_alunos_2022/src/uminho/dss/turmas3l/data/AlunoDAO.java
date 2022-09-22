package uminho.dss.turmas3l.data;

import uminho.dss.turmas3l.business.Aluno;

import java.sql.*;
import java.util.*;

public class AlunoDAO implements Map<String, Aluno> {
    private static AlunoDAO singleton = null;

    private AlunoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS alunos (" +
                    "Num varchar(10) NOT NULL PRIMARY KEY," +
                    "Nome varchar(45) DEFAULT NULL," +
                    "Email varchar(45) DEFAULT NULL," +
                    "Turma varchar(10), foreign key(Turma) references turmas(Id))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    public static AlunoDAO getInstance() {
        if (AlunoDAO.singleton == null) {
            AlunoDAO.singleton = new AlunoDAO();
        }
        return AlunoDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM alunos")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size() ==0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Num FROM alunos WHERE Num='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Aluno a = (Aluno) value;
        return this.containsKey(a.getNumero());
    }

    @Override
    public Aluno get(Object key) {
        Aluno aluno = null;
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM alunos WHERE Num= '"+key.toString()+"'")) {
            if(rs.next()) aluno = new Aluno(rs.getString("Num"),rs.getString("Nome"),rs.getString("Email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    @Override
    public Aluno put(String key, Aluno value) {
        Aluno al = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            if(this.containsKey(key)){
                String sql = "UPDATE alunos SET Nome=?, Email=?, Turma=? WHERE Num='"+key+"'";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setString(1,value.getNome());
                pstm.setString(2,value.getEmail());
                pstm.setString(3,key);
            }else {
                String sql = "INSERT INTO alunos VALUES ('" + value.getNumero() + "', '" + value.getNome() + "', '" + value.getEmail() + "', NULL" + ")";
                stm.executeUpdate(sql);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return al;
    }

    @Override
    public Aluno remove(Object key) {
        Aluno a = (Aluno) key;
        try(Connection con = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("DELETE FROM alunos WHERE Num ='"+a.getNumero()+"'")) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a ;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Aluno> m) {
        for(Aluno a : m.values()){
            this.put(a.getNumero(),a);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE alunos");//elimina os dados da tabela mas nao elimina a tabela em si
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        Set<String> Setalunos = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Num FROM alunos");
            while(resultSet.next()){
                String num = resultSet.getString("Num");
                Setalunos.add(num);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        } ;
        return Setalunos;
    }

    @Override
    public Collection<Aluno> values() {
        Collection<Aluno> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Num FROM alunos")) {
            while (rs.next()) {
                String idt = rs.getString("Num");
                Aluno a = this.get(idt);
                res.add(a);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Aluno>> entrySet() {
        Map.Entry<String, Aluno> entry;
        HashSet<Entry<String, Aluno>> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT Num FROM alunos")) {
            while (rs.next()) {
                entry =  new AbstractMap.SimpleEntry<>(rs.getString("Num"),this.get(rs.getString("Num")));
                col.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }
}
