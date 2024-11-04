package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    //Allows program to search database for all messages
    public List<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            //SQL query
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //for each message found, add to messages list
            while(rs.next()){
                Message mess = new Message(rs.getInt("message_id"),
                                           rs.getInt("posted_by"),
                                           rs.getString("message_text"),
                                           rs.getLong("time_posted_epoch"));
                messages.add(mess);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //return all messages found
        return messages;
    }

    //Allows program to add messages to database
    public Message addMessage(Message mess){
        Connection conn = ConnectionUtil.getConnection();

        try {

            //SQL query
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Add variables from service method to sql query
            ps.setInt(1, mess.getPosted_by());
            ps.setString(2, mess.getMessage_text());
            ps.setLong(3, mess.getTime_posted_epoch());
            ps.executeUpdate();

            //if message is added, get message_id and return added message
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, mess.getPosted_by(), mess.getMessage_text(), mess.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if message was not added, return null
        return null;
    }

    //Search database for specific message with message_id
    public Message getMessageByID(int id){
        Connection conn = ConnectionUtil.getConnection();
        try{

            //SQL query
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //Addvariable from service method to query
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //if message is found, add to variable and return
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                              rs.getInt("posted_by"), 
                                              rs.getString("message_text"), 
                                              rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //if no message is found, return null
        return null;
    }

    //Allows system to delete messages from database
    public void deleteSingleMessage(int id){
        Connection conn = ConnectionUtil.getConnection();
        try {
            //SQL query
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            //Add variable from service method to query
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Allows system to update specific messages
    public void updateMessage(int messId, String text) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            //SQL query
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //Add variables from service method to query
            ps.setString(1, text);
            ps.setInt(2, messId);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Allows system to find messages from specific users
    public List<Message> getMessagesWithAccountID(int id){
        Connection conn = ConnectionUtil.getConnection();
        try {
            //initialize variable
            List<Message> messageList = new ArrayList<>();

            //SQL query
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            //add variable from service method to query
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            //if user is found, add each message to messageList
            while(rs.next()){
                Message mess = new Message(rs.getInt("message_id"), 
                                           rs.getInt("posted_by"), 
                                           rs.getString("message_text"), 
                                           rs.getLong("time_posted_epoch"));
                messageList.add(mess);
            }

            //return message list if messages are found
            return messageList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if no messages found, return null
        return null;
    }
}
