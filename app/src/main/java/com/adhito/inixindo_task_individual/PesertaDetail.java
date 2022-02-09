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
// * Use the {@link PesertaDetail#newInstance} factory method to
// * create an instance of this fragment.
// *
// */
//public class PesertaDetail extends Fragment {
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
//     * @return A new instance of fragment PesertaDetail.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PesertaDetail newInstance(String param1, String param2) {
//        PesertaDetail fragment = new PesertaDetail();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public PesertaDetail() {
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

import android.app.ProgressDialog;
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

public class PesertaDetail extends AppCompatActivity implements View.OnClickListener{

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
        // MENGAMBIL DATA DARI ANDROID KE SERVER
        // BANTUAN DARI CLASS ASYNCtASK
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // ctrl + o pilih OnPreExcetue
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PesertaDetail.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Saat proses ambil data terjadi
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_PESERTA_GET_DETAIL, id_pst);
                Log.d("Result PesertaDetail : ",result);
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
            updateDataPeserta();
        }
        else if(view == btn_delete_peserta){
            confirmDeleteDataPeserta();
        }
    }

    private void confirmDeleteDataPeserta() {
        class DeleteDataPeserta extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PesertaDetail.this,
                        "Menghapus data","Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_pst", id_pst);

                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_PESERTA_DELETE, params);
                Log.d("result",result);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(PesertaDetail.this,
                        "pesan: "+s, Toast.LENGTH_SHORT).show();
                //redirect ke lihat data activity
//                startActivity(new Intent(PesertaDetail.this,PesertaFragment.class));
                startActivity(new Intent(PesertaDetail.this,PesertaFragment.class));

            }
        }
        DeleteDataPeserta deleteDataPeserta = new DeleteDataPeserta();
        deleteDataPeserta.execute();
    }

    private void updateDataPeserta() {
        final String nama_pst = edit_nama_pst.getText().toString().trim();
        final String email_pst = edit_email_pst.getText().toString().trim();
        final String hp_pst = edit_hp_pst.getText().toString().trim();
        final String instansi_pst = edit_instansi_pst.getText().toString().trim();

        class UpdateDataPeserta extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PesertaDetail.this,
                        "Mengubah Data", "Harap Tunggu",
                        false, false);
            }

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

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(PesertaDetail.this,
                        "pesan: "+s, Toast.LENGTH_SHORT).show();
                //redirect ke lihat data activity
                startActivity(new Intent(PesertaDetail.this,PesertaFragment.class));
            }
        }
        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }
}
