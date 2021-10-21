package com.GDLearn.util;

import com.GDLearn.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils {

    public static AccountProfile getProfile(){
        return (AccountProfile)SecurityUtils.getSubject().getPrincipal();
    }
}
