package com.adhito.inixindo_task_individual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adhito.inixindo_task_individual.databinding.ActivityMainBinding;

import java.util.HashMap;

public class PesertaTambahActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    EditText edit_id_pst, edit_nama_pst, edit_email_pst, edit_hp_pst, edit_instansi_pst;
    Button btn_tambah_peserta, btn_lihat_peserta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta_tambah);

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
                confirmAddDataPeserta();
                break;
            case R.id.btn_lihat_peserta:
                fragment = new PesertaFragment();
                startActivity(new Intent(PesertaTambahActivity.this, MainActivity.class));
                break;
        }
    }

    private void confirmAddDataPeserta() {
        // Get confirmation to add participant (Name, E-mail, Phone and Instance)
        final String nama_pst = edit_nama_pst.getText().toString().trim();
        final String email_pst = edit_email_pst.getText().toString().trim();
        final String hp_pst = edit_hp_pst.getText().toString().trim();
        final String instansi_pst = edit_instansi_pst.getText().toString().trim();

        // Confirmation of alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menambahkan data");
        // builder.setMessage("Apakah anda ingin menambah peserta ini ? ");
        builder.setMessage("Apakah anda ingin menambah peserta ini ?" +
                "\n Nama \t\t\t: " + nama_pst +
                "\n Email \t\t\t\t: " + email_pst +
                "\n No Hp \t\t\t: " + hp_pst +
                "\n Instansi \t\t: " + instansi_pst);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataPeserta();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
                loading = ProgressDialog.show(PesertaTambahActivity.this,
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
                Toast.makeText(PesertaTambahActivity.this, "pesan:" + s,
                        Toast.LENGTH_SHORT).show();
                // method untuk clear setelah data ditambah di form
                clearText();
            }
        }
        SimpanDataPeserta simpanDataPeserta = new SimpanDataPeserta();
        simpanDataPeserta.execute();

        // Back to previous page after update
        Intent myIntent = new Intent(PesertaTambahActivity.this, MainActivity.class);
        myIntent.putExtra("KeyName", "Peserta");
        startActivity(myIntent);
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