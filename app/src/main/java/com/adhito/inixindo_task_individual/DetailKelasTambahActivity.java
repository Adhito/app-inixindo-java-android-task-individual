package com.adhito.inixindo_task_individual;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailKelasTambahActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edit_id_detail_kls, edit_id_kls, edit_id_pst;
    Button btn_tambah_detail_kelas, btn_lihat_detail_kelas;
    Spinner spinner_kls,spinner_pst;
    private int spinner_val_kls, spinner_val_pst;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_tambah);

        edit_id_detail_kls = findViewById(R.id.edit_id_detail_kls);
        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_id_pst = findViewById(R.id.edit_id_pst);

        spinner_kls = findViewById(R.id.spinner_kls);
        spinner_pst = findViewById(R.id.spinner_pst);

        btn_tambah_detail_kelas = findViewById(R.id.btn_tambah_detail_kelas);
        btn_lihat_detail_kelas = findViewById(R.id.btn_lihat_detail_kelas);

        btn_tambah_detail_kelas.setOnClickListener(this);
        btn_lihat_detail_kelas.setOnClickListener(this);

        fetch_kelas();
        fetch_peserta();
    }

    private void fetch_kelas() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(DetailKelasTambahActivity.this, "Getting Data", "Please wait...", false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_GET_ALL);
                Log.d("GetData", result);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<String> listID = new ArrayList<>();
                ArrayList<String> listNamaMat = new ArrayList<>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id_kls");
                        String nama_mat = object.getString("nama_mat");


                        listID.add(id);
                        listNamaMat.add(nama_mat);
                        Log.d("id: ", String.valueOf(id));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailKelasTambahActivity.this, android.R.layout.simple_spinner_dropdown_item, listID);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_kls.setAdapter(adapter);
                spinner_kls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner_val_kls = Integer.parseInt(listID.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spinner_kls.setAdapter(adapter);

                Log.d("Spinner", String.valueOf(arrayList));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }


    private void fetch_peserta() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(DetailKelasTambahActivity.this, "Getting Data", "Please wait...", false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_PESERTA_GET_ALL);
                Log.d("GetData", result);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<String> listID = new ArrayList<>();
                ArrayList<String> listNama = new ArrayList<>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id_pst");
                        String nama = object.getString("nama_pst");

                        listID.add(id);
                        listNama.add(nama);
                        Log.d("id: ", String.valueOf(id));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailKelasTambahActivity.this, android.R.layout.simple_spinner_dropdown_item, listNama);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_pst.setAdapter(adapter);
                spinner_pst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinner_val_pst = Integer.parseInt(listID.get(i));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                spinner_pst.setAdapter(adapter);

                Log.d("spin", String.valueOf(arrayList));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tambah_detail_kelas:
                simpanDataDetailKelas();
                Intent myIntent = new Intent(DetailKelasTambahActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Detail_Kelas");
                startActivity(myIntent);
                break;
            case R.id.btn_lihat_detail_kelas:
                Intent i = new Intent(DetailKelasTambahActivity.this, MainActivity.class);
                i.putExtra("KeyName", "Detail_Kelas");
                startActivity(i);
                break;
        }
    }

    private void simpanDataDetailKelas() {

        final String id_kls = spinner_kls.getSelectedItem().toString().trim();
        final String id_pst = String.valueOf(spinner_val_pst);

        class SimpanDataKelas extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasTambahActivity.this,
                        "Menyimpan Data", "Harap Tunggu ...",
                        false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_kls", id_kls);
                params.put("id_pst", id_pst);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_KELAS_DETAIL_ADD, params);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                // Toast.makeText(DetailKelasTambahActivity.this, "pesan:" + s, Toast.LENGTH_SHORT).show();
                clearText();
            }
        }
        SimpanDataKelas simpanDataKelas = new SimpanDataKelas();
        simpanDataKelas.execute();
    }

    private void clearText() {
        // edit_id_kls.setText("");
        // edit_id_pst.setText("");
        // untuk pointer langsung menuju kolom nama di layout
        edit_id_kls.requestFocus();
    }
}