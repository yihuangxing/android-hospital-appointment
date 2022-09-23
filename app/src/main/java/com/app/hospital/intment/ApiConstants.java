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

    //删除科室
    public final static String DELDEPARTMENT_URL = BASE_URL + "/user/delDepartment";

    //添加科室
    public final static String ADDDEPARTMENT_URL = BASE_URL + "/user/addDepartment";

    //修改科室
    public final static String UPDATEDEPARTMENT_URL = BASE_URL + "/user/updateDepartment";


    //获取科室下的医生列表
    public final static String DOCTORLIST_URL = BASE_URL + "/user/doctorList";

    //删除医生
    public final static String DEL_DOCTOR_URL = BASE_URL + "/user/delDoctorList";

    //添加医生
    public final static String ADD_DOCTOR_URL = BASE_URL + "/user/AddDoctorList";

    //修改医生
    public final static String UPDATE_DOCTOR_URL = BASE_URL + "/user/updateDoctorList";

    //通过doctor_name 获取当前医生的排班列表
    public final static String SCHEDULLIST_URL = BASE_URL + "/user/schedulList";

    //根据医生添加排班信息
    public final static String ADD_SCHEDUL_URL = BASE_URL + "/user/AddSchedulList";

    //修改排班
    public final static String UPDATE_SCHEDUL_URL = BASE_URL + "/user/updateSchedulList";


    //收藏
    public final static String COLLECTION_URL = BASE_URL + "/user/collection";

    //查询收藏列表
    public final static String QUERY_COLLECTION_URL = BASE_URL + "/user/queryCollectionList";

    //检查是否收藏
    public final static String CHECKCOLLECTION_URL = BASE_URL + "/user/checkCollection";

    //获取注册医生数据
    public final static String REGISTER_DOCTOR_URL = BASE_URL + "/user/registerDoctorList";


    //预约 下单
    public final static String CREATE_ORDER_URL = BASE_URL + "/user/createOrder";

    //查询我的预约
    public final static String QUERY_ORDER_LIST_URL = BASE_URL + "/user/queryListOrder";


    //支付
    public final static String PAY_ORDER_URL = BASE_URL + "/user/payOrder";

    //删除预约记录
    public final static String DEL_ORDER_URL = BASE_URL + "/user/delOrder";
}
