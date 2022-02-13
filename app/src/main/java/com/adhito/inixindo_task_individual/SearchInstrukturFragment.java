package com.adhito.inixindo_task_individual;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchInstrukturFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchInstrukturFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener, AdapterView.OnItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText edit_search;
    private Button button_search;
    private View view;
    private ListView listView;
    private String JSON_STRING;
    private ProgressDialog loading;

    public SearchInstrukturFragment() {
        // Required empty public constructor
    }
    
    // TODO: Rename and change types and number of parameters
    public static SearchInstrukturFragment newInstance(String param1, String param2) {
        SearchInstrukturFragment fragment = new SearchInstrukturFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_instruktur, container, false);

        edit_search = view.findViewById(R.id.edit_search);

        listView = view.findViewById(R.id.listView);

        button_search = view.findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = edit_search.getText().toString().trim();

                getData(val);
            }
        });


        return view;
    }

    private void getData(String val) {
        class GetJsonData extends AsyncTask<Void, Void, String> {
            // Override PreExecute (Ctrl + O select the onPreExecute)
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Querying data instruktur ... ", "Please wait ...", false, false);
            }

            // Override doInBackground (Ctrl + O select the doInBackground)
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_INSTRUKTUR,val);
                return result;
            }

            // Override onPostExecute (Ctrl + O select the onPostExecute)
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                displaySearchResult(JSON_STRING);
            }
        }
        GetJsonData getJsonData = new GetJsonData();
        getJsonData.execute();
    }


    private void displaySearchResult(String json) {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        Log.d("json",json);

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id_ins = object.getString("id_ins");
                String nama_ins = object.getString("nama_ins");
                String email_ins = object.getString("email_ins");
                String hp_ins = object.getString("hp_ins");

                HashMap<String, String> res = new HashMap<>();
                res.put("id_ins", id_ins);
                res.put("nama_ins", nama_ins);
                res.put("email_ins", email_ins);
                res.put("hp_ins", hp_ins);

                list.add(res);
                Log.d("RES", String.valueOf(res));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create adapter to put array list to ListView
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.activity_list_item_search_instruktur,
                new String[]{"id_ins", "nama_ins", "email_ins", "hp_ins", ""},
                new int[]{R.id.txt_id_ins, R.id.txt_nama_ins, R.id.txt_email_ins, R.id.txt_hp_ins}

        );
        listView.setAdapter(adapter);
    }


    private void search_data(String val) {
        Toast.makeText(getContext(), val, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void doBack() {
    }

}