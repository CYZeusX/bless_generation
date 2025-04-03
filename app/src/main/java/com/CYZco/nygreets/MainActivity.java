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
    public TextView textField;
    public String rankDefault = "without dear";
    public String textMode = "mail";
    public String show_Greet;
    public String name = "";
    public String you = "你";
    public String event = "";
    private final ArrayList<String> RECORDS = new ArrayList<>(2);
    private final Settings SETTING = new Settings();
    private final BlessManager BLESS_MANAGER = new BlessManager();

    private String[] kidBlesses;
    private String[] teenBlesses;
    private String[] adultBlesses;
    private String[] elderBlesses;
    public ArrayList<String> saveBlesses = new ArrayList<>();

    public void addRecord(String s)
    {
        if(RECORDS.size() == 2)
            RECORDS.remove(0);
        RECORDS.add(s);
    }

    private String newRandomBless(String[] blessArrays)
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

        String event = SETTING.eventSpinner == null ? "新年快樂" : SETTING.eventSpinner.getSelectedItem().toString();
        BLESS_MANAGER.setGreetingsByEvent(event);
        assignBlessByStage();

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

        // populate the records
        saveBlesses.add("");
        RECORDS.add(textField.getText().toString());
        RECORDS.add(textField.getText().toString());

        // algorithms to initialize the elements
        bless.setLayerType(View.LAYER_TYPE_SOFTWARE, bless.getPaint());
        bless.setShadowLayer(150f, 0f, 0f, getResources().getColor(R.color.yellow_a50));
        settingButton.setVisibility(View.INVISIBLE);
        showTextScroller.setVerticalScrollBarEnabled(false);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        generatePlace.startAnimation(slideUp);

        generateButton.setOnClickListener(v ->
        {
            Spinner stageSpinner = SETTING.stageSpinner;
            Spinner eventSpinner = SETTING.eventSpinner;
            String events = eventSpinner == null ? "新年快樂" : eventSpinner.getSelectedItem().toString();
            BLESS_MANAGER.setGreetingsByEvent(events);

            if (stageSpinner == null)
            {
                Log.d("MainActivity", "stageSpinner is null");
                setBless("成年");
                return;
            }

            setBless(stageSpinner.getSelectedItem().toString());
        });

        emojiButton.setOnClickListener(e->
        {
            String getMessage = textField.getText().toString();
            if (saveBlesses.get(0).isEmpty())
                return;

            if (!getMessage.contains(" "))
                return;

            String wishEmoji = "\u3297\uFE0F";
            String blessWithEmoji = saveBlesses.get(0);
            String blessWithoutEmoji = removeEmoji(blessWithEmoji);
            String noEmojiMessage = getMessage.substring(0, getMessage.lastIndexOf(" ")).replace(wishEmoji,"祝");
            String emojiReplacedMessage = noEmojiMessage.replace("祝", wishEmoji);

            String blesses = !emojiMode ? blessWithEmoji : blessWithoutEmoji;
            String message = !emojiMode ? emojiReplacedMessage : noEmojiMessage;
            int shadowColor = emojiMode ? getResources().getColor(R.color.green) : getResources().getColor(R.color.red);
            String differentEmojiEffect = emojiMode ? "\uD83D\uDE04" : "\uD83D\uDE36"; // :D & :|

            emojiButton.setText(differentEmojiEffect);
            emojiButton.setShadowLayer(40f, 0f , 0f, shadowColor);

            emojiMode = !emojiMode;
            String textWithEmoji = message + " " + blesses;

            addRecord(textWithEmoji);
            textField.setText(textWithEmoji);
        });

        showBlessing.setOnClickListener(v ->
        {
            Spinner stageSpinner = SETTING.stageSpinner;
            Spinner eventSpinner = SETTING.eventSpinner;

            // get the event and stage
            String events = eventSpinner == null ? "新年快樂" : SETTING.eventSpinner.getSelectedItem().toString();
            String stage = stageSpinner == null ? "成年" : SETTING.stageSpinner.getSelectedItem().toString();

            // get the greetings
            BLESS_MANAGER.setGreetingsByEvent(events);
            String[] blessArrays = BLESS_MANAGER.getGreetingsByStage(stage);
            StringBuilder greet = new StringBuilder();

            // build the blessing sentence
            for (int i=0; i< blessArrays.length; i++)
            {
                String arrays = blessArrays[i];
                if (i != blessArrays.length-1)
                    arrays += i % 2 == 0 ? "、" : "\n" ;
                greet.append(arrays);
            }

            // fine tune the text style, and show the greetings
            show_Greet = "\n" + greet + "\n";
            String text;
            String check = textField.getText().toString();

            // algorithms to store text cache, and switch emoji button visibility
            boolean shown = check.equals(show_Greet);
            text = shown ? RECORDS.get(1) : show_Greet;
            emojiButton.setVisibility(shown ? View.VISIBLE : View.INVISIBLE);

            // fine tune the text style
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
                buildBless("line");
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

    private void assignBlessByStage()
    {
        kidBlesses = BLESS_MANAGER.getGreetingsByStage("童年");
        teenBlesses = BLESS_MANAGER.getGreetingsByStage("青年");
        adultBlesses = BLESS_MANAGER.getGreetingsByStage("成年");
        elderBlesses = BLESS_MANAGER.getGreetingsByStage("老年");
    }

    public String[] assignBlesses(String selected)
    {
        // initialize the spinner, and get the event
        Spinner eventSpinner = SETTING.eventSpinner;
        String events = eventSpinner == null ? "新年快樂" : eventSpinner.getSelectedItem().toString();

        // set and assign corresponding greetings
        BLESS_MANAGER.setGreetingsByEvent(events);
        assignBlessByStage();

        return switch (selected)
        {
            case "童年" -> kidBlesses;
            case "青年" -> teenBlesses;
            case "成年" -> adultBlesses;
            case "老年" -> elderBlesses;
            default -> null;
        };
    }

    private void setBless(String selectedStage)
    {
        textField.setTextColor(Color.BLACK);
        emojiButton.setVisibility(View.VISIBLE);
        settingButton.setVisibility(View.VISIBLE);
        settingButton.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.rect_round45));

        Spinner eventSpinner = SETTING.eventSpinner;
        String events = eventSpinner == null ? "新年快樂" : eventSpinner.getSelectedItem().toString();
        BLESS_MANAGER.setGreetingsByEvent(events);

        String onEmoji = newRandomBless(assignBlesses(selectedStage));
        saveBlesses.set(0, onEmoji);
        devBuildBless(onEmoji, "mail");
    }

    public void buildBless(String textModeDecision)
    {
        String onEmoji = saveBlesses.get(0);
        devBuildBless(onEmoji, textModeDecision);
    }

    private void devBuildBless(String onEmoji, String textModeDecision)
    {
        String offEmoji = removeEmoji(onEmoji);
        String messageDecision = emojiMode? onEmoji : offEmoji;
        String emojiDecision = emojiMode ? "y" : "n" ;
        textModeDecision = textModeDecision.equals("mail") ? "mail" : "line";

        if (textMode.equals(textModeDecision))
            setBlessingMail(rankDefault, emojiDecision, messageDecision);
        else setBlessingLineText(emojiDecision, messageDecision);
    }

    public void setBlessingMail(String rankDefault, String emoji, String greet)
    {
        // set the text mode
        textMode = "mail";

        // algorithms to set the elements in the blessing sentence
        String dear = "親愛的 ";
        name = SETTING.name == null ? "某某某" : SETTING.name.getText().toString();
        String wish = Objects.equals(emoji, "y") ? "\u3297\uFE0F" : "祝";
        you = SETTING.youSpinner == null ? "你" : SETTING.youSpinner.getSelectedItem().toString();
        event = SETTING.eventSpinner == null ? "新年快樂" : SETTING.eventSpinner.getSelectedItem().toString();
        String formatAlign = "\n "; // important for emoji

        // algorithms to set the name
        if (!name.isEmpty())
            name += !this.rankDefault.equals("with dear") ? ":\n" : "";

        // build the blessing sentence, for having "親愛的"
        String dearOn =
                dear + name + ":" + formatAlign +
                wish + you + event + "!" + formatAlign + greet;

        // build the blessing sentence, for without "親愛的"
        String dearOff =
                name + " " +
                wish + you + event + "!" + formatAlign + greet;

        // algorithms to decide the sentence
        String sentence = rankDefault.equals("with dear") ? dearOn : dearOff;

        // add record, fine tune the text style
        addRecord(sentence);
        textField.setTextSize(24f);
        textField.setText(sentence);
    }

    public void setBlessingLineText(String emoji, String greet)
    {
        textMode = "line";

        // algorithms to set the word for wish
        String wish = Objects.equals(emoji, "y") ? "\u3297\uFE0F" : "祝";

        // algorithms to set the name
        String name = SETTING.name != null ? SETTING.name.getText().toString() : "某某某";
        you = SETTING.youSpinner == null ? "你" : SETTING.youSpinner.getSelectedItem().toString();
        if (name.isEmpty()) name = you;

        // algorithms to set the event
        event = SETTING.eventSpinner == null ? "新年快樂" : SETTING.eventSpinner.getSelectedItem().toString();

        // build the blessing sentence
        String sentence = wish + name + event + "! " + greet;

        // add record, fine tune the text style
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
}