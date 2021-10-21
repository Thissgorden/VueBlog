package com.GDLearn.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String Username;
    private String avatar;
    private String email;
}
