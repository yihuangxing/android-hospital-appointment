package com.app.hospital.intment.activity;


import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.CollectionListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.CollectionInfo;
import com.app.hospital.intment.entity.CollectionInfoList;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;

public class CollectionActivity extends BaseActivity {
    private CollectionListAdapter mCollectionListAdapter;
    private RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mCollectionListAdapter = new CollectionListAdapter();
        recyclerView.setAdapter(mCollectionListAdapter);
    }

    @Override
    protected void setListener() {

        mCollectionListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CollectionInfo collectionInfo = (CollectionInfo) adapter.getData().get(position);
                DoctorInfo doctorInfo =new DoctorInfo(collectionInfo.getUid(),collectionInfo.getDepart_name(),collectionInfo.getDoctor_name(),collectionInfo.getDoctor_avatar(),collectionInfo.getGood_at());
                Intent intent = new Intent(CollectionActivity.this, DoctorDetailActivity.class);
                intent.putExtra("info", doctorInfo);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        queryCollectionList();
    }

    //根据用户名查询收藏医生列表数据
    public void queryCollectionList() {
        UserInfo userInfo = ApiConstants.getUserInfo();
        if (null != userInfo) {
            OkGo.<String>get(ApiConstants.QUERY_COLLECTION_URL)
                    .params("username", userInfo.getUsername())
                    .execute(new HttpStringCallback(this) {
                        @Override
                        protected void onSuccess(String msg, String response) {
                            CollectionInfoList collectionInfoList = GsonUtils.parseJson(response, CollectionInfoList.class);
                            mCollectionListAdapter.setList(collectionInfoList.getList());
                        }

                        @Override
                        protected void onError(String response) {
                            showToast(response);
                        }
                    });

        }
    }
}