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

public class MateriTambahActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    EditText edit_id_materi, edit_nama_mat;
    Button btn_tambah_materi, btn_lihat_materi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_tambah);

        edit_id_materi = findViewById(R.id.edit_id_mat);
        edit_nama_mat = findViewById(R.id.edit_nama_mat);
        btn_tambah_materi = findViewById(R.id.btn_tambah_materi);
        btn_lihat_materi = findViewById(R.id.btn_lihat_materi);
        btn_tambah_materi.setOnClickListener(this);
        btn_lihat_materi.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_tambah_materi:
                confirmAddDataInstruktur();
                break;
            case R.id.btn_lihat_materi:
                fragment = new PesertaFragment();
                startActivity(new Intent(MateriTambahActivity.this, MainActivity.class));
                break;
        }
    }

    private void confirmAddDataInstruktur() {
        // Get confirmation to add participant (Name, E-mail, Phone and Instance)
        final String nama_mat = edit_nama_mat.getText().toString().trim();

        // Show confirmation of alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menambahkan data materi");
        builder.setMessage("Apakah anda ingin menambah materi ini ?" +
                "\n Nama \t\t\t: " + nama_mat);
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                simpanDataMateri();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void simpanDataMateri() {
        final String nama_mat = edit_nama_mat.getText().toString().trim();

        class SimpanDataMateri extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MateriTambahActivity.this,
                        "Menyimpan Data",
                        "Harap Tunggu ...",
                        false,
                        false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // Create hashmap to store values which will be sent to HttpHandler
                HashMap<String, String> params = new HashMap<>();
                params.put("nama_mat", nama_mat);
                HttpHandler handler = new HttpHandler();

                // Create HttpHandler to send data with sendPostRequest
                String result = handler.sendPostRequest(Konfigurasi.URL_MATERI_ADD, params);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MateriTambahActivity.this, "Pesan:" + s,
                        Toast.LENGTH_SHORT).show();
                clearText();
            }
        }
        SimpanDataMateri simpanDataMateri = new SimpanDataMateri();
        simpanDataMateri.execute();

        // Back to homepage after add
        // startActivity(new Intent(MateriTambahActivity.this,MainActivity.class));

        // Back to instruktur fragment after add
        Intent myIntent = new Intent(MateriTambahActivity.this, MainActivity.class);
        myIntent.putExtra("KeyName", "Materi");
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
        edit_nama_mat.setText("");
    }
}