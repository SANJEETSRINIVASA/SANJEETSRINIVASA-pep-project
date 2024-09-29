package Service;

import DAO.accountdao;

public class AccountService {
    accountdao accountdao = new accountdao();
    public int UserRegistration_check(String username,String password){
        if(username!=null && username.trim().length()!=0 && (password!=null && password.length()>3)){
            return accountdao.UserRegistration_dao(username,password);
        }
        else{
            return -1;
        }
    }

    public int login_check(String username,String password){
        return accountdao.login_dao(username, password);
    }
}
