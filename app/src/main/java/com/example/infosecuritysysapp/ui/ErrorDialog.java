package com.example.infosecuritysysapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.infosecuritysysapp.R;

import org.w3c.dom.Text;

public class ErrorDialog extends Dialog
{

    public ErrorDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.error_dialog);
        this.findViewById(R.id.done_btn).setOnClickListener(view -> dismiss());
    }


    public ErrorDialog setErrorMessage(String errorMessage){
        TextView view = this.findViewById(R.id.error_txt);
        view.setText(errorMessage);
        return this;
    }

    @Override
    public void show() {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // match width dialog
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.show();
    }
}
