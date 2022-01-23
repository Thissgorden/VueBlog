package com.GDLearn.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistryDto {
    @NotBlank(message = "邮箱不可为空")
    private String Email;

    //private String 其他数据;
}
