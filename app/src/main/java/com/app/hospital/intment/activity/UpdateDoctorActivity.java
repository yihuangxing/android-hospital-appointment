package com.app.hospital.intment.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GlideEngine;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.UriToFileTransformEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.SandboxTransformUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.ArrayList;


/**
 * 修改和添加医生数据
 */
public class UpdateDoctorActivity extends BaseActivity {
    private TextView depart_name;
    private TextView doctor_name;
    private EditText good_at;
    private Button update;
    private CardView cardView;
    private ImageView doctor_avatar;
    private DoctorInfo doctorInfo;
    private String doctor_avatar_path;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_doctor;
    }

    @Override
    protected void initView() {
        depart_name = findViewById(R.id.depart_name);
        doctor_name = findViewById(R.id.doctor_name);
        good_at = findViewById(R.id.good_at);
        update = findViewById(R.id.update);
        doctor_avatar = findViewById(R.id.doctor_avatar);
        cardView = findViewById(R.id.cardView);

    }

    @Override
    protected void setListener() {

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctor_name_str = doctor_name.getText().toString().trim();
                String depart_name_str = depart_name.getText().toString().trim();
                String good_at_str = good_at.getText().toString().trim();

                if (TextUtils.isEmpty(doctor_name_str) || TextUtils.isEmpty(depart_name_str) || TextUtils.isEmpty(good_at_str)) {
                    showToast("请完善医生信息");
                } else {

                    if (doctorInfo != null) {
                        updateDoctorInfo(doctorInfo.getUid(), doctor_name_str, depart_name_str, good_at_str, doctor_avatar_path);
                    } else {
                        addDoctorInfo(doctor_name_str, depart_name_str, good_at_str, doctor_avatar_path);
                    }
                }


            }
        });

        depart_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateDoctorActivity.this, SelectDepartActivity.class);
                startActivityForResult(intent, 3000);
            }
        });

        doctor_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(UpdateDoctorActivity.this)
                        .openGallery(SelectMimeType.ofImage())
                        .setImageEngine(GlideEngine.createGlideEngine())
                        .setSelectionMode(SelectModeConfig.SINGLE)
                        .setSandboxFileEngine(new UriToFileTransformEngine() {
                            @Override
                            public void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call) {
                                if (call != null) {
                                    String sandboxPath = SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType);
                                    call.onCallback(srcPath, sandboxPath);
                                }
                            }
                        })
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(ArrayList<LocalMedia> result) {
                                LocalMedia localMedia = result.get(0);
                                if (null != localMedia) {
                                    doctor_avatar_path = localMedia.getAvailablePath();
                                    GlideEngine.createGlideEngine().loadGridImage(UpdateDoctorActivity.this, doctor_avatar_path, doctor_avatar);
                                    Log.d("----------------", "onResult: " + doctor_avatar_path);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });

        //添加排班
        findViewById(R.id.add_schedul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateDoctorActivity.this, SchedulListActivity.class);
                intent.putExtra("doctorInfo", doctorInfo);
                startActivity(intent);
            }
        });


        //选择医生
        doctor_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateDoctorActivity.this, SelectDoctorActivity.class);
                startActivityForResult(intent, 5000);
            }
        });

    }

    /***
     * 修改医生信息
     */
    private void updateDoctorInfo(int uid, String doctor_name_str, String depart_name_str, String good_at_str, String doctor_avatar) {

        PostRequest<String> post = OkGo.<String>post(ApiConstants.UPDATE_DOCTOR_URL);
        post.params("uid", uid);
        post.params("depart_name", depart_name_str);
        post.params("doctor_name", doctor_name_str);
        post.params("good_at", good_at_str);
        if (!TextUtils.isEmpty(doctor_avatar)) {
            post.params("file", new File(doctor_avatar));
        } else {
            post.params("doctor_avatar", doctorInfo.getDoctor_avatar());
        }
        post.execute(new HttpStringCallback(this) {
            @Override
            protected void onSuccess(String msg, String response) {
                showToast(msg);
                setResult(2000);
                finish();
            }

            @Override
            protected void onError(String response) {
                showToast(response);
            }
        });

    }


    /***
     * 添加医生信息
     */
    private void addDoctorInfo(String doctor_name_str, String depart_name_str, String good_at_str, String doctor_avatar) {
        PostRequest<String> post = OkGo.<String>post(ApiConstants.ADD_DOCTOR_URL);
        post.params("depart_name", depart_name_str);
        post.params("doctor_name", doctor_name_str);
        post.params("good_at", good_at_str);
        if (!TextUtils.isEmpty(doctor_avatar)) {
            post.params("file", new File(doctor_avatar));
        }
        post.execute(new HttpStringCallback(this) {
            @Override
            protected void onSuccess(String msg, String response) {
                showToast(msg);
                setResult(2000);
                finish();
            }

            @Override
            protected void onError(String response) {
                showToast(response);
            }
        });

    }

    @Override
    protected void initData() {

        String title = getIntent().getStringExtra("title");
        doctorInfo = (DoctorInfo) getIntent().getSerializableExtra("doctorInfo");
        toolbar.setTitle(title);
        if (doctorInfo != null) {
            depart_name.setText(doctorInfo.getDepart_name());
            doctor_name.setText(doctorInfo.getDoctor_name());
            good_at.setText(doctorInfo.getGood_at());
            update.setText("修改");
            GlideEngine.createGlideEngine().loadGridImage(UpdateDoctorActivity.this, doctorInfo.getDoctor_avatar(), doctor_avatar);
            cardView.setVisibility(View.VISIBLE);
        } else {
            update.setText("添加");
            cardView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3000 && null != data) {
            String departName = data.getStringExtra("depart_name");
            depart_name.setText(departName);
            return;
        }
        if (resultCode == 5000 && null != data) {
            String doctorName = data.getStringExtra("doctor_name");
            doctor_name.setText(doctorName);
        }
    }
}