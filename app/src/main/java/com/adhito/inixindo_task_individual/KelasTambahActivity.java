package com.adhito.inixindo_task_individual;

import androidx.appcompat.app.AppCompatActivity;

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

public class KelasTambahActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;
    EditText edit_tgl_mulai_kls, edit_tgl_akhir_kls, edit_id_ins, edit_id_mat;
    Button btn_tambah_kelas, btn_lihat_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_tambah);

        edit_tgl_mulai_kls = findViewById(R.id.edit_tgl_mulai_kls);
        edit_tgl_akhir_kls = findViewById(R.id.edit_tgl_akhir_kls);
        edit_id_ins = findViewById(R.id.edit_id_ins);
        edit_id_mat = findViewById(R.id.edit_id_mat);

        btn_tambah_kelas = findViewById(R.id.btn_tambah_kelas);
        btn_lihat_kelas = findViewById(R.id.btn_lihat_kelas);

        btn_tambah_kelas.setOnClickListener(this);
        btn_lihat_kelas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tambah_kelas:
                simpanDataKelas();
                startActivity(new Intent(KelasTambahActivity.this, MainActivity.class));
                break;
            case R.id.btn_lihat_kelas:
                startActivity(new Intent(KelasTambahActivity.this, MainActivity.class));
                break;
        }
    }

    private void simpanDataKelas() {
        final String tgl_mulai_kls = edit_tgl_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = edit_tgl_akhir_kls.getText().toString().trim();
        final String id_ins = edit_id_ins.getText().toString().trim();
        final String id_mat = edit_id_mat.getText().toString().trim();

        class SimpanDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasTambahActivity.this,
                        "Menyimpan Data", "Harap Tunggu ...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // params digunakan untuk meyimpan ke HttpHandler
                HashMap<String, String> params = new HashMap<>();
                params.put("tgl_mulai_kls", tgl_mulai_kls);
                params.put("tgl_akhir_kls", tgl_akhir_kls);
                params.put("id_ins", id_ins);
                params.put("id_mat", id_mat);
                HttpHandler handler = new HttpHandler();
                // HttpHandler untuk kirim data pakai sendPostRequest
                String result = handler.sendPostRequest(Konfigurasi.URL_KELAS_ADD, params);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(KelasTambahActivity.this, "pesan:" + s,
                        Toast.LENGTH_SHORT).show();
                // method untuk clear setelah data ditambah di form
                clearText();
            }
        }
        SimpanDataKelas simpanDataKelas = new SimpanDataKelas();
        simpanDataKelas.execute();

    }

    private void clearText() {
        edit_tgl_mulai_kls.setText("");
        edit_tgl_akhir_kls.setText("");
        edit_id_ins.setText("");
        edit_id_mat.setText("");
        edit_tgl_mulai_kls.requestFocus();
    }
}