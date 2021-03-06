package com.adhito.inixindo_task_individual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailKelasDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_detail_kls, edit_id_kls, edit_id_pst;
    Button btn_update_detail_kelas, btn_delete_detail_kelas;
    String id_detail_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_detail);
        edit_id_detail_kls = findViewById(R.id.edit_id_detail_kls);
        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_id_pst = findViewById(R.id.edit_id_pst);

        btn_update_detail_kelas = findViewById(R.id.btn_update_detail_kelas);
        btn_delete_detail_kelas = findViewById(R.id.btn_delete_detail_kelas);

        Intent receiveIntent = getIntent();
        id_detail_kls = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id_detail_kls.setText(id_detail_kls);

        getJSON();

        btn_update_detail_kelas.setOnClickListener(this);
        btn_delete_detail_kelas.setOnClickListener(this);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_DETAIL_GET_DETAIL, id_detail_kls);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                // Toast.makeText(DetailKelasDetailActivity.this, message, Toast.LENGTH_LONG).show();
                displayDetailData(message);

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_detail_kls = object.getString("id_detail_kls");
            String id_kls = object.getString("id_kls");
            String id_pst = object.getString("id_pst");

            edit_id_detail_kls.setText(id_detail_kls);
            edit_id_kls.setText(id_kls);
            edit_id_pst.setText(id_pst);

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
        if (view == btn_update_detail_kelas){
            updateDataDetailKelas();
        }
        else if(view == btn_delete_detail_kelas){
            confirmDeleteDataDetailKelas();
        }
    }

    private void confirmDeleteDataDetailKelas() {
        // Create confirmation using alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda yaking menhapus data ini?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDataDetailKelas();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void deleteDataDetailKelas() {
        class DeleteDataDetailKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Menghapus data","Harap tunggu",
                        false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_DETAIL_DELETE, id_detail_kls);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailKelasDetailActivity.this, "pesan: "+s, Toast.LENGTH_LONG).show();

                // Create redirect to home
                Intent myIntent = new Intent(DetailKelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Detail_Kelas");
                startActivity(myIntent);
            }

        }
        DeleteDataDetailKelas deleteDataDetailKelas = new DeleteDataDetailKelas();
        deleteDataDetailKelas.execute();
    }

    private void updateDataDetailKelas() {
        final String id_kls = edit_id_kls.getText().toString().trim();
        final String id_pst = edit_id_pst.getText().toString().trim();
        class UpdateDataDetailKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengubah Data", "Harap Tunggu",
                        false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put("id_detail_kls", id_detail_kls);
                kelas.put("id_kls", id_kls);
                kelas.put("id_pst", id_pst);


                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_KELAS_DETAIL_UPDATE, kelas);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailKelasDetailActivity.this,
                        "pesan: "+s, Toast.LENGTH_SHORT).show();

                // Create redirect to previous fragment
                Intent myIntent = new Intent(DetailKelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Detail_Kelas");
                startActivity(myIntent);
            }
        }
        UpdateDataDetailKelas updateDataDetailKelas = new UpdateDataDetailKelas();
        updateDataDetailKelas.execute();
    }
}