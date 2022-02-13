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
 * Use the {@link SearchPesertaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPesertaFragment extends Fragment implements MainActivity.OnBackPressedListener, View.OnClickListener, AdapterView.OnItemClickListener{

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

    public SearchPesertaFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchPesertaFragment newInstance(String param1, String param2) {
        SearchPesertaFragment fragment = new SearchPesertaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_peserta, container, false);

        edit_search = view.findViewById(R.id.edit_search);

        listView = view.findViewById(R.id.listView);
       // listView.setVisibility(View.GONE);

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
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Querying peserta data ... ", "Please wait ...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_SEARCH_PESERTA,val);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);

                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON: ", JSON_STRING);
                displaySearchResult(JSON_STRING);
//                displaySearchResult();
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
                String id_pst = object.getString("id_pst");
                String nama_pst = object.getString("nama_pst");
                String email_pst = object.getString("email_pst");
                String hp_pst = object.getString("hp_pst");
                String instansi_pst = object.getString("instansi_pst");

                HashMap<String, String> res = new HashMap<>();
                res.put("id_pst", id_pst);
                res.put("nama_pst", nama_pst);
                res.put("email_pst", email_pst);
                res.put("hp_pst", hp_pst);
                res.put("instansi_pst", instansi_pst);


                list.add(res);
                Log.d("RES", String.valueOf(res));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakkan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getContext(), list, R.layout.activity_list_item_search_peserta,
                new String[]{"id_pst", "nama_pst", "email_pst", "hp_pst", "instansi_pst"},
                new int[]{R.id.txt_id_pst, R.id.txt_nama_pst, R.id.txt_email_pst, R.id.txt_hp_pst, R.id.txt_instansi_pst}

        );
        listView.setAdapter(adapter);
//        listView.setVisibility(View.VISIBLE);

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