package com.adhito.inixindo_task_individual;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
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

import com.adhito.inixindo_task_individual.databinding.FragmentKelasBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class KelasFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentKelasBinding kelasBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;
    private ListView list_view;

    public KelasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static KelasFragment newInstance(String param1, String param2) {
        KelasFragment fragment = new KelasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kelasBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kelas, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = kelasBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        // Create customActionBar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Kelas");

        // Event-handling detailed event view
        kelasBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("KelasFragment Log","Clicked");
                Intent myIntent = new Intent(getActivity(), KelasDetailActivity.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(i);
                String id_kelas = map.get(Konfigurasi.TAG_JSON_ID_KLS).toString();
                myIntent.putExtra(Konfigurasi.PGW_ID, id_kelas);
                Log.d("KelasFragment Log",id_kelas);
                startActivity(myIntent);
            }
        });

        // Event-handling add.fab
        kelasBinding.addFab.setOnClickListener(this);

        // Get JSON Data
        getJsonData();
    }


    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(),
                        "Mengambil data kelas",
                        "Harap menunggu...",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_GET_ALL);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                // Toast.makeText(view.getContext(), JSON_STRING, Toast.LENGTH_LONG).show();
                diplayAllDataKelas();
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void diplayAllDataKelas() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String tgl_mulai_kls = object.getString("tgl_mulai_kls");
                String tgl_akhir_kls = object.getString("tgl_akhir_kls");
                String id_kls = object.getString("id_kls");
                String id_ins = object.getString("id_ins");
                String id_mat = object.getString("id_mat");

                HashMap<String, String> kelas = new HashMap<>();
                kelas.put("tgl_mulai_kls", tgl_mulai_kls);
                kelas.put("tgl_akhir_kls", tgl_akhir_kls);
                kelas.put("id_kls", id_kls);
                kelas.put("id_ins", id_ins);
                kelas.put("id_mat", id_mat);

                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create adapter to put array list to ListView
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.activity_list_item_kelas,
                new String[]{"id_kls","tgl_mulai_kls", "tgl_akhir_kls"},
                new int[]{R.id.txt_id_class, R.id.txt_start_date,R.id.txt_end_date}
        );
        kelasBinding.listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(view.getContext(), KelasTambahActivity.class));
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Event-handling when one of the list is selected
        Log.d("test","clicked");
        Intent myIntent = new Intent(getActivity(), KelasDetailActivity.class);
        HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }
}