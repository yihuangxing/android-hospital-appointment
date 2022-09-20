package com.app.hospital.intment.activity;


import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.SchedulListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.entity.SchedulInfo;
import com.app.hospital.intment.entity.SchedulInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GlideEngine;
import com.app.hospital.intment.utils.GsonUtils;
import com.lzy.okgo.OkGo;


/**
 * 排班
 */
public class DoctorDetailActivity extends BaseActivity {
    private DoctorInfo info;

    private TextView doctor_name;
    private TextView depart_name;
    private TextView good_at;

    private AppCompatImageView collection,doctor_avatar;


    private RecyclerView recyclerView;
    private SchedulListAdapter mSchedulListAdapter;

    private boolean is_first;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_detail;
    }

    @Override
    protected void initView() {
        info = (DoctorInfo) getIntent().getSerializableExtra("info");

        doctor_name = findViewById(R.id.doctor_name);
        depart_name = findViewById(R.id.depart_name);
        good_at = findViewById(R.id.good_at);
        recyclerView = findViewById(R.id.recyclerView);
        collection = findViewById(R.id.collection);
        doctor_avatar = findViewById(R.id.doctor_avatar);

        if (null != info) {
            doctor_name.setText(info.getDoctor_name());
            depart_name.setText("科室：" + info.getDepart_name());
            good_at.setText("擅长：" + info.getGood_at());
            GlideEngine.createGlideEngine().loadGridImage(this,info.getDoctor_avatar(),doctor_avatar);
        }

    }

    @Override
    protected void setListener() {
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != info) {
                    collection(info);
                }
            }
        });

    }

    @Override
    protected void initData() {

        mSchedulListAdapter = new SchedulListAdapter();

        mSchedulListAdapter.setSchedulListener(new SchedulListAdapter.SchedulListener() {
            @Override
            public void pm(SchedulInfo schedulInfo) {
                if (schedulInfo.getPm_state() == 0) {

                } else {
                    showToast("预约已满，请另外选择");
                }
            }

            @Override
            public void am(SchedulInfo schedulInfo) {
                if (schedulInfo.getAm_state() == 0) {

                } else {
                    showToast("预约已满，请另外选择");
                }
            }
        });
        recyclerView.setAdapter(mSchedulListAdapter);

        if (null != info) {
            getSchedulListData(info.getDoctor_name());
            checkCollection(info.getDoctor_name());
        }

    }

    /**
     * 获取排班数据
     */

    private void getSchedulListData(String doctor_name) {
        OkGo.<String>get(ApiConstants.SCHEDULLIST_URL)
                .params("doctor_name", doctor_name)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        SchedulInfoList schedulInfoList = GsonUtils.parseJson(response, SchedulInfoList.class);
                        mSchedulListAdapter.setList(schedulInfoList.getList());

                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }

    /***
     * 点击收藏
     */
    public void collection(DoctorInfo doctorInfo) {
        OkGo.<String>get(ApiConstants.COLLECTION_URL)
                .params("doctor_name", doctorInfo.getDoctor_name())
                .params("depart_name", doctorInfo.getDepart_name())
                .params("good_at", doctorInfo.getGood_at())
                .params("doctor_avatar", doctorInfo.getDoctor_avatar())
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        if (msg.contains("收藏成功")) {
                            collection.setImageResource(R.mipmap.ic_coll);
                        } else {
                            collection.setImageResource(R.mipmap.ic_coll_normal);
                        }
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });

    }

    public void checkCollection(String doctor_name) {
        OkGo.<String>get(ApiConstants.CHECKCOLLECTION_URL)
                .params("doctor_name", doctor_name)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        if (msg.contains("收藏成功")) {
                            collection.setImageResource(R.mipmap.ic_coll);
                        } else {
                            collection.setImageResource(R.mipmap.ic_coll_normal);
                        }
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });

    }
}