package com.CYZco.nygreets;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.annotation.SuppressLint;
import android.view.WindowManager;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import java.util.ArrayList;
import android.view.Window;
import android.view.View;
import android.os.Bundle;
import android.os.Build;
import android.widget.RelativeLayout;
import android.os.Handler;

import java.util.List;

public class TutorialActivity extends AppCompatActivity
{
    private Button home, tutorial;
    private RecyclerView tut_SCROLL;
    private RelativeLayout generate_place;
    private Animation slideDown, slideGone, slideUpGone;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        String path = "android.resource://" + getPackageName() + "/";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.getDecorView().setSystemUiVisibility
                    (
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    );
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {actionBar.hide();}

        tut_SCROLL = findViewById(R.id.tutorial_scroll);
        home = findViewById(R.id.home_button);
        tutorial = findViewById(R.id.tut_button);
        generate_place = findViewById(R.id.generate_place);

        slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        slideGone = AnimationUtils.loadAnimation(this, R.anim.slide_gone);
        slideUpGone = AnimationUtils.loadAnimation(this, R.anim.slide_up_gone);

        tut_SCROLL.startAnimation(slideDown);
        generate_place.startAnimation(slideGone);

        home.setOnClickListener(v ->
        {
            tut_SCROLL.startAnimation(slideUpGone);
            int animationDuration = 300;
            new Handler().postDelayed(() ->
            {
                Intent intent = new Intent(home.getContext(), MainActivity.class);
                home.getContext().startActivity(intent);
                overridePendingTransition(R.anim.zero_ani, R.anim.zero_ani);
            }, animationDuration);
        });

        tutorial.setOnClickListener(v -> {});


        tut_SCROLL.setLayoutManager(new LinearLayoutManager(this));
        tut_SCROLL.setVerticalScrollBarEnabled(false);

        List<String> videoPaths = new ArrayList<>();
        videoPaths.add(path + R.raw.test);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);
        videoPaths.add(path + R.raw.start_choose);

        List<String> which = new ArrayList<>();
        which.add("* 選擇階段 *");
        which.add("選擇輩分");
        which.add("選擇節日");
        which.add("輸入稱呼");
        which.add("生成祝福");
        which.add("加入 emoji");
        which.add("切換格式");
        which.add("copy 文本");
        which.add("查看祝福");

        TutorialAdapter adapter = new TutorialAdapter(videoPaths, which);
        tut_SCROLL.setAdapter(adapter);
        tut_SCROLL.scrollToPosition(adapter.getItemCount() - 1);
    }
}
