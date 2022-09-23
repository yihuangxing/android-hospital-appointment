package com.app.hospital.intment.activity;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.fragment.HomeFragment;
import com.app.hospital.intment.fragment.MineFragment;
import com.app.hospital.intment.fragment.OrderFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


/**
 * 用户端挂号主页
 */
public class UserMainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private HomeFragment mHomeFragment;
    private OrderFragment mOrderFragment;
    private MineFragment mMineFragment;

    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_main;
    }

    @Override
    protected void initView() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setActiveColor("#018786") //选中颜色
                .setInActiveColor("#515151") //未选中颜色
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setBarBackgroundColor("#ffffff");//导航栏背景色


        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_home, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_home_normal)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_order, "预约").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_order_normal)))
                .addItem(new BottomNavigationItem(R.mipmap.ic_mine, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_mine_normal)))
                .setFirstSelectedPosition(0)
                .initialise();

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {


        selectedFragment(0);

    }


    private void selectedFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        if (position == 0) {
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.content, mHomeFragment);
            } else {
                fragmentTransaction.show(mHomeFragment);
            }
        } else if (position == 1) {
            if (mOrderFragment == null) {
                mOrderFragment = new OrderFragment();
                fragmentTransaction.add(R.id.content, mOrderFragment);
            } else {
                fragmentTransaction.show(mOrderFragment);
                mOrderFragment.refreshData();
            }
        } else {
            if (mMineFragment == null) {
                mMineFragment = new MineFragment();
                fragmentTransaction.add(R.id.content, mMineFragment);
            } else {
                fragmentTransaction.show(mMineFragment);

            }
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction beginTransaction) {

        if (null != mHomeFragment) {
            beginTransaction.hide(mHomeFragment);
        }

        if (null != mOrderFragment) {
            beginTransaction.hide(mOrderFragment);
        }
        if (null != mMineFragment) {
            beginTransaction.hide(mMineFragment);
        }

    }

    @Override
    public void onTabSelected(int position) {
        selectedFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}