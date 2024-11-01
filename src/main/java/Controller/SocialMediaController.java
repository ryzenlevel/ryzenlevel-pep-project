package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        //app.post("/register", this::postRegisterHandler);
        //app.post("/login", this::postLoginHandler);
        //app.post("/messages", this::postMessageHandler);
        //app.get("/messages", this::getMessageHandler);
        //app.get("/messages/{message_id}", this::getSpecificMessageHandler);
        //app.delete("/messages/{message_id}", this::deleteSpecificMessageHandler);
        //app.patch("/messages/{message_id}", this::updateSpecificMessageHandler);
        //app.get("/accounts/{account_id}/messages", this::getAccountMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /*private Account postRegisterHandler(Context ctx) throws JsonProcessingException {

    }*/

    /*private Account postLoginHandler(Context ctx) throws JsonProcessingException {
     
    } */

    /*private Message postMessageHandler(Context ctx) throws JsonProcessingException {
     
    } */

    /*private void getMessageHandler(Context ctx) {
     
    } */

    /*private void getSpecificMessageHandler(Context ctx) {
     
    } */

    /*private Message deleteSpecificMessageHandler(Context ctx) {
     
    } */

    /*private Message updateSpecificMessageHandler(Context ctx) throws JsonProcessingException {
     
    } */

    /*private void getAccountMessageHandler(Context ctx) {
    
    } */


}