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

public class MateriDetailActivity extends AppCompatActivity implements View.OnClickListener {
    
    EditText edit_id_mat, edit_nama_mat;
    Button btn_update_materi, btn_delete_materi;
    String id_mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_detail);

        edit_id_mat = findViewById(R.id.edit_id_mat);
        edit_nama_mat = findViewById(R.id.edit_nama_mat);
        btn_update_materi = findViewById(R.id.btn_update_materi);
        btn_delete_materi = findViewById(R.id.btn_delete_materi);

        Intent receiveIntent = getIntent();
        id_mat = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id_mat.setText(id_mat);

        getJSON();

        btn_update_materi.setOnClickListener(this);
        btn_delete_materi.setOnClickListener(this);
    }

    private void getJSON() {

        // Get peserta data from MySQL throught Web-API with JSON format
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MateriDetailActivity.this,
                        "Mengambil data materi",
                        "Harap Menunggu ...",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_MATERI_GET_DETAIL, id_mat);
                Log.d("Result MateriDetailActivity", result);
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

            String nama_mat = object.getString("nama_mat");

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
        if (view == btn_update_materi){
            confirmUpdateMateri();
        } else if (view == btn_delete_materi){
            confirmDeleteMateri();
        }
    }

    private void confirmUpdateMateri() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Memperbarui data materi");
        builder.setMessage("Apakah anda ingin memperbarui materi ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataMateri();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataMateri() {
        // Define variables that is used
        final String nama_mat = edit_nama_mat.getText().toString().trim();

        class UpdateDataMateri extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MateriDetailActivity.this,
                        "Memperbarui data",
                        "Harap tunggu ...",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> materi = new HashMap<>();
                materi.put("id_mat", id_mat);
                materi.put("nama_mat", nama_mat);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_MATERI_UPDATE, materi)  ;
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(
                        MateriDetailActivity.this,
                        "Pesan InstrukturDetailActivity:"+message,
                        Toast.LENGTH_SHORT)
                        .show();

                // Back to homepage after update
                startActivity(new Intent(MateriDetailActivity.this,MainActivity.class));
            }
        }
        UpdateDataMateri updateDataMateri = new UpdateDataMateri();
        updateDataMateri.execute();
    }


    private void confirmDeleteMateri() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus data : ");
        builder.setMessage("Apakah anda ingin menghapus materi ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataMateri() {
        class DeleteDataMateri extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MateriDetailActivity.this,
                        "Menghapus data",
                        "Harap tunggu",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_mat", id_mat);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_MATERI_DELETE, id_mat);
                Log.d("result",result);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        MateriDetailActivity.this,
                        "Pesan Delete: "+s,
                        Toast.LENGTH_SHORT)
                        .show();

                // Back to homepage after update
                startActivity(new Intent(MateriDetailActivity.this,MainActivity.class));

            }
        }
        DeleteDataMateri deleteDataMateri = new DeleteDataMateri();
        deleteDataMateri.execute();
    }
}