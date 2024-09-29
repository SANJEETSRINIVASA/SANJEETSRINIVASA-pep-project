package DAO;
import Util.ConnectionUtil;
import java.sql.*;
public class accountdao {

    public int UserRegistration_dao(String username, String password){
        //if(username==null || (password==null || password.length()<5)) #javalin


        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select username from account where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()==false)
            {
                sql="insert into account(username,password) values(?,?)";
                //PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                //ResultSet rs1 = preparedStatement.executeQuery();
                preparedStatement.executeUpdate();
                sql="select account_id from account where username=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
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
    
public int login_dao(String username, String password){
    Connection connection = ConnectionUtil.getConnection();
    try {
        //Write SQL logic here
        String sql = "select account_id from account where username=? and password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()==true)
            return rs.getInt(1);
        else
            return -1;             
        }

    catch(SQLException e){
        System.out.println(e.getMessage());
        return -1;
    }
}
}

