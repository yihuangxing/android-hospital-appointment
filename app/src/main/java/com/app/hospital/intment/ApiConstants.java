package com.app.hospital.intment;

import com.app.hospital.intment.entity.UserInfo;

/**
 * desc   :
 */
public class ApiConstants {

    public final static String BASE_URL = "http://192.168.14.13:8080";

    public static UserInfo sUserInfo;

    public static UserInfo getUserInfo() {
        return sUserInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        sUserInfo = userInfo;
    }


    //注册
    public final static String REGISTER_URL = BASE_URL + "/user/register";

    //登录
    public final static String LOGIN_URL = BASE_URL + "/user/login";

    //修改密码
    public final static String EDIT_URL = BASE_URL + "/user/edit";

    //获取科室列表
    public final static String DEPARTMENT_URL = BASE_URL + "/user/department";


    //获取科室下的医生列表
    public final static String DOCTORLIST_URL = BASE_URL + "/user/doctorList";

    //获取当前医生的排班列表
    public final static String SCHEDULLIST_URL = BASE_URL + "/user/schedulList";
}
