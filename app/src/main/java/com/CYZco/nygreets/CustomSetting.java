package com.CYZco.nygreets;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.graphics.RenderEffect;
import androidx.fragment.app.DialogFragment;

public class CustomSetting extends DialogFragment
{
    private View rootView;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null)
        {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Blur the activity's root view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                rootView = requireActivity().getWindow().getDecorView().getRootView();
                rootView.setRenderEffect(
                        RenderEffect.createBlurEffect(10, 10, Shader.TileMode.CLAMP)
                );
            }
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        // Remove blur effect when the dialog is dismissed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && rootView != null)
        {
            rootView.setRenderEffect(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.custom_setting, container, false);
    }
}