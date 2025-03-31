package com.CYZco.nygreets;

import android.util.Log;
import android.os.Build;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.text.Editable;
import android.widget.Spinner;
import android.view.ViewGroup;
import android.widget.EditText;
import android.graphics.Shader;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.graphics.RenderEffect;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class Settings extends DialogFragment
{
    private View rootView;
    public EditText name;
    public Spinner stageSpinner;
    public Spinner eventSpinner;
    public Spinner youSpinner;
    public Button dear;
    private boolean textMode = true; //true-senior, false-without dear
    private MainActivity mainActivity;
    private final BlessManager BLESS_MANAGER = new BlessManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        rootView = view.findViewById(R.id.settings_dialog);
        stageSpinner = view.findViewById(R.id.stage);
        eventSpinner = view.findViewById(R.id.event);
        youSpinner = view.findViewById(R.id.you);
        dear = view.findViewById(R.id.dear);
        name = view.findViewById(R.id.name);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> stageAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner, BLESS_MANAGER.STAGES);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner, BLESS_MANAGER.EVENTS);
        ArrayAdapter<String> youAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner, BLESS_MANAGER.YOU);

        // Set layout for the dropdown
        stageAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        eventAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        youAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        // Apply the adapter to the spinner
        stageSpinner.setAdapter(stageAdapter);
        eventSpinner.setAdapter(eventAdapter);
        youSpinner.setAdapter(youAdapter);

        // real time update decided option to blessing text
        realTimeUpdate(dear);
        realTimeUpdate(stageSpinner);
        realTimeUpdate(eventSpinner);
        realTimeUpdate(youSpinner);
        realTimeUpdate(name);

        dear.setTextColor(ContextCompat.getColor(requireContext(), !textMode ? R.color.white : R.color.white_a25));
    }

    // real-time update name to blessing text when editing
    private void realTimeUpdate(EditText editText)
    {
        if (isAdded() && getActivity() == null)
            return;

        mainActivity = (MainActivity) requireActivity();
        final boolean[] isFirstTriggered = {true};

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (isAdded() && getActivity() == null)
                    return;

                if (isFirstTriggered[0])
                {
                    isFirstTriggered[0] = false;
                    return;
                }
                blessTextUpdate();
            }
        });
    }

    // real-time update spinner to blessing text when selecting
    private void realTimeUpdate(Spinner spinner)
    {
        if (isAdded() && getActivity() == null)
            return;

        mainActivity = (MainActivity) requireActivity();

        // vanish the spinner to avoid overlaying
        spinner.setOnTouchListener((v, event) ->
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                spinner.performClick();
                if (spinner.isShown())
                    spinner.setAlpha(0f);
            }
            return false;
        });

        // avoid spinner vanishing, when not clicking blank area to quit


        // avoid spinner vanishing, when selecting the same option
        spinner.getViewTreeObserver().addOnGlobalLayoutListener(() -> spinner.setAlpha(1f));

        // avoid spinner vanishing, when selected an option
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (isAdded() && getActivity() != null)
                {
                    spinner.setAlpha(1f);
                    blessTextUpdate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // update, refresh blessing text,
    // the method should be used within -> isAdded() && getActivity() != null
    private void blessTextUpdate()
    {
        // Initialize views
        mainActivity.buildBless("mail");

        // assign updated values to the mainActivity
        mainActivity.you = youSpinner.getSelectedItem().toString();
        mainActivity.event = eventSpinner.getSelectedItem().toString();
        mainActivity.name = name.getText().toString();

        // Update the text field
        mainActivity.addRecord(mainActivity.textField.getText().toString());
    }

    // navigate decision on adding "dear"
    private void realTimeUpdate(Button button)
    {
        if (isAdded() && getActivity() != null)
        {
            mainActivity = (MainActivity) requireActivity();
            button.setOnClickListener(e ->
            {
                if (isAdded() && getActivity() != null)
                {
                    mainActivity.rankDefault = textMode ? "with dear" : "without dear";
                    textMode = !textMode;
                    dearButtonSetting();
                }
            });
        }
    }

    // update text colours
    public void dearButtonSetting()
    {
        // real-time update text colours
        dear.setTextColor(ContextCompat.getColor(requireContext(), !textMode ? R.color.white : R.color.white_a25));

        // update blessing text
        if (!mainActivity.saveBlesses.get(0).isEmpty())
        {
            mainActivity.buildBless("mail");
            mainActivity.addRecord(mainActivity.textField.getText().toString());
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d("CustomSetting", "onStart called for CustomSetting fragment");
        if (getDialog() != null && getDialog().getWindow() != null)
        {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Blur the activity's root view
            if(isAdded())
            {
                rootView = requireActivity().getWindow().getDecorView().getRootView();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    rootView.setRenderEffect(RenderEffect.createBlurEffect(10, 10, Shader.TileMode.MIRROR));
            }
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        // Remove blur effect when the dialog is dismissed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && rootView != null)
            rootView.setRenderEffect(null);
    }
}