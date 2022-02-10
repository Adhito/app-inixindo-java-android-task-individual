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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.adhito.inixindo_task_individual.databinding.FragmentInstrukturBinding;

public class InstrukturFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private FragmentInstrukturBinding instrukturBinding;
    private View view;
    private String JSON_STRING;
    private ProgressDialog loading;
    private ListView list_view;

    public InstrukturFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InstrukturFragment newInstance(String param1, String param2) {
        InstrukturFragment fragment = new InstrukturFragment();
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
        instrukturBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_instruktur, container, false);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
        view = instrukturBinding.getRoot();
        initView();
        return view;

    }

    private void initView() {
        // ActionBar custom define
        ActionBar customActionBar = ((MainActivity) getActivity()).getSupportActionBar();
        customActionBar.setTitle("Data Instruktur");

        // Event-handling detailed event view
        instrukturBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("InstrukturFragment Log", "Clicked");
                Intent myIntent = new Intent(getActivity(), InstrukturDetailActivity.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(i);
                String id_instruktur = map.get(Konfigurasi.TAG_JSON_ID_INS).toString();
                myIntent.putExtra(Konfigurasi.PGW_ID, id_instruktur);
                Log.d("InstrukturFragment Log", id_instruktur);
                startActivity(myIntent);
            }
        });

        // Event-handling add.fab
        instrukturBinding.addFab.setOnClickListener(this);

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
                        "Mengambil Data Instruktur",
                        "Harap menunggu...",
                        false,
                        false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_INSTRUKTUR_GET_ALL);
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
                String id_ins = object.getString("id_ins");
                String nama_ins = object.getString("nama_ins");
                String email_ins = object.getString("email_ins");
                String hp_ins = object.getString("hp_ins");

                HashMap<String, String> peserta = new HashMap<>();
                peserta.put("id_ins", id_ins);
                peserta.put("nama_ins", nama_ins);
                peserta.put("email_ins", email_ins);
                peserta.put("hp_ins", hp_ins);
                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Adapter to put array list to ListView
        ListAdapter adapter = new SimpleAdapter(
                view.getContext(), list, R.layout.activity_list_item,
                new String[]{"id_ins", "nama_ins"},
                new int[]{R.id.txt_id, R.id.txt_name}
        );
        instrukturBinding.listView.setAdapter(adapter);
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
    public void onClick(View view) {
        // Event-handling add instructor
        startActivity(new Intent(view.getContext(), InstrukturTambahActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Event-handling when one of the list is selected
        Log.d("InstrukturFragment Log", "clicked");
        Intent myIntent = new Intent(getActivity(), InstrukturDetailActivity.class);
        HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }
}