package com.CYZco.nygreets;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import android.annotation.SuppressLint;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Button;
import java.util.ArrayList;
import android.view.Window;
import android.view.View;
import android.os.Bundle;
import android.os.Build;
import java.util.List;

public class TutorialActivity extends AppCompatActivity
{
    private Button home, tutorial;
    private RecyclerView tut_SCROLL;

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

        home.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zero_ani, R.anim.zero_ani);
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
    }
}
