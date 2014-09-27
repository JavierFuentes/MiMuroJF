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
@Table(name = "likeentry")
@Entity
@JsonAutoDetect
public class LikeEntry {

    @JsonProperty
    protected Long id;

    @JsonIgnore
    protected UserStatus userStatus;

    @JsonIgnore
    protected User user;

    @JsonProperty
    protected Date date = new Date(System.currentTimeMillis());

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

}
