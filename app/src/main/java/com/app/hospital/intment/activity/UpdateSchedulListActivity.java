package com.app.hospital.intment.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.entity.SchedulInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lzy.okgo.OkGo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 管理员端   添加修改医生排班
 */
public class UpdateSchedulListActivity extends BaseActivity {
    private TextView schedul_day;
    private TextView am_start_time;
    private TextView am_end_time;
    private TextView pm_start_time;
    private TextView pm_end_time;
    private Button update;

    private EditText free_pm;
    private EditText free_am;
    private EditText am_state;
    private EditText pm_state;

    private DoctorInfo doctorInfo;
    private SchedulInfo schedulInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_schedul_list;
    }

    @Override
    protected void initView() {

        schedul_day = findViewById(R.id.schedul_day);
        am_start_time = findViewById(R.id.am_start_time);
        am_end_time = findViewById(R.id.am_end_time);
        pm_end_time = findViewById(R.id.pm_end_time);
        pm_start_time = findViewById(R.id.pm_start_time);
        free_pm = findViewById(R.id.free_pm);
        free_am = findViewById(R.id.free_am);
        am_state = findViewById(R.id.am_state);
        pm_state = findViewById(R.id.pm_state);
        update = findViewById(R.id.update);

    }

    @Override
    protected void setListener() {
        schedul_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(UpdateSchedulListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        schedul_day.setText(getDay(date));
                    }
                }).setType(new boolean[]{false, true, true, false, false, false})
                        .build();

                pvTime.show();
            }
        });
        am_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(UpdateSchedulListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        am_start_time.setText(getTime(date));
                    }
                }).setType(new boolean[]{false, false, false, true, true, false})
                        .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                        .build();

                pvTime.show();
            }
        });


        am_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(UpdateSchedulListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        am_end_time.setText(getTime(date));
                    }
                }).setType(new boolean[]{false, false, false, true, true, false})
                        .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                        .build();

                pvTime.show();
            }
        });


        pm_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(UpdateSchedulListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        pm_start_time.setText(getTime(date));
                    }
                }).setType(new boolean[]{false, false, false, true, true, false})
                        .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                        .build();

                pvTime.show();
            }
        });

        pm_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(UpdateSchedulListActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        pm_end_time.setText(getTime(date));
                    }
                }).setType(new boolean[]{false, false, false, true, true, false})
                        .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                        .build();

                pvTime.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = schedul_day.getText().toString().trim();
                String am_start_time_str = am_start_time.getText().toString().trim();
                String am_end_time_str = am_end_time.getText().toString().trim();
                String pm_start_time_str = pm_start_time.getText().toString().trim();
                String pm_end_time_str = pm_end_time.getText().toString().trim();
                String free_am_str = free_am.getText().toString().trim();
                String free_pm_str = free_pm.getText().toString().trim();
                String am_state_str = am_state.getText().toString().trim();
                String pm_state_str = pm_state.getText().toString().trim();

                if (TextUtils.isEmpty(day) || TextUtils.isEmpty(am_start_time_str) || TextUtils.isEmpty(am_end_time_str)
                        || TextUtils.isEmpty(pm_start_time_str) || TextUtils.isEmpty(pm_end_time_str) || TextUtils.isEmpty(free_am_str)
                        || TextUtils.isEmpty(free_pm_str) || TextUtils.isEmpty(am_state_str) || TextUtils.isEmpty(pm_state_str)) {

                    showToast("请完善排班信息");
                } else {
                    if (null != schedulInfo) {
                        updateSchedul(schedulInfo.getUid(), day, am_start_time_str + "-" + am_end_time_str, pm_start_time_str + "-" + pm_end_time_str, free_am_str, free_pm_str, am_state_str, pm_state_str);
                    } else {
                        addSchedul(day, am_start_time_str + "-" + am_end_time_str, pm_start_time_str + "-" + pm_end_time_str, free_am_str, free_pm_str, am_state_str, pm_state_str);
                    }

                }

            }
        });

    }

    /**
     * 添加排班
     */
    private void addSchedul(String day, String time_am, String time_pm, String free_am, String free_pm, String am_state, String pm_state) {
        if (null != doctorInfo) {
            OkGo.<String>get(ApiConstants.ADD_SCHEDUL_URL)
                    .params("doctor_id", doctorInfo.getUid())
                    .params("doctor_name", doctorInfo.getDoctor_name())
                    .params("doctor_day", day)
                    .params("time_am", time_am)
                    .params("time_pm", time_pm)
                    .params("free_am", free_am)
                    .params("free_pm", free_pm)
                    .params("am_state", am_state)
                    .params("pm_state", pm_state)
                    .execute(new HttpStringCallback(this) {
                        @Override
                        protected void onSuccess(String msg, String response) {
                            setResult(3000);
                            showToast(msg);
                            finish();
                        }

                        @Override
                        protected void onError(String response) {
                            showToast(response);
                        }
                    });
        }

    }

    /**
     * 修改排班
     */
    private void updateSchedul(int uid, String day, String time_am, String time_pm, String free_am, String free_pm, String am_state, String pm_state) {
        if (null != schedulInfo) {
            OkGo.<String>get(ApiConstants.UPDATE_SCHEDUL_URL)
                    .params("uid", uid)
                    .params("doctor_day", day)
                    .params("time_am", time_am)
                    .params("time_pm", time_pm)
                    .params("free_am", free_am)
                    .params("free_pm", free_pm)
                    .params("am_state", am_state)
                    .params("pm_state", pm_state)
                    .execute(new HttpStringCallback(this) {
                        @Override
                        protected void onSuccess(String msg, String response) {
                            setResult(3000);
                            showToast(msg);
                            finish();
                        }

                        @Override
                        protected void onError(String response) {
                            showToast(response);
                        }
                    });
        }

    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        doctorInfo = (DoctorInfo) getIntent().getSerializableExtra("doctorInfo");
        schedulInfo = (SchedulInfo) getIntent().getSerializableExtra("schedulInfo");
        toolbar.setTitle(title);
        if (null != schedulInfo) {
            update.setText("修改");
            schedul_day.setText(schedulInfo.getDoctor_day());
            free_am.setText(schedulInfo.getFree_am());
            free_pm.setText(schedulInfo.getFree_pm());
            am_state.setText(schedulInfo.getAm_state()+"");
            pm_state.setText(schedulInfo.getPm_state()+"");
            String[] split = schedulInfo.getTime_am().split("-");
            am_start_time.setText(split[0]);
            am_end_time.setText(split[1]);

            String[] split1 = schedulInfo.getTime_pm().split("-");
            pm_start_time.setText(split1[0]);
            pm_end_time.setText(split1[1]);
        } else {
            update.setText("添加");
        }


    }

    private String getDay(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(date);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}