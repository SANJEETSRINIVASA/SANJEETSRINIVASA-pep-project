package Service;
import DAO.messagedao;
import Model.*;
import java.util.List;
public class MessageService {
    messagedao messagedao = new messagedao();
    public int CreateNewMessage_check(int posted_by,String message_text,long time_posted_epoch){
        if(message_text!=null && message_text.trim().length()!=0 && message_text.length()<=255){
            return messagedao.CreateNewMessage_dao(posted_by,message_text,time_posted_epoch);
        }
        else{
            return -1;
        }
    }

    public List<Message> getAllMessagesService(){
        return messagedao.getAllMessages_dao();
    }

    public Message GetOneMessageService(int message_id){
        return messagedao.GetOneMessage_dao(message_id);
    }

    public Message DeleteAMessageService(int message_id){
        return messagedao.DeleteAMessage_dao(message_id);
    }

    public Message UpdateMessageService(int message_id,String message_text){
        if(message_text!=null && message_text.trim().length()!=0 && message_text.length()<=255){
            return messagedao.UpdateMessage_dao(message_id, message_text);
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessagesService(int account_id){
        return messagedao.getAllMessages_dao(account_id);
    }
}
