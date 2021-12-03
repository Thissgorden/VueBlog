package com.GDLearn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("m_role")
public class Role {

    private int id;

    private String rolename;

    private String code;

    private String remark;
}
