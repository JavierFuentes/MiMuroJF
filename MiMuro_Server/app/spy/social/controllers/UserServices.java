package spy.social.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import play.mvc.Controller;
import play.mvc.Result;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.User;

import java.util.List;

//import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by javierfuentes on 16/08/14.
 */
@org.springframework.stereotype.Controller
public class UserServices
        extends Controller {

    @Autowired
    @Qualifier(value = "target")
    protected UserDAO userDAO;

    public Result getUserList() throws JsonProcessingException {
        List<User> userList = userDAO.getAllUsers(true);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(userList));
    }

    public Result checkCredentials() throws JsonProcessingException {
        JsonNode json = request().body().asJson();

        String userId = json.findPath("data_mail").asText();
        String pass = json.findPath("data_pass").asText();

        Boolean credentialsOK = userDAO.checkCredentials(userId, pass);

        if (!credentialsOK) {
            return unauthorized("Usuario o Password incorrectos."); // 401
        }

        User user = userDAO.getById(userId, false);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(user)); // 200
    }

    public Result addUser() throws JsonProcessingException {
        JsonNode json = request().body().asJson();

////////////////// Pruebas de envío y recepción de JSON complejos
//        JsonNode data = json.findPath("data");
//            String name = data.findPath("name").asText();
//            JsonNode document = data.findPath("document");
//                long number = document.findPath("number").asLong();
//                String letter = document.findPath("letter").asText();
//                ArrayNode array =(ArrayNode) data.findPath("array");
//                for(JsonNode node : array) {
//                    String first = node.findPath("first").asText();
//                    String second = node.findPath("second").asText();
//                    // nuestro código...
//                }
////////////////// fin. Pruebas

        String userId = json.findPath("data_mail").asText();
        String nick = json.findPath("data_nick").asText();
        String pass = json.findPath("data_pass").asText();

        User user = userDAO.getById(userId, false);

        if ((user != null) && (!user.getPsw().equals(pass))) {

            return badRequest("El Usuario " + userId + " ya existe"); // 400

        } else {

            if (user == null)
                user = new User();

            user.setId(userId);
            user.setPsw(pass);
            user.setNickname(nick);

            user = userDAO.makePersistent(user); // SaveOrUpdate() !!!

            ObjectMapper mapper = new ObjectMapper();
            return created(mapper.writeValueAsString(user)); // 201

        }
    }

}
