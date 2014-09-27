package spy.social.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import play.mvc.Controller;
import play.mvc.Result;
import spy.social.model.dao.LikeEntryDAO;
import spy.social.model.dao.StatusDAO;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.LikeEntry;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

//import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by javierfuentes on 16/08/14.
 */
@org.springframework.stereotype.Controller
public class LikesServices
        extends Controller {

    @Autowired
    @Qualifier(value = "target")
    protected UserDAO userDAO;

    @Autowired
    @Qualifier(value = "target")
    protected StatusDAO statusDAO;

    @Autowired
    @Qualifier(value = "target")
    protected LikeEntryDAO likeEntryDAO;

    public Result getLikesListByStatusId(Long statusId) throws JsonProcessingException {
//        Parece que findByExample no funciona bien... me devuelve todos los registros de la tabla
//        UserStatus userStatus = statusDAO.getById(statusId, false);
//        LikeEntry likeEntry = new LikeEntry();
//        likeEntry.setUserStatus(userStatus);
//        likeEntry.setDate(null);
//        List<LikeEntry> likeEntryList = likeEntryDAO.findByExample(likeEntry);

        UserStatus userStatus = statusDAO.getById(statusId, false);
        List<LikeEntry> likeEntryList = likeEntryDAO.getLikesListByStatus(userStatus);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(likeEntryList));
    }

    public Result getLikesListByNickName(String nick) throws JsonProcessingException {
        User user = userDAO.findByNickName(nick);

        List<LikeEntry> likeEntryList = likeEntryDAO.getLikesListByUser(user);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(likeEntryList));
    }

    public Result addLike() throws JsonProcessingException {
        JsonNode json = request().body().asJson();

        String nick = json.findPath("data_nick").asText();
        Long statusId = json.findPath("data_statusId").asLong();

        User user = userDAO.findByNickName(nick);
        UserStatus userStatus = statusDAO.getById(statusId, true);

        // Users can't like their own Status
        if (userStatus.getUser().getId().equals(user.getId())) {

            return badRequest("No se puede pulsar en Me Gusta sobre un Estado propio.");  // 302

        }

        ObjectMapper mapper = new ObjectMapper();

        // Only 1 Like per User+Status
        List<LikeEntry> likeEntryList = likeEntryDAO.getLikeEntryByUserAndStatus(user, userStatus);

        if (likeEntryList.size() == 0) {

            LikeEntry likeEntry = new LikeEntry();
            likeEntry.setUser(user);
            likeEntry.setUserStatus(userStatus);
//          likeEntry.setDate(new Date(System.currentTimeMillis()));  // La asigna Hibernate
            likeEntry = likeEntryDAO.makePersistent(likeEntry);

            // Update counter in UserStatus
            userStatus.incLikesCounter();
            statusDAO.makePersistent(userStatus);

            return ok(mapper.writeValueAsString(likeEntry));

        } else {

            return badRequest("El Usuario " + likeEntryList.get(0).getUserNick() +
                    " ya ha hecho click en Me Gusta para el Estado " + likeEntryList.get(0).getUserStatus().getId());  // 302

        }

    }
}
