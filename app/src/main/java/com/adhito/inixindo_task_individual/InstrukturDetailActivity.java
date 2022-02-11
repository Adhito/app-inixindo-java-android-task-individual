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

public class InstrukturDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_ins, edit_nama_ins, edit_email_ins, edit_hp_ins;
    Button btn_update_instruktur, btn_delete_instruktur;
    String id_ins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruktur_detail);

        edit_id_ins = findViewById(R.id.edit_id_ins);
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_email_ins = findViewById(R.id.edit_email_ins);
        edit_hp_ins = findViewById(R.id.edit_hp_ins);
        btn_update_instruktur = findViewById(R.id.btn_update_instruktur);
        btn_delete_instruktur = findViewById(R.id.btn_delete_instruktur);

        Intent receiveIntent = getIntent();
        id_ins = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id_ins.setText(id_ins);

        getJSON();

        btn_update_instruktur.setOnClickListener(this);
        btn_delete_instruktur.setOnClickListener(this);

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
                        InstrukturDetailActivity.this,
                        "Mengambil data instruktur",
                        "Harap Menunggu ...",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_INSTRUKTUR_GET_DETAIL, id_ins);
                Log.d("Log", result);
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
            
            String nama_ins = object.getString("nama_ins");
            String email_ins = object.getString("email_ins");
            String hp_ins = object.getString("hp_ins");
            
            edit_nama_ins.setText(nama_ins);
            edit_email_ins.setText(email_ins);
            edit_hp_ins.setText(hp_ins);
            
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
        if (view == btn_update_instruktur){
            confirmUpdateInstruktur();
        } else if (view == btn_delete_instruktur){
            confirmDeleteInstruktur();
        }
    }

    private void confirmUpdateInstruktur() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Memperbarui data instruktur");
        builder.setMessage("Apakah anda ingin memperbarui instruktur ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataInstruktur();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataInstruktur() {
        // Define variables that is used
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String email_ins = edit_email_ins.getText().toString().trim();
        final String hp_ins = edit_hp_ins.getText().toString().trim();

        class UpdateDataInstruktur extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        InstrukturDetailActivity.this,
                        "Memperbarui data",
                        "Harap tunggu ...",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> instruktur = new HashMap<>();
                instruktur.put("id_ins", id_ins);
                instruktur.put("nama_ins", nama_ins);
                instruktur.put("email_ins", email_ins);
                instruktur.put("hp_ins", hp_ins);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_INSTRUKTUR_UPDATE, instruktur)  ;
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(
                        InstrukturDetailActivity.this,
                        "Pesan InstrukturDetailActivity:"+message,
                        Toast.LENGTH_SHORT)
                        .show();

                // Back to homepage after update
                // startActivity(new Intent(InstrukturDetailActivity.this,MainActivity.class));

                // Back to instruktur fragment after update
                Intent myIntent = new Intent(InstrukturDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Instruktur");
                startActivity(myIntent);
            }
        }
        UpdateDataInstruktur updateDataInstruktur = new UpdateDataInstruktur();
        updateDataInstruktur.execute();
    }

    private void confirmDeleteInstruktur() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus data : ");
        builder.setMessage("Apakah anda ingin menghapus instruktur ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataInstruktur() {
        class DeleteDataInstruktur extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        InstrukturDetailActivity.this,
                        "Menghapus data",
                        "Harap tunggu",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_ins", id_ins);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_INSTRUKTUR_DELETE, id_ins);
                Log.d("result",result);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        InstrukturDetailActivity.this,
                        "Pesan Delete: "+s,
                        Toast.LENGTH_SHORT)
                        .show();

                // Back to homepage after update
                //startActivity(new Intent(InstrukturDetailActivity.this,MainActivity.class));

                // Back to instruktur fragment after update
                Intent myIntent = new Intent(InstrukturDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Instruktur");
                startActivity(myIntent);

            }
        }
        DeleteDataInstruktur deleteDataInstruktur = new DeleteDataInstruktur();
        deleteDataInstruktur.execute();
    }
}