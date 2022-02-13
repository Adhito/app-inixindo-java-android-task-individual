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

public class InstrukturTambahActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    EditText edit_id_ins, edit_nama_ins, edit_email_ins, edit_hp_ins, edit_instansi_ins;
    Button btn_tambah_instruktur, btn_lihat_instruktur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruktur_tambah);

        edit_id_ins = findViewById(R.id.edit_id_ins);
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_email_ins = findViewById(R.id.edit_email_ins);
        edit_hp_ins = findViewById(R.id.edit_hp_ins);
        btn_tambah_instruktur = findViewById(R.id.btn_tambah_instruktur);
        btn_lihat_instruktur = findViewById(R.id.btn_lihat_instruktur);
        btn_tambah_instruktur.setOnClickListener(this);
        btn_lihat_instruktur.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_tambah_instruktur:
                confirmAddDataInstruktur();
                break;
            case R.id.btn_lihat_instruktur:
                fragment = new PesertaFragment();
                startActivity(new Intent(InstrukturTambahActivity.this, MainActivity.class));
                break;
        }
    }

    private void confirmAddDataInstruktur() {
        // Get confirmation to add participant (Name, E-mail, Phone and Instance)
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String email_ins = edit_email_ins.getText().toString().trim();
        final String hp_ins = edit_hp_ins.getText().toString().trim();

        // Show confirmation of alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menambahkan data");
        // builder.setMessage("Apakah anda ingin menambah peserta ini ? ");
        builder.setMessage("Apakah anda ingin menambah peserta ini ?" +
                "\n Nama \t\t\t: " + nama_ins +
                "\n Email \t\t\t\t: " + email_ins +
                "\n No Hp \t\t\t: " + hp_ins );
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataInstruktur();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataInstruktur() {
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String email_ins = edit_email_ins.getText().toString().trim();
        final String hp_ins = edit_hp_ins.getText().toString().trim();

        class SimpanDataInstruktur extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InstrukturTambahActivity.this,
                        "Menyimpan Data",
                        "Harap Tunggu ...",
                        false,
                        false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // Create hashmap to store values which will be sent to HttpHandler
                HashMap<String, String> params = new HashMap<>();
                params.put("nama_ins", nama_ins);
                params.put("email_ins", email_ins);
                params.put("hp_ins", hp_ins);
                HttpHandler handler = new HttpHandler();

                // Create HttpHandler to send data with sendPostRequest
                String result = handler.sendPostRequest(Konfigurasi.URL_INSTRUKTUR_ADD, params);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        InstrukturTambahActivity.this,
                        "pesan:" + s,
                        Toast.LENGTH_SHORT)
                        .show();
                clearText();
            }
        }
        SimpanDataInstruktur simpanDataInstruktur = new SimpanDataInstruktur();
        simpanDataInstruktur.execute();

        // Back to home after add process
        // startActivity(new Intent(InstrukturTambahActivity.this, MainActivity.class));

        // Back to instruktur fragment after add process
        Intent myIntent = new Intent(InstrukturTambahActivity.this, MainActivity.class);
        myIntent.putExtra("KeyName", "Instruktur");
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
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void clearText() {
        edit_nama_ins.setText("");
        edit_email_ins.setText("");
        edit_hp_ins.setText("");
        edit_nama_ins.requestFocus();
    }
}