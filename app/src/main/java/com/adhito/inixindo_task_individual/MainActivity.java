package com.adhito.inixindo_task_individual;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adhito.inixindo_task_individual.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private OnBackPressedListener onBackPressedListener;
    Toolbar toolbar;
    String myStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        // Custom Toolbar
        setSupportActionBar(binding.toolbar);

//        // Default fragment yang dibuka pertama kali. (menu pada saat aplikasi dibuka)
//        getSupportActionBar().setTitle("Home");
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frame_layout, new HomeFragment())
//                .commit();
//        binding.navView.setCheckedItem(R.id.nav_home);

        // Event-handling after updating/deleting/create
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Fragment fragmentMenu = null;
        myStr = "home";
        if(extras != null)
            if(extras != null){
                myStr = extras.getString("KeyName");
            } else {
                myStr = "home";
            }

        switch (myStr){
            case "home":
                //default fragment dibuka pertama kali
                getSupportActionBar().setTitle("Home");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new HomeFragment())
                        .commit();
                binding.navView.setCheckedItem(R.id.nav_home);
                break;

            case "Peserta":
                getSupportActionBar().setTitle("Peserta");
                fragmentMenu = new PesertaFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_materi);
                break;

            case "Instruktur":
                getSupportActionBar().setTitle("Instruktur");
                fragmentMenu = new InstrukturFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_instruktur);
                break;

            case "Materi":
                getSupportActionBar().setTitle("Materi");
                fragmentMenu = new MateriFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_materi);
                break;

            case "Kelas":
                getSupportActionBar().setTitle("Kelas");
                fragmentMenu = new KelasFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_kelas);
                break;

            case "Detail_Kelas":
                getSupportActionBar().setTitle("Detail_Kelas");
                fragmentMenu = new DetailKelasFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragmentMenu);
                binding.navView.setCheckedItem(R.id.nav_detail_kelas);
                break;
        }

        // Drawer Open
        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.open, R.string.close);

        // Drawer BackButton
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        // Drawer Syncronization
        toggle.syncState();

        // Menu navigasi dipilih
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            // Pindah ke fragment saat dipilih
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_form:
                        fragment = new FormFragment();
                        getSupportActionBar().setTitle("Form Fragment");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_peserta:
                        fragment = new PesertaFragment();
                        getSupportActionBar().setTitle("Peserta");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_instruktur:
                        fragment = new InstrukturFragment();
                        getSupportActionBar().setTitle("Instruktur");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_materi:
                        fragment = new MateriFragment();
                        getSupportActionBar().setTitle("Materi");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_kelas:
                        fragment = new KelasFragment();
                        getSupportActionBar().setTitle("Kelas");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_detail_kelas:
                        fragment = new DetailKelasFragment();
                        getSupportActionBar().setTitle("Detail Kelas");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;

                    case R.id.nav_detail_kelas_jumlah:
                        fragment = new DetailKelasJumlahPesertaFragment();
                        getSupportActionBar().setTitle("Detail Jumlah Peserta");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                }
                return true;
            }
        });

        NavigationView navigationView = findViewById(R.id.navView);
        View headerView = getLayoutInflater().inflate(R.layout.nav_header_layout, navigationView, false);
        navigationView.addHeaderView(headerView);

    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null){
            getSupportActionBar().setTitle("Home Fragment");
            binding.navView.setCheckedItem(R.id.nav_home);
            onBackPressedListener.doBack();
            binding.drawer.closeDrawer(GravityCompat.START);

        }else if(onBackPressedListener == null){
            super.onBackPressed();
        }
    }

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}