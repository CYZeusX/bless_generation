package com.CYZco.nygreets;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
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
    public Spinner festivalSelector;
    public Button juniorRankButton;
    public Button peerRankButton;
    public Button seniorRankButton;
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

        stageSpinner = view.findViewById(R.id.stage_spinner);
        festivalSelector = view.findViewById(R.id.festival);
        juniorRankButton = view.findViewById(R.id.rank1);
        peerRankButton = view.findViewById(R.id.rank2);
        seniorRankButton = view.findViewById(R.id.rank3);
        targetName = view.findViewById(R.id.name);

        rankButtonSetting(juniorRankButton, "junior");
        rankButtonSetting(peerRankButton, "peer");
        rankButtonSetting(seniorRankButton, "senior");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, BLESS_MANAGER.STAGES);
        ArrayAdapter<String> festivalAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, BLESS_MANAGER.FESTIVALS);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        festivalAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        stageSpinner.setAdapter(adapter);
        festivalSelector.setAdapter(festivalAdapter);

        rankLevelColourChange(mainActivity.rankDefault);
    }

    private void rankButtonSetting(Button button, String rank)
    {
        if (isAdded() && getActivity() != null)
        {
            mainActivity = (MainActivity) requireActivity();
            button.setOnClickListener(e ->
            {
                if (isAdded() && getActivity() != null)
                {
                    mainActivity.rankDefault = rank;
                    rankButton();
                }
            });
        }
    }

    public void rankButton()
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
        {
            rankLevelColourChange(mainActivity.rankDefault);

            if (!mainActivity.saveBlesses.get(0).isEmpty())
            {
                mainActivity.buildBless("mail");
                mainActivity.addRecord(mainActivity.textField.getText().toString());
            }
        }
    }

    public void rankLevelColourChange(String rankLevel)
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
        {
            Button[] rankButtons = new Button[] {juniorRankButton, peerRankButton, seniorRankButton};

            // Map rankLevel to index
            int selectedIndex = switch (rankLevel)
            {
                case "junior" -> 0;
                case "peer" -> 1;
                case "senior" -> 2;
                default -> 1; // Default to "peer" (index 1)
            };

            // Update button styles
            for (int count = 0; count < rankButtons.length; count++)
            {
                rankButtons[count].setTextSize(count == selectedIndex ? 28 : 20);
                rankButtons[count].setTextColor(ContextCompat.getColor(requireContext(),
                        count == selectedIndex ? R.color.gray_200 : R.color.gray_64));
            }
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