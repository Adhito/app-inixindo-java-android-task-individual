package com.adhito.inixindo_task_individual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

//public class KelasDetailActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_kelas_detail);
//    }
//}


public class KelasDetailActivity extends AppCompatActivity implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    EditText edit_id_kls, edit_tgl_mulai_kls, edit_tgl_akhir_kls, edit_nama_ins, edit_nama_mat;
    Button btn_update_kelas, btn_delete_kelas;
    String id_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_detail);

        edit_tgl_mulai_kls = findViewById(R.id.edit_tgl_mulai_kls);
        edit_tgl_akhir_kls = findViewById(R.id.edit_tgl_akhir_kls);
        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_nama_mat = findViewById(R.id.edit_nama_mat);
        btn_update_kelas = findViewById(R.id.btn_update_kelas);
        btn_delete_kelas = findViewById(R.id.btn_delete_kelas);

        Intent receiveIntent = getIntent();
        id_kls = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id_kls.setText(id_kls);

        getJSON();

        btn_update_kelas.setOnClickListener(this);
        btn_delete_kelas.setOnClickListener(this);

    }

    private void getJSON() {
        // MENGAMBIL DATA DARI ANDROID KE SERVER
        // BANTUAN DARI CLASS ASYNCtASK
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // ctrl + o pilih OnPreExcetue
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Saat proses ambil data terjadi
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_GET_DETAIL, id_kls);
                Log.d("KelasDetailActivity",result);
                return result;
            }

            // ctrl + o pilih OnPostExcetue
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String tgl_mulai_kls = object.getString("tgl_mulai_kls");
            String tgl_akhir_kls = object.getString("tgl_akhir_kls");
            String id_kls =  object.getString("id_kls");
            String nama_ins =  object.getString("nama_ins");
            String nama_mat = object.getString("nama_mat");

            edit_tgl_mulai_kls.setText(tgl_mulai_kls);
            edit_tgl_akhir_kls.setText(tgl_akhir_kls);
            edit_id_kls.setText(id_kls);
            edit_nama_ins.setText(nama_ins);
            edit_nama_mat.setText(nama_mat);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update_kelas){
            updateDataKelas();
        }
        else if(view == btn_delete_kelas){
            confirmDeleteDataKelas();
        }
    }

    private void confirmDeleteDataKelas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda yaking menhapus data ini?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDataKelas();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataKelas() {
        class DeleteDataKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        KelasDetailActivity.this,
                        "Menghapus data",
                        "Harap tunggu",
                        false,
                        false);
            }

            @Override
            protected String doInBackground(Void... voids) {

                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_DELETE, id_kls);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(KelasDetailActivity.this,
                        "pesan: "+s, Toast.LENGTH_LONG).show();

                startActivity(new Intent(KelasDetailActivity.this,MainActivity.class));

            }
        }
        DeleteDataKelas deleteDataKelas = new DeleteDataKelas();
        deleteDataKelas.execute();
    }

    private void updateDataKelas() {
        final String tgl_mulai_kls = edit_tgl_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = edit_tgl_akhir_kls.getText().toString().trim();
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String nama_mat = edit_nama_mat.getText().toString().trim();

        class UpdateDataKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        KelasDetailActivity.this,
                        "Mengubah Data",
                        "Harap Tunggu",
                        false,
                        false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put("tgl_mulai_kls", tgl_mulai_kls);
                kelas.put("tgl_akhir_kls", tgl_akhir_kls);
                kelas.put("nama_ins", nama_ins);
                kelas.put("nama_mat", nama_mat);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_KELAS_UPDATE, kelas);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(KelasDetailActivity.this,
                        "pesan: "+s, Toast.LENGTH_SHORT).show();
                //redirect ke lihat data activity
                startActivity(new Intent(KelasDetailActivity.this,MainActivity.class));
            }
        }
        UpdateDataKelas updateDataKelas = new UpdateDataKelas();
        updateDataKelas.execute();

    }
}