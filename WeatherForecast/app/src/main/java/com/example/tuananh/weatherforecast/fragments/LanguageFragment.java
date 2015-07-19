package com.example.tuananh.weatherforecast.fragments;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.activitys.SettingActivity;
import com.example.tuananh.weatherforecast.other.SettingShare;

import java.util.Locale;

public class LanguageFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    RadioGroup rg_language;
    Button btn_save;
    private String language = "";
    FragmentManager mManeger;

    public LanguageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        language = new SettingShare(getActivity())
                .getShare(SettingShare.LANGUAGE, Locale.getDefault().getLanguage());
        rg_language = (RadioGroup) view.findViewById(R.id.rg_langauge);
        btn_save = (Button) view.findViewById(R.id.btn_save_language);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SettingShare(getActivity()).saveShare(
                        SettingShare.LANGUAGE, language
                );
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        rg_language.setOnCheckedChangeListener(this);

        switch (language) {
            case "vi":
                rg_language.check(R.id.rb_languagevn);
                break;
            case "en":
                rg_language.check(R.id.rb_english);
                break;
            default:
                rg_language.check(R.id.rb_english);
                break;
        }

        mManeger = getActivity().getFragmentManager();

        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_english:
                language= SettingShare.LANGUAGE_DEFALUT;
                break;
            case R.id.rb_languagevn:
                language =SettingShare.LANGUAGE_VI;
        }
    }


}
