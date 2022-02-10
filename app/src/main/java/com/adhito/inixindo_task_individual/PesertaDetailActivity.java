package com.adhito.inixindo_task_individual;
//
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link PesertaDetailActivity#newInstance} factory method to
// * create an instance of this fragment.
// *
// */
//public class PesertaDetailActivity extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PesertaDetailActivity.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PesertaDetailActivity newInstance(String param1, String param2) {
//        PesertaDetailActivity fragment = new PesertaDetailActivity();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public PesertaDetailActivity() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_peserta_detail, container, false);
//    }
//}


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

public class PesertaDetailActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edit_id_pst, edit_nama_pst, edit_email_pst, edit_hp_pst, edit_instansi_pst;
    Button btn_update_peserta, btn_delete_peserta;
    String id_pst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta_detail);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Detail Data Peserta");

        edit_id_pst = findViewById(R.id.edit_id_pst);
        edit_nama_pst = findViewById(R.id.edit_nama_pst);
        edit_email_pst = findViewById(R.id.edit_email_pst);
        edit_hp_pst = findViewById(R.id.edit_hp_pst);
        edit_instansi_pst = findViewById(R.id.edit_instansi_pst);
        btn_update_peserta = findViewById(R.id.btn_update_peserta);
        btn_delete_peserta = findViewById(R.id.btn_delete_peserta);

        Intent receiveIntent = getIntent();
        id_pst = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id_pst.setText(id_pst);

        getJSON();

        btn_update_peserta.setOnClickListener(this);
        btn_delete_peserta.setOnClickListener(this);

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
                        PesertaDetailActivity.this,
                        "Mengambil data peserta ... ",
                        "Harap Menunggu",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_PESERTA_GET_DETAIL, id_pst);
                Log.d("Result PesertaDetailActivity : ",result);
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

            String nama_pst = object.getString("nama_pst");
            String email_pst = object.getString("email_pst");
            String hp_pst = object.getString("hp_pst");
            String instansi_pst = object.getString("instansi_pst");

            edit_nama_pst.setText(nama_pst);
            edit_email_pst.setText(email_pst);
            edit_hp_pst.setText(hp_pst);
            edit_instansi_pst.setText(instansi_pst);

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
        if (view == btn_update_peserta){
            confirmUpdateDataPeserta();
        }
        else if(view == btn_delete_peserta){
            confirmDeleteDataPeserta();
        }
    }

    private void confirmUpdateDataPeserta() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Memperbarui data peserta");
        builder.setMessage("Apakah anda ingin memperbarui peserta ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateDataPeserta();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataPeserta() {
        // Define variables that is used
        final String nama_pst = edit_nama_pst.getText().toString().trim();
        final String email_pst = edit_email_pst.getText().toString().trim();
        final String hp_pst = edit_hp_pst.getText().toString().trim();
        final String instansi_pst = edit_instansi_pst.getText().toString().trim();

        class UpdateDataPeserta extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        PesertaDetailActivity.this,
                        "Mengubah Data",
                        "Harap Tunggu",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> peserta = new HashMap<>();
                peserta.put("id_pst", id_pst);
                peserta.put("nama_pst", nama_pst);
                peserta.put("email_pst", email_pst);
                peserta.put("hp_pst", hp_pst);
                peserta.put("instansi_pst", instansi_pst);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_PESERTA_UPDATE, peserta);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(
                        PesertaDetailActivity.this,
                        "Pesan PesertaDetailActivity: "+message,
                        Toast.LENGTH_SHORT)
                        .show();

                // Back to homepage after update
                // startActivity(new Intent(PesertaDetailActivity.this,MainActivity.class));

                // Back to previous page after update
                Intent myIntent = new Intent(PesertaDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Peserta");
                startActivity(myIntent);
            }
        }
        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }

    private void confirmDeleteDataPeserta() {
        // Show confirmation alert dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus data");
        builder.setMessage("Apakah anda ingin menghapus peserta ini ?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataPeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataPeserta() {
        class DeleteDataPeserta extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            // Override onPreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        PesertaDetailActivity.this,
                        "Menghapus data",
                        "Harap tunggu",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_pst", id_pst);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_PESERTA_DELETE, id_pst);
                Log.d("result",result);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(
                        PesertaDetailActivity.this,
                        "Pesan Delete: "+s,
                        Toast.LENGTH_SHORT)
                        .show();

                // Back to homepage after delete
                startActivity(new Intent(PesertaDetailActivity.this,MainActivity.class));

                // Back to previous page after delete
                Intent myIntent = new Intent(PesertaDetailActivity.this, MainActivity.class);
                myIntent.putExtra("KeyName", "Peserta");
                startActivity(myIntent);

            }
        }
        DeleteDataPeserta deleteDataPeserta = new DeleteDataPeserta();
        deleteDataPeserta.execute();
    }
}
