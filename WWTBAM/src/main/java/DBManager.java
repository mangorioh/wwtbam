/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aron
 */

//todo: migrate fileio to dbmanager
//      failsafe establish connection to use file pool
//      make data manager for game side (prevent excessive db accessing)

public class DBManager {
    
    String url = "jdbc:derby://localhost:1527/WWTBAMDB;create=true";  //url of the DB host
    
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";   //your DB password
    
    Connection conn = null;
    
    public DBManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            
            System.out.println(url + "db connected");
            
//            Statement statement = conn.createStatement();
//            String tableName = "UserInfo";
//
//            if (!checkTableExisting(tableName)) {
//                statement.executeUpdate("CREATE TABLE " + tableName + " (user VARCHAR(12), score INT)");
//            }
//            
//            statement.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());

        }
    }

    public ResultSet queryDB(String sql) {
        Statement statement;
        ResultSet resultSet = null;

        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        
        return resultSet;
    }
    
    public void updateDB(String sql) {
        Statement statement;

        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  is there");
                    flag = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return flag;
    }
    
    public boolean checkUser(String username) {
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT USER FROM SCORES "
                    + "WHERE USER = '" + username + "'");
            if (rs.next()) {
                System.out.println("found user");
                return true;
            } else {
                System.out.println("user not found");
                return false;
            }

        } catch (SQLException ex) {
        }
        
        return false;
    }
    
    public ArrayList<User> getScores()
    {
        Statement statement;
        ArrayList<User> out = new ArrayList<>();
        
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM SCORES ORDER BY SCORE DESC");
            
            while (rs.next())
            {
                out.add(new User(rs.getString("USER"), rs.getInt("SCORE")));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        
        return out;
    }
    
    public void addScore(User u)
    {
        Statement statement;
        
        try {
            statement = conn.createStatement();
            if (checkUser(u.getUsername()))
            {
                statement.executeUpdate("UPDATE SCORES SET SCORE = " + u.getScore() + " WHERE USER = '" + u.getUsername() + "'");
            }
            else
            {
                statement.executeUpdate("INSERT INTO SCORES VALUES (" + u.getUsername() + ", " + u.getScore() + ")");
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    public HashMap<String, Double> getFriends() {
        HashMap<String, Double> out = new HashMap<>();
        Statement statement;
        
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM FRIENDS");
            
            while (rs.next())
            {
                out.put(rs.getString("NAME"), rs.getDouble("WEIGHT"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        
        return out;
    }
    
    public void addFriend(String name, double weight)
    {
        Statement statement;
        
        try {
            statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO FRIENDS VALUES (" + name + ", " + weight + ")");

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> out = new ArrayList<>();
        Statement statement;

        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM QUESTIONS");
            
            while (rs.next())
            {
                String prompt = rs.getString("PROMPT");
                int cAnswer = rs.getInt("ANSWER");
                String[] options = {rs.getString("OPTION1"), rs.getString("OPTION2"), rs.getString("OPTION3"), rs.getString("OPTION4")};
                out.add(new Question(prompt, cAnswer, options));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        
        return out;
    }
    
    public void addQuestion(Question q)
    {
        Statement statement;
        
        String sql = "INSERT INTO QUESTIONS VALUES (";
        sql += q.getPrompt();
        sql += ", " + q.getCorrectAnswer();
        for (String s : q.getAnswers())
        {
            sql += ", " + s;
        }
        sql += ")";
        
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    
}
