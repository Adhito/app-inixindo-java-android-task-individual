package com.adhito.inixindo_task_individual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adhito.inixindo_task_individual.databinding.ActivityMainBinding;

import java.util.HashMap;

public class TambahPeserta extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    EditText edit_id_pst, edit_nama_pst, edit_email_pst, edit_hp_pst, edit_instansi_pst;
    Button btn_tambah_peserta, btn_lihat_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_peserta);

        edit_id_pst = findViewById(R.id.edit_id_pst);
        edit_nama_pst = findViewById(R.id.edit_nama_pst);
        edit_email_pst = findViewById(R.id.edit_email_pst);
        edit_hp_pst = findViewById(R.id.edit_hp_pst);
        edit_instansi_pst = findViewById(R.id.edit_instansi_pst);
        btn_tambah_peserta = findViewById(R.id.btn_tambah_peserta);
        btn_lihat_peserta = findViewById(R.id.btn_lihat_peserta);

        btn_tambah_peserta.setOnClickListener(this);
        btn_lihat_peserta.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_tambah_peserta:
                simpanDataPeserta();
                startActivity(new Intent(TambahPeserta.this, MainActivity.class));
                break;
            case R.id.btn_lihat_peserta:
                fragment = new PesertaFragment();
                startActivity(new Intent(TambahPeserta.this, MainActivity.class));
                break;
        }
    }

    private void simpanDataPeserta() {
        final String nama_pst = edit_nama_pst.getText().toString().trim();
        final String email_pst = edit_email_pst.getText().toString().trim();
        final String hp_pst = edit_hp_pst.getText().toString().trim();
        final String instansi_pst = edit_instansi_pst.getText().toString().trim();

        class SimpanDataPeserta extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahPeserta.this,
                        "Menyimpan Data", "Harap Tunggu ...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // params digunakan untuk meyimpan ke HttpHandler
                HashMap<String, String> params = new HashMap<>();
                params.put("nama_pst", nama_pst);
                params.put("email_pst", email_pst);
                params.put("hp_pst", hp_pst);
                params.put("instansi_pst", instansi_pst);
                HttpHandler handler = new HttpHandler();
                // HttpHandler untuk kirim data pakai sendPostRequest
                String result = handler.sendPostRequest(Konfigurasi.URL_PESERTA_ADD, params);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TambahPeserta.this, "pesan:" + s,
                        Toast.LENGTH_SHORT).show();
                // method untuk clear setelah data ditambah di form
                clearText();
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();

    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        transaction.replace(R.id.frame_layout, fragment);
        //menunya bergantian
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void clearText() {
        edit_nama_pst.setText("");
        edit_email_pst.setText("");
        edit_hp_pst.setText("");
        edit_instansi_pst.setText("");
        // untuk pointer langsung menuju kolom nama di layout
        edit_nama_pst.requestFocus();
    }
}