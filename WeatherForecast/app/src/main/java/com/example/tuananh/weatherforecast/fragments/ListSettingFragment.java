package com.example.tuananh.weatherforecast.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.activitys.MainActivity;
import com.example.tuananh.weatherforecast.other.SettingShare;

public class ListSettingFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener{

    setOnClickLanuage clickLanguage;
    setOnClickTheme clickTheme;
    RadioGroup rg_temperature;
    Button btn_language;
    Button btn_theme;
    Button btn_exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_setting, container, false);

        rg_temperature = (RadioGroup) view.findViewById(R.id.rg_temperature);
        rg_temperature.setOnCheckedChangeListener(this);
        String temperature = new SettingShare(getActivity()).getShare(
                SettingShare.TEMPERATURE, ""
        );
        if(temperature.equals("") || temperature.equals("c"))
            rg_temperature.check(R.id.rb_c);
        else rg_temperature.check(R.id.rb_f);

        btn_language = (Button) view.findViewById(R.id.btn_language);
        btn_theme = (Button) view.findViewById(R.id.btn_background);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_language.setOnClickListener(this);
        btn_theme.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        return view;
    }

    // TODO: 13/07/2015
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            clickLanguage = (setOnClickLanuage) activity;
            clickTheme = (setOnClickTheme) activity;
        } catch (ClassCastException e) {
            Log.e("ERROR onAttach", activity.toString() + " do implements interface");
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        SettingShare setting = new SettingShare(getActivity());
        switch (checkedId){
            case R.id.rb_c:
                setting.saveShare(SettingShare.TEMPERATURE, "c");
                break;
            case R.id.rb_f:
                setting.saveShare(SettingShare.TEMPERATURE, "f");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_background:
                clickTheme.onClickTheme();
                break;
            case R.id.btn_language:
                clickLanguage.onClickLanguage();
                break;
            case R.id.btn_exit:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }

    public interface setOnClickLanuage{
        void onClickLanguage();
    }
    public interface setOnClickTheme{
        void onClickTheme();
    }
}
