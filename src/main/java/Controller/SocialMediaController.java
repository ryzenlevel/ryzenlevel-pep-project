package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accService;
    MessageService mesService;

    public SocialMediaController(){
        this.accService = new AccountService();
        this.mesService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getSpecificMessageHandler);
        app.delete("/messages/{message_id}", this::deleteSpecificMessageHandler);
        app.patch("/messages/{message_id}", this::updateSpecificMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    //Allows users to register to use the system.
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        //initialize variable
        ObjectMapper om = new ObjectMapper();
        Account na = om.readValue(ctx.body(), Account.class);
        //If username is blank, throw status code 400
        if(na.getUsername() == ""){
            ctx.status(400);
        }
        else {
            //If password is not null and greater than or equal to 4 characters, then enter if, else return status 400
            if(na.getPassword() != null && na.getPassword().length() >= 4){
                Account addedAccount = accService.addAccount(na);
                //If account is added, print JSON format of response, else return status 400
                if(addedAccount != null) {
                    ctx.json(om.writeValueAsString(addedAccount));
                }
                else {
                    ctx.status(400);
                }
            }
            else {
                ctx.status(400);
            }
        }
    }

    //Allows users to log into the system
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        //initialize variable
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        //If username is blank, return status 401 (unauthorized)
        if(acc.getUsername() == ""){
            ctx.status(401);
        }
        else {
            Account dAcc = accService.getSingleAccount(acc);
            //Account is found, else return status 401
            if(dAcc != null) {
                //If password is incorrect, return status 401
                if(!acc.getPassword().equals(dAcc.getPassword())){
                    ctx.status(401);
                }
                //If password is correct, return JSON and status 200;
                else{
                    ctx.json(dAcc);
                    ctx.status(200);
                }
            }
            else{
                ctx.status(401);
            }
        }
    }

    //Allows users to create messages
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        //Initialize variables
        ObjectMapper om = new ObjectMapper();
        Message newMess = om.readValue(ctx.body(), Message.class);
        List<Account> acc = accService.getAllAccounts();
        try {
            //If message_text is not blank, enter. Else return status 400
            if(newMess.getMessage_text() != ""){
                //if message is under 255 characters, enter. Else return status 400
                if(newMess.getMessage_text().length() < 255){
                    //check if account is in the database
                    for(Account accCheck : acc){
                        //if account_id matches logged in user, enter, else return status 400
                        if(newMess.getPosted_by() == accCheck.getAccount_id()){
                            //persists message in database
                            Message addedMess = mesService.addMessage(newMess);
                            //if message returns response, print JSON and return status 200. Else return status 400
                            if(addedMess != null){
                                ctx.json(addedMess);
                                ctx.status(200);
                                break;
                            }
                            else{
                                ctx.status(400);
                            }
                        }
                        else{
                            ctx.status(400);
                        }
                    }
                }
                else{
                    ctx.status(400);
                }
            }
            else{
                ctx.status(400);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Returns all messages stored in database in JSON format
    private void getMessageHandler(Context ctx) {
        List<Message> message = mesService.getAllMessages();
        ctx.json(message);
    } 

    //Returns message from database with message_id
    private void getSpecificMessageHandler(Context ctx) {
        //initialize variable
        Message mes = mesService.getSingleMessage(Integer.parseInt(ctx.pathParam("message_id")));
        //If message is found, return JSON and status 200. Else, still return 200.
        if(mes != null){
            ctx.status(200);
            ctx.json(mes);
        }
        else {
            ctx.status(200);
        }
    }

    //Deletes specific message with message_id
    private void deleteSpecificMessageHandler(Context ctx) throws JsonProcessingException {
        //initialize variable
        Message deleteMes = mesService.getSingleMessage(Integer.parseInt(ctx.pathParam("message_id")));
        //If variable comes back with no message found via message_id. Else, trigger delete method and return JSON and status 200
        if(deleteMes == null){
            ctx.status(200);
        }
        else{
            mesService.deleteSingleMessage(Integer.parseInt(ctx.pathParam("message_id")));
            ctx.status(200);
            ctx.json(deleteMes);
        }

    }

    //Allows user to update specific message with message_id
    private void updateSpecificMessageHandler(Context ctx) throws JsonProcessingException {
        //initialize variables
        ObjectMapper om = new ObjectMapper();
        Message newMes = om.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        List<Message> mes = mesService.getAllMessages();
        Message updatedMessage = null;
        
        //check for message in database
        for(Message mesCheck : mes){
            //if message is found, enter. Else return status 400
            if(message_id == mesCheck.getMessage_id()){
                //if message_text is not blank, enter. Else, return status 400 
                if(newMes.getMessage_text() != ""){
                    //if message_text is under 255 characters, enter. Else, return status 400
                    if(newMes.getMessage_text().length() < 255){
                        //update message and return to variable. Print vis JSON and return status 200
                        updatedMessage = mesService.updateMessage(message_id, newMes.getMessage_text());
                        ctx.json(om.writeValueAsString(updatedMessage));
                        ctx.status(200);
                    }
                    else{
                        ctx.status(400);
                    }
                }
            }
        }
        //if variable is null, throw status 400.
        if(updatedMessage == null){
            ctx.status(400);
        }        
    }
    
    //Allows user to find all messages from one user
    private void getAccountMessageHandler(Context ctx) throws JsonProcessingException {
        try {
            //initialize variables
            int accID = Integer.parseInt(ctx.pathParam("account_id"));
            List<Message> messages = mesService.getMessagesWithAccountID(accID);

            //if messages are found, print JSON and return status 200. Else, return status 200
            if(messages != null){
                ctx.json(messages);
            }
            ctx.status(200);
        } catch (Exception e) {
            e.printStackTrace();
        }      
    }
}