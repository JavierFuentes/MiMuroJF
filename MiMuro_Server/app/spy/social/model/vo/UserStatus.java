package spy.social.model.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by javierfuentes on 16/08/14.
 */

@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "userStatus")
@javax.persistence.Entity
@JsonAutoDetect
public class UserStatus {

    @JsonProperty
    protected Long id;

    @JsonIgnore
    protected User user;

    @JsonProperty
    protected Date date = new Date(System.currentTimeMillis());

    @JsonProperty
    protected String text;

    @JsonProperty
    protected Integer commentsCounter = 0;

    @JsonProperty
    protected Integer likesCounter = 0;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    @JsonProperty
    public String getUserNick() {
        if (user == null)
            return null;

        return user.getNickname();
    }

    @Column(name = "date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Transient
    @JsonProperty
    public String getDateString() {
        if (date == null)
            return "";

        // Return ISO 8601 String format using Joda Time
        //return date.toLocaleString();
        DateTime jdt = new DateTime(date);
        return jdt.toString();
    }

    @Column(name = "text", nullable = false, length = 1024)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

///////////////////////////////////////////////////////////////////////////////////////
//   CALLEJON SIN SALIDA: No se pueden poner LAZY ni tampoco admite más de 1 EAGER   //
//   ¿What can we do to resolve this important problem? I don't know!                //
///////////////////////////////////////////////////////////////////////////////////////
//
//    @JsonIgnore
//    protected List<Comment> commentList;
//
//    @JsonIgnore
//    protected List<LikeEntry> likeEntryList;
//
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userStatus")
//    @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
//    public List<Comment> getCommentList() {
//        return commentList;
//    }
//
//    public void setCommentList(List<Comment> commentList) {
//        this.commentList = commentList;
//    }
//
//    @Transient
//    @JsonProperty
//    public Integer getCommentsCounter() {
//        return commentList.size();
//    }
//
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userStatus")
//    @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
//    public List<LikeEntry> getLikeEntryList() {
//        return likeEntryList;
//    }
//
//    public void setLikeEntryList(List<LikeEntry> likeEntryList) {
//        this.likeEntryList = likeEntryList;
//    }
//
//    @Transient
//    @JsonProperty
//    public Integer getLikesCounter() {
//        return likeEntryList.size();
//    }
///////////////////////////////////////////////////////////////////////////////////////

    // TIP: We use this Transient properties to easy include Lists in JSON response
    @JsonProperty
    @Transient
    protected List<Comment> commentList;

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @JsonProperty
    @Transient
    protected List<LikeEntry> likeEntryList;

    public void setLikeEntryList(List<LikeEntry> likeEntryList) {
        this.likeEntryList = likeEntryList;
    }
    // end TIP.

    @Column(name = "commentsCounter")
    public Integer getCommentsCounter() {
        return commentsCounter;
    }

    // Hibernate me obliga a establecer un setter... menos mal que me deja ponerlo private
    private void setCommentsCounter(Integer commentsCounter) {
        this.commentsCounter = commentsCounter;
    }

    public Integer incCommentsCounter() {
        return ++this.commentsCounter;
    }

    @Column(name = "likesCounter")
    public Integer getLikesCounter() {
        return likesCounter;
    }

    // Hibernate me obliga a establecer un setter... menos mal que me deja ponerlo private
    private void setLikesCounter(Integer likesCounter) {
        this.likesCounter = likesCounter;
    }

    public Integer incLikesCounter() {
        return ++this.likesCounter;
    }

    @JsonProperty
    @Transient
    public Integer getRelevanceIndicator() {
        return this.likesCounter + (this.commentsCounter * 2);
    }
}
