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

import com.adhito.inixindo_task_individual.databinding.FragmentDetailKelasBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DetailKelasFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentDetailKelasBinding detailKelasBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;
    private ListView list_view;

    public DetailKelasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailKelasFragment newInstance(String param1, String param2) {
        DetailKelasFragment fragment = new DetailKelasFragment();
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
        detailKelasBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_kelas, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = detailKelasBinding.getRoot();
        initView();
        return view;
    }


    private void initView() {
        // Create customActionBar
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Detail Kelas");

        // Event-handling detailed event view
        detailKelasBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                // membuka detail
                Log.d("test","clicked");
                Intent myIntent = new Intent(getActivity(), DetailKelasDetailActivity.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(i);
                String id_detail_kls = map.get("id_detail_kls").toString();
                myIntent.putExtra(Konfigurasi.PGW_ID, id_detail_kls);
                Log.d("test",id_detail_kls);
                startActivity(myIntent);
            }
        });

        // Event-handling add.fab
        detailKelasBinding.addFab.setOnClickListener(this);

        // Get JSON Data
        getJsonData();
    }

    private void getJsonData() {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Ambil Data Detail Kelas", "Harap menunggu...", false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_KELAS_DETAIL_GET_ALL);
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
                displayAllDataDetailKelas();

            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }

    private void displayAllDataDetailKelas() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id_kls = object.getString("k.id_kls");
                String nama_mat = object.getString("m.nama_mat");
                String nama_ins = object.getString("i.nama_ins");
                String id_detail_kls = object.getString("dk.id_detail_kls");

                HashMap<String, String> detailKelas = new HashMap<>();
                detailKelas.put("id_kls", id_kls);
                detailKelas.put("nama_mat", nama_mat);
                detailKelas.put("nama_ins", nama_ins);
                detailKelas.put("id_detail_kls",id_detail_kls);
                list.add(detailKelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create adapter to put array list to ListView
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.activity_list_item_detail_kelas,
                new String[]{"id_detail_kls","nama_mat", "nama_ins"},
                new int[]{R.id.txt_id, R.id.txt_name_mat,R.id.txt_name_ins}
        );
        detailKelasBinding.listView.setAdapter(adapter);
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
        // Event-handling add instructor
        startActivity(new Intent(view.getContext(), DetailKelasTambahActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Event-handling when one of the list is selected
        Log.d("DetailKelasFragment","clicked");
        Intent myIntent = new Intent(getActivity(), DetailKelasFragment.class);
        HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }
}