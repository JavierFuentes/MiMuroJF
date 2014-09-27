package spy.social.model.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
//import java.util.UUID;

/**
 * Created by javierfuentes on 16/08/14.
 */

@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "user_")  // Postgresql añadimos _ al final del nombre ... con MySQL no es necesario
@javax.persistence.Entity
@JsonAutoDetect
public class User {

    @JsonProperty
    protected String id;

    @JsonIgnore
    protected String psw;

    @JsonProperty(value = "nick")
    protected String nickname;

    @JsonIgnore
    protected Boolean active = true;

    // Si el ID lo generasemos aleatoriamente como un UUID, usariamos esto
//    public User() {
//        this.id = UUID.randomUUID().toString();
//    }

    @Id
    @Column(name = "id", length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "psw", length = 10, nullable = false)
    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Column(name = "nick", length = 20, nullable = false, unique = true)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
