package com.kj.kdove.commons.domain;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "k_dove_user")
public class KDoveUser implements Serializable {

    private static final long serialVersionUID = 6463814946309743100L;

    public KDoveUser() {
    }

    public KDoveUser(String username){
        this.username = username;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "`password`")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "ucode")
    private String ucode;

    @Column(name = "email")
    private String email;

    @Column(name = "headurl")
    private String headurl;

    @Column(name = "regdate")
    private Date regdate;

}