package com.example.aplikasiujian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aplikasiujian.Home.HomeFragment;
import com.example.aplikasiujian.Nilai.NilaiFragment;
import com.example.aplikasiujian.Profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;
    private boolean doubleback;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeFragment(new HomeFragment(), HomeFragment.class.getSimpleName());

        bottomView();

    }

    private void bottomView() {
        Intent intent = getIntent();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        changeFragment(new HomeFragment(), HomeFragment.class.getSimpleName());
                        break;
                    case R.id.nilai:
                        changeFragment(new NilaiFragment(), NilaiFragment.class.getSimpleName());
                        break;
                    case R.id.profile:
                        changeFragment(new ProfileFragment(), ProfileFragment.class.getSimpleName());
                        break;
                }
                return true;
            }
        });
    }

    public void changeFragment(Fragment fragment, String tagFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment current = fragmentManager.getPrimaryNavigationFragment();
        if (current != null) {
            fragmentTransaction.hide(current);
        }

        Fragment temp = fragmentManager.findFragmentByTag(tagFragment);
        if (temp == null) {
            temp = fragment;
            fragmentTransaction.add(R.id.fragment_container, temp, tagFragment);
        } else {
            fragmentTransaction.show(temp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(temp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        if (doubleback){
            toast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
        }else{
            toast = Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT);
            toast.show();
            doubleback = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleback = false;
                }
            },2000);
        }

    }

}
