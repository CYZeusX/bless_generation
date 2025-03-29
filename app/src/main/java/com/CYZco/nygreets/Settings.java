package com.CYZco.nygreets;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.ViewGroup;
import android.graphics.Shader;
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
    public EditText targetName;
    public Spinner stageSpinner;
    public Spinner eventSelector;
    public Spinner youSelector;
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
        stageSpinner = view.findViewById(R.id.stage_spinner);
        eventSelector = view.findViewById(R.id.festival);
        youSelector = view.findViewById(R.id.you);
        dear = view.findViewById(R.id.dear);
        targetName = view.findViewById(R.id.name);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, BLESS_MANAGER.STAGES);
        ArrayAdapter<String> festivalAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, BLESS_MANAGER.FESTIVALS);
        ArrayAdapter<String> youAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, BLESS_MANAGER.YOU);

        // Set layout for the dropdown
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        festivalAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        youAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        stageSpinner.setAdapter(adapter);
        eventSelector.setAdapter(festivalAdapter);
        youSelector.setAdapter(youAdapter);

        // real time update decided option to blessing text
        dearButton(dear);
        realTimeUpdate(stageSpinner);
        realTimeUpdate(eventSelector);
        realTimeUpdate(youSelector);

        dear.setTextColor(ContextCompat.getColor(requireContext(), !textMode ? R.color.gray_200 : R.color.gray_64));
    }

    private void realTimeUpdate(Spinner spinner)
    {
        if (isAdded() && getActivity() != null)
        {
            mainActivity = (MainActivity) requireActivity();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if (isAdded() && getActivity() != null)
                        blessTextUpdate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
    }

    private void blessTextUpdate()
    {
        // should be use the method within isAdded() && getActivity() != null

        // Initialize views
        mainActivity.buildBless("mail");

        // assign updated values to the mainActivity
        mainActivity.you = youSelector.getSelectedItem().toString();
        mainActivity.event = eventSelector.getSelectedItem().toString();

        // Update the text field
        mainActivity.addRecord(mainActivity.textField.getText().toString());
    }

    private void dearButton(Button button)
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

    public void dearButtonSetting()
    {
        // real-time update text colours
        dear.setTextColor(ContextCompat.getColor(requireContext(), !textMode ? R.color.gray_200 : R.color.gray_64));

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