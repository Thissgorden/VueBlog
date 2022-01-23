package com.GDLearn.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistryDto {
    @NotBlank(message = "邮箱不可为空")
    private String Email;
    @NotBlank
    private Long UserId;
    @NotBlank(message = "昵称不可为空")
    private String username;
    //private String 其他数据;
}
