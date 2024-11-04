package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    //initialize dao variable
    private MessageDAO mesDAO;

    //blank constructor
    public MessageService(){
        mesDAO = new MessageDAO();
    }

    //constructor with already initialized dao
    public MessageService(MessageDAO mesDAO){
        this.mesDAO = mesDAO;
    }

    //Service method to call dao method getAllMessages
    public List<Message> getAllMessages(){
        return mesDAO.getAllMessages();
    }
    
    //Service method to call dao method addMessage
    public Message addMessage(Message mes){
        return mesDAO.addMessage(mes);
    }
    
    //Service method to call dao method getMessageByID
    public Message getSingleMessage(int id){
        return mesDAO.getMessageByID(id);
    }
    
    //Service method to call dao method deleteSingleMessage
    public void deleteSingleMessage(int id){
       mesDAO.deleteSingleMessage(id);
    }
    
    //Service method to call dao method updateMessage
    public Message updateMessage(int id, String text){
        //if message is found, call dao updateMessage and return updated message. Else, return null
        if(mesDAO.getMessageByID(id) != null){
            mesDAO.updateMessage(id, text);
            return mesDAO.getMessageByID(id);
        }
        else {
            return null;
        }
    }
    
    //Service method to call dao method getMessagesWithAccountID
    public List<Message> getMessagesWithAccountID(int accID){
        return mesDAO.getMessagesWithAccountID(accID);
    }
}
