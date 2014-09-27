package spy.social.model.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by javierfuentes on 16/08/14.
 */

@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "comment")
@Entity
@JsonAutoDetect
public class Comment {

    @JsonProperty
    protected Long id;

    @JsonIgnore
    protected User author;

    @JsonIgnore
    protected UserStatus userStatus;

    @JsonProperty
    protected Date date = new Date(System.currentTimeMillis());

    @JsonProperty
    protected String text;

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
    @JoinColumn(name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Transient
    @JsonProperty
    public String getAuthorNick() {
        if (author == null)
            return null;

        return author.getNickname();
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "statusId")
    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Transient
    @JsonProperty
    public Long getStatusId() {
        if (userStatus == null)
            return null;

        return userStatus.getId();
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
}
