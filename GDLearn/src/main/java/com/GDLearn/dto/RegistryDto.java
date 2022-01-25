package com.GDLearn.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistryDto {
    @NotBlank(message = "邮箱不可为空")
    private String email;//todo 需要正则验证

    private Long UserId;
    @NotBlank(message = "昵称不可为空")
    private String username;//需要正则验证

    @NotBlank(message = "密码不可为空")
    private String password;
    //private String 其他数据;

    @Override
    public String toString() {
        return "RegistryDto{" +
                "Email='" + email + '\'' +
                ", UserId=" + UserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
