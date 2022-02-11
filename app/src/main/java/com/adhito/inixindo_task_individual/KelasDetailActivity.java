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

public class KelasDetailActivity extends AppCompatActivity implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    EditText edit_id_kls, edit_id_ins, edit_id_mat, edit_tgl_mulai_kls, edit_tgl_akhir_kls;
    Button btn_update_kelas, btn_delete_kelas;
    String id_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_detail);

        edit_tgl_mulai_kls = findViewById(R.id.edit_tgl_mulai_kls);
        edit_tgl_akhir_kls = findViewById(R.id.edit_tgl_akhir_kls);
        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_id_ins = findViewById(R.id.edit_id_ins);
        edit_id_mat = findViewById(R.id.edit_id_mat);
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
        // Get peserta data from MySQL throught Web-API with JSON format
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_GET_DETAIL, id_kls);
                Log.d("KelasDetailActivity",result);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
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
            String id_ins =  object.getString("id_ins");
            String id_mat = object.getString("id_mat");

            edit_tgl_mulai_kls.setText(tgl_mulai_kls);
            edit_tgl_akhir_kls.setText(tgl_akhir_kls);
            edit_id_kls.setText(id_kls);
            edit_id_ins.setText(id_ins);
            edit_id_mat.setText(id_mat);
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
            confirmUpdateKelas();
        }
        else if(view == btn_delete_kelas){
            confirmDeleteDataKelas();
        }
    }

    private void confirmUpdateKelas() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Memperbarui data instruktur");
        builder.setMessage("Apakah anda ingin memperbarui kelas ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataKelas();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataKelas() {
        final String tgl_mulai_kls = edit_tgl_mulai_kls.getText().toString().trim();
        final String tgl_akhir_kls = edit_tgl_akhir_kls.getText().toString().trim();
        final String id_ins = edit_id_ins.getText().toString().trim();
        final String id_mat = edit_id_mat.getText().toString().trim();

        class UpdateDataKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
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

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put("tgl_mulai_kls", tgl_mulai_kls);
                kelas.put("tgl_akhir_kls", tgl_akhir_kls);
                kelas.put("id_kls", id_kls);
                kelas.put("id_ins", id_ins);
                kelas.put("id_mat", id_mat);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_KELAS_UPDATE, kelas);

                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        KelasDetailActivity.this,
                        "pesan: "+s,
                        Toast.LENGTH_SHORT).show();

                // Back to homepage after update
                // startActivity(new Intent(KelasDetailActivity.this,MainActivity.class));

                // Back to instruktur fragment after update
                Intent myIntent = new Intent(KelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Kelas");
                startActivity(myIntent);
            }
        }
        UpdateDataKelas updateDataKelas = new UpdateDataKelas();
        updateDataKelas.execute();

    }

    private void confirmDeleteDataKelas() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda ingin menghapus kelas ini?");
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

            // Override onPreExecute (Ctrl + O select the onPreExecute)
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

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {

                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_DELETE, id_kls);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        KelasDetailActivity.this,
                        "pesan: "+s,
                        Toast.LENGTH_LONG)
                        .show();

                // Back to homepage after update
                //startActivity(new Intent(KelasDetailActivity.this,MainActivity.class));

                // Back to instruktur fragment after update
                Intent myIntent = new Intent(KelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Kelas");
                startActivity(myIntent);

            }
        }
        DeleteDataKelas deleteDataKelas = new DeleteDataKelas();
        deleteDataKelas.execute();
    }
}