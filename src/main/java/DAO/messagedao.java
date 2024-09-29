package DAO;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
public class messagedao {

    public int CreateNewMessage_dao(int posted_by,String message_text,long time_posted_epoch){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select account_id from account where account_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()==true)
            {
                sql="insert into message(posted_by,message_text,time_posted_epoch) values(?,?,?)";
                //PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, posted_by);
                preparedStatement.setString(2, message_text);
                preparedStatement.setLong(3, time_posted_epoch);
                //ResultSet rs1 = preparedStatement.executeQuery();
                preparedStatement.executeUpdate();
                sql="select message_id from message where posted_by=? AND message_text=? AND time_posted_epoch=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, posted_by);
                preparedStatement.setString(2, message_text);
                preparedStatement.setLong(3, time_posted_epoch);
                rs = preparedStatement.executeQuery();
                rs.next();
                return rs.getInt(1);
            }
            else{
                return -1;
            }                
        
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return -1;
        }
    }
   

    public List<Message> getAllMessages_dao(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages_list = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message messages = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages_list.add(messages);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages_list;
    }

    public Message GetOneMessage_dao(int message_id){
       
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message messages = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return messages;
            }             
        
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Message DeleteAMessage_dao(int message_id){
       
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()==true)
            {      
            sql = "delete from message where message_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
            Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                    return message;       
            }  
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message UpdateMessage_dao(int message_id,String message_text){
        
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select message_id from message where message_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()==true){
                sql = "update message set message_text=? where message_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, message_text);
                preparedStatement.setInt(2, message_id);
                preparedStatement.executeUpdate();
                sql = "select * from message where message_id=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, message_id);
                rs = preparedStatement.executeQuery();
                rs.next();
            Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            return message;
                }                
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages_dao(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages_list = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message messages = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages_list.add(messages);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages_list;
    }
}


    




