package com.CYZco.nygreets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipboardManager;
import android.annotation.SuppressLint;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.content.ClipData;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.EditText;
import android.graphics.Color;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Button;
import android.view.Window;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Random;
import android.os.Build;

public class MainActivity extends AppCompatActivity
{
    private final String[] stages = {"(選階段)","童年","青年","成年","老年"};
    private final String[] festes = {"新年","父親節","母親節","生日"};
    private RelativeLayout generate_place;
    private ScrollView show_text_scroll;
    private ArrayList<String> record = new ArrayList<>(2);
    private Button generateButton, convertBtn, showGreet, emoji, rank1, rank2, rank3, copy, home, tutorial;
    private Spinner stageSpinner, fest;
    private int rankDefault = 2;
    private int emojiMode = 0;
    private int nullSpin = 0;
    private TextView greets;
    private EditText name;
    private int textMode;
    private String show_Greet;

    String kG = "心想事成\uD83D\uDCAB 大吉大利\uD83C\uDF4A 身體健康\uD83D\uDCAA 學業進步\uD83C\uDF93 步步高升\uD83D\uDCC8 萬事如意\uD83C\uDF40 家庭幸福\uD83C\uDFE0 天天向上\uD83D\uDCC8 夢想成真\uD83C\uDF20 福星高照\uD83C\uDF1F 鵬程萬里\uD83E\uDD85 鶴立雞群\uD83D\uDC51 " +
                "吉祥安康\uD83C\uDF08 青春常駐\uD83C\uDF1E 笑口常開\uD83D\uDE04 一帆風順\u200b\u26F5 喜氣洋洋\uD83D\uDE01 龍馬精神\uD83D\uDC32 鴻運當頭\uD83C\uDF40 如意吉祥\uD83D\uDCAB 歡樂無邊\uD83D\uDE0A 金榜題名\uD83C\uDFC6 福氣滿滿\uD83D\uDC96 前程似錦\u200b\u2728 " +
                "彩雲追月\uD83C\uDF19 福如東海\uD83C\uDF0A 壽比南山\u26f0\uFE0F 萬事勝意\uD83C\uDFAF 春風得意\uD83C\uDF43 人見人愛\uD83E\uDD70 學業有成\uD83D\uDCD6 事事順心\uD83C\uDF08 福祿雙全\uD83C\uDF89 日進斗金\uD83E\uDE99 龍騰虎躍\uD83D\uDCAA 年年高升\uD83D\uDE80 " +
                "歡樂年年\uD83C\uDF8A 天天開心\uD83C\uDF1E 快樂無限\uD83C\uDF08 諸事順利\uD83D\uDCAB 繁花似錦\uD83C\uDF38 四季平安\uD83D\uDE0C 平安快樂\uD83D\uDE07 成長茁壯\uD83C\uDF31 五福臨門\uD83C\uDF40 快高長大\uD83C\uDF33 百事可樂\uD83D\uDE04 如虎添翼\uD83E\uDEBD";

    String tG = "成長茁壯\uD83C\uDF31 心想事成\uD83D\uDCAB 身體健康\uD83D\uDCAA 學業進步\uD83C\uDF93 萬事如意\uD83C\uDF40 步步高升\uD83D\uDCC8 家庭幸福\uD83C\uDFE0 財源滾滾\uD83D\uDC8E 夢想成真\uD83C\uDF20 福星高照\uD83C\uDF1F 吉祥安康\uD83C\uDF08 青春常駐\uD83C\uDF1E " +
                "前途無量\uD83D\uDE80 青雲直上\u200b\u2601 如日方升\uD83C\uDF1E 天天向上\uD83D\uDCC8 勇往直前\uD83C\uDFC3 一帆風順\u200b\u26F5 福氣滿滿\uD83D\uDC96 才華橫溢\uD83C\uDF1F 前途光明\uD83D\uDD06 天道酬勤\uD83E\uDD78 歡樂年年\uD83C\uDF8A 如意吉祥\uD83D\uDCAB " +
                "笑口常開\uD83D\uDE04 喜氣洋洋\uD83D\uDE01 龍馬精神\uD83D\uDC32 鴻運當頭\uD83C\uDF40 笑容滿面\u200b\u263A 快高長大\uD83C\uDF33 金榜題名\uD83C\uDFC6 事事順心\uD83C\uDF08 前程似錦\u200b\u2728 大展鴻圖\uD83D\uDCAA 四季平安\uD83D\uDE0C 平安喜樂\uD83D\uDE07 " +
                "百事順利\uD83D\uDCAB 美夢成真\uD83C\uDF20 青春無敵\uD83D\uDD25 飛黃騰達\uD83E\uDD85 蓬勃發展\uD83D\uDCC8 如虎添翼\uD83E\uDEBD 諸事順利\uD83D\uDCAB 學業進步\uD83C\uDF93 才貌雙全\uD83D\uDE0E 學業有成\uD83D\uDCD6 龍騰虎躍\uD83D\uDCAA 家和萬事興\uD83E\uDD29";

    String aG = "青春常駐\uD83C\uDF1E 萬事如意\uD83C\uDF40 身體健康\uD83D\uDCAA 財源廣進\uD83D\uDCB5 步步高升\uD83D\uDCC8 闔家平安\u200b\u262E️ 心想事成\uD83D\uDCAB 平安喜樂\uD83D\uDE07 前程似錦\u200b\u2728 一帆風順\u200b\u26F5 大展鴻圖\uD83D\uDCAA 福星高照\uD83C\uDF1F " +
                "迎春納福\uD83C\uDF43 開門見喜\uD83C\uDF89 喜氣洋洋\uD83D\uDE01 祥瑞滿滿\uD83C\uDE35 前途光明\uD83D\uDD06 龍馬精神\uD83D\uDC32 心花怒放\uD83C\uDF38 金玉滿堂\uD83C\uDFE6 前程無限\u200b\u267E️ 天天順心\uD83D\uDE0C 家庭幸福\uD83C\uDFE0 和氣生財\uD83E\uDDD8 " +
                "富貴有餘\uD83D\uDCC8 福如東海\uD83C\uDF0A 壽比南山\u26f0\ufe0f 五福臨門\uD83C\uDF40 萬象更新\uD83D\uDCA8 龍騰虎躍\uD83D\uDCAA 如意吉祥\uD83D\uDCAB 開運亨通\uD83D\uDCB0 八方來財\uD83D\uDCB5 年年有餘\uD83D\uDC21 事事順利\uD83E\uDD1E 歡樂年年\uD83C\uDF8A " +
                "豐衣足食\uD83C\uDF5A 富貴滿堂\uD83D\uDC8E 年年高升\uD83D\uDE80 招財進寶\uD83E\uDDE7 日進斗金\uD83E\uDE99 光明燦爛\u200b\u2600 四季平安\uD83D\uDE0C 鴻運當頭\uD83C\uDF40 財源滾滾\uD83D\uDC8E 事業有成\uD83D\uDCCA 如虎添翼\uD83E\uDEBD 家和萬事興\uD83E\uDD29";

    String eG = "身體健康\uD83D\uDCAA 長命百歲\uD83D\uDCAF 如意吉祥\uD83D\uDCAB 福壽安康\uD83E\uDDD8 萬事如意\uD83C\uDF40 心想事成\uD83D\uDCAB 福星高照\uD83C\uDF1F 壽與天齊\u200b\u23F3 龍馬精神\uD83D\uDC32 平安喜樂\uD83D\uDE07 老當益壯\uD83D\uDCAA 萬壽無疆\u200b\u2728 " +
                "喜氣洋洋\uD83D\uDE01 長壽康寧\uD83D\uDE0C 安享晚年\uD83E\uDD20 福氣滿滿\uD83D\uDC96 壽比南山\u26f0\ufe0f 笑口常開\uD83D\uDE04 天倫之樂\uD83D\uDE06 身體康泰\uD83D\uDD7A 快樂長伴\uD83D\uDE04 寶刀未老\uD83E\uDDBE 歲歲平安\u262E\uFE0F 年年有餘\uD83D\uDC21 " +
                "松鶴延年\uD83E\uDDA9 鴻運當頭\uD83C\uDF40 歡樂年年\uD83C\uDF8A 金玉滿堂\uD83C\uDFE6 喜迎新歲\uD83C\uDF38 吉祥安康\uD83C\uDF08 和樂融融\uD83D\uDE07 延年益壽\u200b\u23EB 年年健康\uD83E\uDD29 大吉大利\uD83C\uDF4A 財源廣進\uD83D\uDCB5 心境常寧\u200b\u263A " +
                "闔家平安\u262E\uFE0F 家庭幸福\uD83C\uDFE0 五福臨門\uD83C\uDF40 四季平安\uD83D\uDE0C 豐衣足食\uD83C\uDF5A 福壽綿延\u200b\u23F3 和睦安康\uD83E\uDD1D 美滿生活\u200b\u2728 福壽雙全\uD83D\uDE46 平安長樂\uD83D\uDE4F 富貴有餘\uD83D\uDCC8 福如東海\uD83C\uDF0A";

    String[] kidG = kG.split(" ");
    String[] teenG = tG.split(" ");
    String[] adultG = aG.split(" ");
    String[] elderG = eG.split(" ");
    String[] greetingsArray;
    ArrayList<String> saveGreets = new ArrayList<>();

    private void addRecord(String s)
    {
        if(record.size() == 2)
        {record.remove(0);}
        record.add(s);
    }
    private String newGreet(String[] array)
    {
        String[] copy = Arrays.copyOf(array, array.length);
        String s1 = ranIndex(copy);

        ArrayList<String> s2A = new ArrayList<>(Arrays.asList(copy));
        s2A.remove(s1);
        copy = s2A.toArray(new String[0]);
        String s2 = ranIndex(copy);

        return s1 + "、" + s2;
    }

    private String ranIndex(String[] array)
    {return array[new Random().nextInt(array.length)];}

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveGreets.add("");
        record.add("");
        record.add("");

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

        show_text_scroll = findViewById(R.id.showGreet_scroll);
        generateButton = findViewById(R.id.generate_button);
        generate_place = findViewById(R.id.generate_place);
        stageSpinner = findViewById(R.id.stage_spinner);
        convertBtn = findViewById(R.id.convert_btn);
        showGreet = findViewById(R.id.show_button);
        tutorial = findViewById(R.id.tut_button);
        home = findViewById(R.id.home_button);
        emoji = findViewById(R.id.emojiAdd);
        greets = findViewById(R.id.greets);
        fest = findViewById(R.id.festival);
        rank1 = findViewById(R.id.rank1);
        rank2 = findViewById(R.id.rank2);
        rank3 = findViewById(R.id.rank3);
        copy = findViewById(R.id.copy);
        name = findViewById(R.id.name);

        show_text_scroll.setVerticalScrollBarEnabled(false);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        generate_place.startAnimation(slideUp);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, stages);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        stageSpinner.setAdapter(adapter);
        stageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                setSpinner(selectedItem);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter<String> festAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, festes);
        festAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        fest.setAdapter(festAdapter);
        fest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                setSpinner(selectedItem);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        generateButton.setOnClickListener
        (v -> setGreet( stageSpinner.getSelectedItem().toString() ) );

        emoji.setOnClickListener(e->
        {
            String getMes = greets.getText().toString();
            if (!stageSpinner.getSelectedItem().toString().equals("(選階段)") && !saveGreets.get(0).isEmpty())
            {
                if (getMes.contains(" "))
                {
                    String replace = "\u3297\uFE0F";
                    String greet_e = saveGreets.get(0);
                    String greet_n = cutEmoji(greet_e);
                    String newMes = getMes.substring(0, getMes.lastIndexOf(" ")).replace(replace,"祝");
                    String newMesR = newMes.replace("祝", replace);

                    String text = emojiMode == 0 ? greet_e : greet_n;
                    String mes = emojiMode == 0 ? newMesR : newMes;

                    String icon_txt = Objects.equals(text, greet_e)? "\uD83D\uDE42✖\uFE0F" : "\uD83D\uDE04✔\uFE0F";
                    if (icon_txt.equals("\uD83D\uDE42✖\uFE0F")) {emoji.setTextColor(Color.GRAY);}
                    if (icon_txt.equals("\uD83D\uDE04✔\uFE0F")) {emoji.setTextColor(ContextCompat.getColor(this,R.color.tick));}
                    emoji.setText(icon_txt);

                    emojiMode = emojiMode == 0 ? 1 : 0;
                    String textE = mes + " " + text;

                    addRecord(textE);
                    greets.setText(textE);
                }
            }
        });

        showGreet.setOnClickListener(v ->
        {
            String stage = stageSpinner.getSelectedItem().toString();
            switch (stage)
            {
                case "(選階段)":
                    setText3();
                    return;
                case "童年":
                    show_Greet = kG;
                    break;
                case "青年":
                    show_Greet = tG;
                    break;
                case "成年":
                    show_Greet = aG;
                    break;
                case "老年":
                    show_Greet = eG;
                    break;

                default:
                    return;
            }

            StringBuilder greet = new StringBuilder();
            String[] array = show_Greet.split(" ");
            for (int i=0; i<array.length; i++)
            {
                String arrays = array[i];
                if (i != array.length-1)
                {arrays += i % 2 == 0 ? "、" : "、\n" ;}
                greet.append(arrays);
            }

            show_Greet = greet.toString();
            String text;
            String check = greets.getText().toString();

            boolean shown = check.equals(show_Greet);
            if (!shown)
            {
                text = show_Greet;
                emoji.setVisibility(View.INVISIBLE);
            }
            else
            {
                text = record.get(1);
                emoji.setVisibility(View.VISIBLE);
            }

            float size = shown? 24f : 19f ;
            greets.setTextColor(Color.BLACK);
            greets.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
            greets.setText(text);
        });

        convertBtn.setOnClickListener(e ->
        {
            if (!saveGreets.get(0).isEmpty())
            {
                emoji.setVisibility(View.VISIBLE);
                String ysEmoji = saveGreets.get(0);
                String noEmoji = cutEmoji(ysEmoji);
                String mesDecide = emojiMode == 1? ysEmoji : noEmoji;
                String yn = emojiMode == 1? "y" : "n" ;

                if (textMode ==0) {setText2(rankDefault, yn, mesDecide);}
                else if (textMode ==1) {setText(rankDefault, yn, mesDecide);}
            }
        });

        copy.setOnClickListener(e ->
        {
            String getGreet = greets.getText().toString();
            String satisfy = getGreet.substring(2);
            if (!getGreet.isEmpty())
            {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null, satisfy);
                clipboard.setPrimaryClip(clip);
            }
        });

        rank1.setOnClickListener(e ->
        {//晚輩
            rankDefault = 1;
            rankBTN();
        });

        rank2.setOnClickListener(e ->
        {//平輩
            rankDefault = 2;
            rankBTN();
        });

        rank3.setOnClickListener(e ->
        {//長輩
            rankDefault = 3;
            rankBTN();
        });

        home.setOnClickListener(v -> {});

        tutorial.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zero_ani, R.anim.zero_ani);
        });
    }

    private void rankBTN()
    {
        checkRLV(rankDefault);

        if (!saveGreets.get(0).isEmpty())
        {
            String greet = emojiMode == 1 ? saveGreets.get(0) : cutEmoji(saveGreets.get(0));
            String yn = emojiMode == 1 ? "y" : "n";

            if (textMode == 0)
            {setText(rankDefault, yn, greet);}
            if (textMode == 1)
            {setText2(rankDefault, yn, greet);}
            addRecord(greets.getText().toString());
        }
    }

    private void setGreet(String selected)
    {
        switch (selected)
        {
            case "(選階段)":
                setText3();
                return;
            case "童年":
                greetingsArray = kidG;
                break;
            case "青年":
                greetingsArray = teenG;
                break;
            case "成年":
                greetingsArray = adultG;
                break;
            case "老年":
                greetingsArray = elderG;
                break;
            default:
                return;
        }

        greets.setTextColor(Color.BLACK);
        String ysEmoji = newGreet(greetingsArray);
        String noEmoji = cutEmoji(ysEmoji);
        saveGreets.set(0, ysEmoji);

        String mesDecide = emojiMode == 1 ? ysEmoji : noEmoji;
        String yn = emojiMode == 1 ? "y" : "n" ;

        if (textMode == 0)
        {setText(rankDefault, yn, mesDecide);}
        if (textMode == 1)
        {setText2(rankDefault, yn, mesDecide);}
    }

    private String cutEmoji(String text)
    {
        String[] s0 = text.split("、");
        String s1 = s0[0].substring(0,s0[0].length()-2);
        String s2 = s0[1].substring(0,s0[1].length()-2);
        return s1 + "、" + s2;
    }

    String t0;
    String t1;
    private void setText(int rlv, String emoji, String greet)
    {
        textMode =0;
        checkRLV(rlv);
        String dear = "\n\n親愛的 ";
        String names = name.getText().toString();
        String space1 = " "; // for align
        String zhu = Objects.equals(emoji, "y") ? "\u3297\uFE0F" : "祝";
        String ny = "新年快樂!";
        String space2 = "\n "; // important for emoji

        if (!names.isEmpty())
        {
            if (rankDefault != 3)
            {names += ":\n";}
            else {names += "";}
        }

        if (rlv == 1)
        {names = surname(names);}

        String dearY = dear + names + ":\n" + space1 + zhu + "您" + ny + space2 + greet;
        String dearN = "\n\n" + names + space1 + zhu + "你" + ny + space2 + greet;

        String sentence;
        if (rlv == 3) {sentence = dearY;}
        else {sentence = dearN;}

        t0 = sentence;
        addRecord(sentence);
        greets.setTextSize(24f);
        greets.setText(sentence);
    }

    private void setText2(int rlv, String emoji, String greet)
    {
        textMode =1;
        checkRLV(rlv);

        String zhu = Objects.equals(emoji, "y") ? "\u3297\uFE0F" : "祝";
        String names = name.getText().toString();

        if (rlv == 1)
        {names = surname(names);}

        String sentence = "\n\n" + zhu + names + "新年快樂!  " + greet;
        t1 = sentence;
        addRecord(sentence);
        greets.setTextSize(24f);
        greets.setText(sentence);
    }

    @SuppressLint("SetTextI18n")
    private void setText3()
    {
        nullSpin = nullSpin == 0 ? 1 : 0 ;
        greets.setTextSize(30);
        greets.setText(R.string.tutorial);
        int gray = nullSpin == 0 ? R.drawable.rect_round10_stroke7_red : R.drawable.rect_round10_stroke7_green;
        int color = nullSpin == 0 ? R.color.red : R.color.green ;
        stageSpinner.setBackground(ContextCompat.getDrawable(MainActivity.this, gray));
        greets.setTextColor(getResources().getColor(color));
    }

    @SuppressLint("ResourceAsColor")
    private void byDefault(boolean bool)
    {
        int visa = bool ? View.VISIBLE : View.INVISIBLE;
        copy.setEnabled(bool);
        convertBtn.setEnabled(bool);
        rank1.setEnabled(bool);
        rank2.setEnabled(bool);
        rank3.setEnabled(bool);
        showGreet.setVisibility(visa);
        emoji.setVisibility(visa);
    }

    private void setSpinner(String selected)
    {
        boolean isDefault = selected.equals("(選階段)"); //true when (選階段)
        int spinnerDrawable = isDefault ? R.drawable.rect_round10_stroke1 : R.drawable.rect_round10_stroke1;
        if (isDefault)
        {
            greets.setTextSize(30);
            greets.setTextColor(Color.GRAY);
            greets.setText(R.string.tutorial);
        }

        stageSpinner.setBackground(ContextCompat.getDrawable(MainActivity.this, spinnerDrawable));
        byDefault(!isDefault);

        if (greets.getText().toString().equals(show_Greet))
        {emoji.setVisibility(View.INVISIBLE);}
    }

    private void checkRLV(int rlv)
    {
        Button[] ranks = new Button[]{rank1, rank2, rank3};
        emoji.setVisibility(View.VISIBLE);

        for (int i = 0; i < ranks.length; i++)
        {
            if (i == rlv - 1)
            {
                ranks[i].setTextColor(ContextCompat.getColor(this, R.color.btnB));
                ranks[i].setTextSize(20);
            }
            else
            {
                ranks[i].setTextColor(ContextCompat.getColor(this, R.color.btnBg));
                ranks[i].setTextSize(20);
            }
        }
    }

    private Boolean checkEnglish(String name)
    {
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char c : name.toCharArray())
        {
            for (char charABC : abc.toCharArray())
            {
                if (c == charABC)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private String surname(String name)
    {
        if (!checkEnglish(name))
        {
            switch (name.length())
            {
                case 0:
                case 3:
                case 4:
                    break;
                case 5:
                    name = name.substring(1);
                    break;
                default:
                    name = name.substring(2);
                    break;
            }
        }
        return name;
    }
}