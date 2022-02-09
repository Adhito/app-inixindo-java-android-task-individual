package com.adhito.inixindo_task_individual;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

//import id.bagusip.inixindoprojectindividu.databinding.FragmentPesertaBinding;
import com.adhito.inixindo_task_individual.databinding.FragmentPesertaBinding;


public class PesertaFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private FragmentPesertaBinding pesertaBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;
    private ListView list_view;

    public PesertaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pesertaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_peserta, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = pesertaBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // custom action bar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Peserta");


        // penanganan List View
        pesertaBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                // membuka detail
                Log.d("test","clicked");
                Intent myIntent = new Intent(getActivity(), PesertaDetail.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(i);
                String id_peserta = map.get(Konfigurasi.TAG_JSON_ID).toString();
                myIntent.putExtra(Konfigurasi.PGW_ID, id_peserta);
                Log.d("test",id_peserta);
                startActivity(myIntent);
            }
        });

        // penanganan FAB
        pesertaBinding.addFab.setOnClickListener(this);

        // ambil data dari JSON
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Mengambil Data Peserta", "Harap menunggu...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_PESERTA_GET_ALL);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                // Toast.makeText(view.getContext(), JSON_STRING, Toast.LENGTH_LONG).show();

                // menampilkan data json kedalam list view
                displayAllDataPeserta();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displayAllDataPeserta() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id_pst = object.getString("id_pst");
                String nama_pst = object.getString("nama_pst");
                String email_pst = object.getString("email_pst");
                String hp_pst = object.getString("hp_pst");
                String instansi_pst = object.getString("instansi_pst");

                HashMap<String, String> peserta = new HashMap<>();
                peserta.put("id_pst", id_pst);
                peserta.put("nama_pst", nama_pst);
                peserta.put("email_pst", email_pst);
                peserta.put("hp_pst", hp_pst);
                peserta.put("instansi_pst", instansi_pst);

                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.activity_list_item,
                new String[]{"id_pst", "nama_pst"},
                new int[]{R.id.txt_id, R.id.txt_name}
        );
        pesertaBinding.listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Ketika salah satu list dipilih
        // detail : id, name, Desg, Salary
        Log.d("test","clicked");
        Intent myIntent = new Intent(getActivity(), PesertaDetail.class);
        HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }

    @Override
    public void doBack() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frame_layout, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        // penanganan FAB
        startActivity(new Intent(view.getContext(), TambahPeserta.class));
    }


}