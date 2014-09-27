package spy.social.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import play.mvc.Controller;
import play.mvc.Result;
import spy.social.model.dao.CommentDAO;
import spy.social.model.dao.StatusDAO;
import spy.social.model.dao.UserDAO;
import spy.social.model.vo.Comment;
import spy.social.model.vo.User;
import spy.social.model.vo.UserStatus;

import java.util.List;

//import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by javierfuentes on 16/08/14.
 */
@org.springframework.stereotype.Controller
public class CommentServices
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

    public Result getCommentListByStatusId(Long statusId) throws JsonProcessingException {
        UserStatus userStatus = statusDAO.getById(statusId, false);
        List<Comment> commentList = commentDAO.getCommentListByStatus(userStatus);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(commentList));
    }

    public Result getCommentsByNickName(String nick) throws JsonProcessingException {
        User author = userDAO.findByNickName(nick);

        List<Comment> commentList = commentDAO.getCommentListByUser(author);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(commentList));
    }

    public Result addComment() throws JsonProcessingException {
        JsonNode json = request().body().asJson();

        String nick = json.findPath("data_nick").asText();
        Long statusId = json.findPath("data_statusId").asLong();
        String commentText = json.findPath("data_text").asText();

        User user = userDAO.findByNickName(nick);
        UserStatus userStatus = statusDAO.getById(statusId, true);

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setUserStatus(userStatus);
        comment.setText(commentText);
//        comment.setDate(new Date(System.currentTimeMillis()));  // La asigna Hibernate
        comment = commentDAO.makePersistent(comment);

        // Update counter in UserStatus
        userStatus.incCommentsCounter();
        statusDAO.makePersistent(userStatus);

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(comment));
    }
}
