package Controller;

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

        /*Allows Users to log into the system and access messages */
        /*app.post("/login", ctx -> {
         * 
         *}) */

        /*Allows users to register into the system */
        /*app.post("/register", ctx -> {
         * 
        *}) */

        /*Allows users to post messages through the system */
        /*app.post("/messages", ctx -> {
         * 
        *}) */

        /*Allows users to see all messages persisted in the database */
        /*app.get("/messages", ctx -> {
         * 
         *}); */

        /*Allows users to see a specific message using the message's internal id */
        /*app.get("/messages/{messageid}", ctx -> {
         * 
        *}) */

        /*Allows users to delete specific messages using the message's internal id */
        /*app.delete("/messages/{messageid}", ctx -> {
         * 
        *}) */

        /*Allows users to updates specific messages using the message's internal id */
        /*app.patch("/messages/{messageid}", ctx -> {
         * 
        *}) */

        /*Allows users to see specific messages from a specific user using the user's internal id */
        /*app.get("/accounts/{account_id}/messages", ctx -> {
         * 
        *}) */

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}