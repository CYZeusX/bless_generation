package com.CYZco.nygreets;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;
import android.widget.Spinner;
import android.view.ViewGroup;
import android.widget.TextView;
import android.graphics.Shader;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.graphics.RenderEffect;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class CustomSetting extends DialogFragment
{
    private View rootView;
    public Spinner stageSpinner;
    public Spinner festivalSelector;
    public Button juniorRankButton;
    public Button peerRankButton;
    public Button seniorRankButton;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {return inflater.inflate(R.layout.custom_setting, container, false);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        String[] STAGES = {"(選階段)", "童年", "青年", "成年", "老年"};
        String[] FESTIVALS = {"新年","父親節","母親節","生日"};
        stageSpinner = view.findViewById(R.id.stage_spinner);
        festivalSelector = view.findViewById(R.id.festival);
        juniorRankButton = view.findViewById(R.id.rank1);
        peerRankButton = view.findViewById(R.id.rank2);
        seniorRankButton = view.findViewById(R.id.rank3);

        rankButtonSetting(juniorRankButton, "junior");
        rankButtonSetting(peerRankButton, "peer");
        rankButtonSetting(seniorRankButton, "senior");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, STAGES);
        ArrayAdapter<String> festivalAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, FESTIVALS);

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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        festivalAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        festivalSelector.setAdapter(festivalAdapter);
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

    }

    private void rankButtonSetting(Button button, String rank)
    {
        mainActivity = (MainActivity) requireActivity();
        button.setOnClickListener(e ->
        {
            mainActivity.rankDefault = rank;
            rankButton();
        });
    }

    private void setSpinner(String selected)
    {
        boolean isDefault = selected.equals("(選階段)");

        // Access the main activity safely
        MainActivity mainActivity = (MainActivity) requireActivity();

        Button emojiButton = mainActivity.emojiButton;
        TextView textField = mainActivity.textField;

        int spinnerDrawable = R.drawable.rect_round10_stroke1;

        if (isDefault)
        {
            textField.setTextSize(30);
            textField.setTextColor(Color.GRAY);
            textField.setText(R.string.tutorial);
        }

        stageSpinner.setBackground(ContextCompat.getDrawable(mainActivity, spinnerDrawable));
        mainActivity.byDefault(!isDefault);

        if (textField.getText().toString().equals(mainActivity.show_Greet))
        {emojiButton.setVisibility(View.INVISIBLE);}
    }

    public void rankButton()
    {
        mainActivity = (MainActivity) requireActivity();
        checkRankLevel(mainActivity.rankDefault);
        MainActivity mainActivity = (MainActivity) requireActivity();

        if (!mainActivity.saveBlesses.get(0).isEmpty())
        {
            String greet = mainActivity.emojiMode ? mainActivity.saveBlesses.get(0) : mainActivity.cutEmoji(mainActivity.saveBlesses.get(0));
            String yn = mainActivity.emojiMode ? "y" : "n";

            if (mainActivity.textMode.equals("mail"))
            {
                mainActivity.setBlessingMail(mainActivity.rankDefault, yn, greet);}
            if (mainActivity.textMode.equals("line"))
            {
                mainActivity.setBlessingLineText(mainActivity.rankDefault, yn, greet);}
            mainActivity.addRecord(mainActivity.textField.getText().toString());
        }
    }

    public void checkRankLevel(String rankLevel)
    {
        MainActivity mainActivity = (MainActivity) requireActivity();
        Button[] rankButtons = new Button[]{juniorRankButton, peerRankButton, seniorRankButton};
        mainActivity.emojiButton.setVisibility(View.VISIBLE);

        // Map rankLevel to index
        int selectedIndex = 1; // Default to "peer" (index 1)
        if (rankLevel.equals("junior")) selectedIndex = 0;
        else if (rankLevel.equals("senior")) selectedIndex = 2;

        // Update button styles
        for (int i = 0; i < rankButtons.length; i++)
        {
            rankButtons[i].setTextSize(i == selectedIndex ? 20 : 18);
            rankButtons[i].setTextColor(ContextCompat.getColor(requireContext(),
                    i == selectedIndex ? R.color.gray_200 : R.color.gray_64));
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null)
        {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Blur the activity's root view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                rootView = requireActivity().getWindow().getDecorView().getRootView();
                rootView.setRenderEffect(RenderEffect.createBlurEffect(10, 10, Shader.TileMode.MIRROR));
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remove blur effect when the dialog is dismissed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && rootView != null) {
            rootView.setRenderEffect(null);
        }
    }
}
