package inhatc.insecure.veganday.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @Column(unique = true)
    private String userId;
    
    private String userName;
    
    private String userEmail;
 
}