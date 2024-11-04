package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    //Allows program to connect to database to retrieve all registered users
    public List<Account> getAllAccounts() {
        Connection conn = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try {
            //SQL query
            String sql = "SELECT * FROM account";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //for each user found, adds them to a list that will be returned to service method
            while(rs.next()) {
                Account acc = new Account(rs.getInt("account_id"), 
                                          rs.getString("username"), 
                                          rs.getString("password"));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    //Allows program to connect to database to add a new user to the system
    public Account addAccount(Account acc) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            //SQL query
            String sql = "INSERT INTO account(username, password) VALUES(?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //add variables passed from service method
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.executeUpdate();

            //If user was added, get account_id and return values for new account
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, acc.getUsername(), acc.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if user is not added, return null
        return null;
    }

    //Allows program to look through database for a specific user
    public Account getSingleAccount(Account acc){
        Connection conn = ConnectionUtil.getConnection();
        try {

            //SQL query
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //add variable from variable passed from service method
            ps.setString(1, acc.getUsername());
            ResultSet rs = ps.executeQuery();

            //if account is found in database, add to variable for return
            while(rs.next()){
                Account foundAcc = new Account(rs.getInt("account_id"),
                                               rs.getString("username"),
                                               rs.getString("password"));
                return foundAcc;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if no account found, return null
        return null;
    }

    public Account getSingleAccountByID(int id){
        Connection conn = ConnectionUtil.getConnection();
        try {

            //SQL query
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //add variable passed from service method
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            //if account_id is found, add from database to variable to be returned to service method
            while(rs.next()){
                Account foundAcc = new Account(rs.getInt("account_id"), 
                                               rs.getString("username"),
                                               rs.getString("password"));
                return foundAcc;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if no account is found, return null
        return null;
    }
}
