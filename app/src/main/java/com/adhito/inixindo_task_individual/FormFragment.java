package com.adhito.inixindo_task_individual;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment implements View.OnClickListener{

    Spinner spinner;
    Button btn_form_save;
    RadioGroup radiogroup_vaksinasi, radiogroup_statusantigenpcr;
    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment();
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

        View view = inflater.inflate(R.layout.fragment_form, container, false);

        EditText form_edit_name = (EditText) view.findViewById(R.id.form_edit_name);
        EditText form_edit_email = (EditText) view.findViewById(R.id.form_edit_email);
        RadioButton radio_vaksinasi_yes = (RadioButton) view.findViewById(R.id.radio_vaksinasi_yes);
        RadioButton radio_vaksinasi_no = (RadioButton) view.findViewById(R.id.radio_vaksinasi_no);
        RadioButton radiogroup_statusantigenpcr_positif = (RadioButton) view.findViewById(R.id.radiogroup_statusantigenpcr_positif);
        RadioButton radiogroup_statusantigenpcr_negatif = (RadioButton) view.findViewById(R.id.radiogroup_statusantigenpcr_negatif);
        spinner = (Spinner) view.findViewById(R.id.form_spinner);

        btn_form_save = view.findViewById(R.id.btn_form_save);
        radiogroup_vaksinasi = view.findViewById(R.id.radiogroup_vaksinasi);
        radiogroup_statusantigenpcr = view.findViewById(R.id.radiogroup_statusantigenpcr);
        String name = form_edit_name.getText().toString().trim();

        btn_form_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status_vaksinasi = null;
                String status_antigenpcr = null;
                    String nama = String.valueOf(form_edit_name.getText());
                String email = String.valueOf(form_edit_email.getText());

                String spinner_val = spinner.getSelectedItem().toString();


                if(radio_vaksinasi_yes.isChecked()){
                    status_vaksinasi = "Yes";
                }
                else if(radio_vaksinasi_no.isChecked()){
                    status_vaksinasi = "No";
                }

                if(radiogroup_statusantigenpcr_positif.isChecked()){
                    status_antigenpcr = "Positif";
                }
                else if(radiogroup_statusantigenpcr_negatif.isChecked()){
                    status_antigenpcr = "Negatif";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Your Data");
                builder.setMessage(
                        "Nama : " + nama +
                        "\nEmail: " + email +
                        "\nJenis Kelamin: " + spinner_val +
                        "\nStatus vaksinasi : " + status_vaksinasi +
                        "\nStatus antigen/pcr : " + status_antigenpcr);
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_input_add));
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Berhasil simpan form!",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Berhasil simpan form!",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btn_form_save){
            Toast.makeText(getActivity(), "Berhasil simpan form!",Toast.LENGTH_LONG).show();
        }
    }
}




