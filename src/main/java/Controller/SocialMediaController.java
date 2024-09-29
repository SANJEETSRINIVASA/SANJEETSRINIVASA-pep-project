package Controller;
import Model.*;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    AccountService accountservice = new AccountService();
    MessageService messageservice = new MessageService();
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        ObjectMapper om = new ObjectMapper();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", ctx -> {
            String jsonString = ctx.body();
            Account a = om.readValue(jsonString, Account.class);
            int r = accountservice.UserRegistration_check(a.getUsername(),a.getPassword());
            if(r>0)
            {
                ctx.status(200);
                a.setAccount_id(r);
                ctx.json(a);    
            }
            else{
                ctx.status(400);
            } 
        });

        app.post("/login", ctx -> {
            String jsonString = ctx.body();
            Account a = om.readValue(jsonString, Account.class);
            int r = accountservice.login_check(a.getUsername(), a.getPassword());
            if(r>0)
            {
                ctx.status(200);
                a.setAccount_id(r);
                ctx.json(a);    
            }
            else{
                ctx.status(401);
            }   
        });

        app.post("/messages", ctx -> {
            String jsonString = ctx.body();
            Message m = om.readValue(jsonString, Message.class);
            int r = messageservice.CreateNewMessage_check(m.getPosted_by(), m.getMessage_text(), m.getTime_posted_epoch());
            if(r>0)
            {
                ctx.status(200);
                m.setMessage_id(r);
                ctx.json(m);    
            }
            else{
                ctx.status(400);
            }   
        });

        app.get("/messages", ctx -> {
            ctx.status(200);
            ctx.json(messageservice.getAllMessagesService()); //ctx.result(om.writeValueAsString(messageservice.getAllMessagesService()));
        });

        app.get("/messages/{message_id}", ctx -> {
            int x;
            String m_id = ctx.pathParam("message_id");
            try{
                x = Integer.valueOf(m_id);
            }
            catch(Exception e){
                ctx.status(400);
                return;
            }
            ctx.status(200);
            //ctx.json(messageservice.GetOneMessageService(x));
            Message m = messageservice.GetOneMessageService(x);
            if(m!=null)
                ctx.json(m);
            else
                ctx.body();
        });

        app.delete("/messages/{message_id}",ctx -> {
            int x;
            String m_id = ctx.pathParam("message_id");
            try{
                x = Integer.valueOf(m_id);
            }
            catch(Exception e){
                ctx.status(400);
                return;
            }
            ctx.status(200);
            //ctx.json(messageservice.DeleteAMessageService(x));
            Message m = messageservice.DeleteAMessageService(x);
            if(m!=null)
                ctx.json(m);
            else
                ctx.body();
        });

        app.patch("/messages/{message_id}",ctx -> {
            String jsonString = ctx.body();
            Message m = om.readValue(jsonString, Message.class);
            int x;
            String m_id = ctx.pathParam("message_id");
            try{
                x = Integer.valueOf(m_id);
            }
            catch(Exception e){
                ctx.status(400);
                return;
            }
            m = messageservice.UpdateMessageService(x, m.getMessage_text());
            if(m!=null){
                ctx.status(200);
                ctx.json(m);
            }
            else{
                ctx.status(400);
            }    
        });

        app.get("/accounts/{accounts_id}/messages", ctx -> {
            int x;
            String a_id = ctx.pathParam("accounts_id");
            try{
                x = Integer.valueOf(a_id);
            }
            catch(Exception e){
                ctx.status(400);
                return;
            }
            ctx.status(200);
            ctx.json(messageservice.getAllMessagesService(x));
        });

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