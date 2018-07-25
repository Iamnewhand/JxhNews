package test.yzx.com.newapp3.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import test.yzx.com.newapp3.R;
import test.yzx.com.newapp3.fragments.NewFragment;
import test.yzx.com.newapp3.fragments.PersonFragment;
import test.yzx.com.slidinglib.SlidingActivity;

public class MainActivity extends AppCompatActivity {

    private View newsBtn;
    private View personBtn;
    private boolean isNews = true;
    private TextView tabName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        newsBtn = findViewById(R.id.ll_news);
        personBtn = findViewById(R.id.ll_my);
        tabName = findViewById(R.id.tv_tab);
        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNews) return;
                isNews = true;
                btnClick();
            }
        });

        personBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNews) return;
                isNews = false;
                btnClick();
            }
        });
        btnClick();
    }


    private Fragment newsFragment;
    private Fragment personFragment;

    public void btnClick() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (newsFragment != null) {
            ft.hide(newsFragment);
        }
        if (personFragment != null) {
            ft.hide(personFragment);
        }
        Fragment fragment;
        if (isNews) {
            tabName.setText("新闻");
            if (newsFragment == null) {
                newsFragment = NewFragment.newInstance();
                ft.add(R.id.fl_container, newsFragment);
            }
            fragment = newsFragment;
        } else {
            tabName.setText("我的");
            if (personFragment == null) {
                personFragment = PersonFragment.newInstance();
                ft.add(R.id.fl_container, personFragment);
            }
            fragment = personFragment;
        }
        ft.show(fragment).commitAllowingStateLoss();
    }

    private long firstClick;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - firstClick) > 1000) {
            firstClick = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();

    }
}
