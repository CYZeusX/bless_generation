package com.CYZco.nygreets;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.graphics.RenderEffect;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class CustomSetting extends DialogFragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] STAGES = {"(選階段)", "童年", "青年", "成年", "老年"};
        Spinner stageSpinner = view.findViewById(R.id.stage_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, STAGES);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        stageSpinner.setAdapter(adapter);
        stageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                setSpinner(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setSpinner(String selected) {
        boolean isDefault = selected.equals("(選階段)");

        // Access the main activity safely
        MainActivity mainActivity = (MainActivity) requireActivity();

        Button emojiButton = mainActivity.emojiButton;
        TextView textField = mainActivity.textField;
        Spinner stageSpinner = mainActivity.stageSpinner;

        int spinnerDrawable = R.drawable.rect_round10_stroke1;

        if (isDefault)
        {
            textField.setTextSize(30);
            textField.setTextColor(Color.GRAY);
            textField.setText(R.string.tutorial);
        }

        stageSpinner.setBackground(ContextCompat.getDrawable(mainActivity, spinnerDrawable));
        mainActivity.byDefault(!isDefault);

        if (textField.getText().toString().equals(mainActivity.show_Greet)) {
            emojiButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Blur the activity's root view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                rootView = requireActivity().getWindow().getDecorView().getRootView();
                rootView.setRenderEffect(
                        RenderEffect.createBlurEffect(10, 10, Shader.TileMode.MIRROR)
                );
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
