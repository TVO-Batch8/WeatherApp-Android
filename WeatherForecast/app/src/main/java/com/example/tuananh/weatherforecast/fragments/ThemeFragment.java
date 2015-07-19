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
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.tuananh.weatherforecast.R;
import com.example.tuananh.weatherforecast.activitys.SettingActivity;
import com.example.tuananh.weatherforecast.other.SettingShare;

public class ThemeFragment extends Fragment implements OnCheckedChangeListener,
        View.OnClickListener {
    RadioGroup rg_theme;
    Button btn_save;
    private int mTheme = 0;
    FragmentManager mManeger;
    public static final int BLACK_THEME = 1;
    public static final int PINK_THEME = 2;
    public static final int RED_THEME = 3;
    public static final int DEFAULE_THEME = 4;

    public ThemeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);

        rg_theme = (RadioGroup) view.findViewById(R.id.rg_theme);
        btn_save = (Button) view.findViewById(R.id.btn_save_theme);

        btn_save.setOnClickListener(this);
        rg_theme.setOnCheckedChangeListener(this);

        int theme = new SettingShare(getActivity())
                .getShareInt(SettingShare.THEME, 0);

        setCheck(theme);
        mManeger = getActivity().getFragmentManager();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_theme:
                new SettingShare(getActivity()).saveShareInt(
                        SettingShare.THEME, mTheme
                );
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_back:
                mTheme = BLACK_THEME;
                break;
            case R.id.rb_pink:
                mTheme = PINK_THEME;
                break;
            case R.id.rb_red:
                mTheme = RED_THEME;
                break;
            case R.id.rb_default:
                mTheme = DEFAULE_THEME;
                break;
        }
    }

    public void setCheck(int choose){
        switch (choose){
            case BLACK_THEME:
                rg_theme.check(R.id.rb_back);
                break;
            case RED_THEME:
                rg_theme.check(R.id.rb_red);
                break;
            case PINK_THEME:
                rg_theme.check(R.id.rb_pink);
                break;
            case DEFAULE_THEME:
                rg_theme.check(R.id.rb_default);
                break;
            default:
                rg_theme.check(R.id.rb_default);
                break;
        }
    }
}
