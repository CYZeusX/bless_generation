package com.CYZco.nygreets;

import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import android.content.ClipboardManager;
import android.view.animation.Animation;
import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import android.widget.RelativeLayout;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.content.ClipData;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Spinner;
import android.graphics.Color;
import android.content.Intent;
import android.widget.Button;
import android.view.Window;
import java.util.ArrayList;
import android.view.View;
import java.util.Objects;
import android.os.Bundle;
import java.util.Arrays;
import java.util.Random;
import android.util.Log;
import android.os.Build;

public class MainActivity extends AppCompatActivity
{
    public RelativeLayout settingPlace;
    public Button showBlessing;
    public Button emojiButton;
    public Button copyButton;
    public Button convertButton;
    public Button settingButton;
    public boolean emojiMode = false; //false = emoji disabled
    private boolean nullSpin = false;
    public TextView textField;
    public String rankDefault = "peer";
    public String textMode = "mail";
    public String show_Greet;
    private final ArrayList<String> RECORDS = new ArrayList<>(2);
    private final Settings SETTING = new Settings();
    private final BlessManager BLESS_MANAGER = new BlessManager();

    String[] kidBlesses = BLESS_MANAGER.getGreetingsByStage("童年");
    String[] teenBlesses = BLESS_MANAGER.getGreetingsByStage("青年");
    String[] adultBlesses = BLESS_MANAGER.getGreetingsByStage("成年");
    String[] elderBlesses = BLESS_MANAGER.getGreetingsByStage("老年");
    String[] blessArrays;
    ArrayList<String> saveBlesses = new ArrayList<>();

    public void addRecord(String s)
    {
        if(RECORDS.size() == 2)
            RECORDS.remove(0);
        RECORDS.add(s);
    }

    private String newGreet(String[] blessArrays)
    {
        String[] blessingCopy = Arrays.copyOf(blessArrays, blessArrays.length);
        ArrayList<String> copyArrayLists = new ArrayList<>(Arrays.asList(blessingCopy));

        String randomBless1 = randomIndex(blessingCopy);
        copyArrayLists.remove(randomBless1);

        blessingCopy = copyArrayLists.toArray(new String[0]);
        String randomBless2 = randomIndex(blessingCopy);

        return randomBless1 + "、" + randomBless2;
    }

    private String randomIndex(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

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
            actionBar.hide();

        ScrollView showTextScroller = findViewById(R.id.showGreet_scroll);
        Button generateButton = findViewById(R.id.generate_button);
        RelativeLayout generatePlace = findViewById(R.id.generate_place);
        Button tutorialButton = findViewById(R.id.tut_button);
        Button homeButton = findViewById(R.id.home_button);
        settingPlace = findViewById(R.id.setting_Area);
        settingButton = findViewById(R.id.settingButton);
        convertButton = findViewById(R.id.convert_btn);
        showBlessing = findViewById(R.id.show_button);
        emojiButton = findViewById(R.id.emojiAdd);
        textField = findViewById(R.id.textField);
        copyButton = findViewById(R.id.copy);
        TextView bless = findViewById(R.id.bless);

        bless.setLayerType(View.LAYER_TYPE_SOFTWARE, bless.getPaint());
        bless.setShadowLayer(150f, 0f, 0f, getResources().getColor(R.color.yellow_a50));
        showTextScroller.setVerticalScrollBarEnabled(false);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        generatePlace.startAnimation(slideUp);

        generateButton.setOnClickListener(v ->
        {

            if (SETTING.stageSpinner == null)
            {
                Log.d("MainActivity", "stageSpinner is null");
                setRemindText();
                return;
            }

            if (SETTING.stageSpinner.getSelectedItem() == null)
            {
                Log.d("MainActivity", "stageSpinner.getSelectedItem() is null");
                setRemindText();
                return;
            }

            setGreet(SETTING.stageSpinner.getSelectedItem().toString());
        });

        emojiButton.setOnClickListener(e->
        {
            Spinner stageSpinner = SETTING.stageSpinner;
            if (stageSpinner == null || stageSpinner.getSelectedItem() == null)
                return;

            String getMes = textField.getText().toString();
            if (!saveBlesses.get(0).isEmpty())
            {
                if (getMes.contains(" "))
                {
                    String replace = "\u3297\uFE0F";
                    String blessWithEmoji = saveBlesses.get(0);
                    String blessWithoutEmoji = removeEmoji(blessWithEmoji);
                    String noEmojiMessage = getMes.substring(0, getMes.lastIndexOf(" ")).replace(replace,"祝");
                    String emojiReplacedMessage = noEmojiMessage.replace("祝", replace);

                    String text = !emojiMode ? blessWithEmoji : blessWithoutEmoji;
                    String message = !emojiMode ? emojiReplacedMessage : noEmojiMessage;
                    int shadowColor = emojiMode ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
                    String differentEmojiEffect = emojiMode ? "\uD83D\uDE04" : "\uD83D\uDE36"; // ^v^ & :

                    emojiButton.setText(differentEmojiEffect);
                    emojiButton.setShadowLayer(40f, 0f , 0f, shadowColor);

                    emojiMode = !emojiMode;
                    String textWithEmoji = message + " " + text;

                    addRecord(textWithEmoji);
                    textField.setText(textWithEmoji);
                }
            }
        });

        showBlessing.setOnClickListener(v ->
        {
            Spinner stageSpinner = SETTING.stageSpinner;
            if (stageSpinner == null || stageSpinner.getSelectedItem() == null)
                return;

            String stage = stageSpinner.getSelectedItem().toString();
            String[] blessArrays = BLESS_MANAGER.getGreetingsByStage(stage);

            StringBuilder greet = new StringBuilder();
            for (int i=0; i<blessArrays.length; i++)
            {
                String arrays = blessArrays[i];
                if (i != blessArrays.length-1)
                    arrays += i % 2 == 0 ? "、" : "\n" ;
                greet.append(arrays);
            }

            show_Greet = "\n" + greet + "\n";
            String text;
            String check = textField.getText().toString();

            boolean shown = check.equals(show_Greet);
            text = shown ? RECORDS.get(1) : show_Greet;
            emojiButton.setVisibility(shown ? View.VISIBLE : View.INVISIBLE);

            float size = 24f;
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
                String noEmoji = removeEmoji(ysEmoji);
                String mesDecide = emojiMode? ysEmoji : noEmoji;
                String yn = emojiMode? "y" : "n" ;

                if (textMode.equals("mail"))
                    setBlessingLineText(rankDefault, yn, mesDecide);
                else if (textMode.equals("line"))
                    setBlessingMail(rankDefault, yn, mesDecide);
            }
        });

        copyButton.setOnClickListener(e ->
        {
            String getGreet = textField.getText().toString();
            if (!getGreet.isEmpty())
            {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null, getGreet);
                clipboard.setPrimaryClip(clip);
            }
        });

        homeButton.setOnClickListener(v -> {});

        settingButton.setOnClickListener(v ->
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag("CustomSetting");

            if (fragment == null)
                SETTING.show(getSupportFragmentManager(), "CustomSetting");
        });

        tutorialButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zero_ani, R.anim.zero_ani);
        });
    }

    private void setGreet(String selected)
    {
        switch (selected)
        {
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

        emojiButton.setVisibility(View.VISIBLE);
        settingButton.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rect_round45));
        textField.setTextColor(Color.BLACK);
        String ysEmoji = newGreet(blessArrays);
        String noEmoji = removeEmoji(ysEmoji);
        saveBlesses.set(0, ysEmoji);

        String mesDecide = emojiMode ? ysEmoji : noEmoji;
        String yn = emojiMode ? "y" : "n" ;

        if (textMode.equals("mail"))
            setBlessingMail(rankDefault, yn, mesDecide);
        if (textMode.equals("line"))
            setBlessingLineText(rankDefault, yn, mesDecide);
    }

    private void setRemindText()
    {
        nullSpin = !nullSpin;
        textField.setTextSize(33);
        textField.setText(R.string.tutorial);
        int flash = !nullSpin ? R.drawable.red_o10_out7 : R.drawable.dark_red_o10_out7;
        int color = !nullSpin ? R.color.dark_red : R.color.red;
        settingButton.setBackground(ContextCompat.getDrawable(MainActivity.this, flash));
        textField.setTextColor(getResources().getColor(color));
    }

    public void setBlessingMail(String rankLevel, String emoji, String greet)
    {
        textMode = "mail";
        SETTING.rankLevelColourChange(rankLevel);
        String dear = "親愛的 ";
        String name = SETTING.targetName.getText().toString();
        String wish = Objects.equals(emoji, "y") ? " \u3297\uFE0F" : " 祝";
        String happyNewYear = "新年快樂!";
        String formatAlign = "\n  "; // important for emoji

        if (!name.isEmpty())
            name += !rankDefault.equals("senior") ? ":\n" : "";

        if (rankLevel.equals("junior"))
            name = surname(name);

        String dearY = dear + name + ":\n " + wish + "您" + happyNewYear + formatAlign + greet;
        String dearN = name + wish + "你" + happyNewYear + formatAlign + greet;

        String sentence;
        sentence = rankLevel.equals("senior") ? dearY : dearN;

        addRecord(sentence);
        textField.setTextSize(24f);
        textField.setText(sentence);
    }

    public void setBlessingLineText(String rankLevel, String emoji, String greet)
    {
        textMode = "line";
        SETTING.rankLevelColourChange(rankLevel);

        String zhu = Objects.equals(emoji, "y") ? "\u3297\uFE0F" : "祝";
        String names = SETTING.targetName.getText().toString();

        if (rankLevel.equals("junior"))
            names = surname(names);

        String sentence = zhu + names + "新年快樂!  " + greet;
        addRecord(sentence);
        textField.setTextSize(24f);
        textField.setText(sentence);
    }

    public String removeEmoji(String twoGreets)
    {
        String[] greets = twoGreets.split("、");
        String greet1 = greets[0].substring(0,greets[0].length()-2);
        String greet2 = greets[1].substring(0,greets[1].length()-2);
        return greet1 + "、" + greet2;
    }

    private Boolean checkEnglish(String name)
    {
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (char c : name.toCharArray())
        {
            for (char charABC : abc.toCharArray())
            {
                if (c == charABC)
                    return true;
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