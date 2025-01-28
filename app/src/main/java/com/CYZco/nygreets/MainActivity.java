package com.CYZco.nygreets;

import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import android.content.ClipboardManager;
import android.view.animation.Animation;
import android.annotation.SuppressLint;
import android.widget.RelativeLayout;
import android.widget.ArrayAdapter;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import java.util.Objects;
import java.util.Arrays;
import java.util.Random;
import android.os.Build;

public class MainActivity extends AppCompatActivity
{
    private final String[] STAGES = {"(選階段)","童年","青年","成年","老年"};
    private final String[] FESTIVALS = {"新年","父親節","母親節","生日"};
    private Button emojiButton;
    private Button copyButton;
    private Button convertButton;
    private Button showBlessing;
    private Button juniorRankButton;
    private Button peerRankButton;
    private Button seniorRankButton;
    private Spinner stageSpinner;
    private int rankDefault = 2;
    private boolean emojiMode = false; //false = emoji disabled
    private int nullSpin = 0;
    private String textMode = "mail";
    private TextView textField;
    private EditText targetName;
    private String show_Greet;
    private final CustomSetting CUSTOM_SETTING = new CustomSetting();
    private final ArrayList<String> RECORDS = new ArrayList<>(2);
    private final Bless BLESS = new Bless();

    String[] kidBlesses = BLESS.getGreetingsByStage("童年");
    String[] teenBlesses = BLESS.getGreetingsByStage("青年");
    String[] adultBlesses = BLESS.getGreetingsByStage("成年");
    String[] elderBlesses = BLESS.getGreetingsByStage("老年");
    String[] blessArrays;
    ArrayList<String> saveBlesses = new ArrayList<>();

    private void addRecord(String s)
    {
        if(RECORDS.size() == 2)
        {
            RECORDS.remove(0);}
        RECORDS.add(s);
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
        saveBlesses.add("");
        RECORDS.add("");
        RECORDS.add("");

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

        ScrollView showTextScroller = findViewById(R.id.showGreet_scroll);
        Button generateButton = findViewById(R.id.generate_button);
        RelativeLayout generatePlace = findViewById(R.id.generate_place);
        Button tutorialButton = findViewById(R.id.tut_button);
        Button homeButton = findViewById(R.id.home_button);
        Button settingButton = findViewById(R.id.setting);
        Spinner festivalSelector = findViewById(R.id.festival);
        stageSpinner = findViewById(R.id.stage_spinner);
        convertButton = findViewById(R.id.convert_btn);
        showBlessing = findViewById(R.id.show_button);
        emojiButton = findViewById(R.id.emojiAdd);
        textField = findViewById(R.id.greets);
        juniorRankButton = findViewById(R.id.rank1);
        peerRankButton = findViewById(R.id.rank2);
        seniorRankButton = findViewById(R.id.rank3);
        copyButton = findViewById(R.id.copy);
        targetName = findViewById(R.id.name);

        showTextScroller.setVerticalScrollBarEnabled(false);

        new Thread(() ->
        {
            Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            generatePlace.startAnimation(slideUp);
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, STAGES);
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

        ArrayAdapter<String> festAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, FESTIVALS);
        festAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        festivalSelector.setAdapter(festAdapter);
        festivalSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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

        emojiButton.setOnClickListener(e->
        {
            String getMes = textField.getText().toString();
            if (!stageSpinner.getSelectedItem().toString().equals("(選階段)") && !saveBlesses.get(0).isEmpty())
            {
                if (getMes.contains(" "))
                {
                    String replace = "\u3297\uFE0F";
                    String blessWithEmoji = saveBlesses.get(0);
                    String blessNoEmoji = cutEmoji(blessWithEmoji);
                    String noEmojiMessage = getMes.substring(0, getMes.lastIndexOf(" ")).replace(replace,"祝");
                    String emojiReplacedMessage = noEmojiMessage.replace("祝", replace);

                    String text = !emojiMode ? blessWithEmoji : blessNoEmoji;
                    String message = !emojiMode ? emojiReplacedMessage : noEmojiMessage;

                    int shadowColor = emojiMode ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
                    emojiButton.setShadowLayer(29f, 0f , 0f, shadowColor);

                    emojiMode = !emojiMode;
                    String textE = message + " " + text;

                    addRecord(textE);
                    textField.setText(textE);
                }
            }
        });

        showBlessing.setOnClickListener(v ->
        {
            String stage = stageSpinner.getSelectedItem().toString();
            if (stage.equals("(選階段)"))
                setText3();

            String[] blessArrays = BLESS.getGreetingsByStage(stage);

            StringBuilder greet = new StringBuilder();
            for (int i=0; i<blessArrays.length; i++)
            {
                String arrays = blessArrays[i];
                if (i != blessArrays.length-1)
                {arrays += i % 2 == 0 ? "、" : "、\n" ;}
                greet.append(arrays);
            }

            show_Greet = greet.toString();
            String text;
            String check = textField.getText().toString();

            boolean shown = check.equals(show_Greet);
            if (!shown)
            {
                text = show_Greet;
                emojiButton.setVisibility(View.INVISIBLE);
            }
            else
            {
                text = RECORDS.get(1);
                emojiButton.setVisibility(View.VISIBLE);
            }

            float size = shown? 24f : 19f ;
            textField.setTextColor(Color.BLACK);
            textField.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
            textField.setText(text);
        });

        convertButton.setOnClickListener(e ->
        {
            if (!saveBlesses.get(0).isEmpty())
            {
                emojiButton.setVisibility(View.VISIBLE);
                String ysEmoji = saveBlesses.get(0);
                String noEmoji = cutEmoji(ysEmoji);
                String mesDecide = emojiMode? ysEmoji : noEmoji;
                String yn = emojiMode? "y" : "n" ;

                if (textMode.equals("mail"))
                {setBlessingLineText(rankDefault, yn, mesDecide);}
                else if (textMode.equals("line"))
                {setBlessingMail(rankDefault, yn, mesDecide);}
            }
        });

        copyButton.setOnClickListener(e ->
        {
            String getGreet = textField.getText().toString();
            String satisfy = getGreet.substring(2);
            if (!getGreet.isEmpty())
            {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null, satisfy);
                clipboard.setPrimaryClip(clip);
            }
        });

        juniorRankButton.setOnClickListener(e ->
        {//晚輩
            rankDefault = 1;
            rankBTN();
        });

        peerRankButton.setOnClickListener(e ->
        {//平輩
            rankDefault = 2;
            rankBTN();
        });

        seniorRankButton.setOnClickListener(e ->
        {//長輩
            rankDefault = 3;
            rankBTN();
        });

        homeButton.setOnClickListener(v -> {});

        settingButton.setOnClickListener(v ->
        {
            CUSTOM_SETTING.show(getSupportFragmentManager(), "settingButton");
        });

        tutorialButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zero_ani, R.anim.zero_ani);
        });
    }

    private void rankBTN()
    {
        checkRLV(rankDefault);

        if (!saveBlesses.get(0).isEmpty())
        {
            String greet = emojiMode ? saveBlesses.get(0) : cutEmoji(saveBlesses.get(0));
            String yn = emojiMode ? "y" : "n";

            if (textMode.equals("mail"))
            {
                setBlessingMail(rankDefault, yn, greet);}
            if (textMode.equals("line"))
            {
                setBlessingLineText(rankDefault, yn, greet);}
            addRecord(textField.getText().toString());
        }
    }

    private void checkRLV(int rlv)
    {
        Button[] ranks = new Button[]{juniorRankButton, peerRankButton, seniorRankButton};
        emojiButton.setVisibility(View.VISIBLE);

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

    private void setBlessingMail(int rlv, String emoji, String greet)
    {
        textMode = "mail";
        checkRLV(rlv);
        String dear = "\n\n親愛的 ";
        String names = targetName.getText().toString();
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

        addRecord(sentence);
        textField.setTextSize(24f);
        textField.setText(sentence);
    }

    private void setBlessingLineText(int rlv, String emoji, String greet)
    {
        textMode = "line";
        checkRLV(rlv);

        String zhu = Objects.equals(emoji, "y") ? "\u3297\uFE0F" : "祝";
        String names = targetName.getText().toString();

        if (rlv == 1)
        {names = surname(names);}

        String sentence = "\n\n" + zhu + names + "新年快樂!  " + greet;
        addRecord(sentence);
        textField.setTextSize(24f);
        textField.setText(sentence);
    }

    @SuppressLint("SetTextI18n")
    private void setText3()
    {
        nullSpin = nullSpin == 0 ? 1 : 0 ;
        textField.setTextSize(30);
        textField.setText(R.string.tutorial);
        int gray = nullSpin == 0 ? R.drawable.rect_round10_stroke7_red : R.drawable.rect_round10_stroke7_green;
        int color = nullSpin == 0 ? R.color.red : R.color.green ;
        stageSpinner.setBackground(ContextCompat.getDrawable(MainActivity.this, gray));
        textField.setTextColor(getResources().getColor(color));
    }

    private void setGreet(String selected)
    {
        switch (selected)
        {
            case "(選階段)":
                setText3();
                return;
            case "童年":
                blessArrays = kidBlesses;
                break;
            case "青年":
                blessArrays = teenBlesses;
                break;
            case "成年":
                blessArrays = adultBlesses;
                break;
            case "老年":
                blessArrays = elderBlesses;
                break;
            default:
                return;
        }

        textField.setTextColor(Color.BLACK);
        String ysEmoji = newGreet(blessArrays);
        String noEmoji = cutEmoji(ysEmoji);
        saveBlesses.set(0, ysEmoji);

        String mesDecide = emojiMode ? ysEmoji : noEmoji;
        String yn = emojiMode ? "y" : "n" ;

        if (textMode.equals("mail"))
        {setBlessingMail(rankDefault, yn, mesDecide);}
        if (textMode.equals("line"))
        {setBlessingLineText(rankDefault, yn, mesDecide);}
    }

    private String cutEmoji(String text)
    {
        String[] s0 = text.split("、");
        String s1 = s0[0].substring(0,s0[0].length()-2);
        String s2 = s0[1].substring(0,s0[1].length()-2);
        return s1 + "、" + s2;
    }

    @SuppressLint("ResourceAsColor")
    private void byDefault(boolean bool)
    {
        int visa = bool ? View.VISIBLE : View.INVISIBLE;
        copyButton.setEnabled(bool);
        convertButton.setEnabled(bool);
        juniorRankButton.setEnabled(bool);
        peerRankButton.setEnabled(bool);
        seniorRankButton.setEnabled(bool);
        showBlessing.setVisibility(visa);
        emojiButton.setVisibility(visa);
    }

    private void setSpinner(String selected)
    {
        boolean isDefault = selected.equals("(選階段)"); //true when (選階段)
        int spinnerDrawable = R.drawable.rect_round10_stroke1;
        if (isDefault)
        {
            textField.setTextSize(30);
            textField.setTextColor(Color.GRAY);
            textField.setText(R.string.tutorial);
        }

        stageSpinner.setBackground(ContextCompat.getDrawable(MainActivity.this, spinnerDrawable));
        byDefault(!isDefault);

        if (textField.getText().toString().equals(show_Greet))
        {
            emojiButton.setVisibility(View.INVISIBLE);}
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