package com.lc.huluobo.desingdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button btn;
    private LinearLayout ll;
    private TextInputLayout til_name, til_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        btn = findViewById(R.id.btn);
        ll = findViewById(R.id.ll);
        til_name = findViewById(R.id.til_name);
        til_pwd = findViewById(R.id.til_pwd);

        //SnackBar
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(ll, "SnackBar", Snackbar.LENGTH_LONG).setAction("Action", new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "toast", Toast.LENGTH_SHORT
                        ).show();
                    }
                }).show();
            }
        });

        //TextInputLayout
        til_name.setHint("Name");
        til_pwd.setHint("Pwd");

        til_name.getEditText().addTextChangedListener(new MyTextWatcher(til_name, "名字长度不能小于6位"));
        til_pwd.getEditText().addTextChangedListener(new MyTextWatcher(til_pwd, "密码长度不能小于6位"));


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);

        //TabLayout
//        TabLayout tabLayout=findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab4"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab5"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab6"));
        setViewPager();
    }

    class MyTextWatcher implements TextWatcher {
        private TextInputLayout textInputLayout;
        private String errorInfo;

        public MyTextWatcher(TextInputLayout textInputLayout, String errorInfo) {
            this.textInputLayout = textInputLayout;
            this.errorInfo = errorInfo;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (textInputLayout.getEditText().getText().toString().length() < 6) {
                textInputLayout.setErrorEnabled(true);//打开错误提示信息
                textInputLayout.setError(errorInfo);//设置错误信息文字
            } else {
                textInputLayout.setErrorEnabled(false);//关闭错误提示信息
            }

            if (TextUtils.isEmpty(textInputLayout.getEditText().getText())) {
                textInputLayout.setErrorEnabled(false);//关闭错误提示信息
            }
        }
    }


    private List<View> viewList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private void setViewPager() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.page_main, null);
        View view2 = inflater.inflate(R.layout.page_main, null);
        View view3 = inflater.inflate(R.layout.page_main, null);
        View view4 = inflater.inflate(R.layout.page_main, null);
        View view5 = inflater.inflate(R.layout.page_main, null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);

        titleList.add("No.1");
        titleList.add("No.2");
        titleList.add("No.3");
        titleList.add("No.4");
        titleList.add("No.5");

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(4)));

        ViewPager viewPager = findViewById(R.id.view_pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList, titleList);
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myPagerAdapter);

    }

    class MyPagerAdapter extends PagerAdapter {

        private List<View> viewList;
        private List<String> titleList;

        /**
         * 构造方法
         *
         * @param viewList  view集合
         * @param titleList 标题集合
         */
        public MyPagerAdapter(List<View> viewList, List<String> titleList) {
            this.viewList = viewList;
            this.titleList = titleList;
        }

        @Override
        public int getCount() {
            if (viewList.size() != 0)
                return viewList.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object
                object) {
            container.removeView(viewList.get(position));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    //NavigationView
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
