package spy.social.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import play.mvc.Controller;
import play.mvc.Result;
import spy.social.model.dao.CommentDAO;
import spy.social.model.dao.LikeEntryDAO;
import spy.social.model.dao.StatusDAO;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.Comment;
import spy.social.model.vo.LikeEntry;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.Date;
import java.util.List;

//import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by javierfuentes on 16/08/14.
 */
@org.springframework.stereotype.Controller
public class StatusServices
        extends Controller {

    @Autowired
    @Qualifier(value = "target")
    protected UserDAO userDAO;

    @Autowired
    @Qualifier(value = "target")
    protected StatusDAO statusDAO;

    @Autowired
    @Qualifier(value = "target")
    protected CommentDAO commentDAO;

    @Autowired
    @Qualifier(value = "target")
    protected LikeEntryDAO likeEntryDAO;

    public Result getStatusList() throws JsonProcessingException {
        List<UserStatus> userStatusList = statusDAO.getAllStatus();

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(userStatusList));
    }

    public Result getUserStatusListByNickName(String nick) throws JsonProcessingException {
        User user = userDAO.findByNickName(nick);

        List<UserStatus> userStatusList = statusDAO.getAllUserStatus(user);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(userStatusList));
    }

//    public Result getAllStatusDetail_OLD(Long statusId) throws JsonProcessingException {
//        UserStatus userStatus = statusDAO.getById(statusId, false);
//
//        // Get all Comments filtered by status
//        List<Comment> commentList = commentDAO.getCommentListByStatus(userStatus);
//
//        // Get all Likes filtered by status
//        List<LikeEntry> likeEntryList = likeEntryDAO.getLikesListByStatus(userStatus);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        String strUserStatus = mapper.writeValueAsString(userStatus);
//        strUserStatus = "[{\"statusData\": [" + strUserStatus + "]},";
//
//        String strComments = mapper.writeValueAsString(commentList);
////            strComments = strComments.substring(0, strComments.length() - 1);  // Quitamos los ]
//        strComments = "{\"commentsData\": [" + strComments + "]},";
//
//        String strLikes = mapper.writeValueAsString(likeEntryList);
////        strLikes = strLikes.substring(0, strLikes.length() - 1);  // Quitamos los ]
//        strLikes = "{\"likesData\": " + strLikes + "}]";
//
//        return ok(strUserStatus + strComments + strLikes);
//    }

    public Result getAllStatusDetail(Long statusId) throws JsonProcessingException {
        UserStatus userStatus = statusDAO.getById(statusId, false);

        // Get all Comments filtered by status
        List<Comment> commentList = commentDAO.getCommentListByStatus(userStatus);
        userStatus.setCommentList(commentList);

        // Get all Likes filtered by status
        List<LikeEntry> likeEntryList = likeEntryDAO.getLikesListByStatus(userStatus);
        userStatus.setLikeEntryList(likeEntryList);

        ObjectMapper mapper = new ObjectMapper();

        return ok(mapper.writeValueAsString(userStatus));
    }

    public Result addStatus() throws JsonProcessingException {
        JsonNode json = request().body().asJson();

        String nick = json.findPath("data_nick").asText();
        String text = json.findPath("data_text").asText();

        User user = userDAO.findByNickName(nick);

        UserStatus userStatus = new UserStatus();
        userStatus.setUser(user);
        userStatus.setText(text);
        userStatus.setDate(new Date(System.currentTimeMillis()));
        userStatus = statusDAO.makePersistent(userStatus);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(userStatus));
    }


//    @BodyParser.Of(BodyParser.Json.class)
//    public Result saveStatus() throws IOException {
//        String jsonResponse = null;
//
//        JsonNode json = request().body().asJson();
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//
//            com.grupoasv.model.vo.UserStatus miObjeto = mapper.readValue(json.toString(), com.grupoasv.model.vo.UserStatus.class);
//            statusDAO.makePersistent(miObjeto);
//
//            return ok(miObjeto.getId().toString());
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return badRequest("Esto ha cascao");
//        }
//    }

}
