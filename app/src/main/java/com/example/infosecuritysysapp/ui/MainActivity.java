package com.example.infosecuritysysapp.ui;

import static com.example.infosecuritysysapp.config.AppSharedPreferences.InitSharedPreferences;
import static com.example.infosecuritysysapp.helper.FN.MAIN_FC;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.infosecuritysysapp.R;
import com.example.infosecuritysysapp.databinding.ActivityMainBinding;
import com.example.infosecuritysysapp.helper.FN;

import com.example.infosecuritysysapp.network.SocketIO;
import com.example.infosecuritysysapp.ui.fragments.auth.LoginFragment;



public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        SocketIO.getInstance().initWebSocketAndConnect();

        InitSharedPreferences(this);
        FN.addFixedNameFadeFragment(MAIN_FC,this,new LoginFragment());
    }
}