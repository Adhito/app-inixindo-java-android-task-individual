

    EditText edit_id_detail_kls, edit_id_kls, edit_id_pst;
    Button btn_update_detail_kelas, btn_delete_detail_kelas;
    String id_detail_kls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_detail);
        edit_id_detail_kls = findViewById(R.id.edit_id_detail_kls);
        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_id_pst = findViewById(R.id.edit_id_pst);

        btn_update_detail_kelas = findViewById(R.id.btn_update_detail_kelas);
        btn_delete_detail_kelas = findViewById(R.id.btn_delete_detail_kelas);

        Intent receiveIntent = getIntent();
        id_detail_kls = receiveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id_detail_kls.setText(id_detail_kls);

        getJSON();

        btn_update_detail_kelas.setOnClickListener(this);
        btn_delete_detail_kelas.setOnClickListener(this);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // ctrl + o pilih OnPreExcetue
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetail.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Saat proses ambil data terjadi
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.DETAIL_KELAS_URL_GET_DETAIL, id_detail_kls);
                return result;
            }

            // ctrl + o pilih OnPostExcetue
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                Toast.makeText(DetailKelasDetail.this, message, Toast.LENGTH_LONG).show();
                displayDetailData(message);

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_detail_kls = object.getString("id_detail_kls");
            String id_kls = object.getString("id_kls");
            String id_pst = object.getString("id_pst");

            edit_id_detail_kls.setText(id_detail_kls);
            edit_id_kls.setText(id_kls);
            edit_id_pst.setText(id_pst);

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
        if (view == btn_update_detail_kelas){
            updateDataDetailKelas();
        }
        else if(view == btn_delete_detail_kelas){
            confirmDeleteDataDetailKelas();
        }
    }

    private void confirmDeleteDataDetailKelas() {
        //Confirmation using alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menghapus Data");
        builder.setMessage("Apakah anda yaking menhapus data ini?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDataDetailKelas();
            }
        });
        builder.setNegativeButton("Cancel",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void deleteDataDetailKelas() {
        class DeleteDataDetailKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetail.this,
                        "Menghapus data","Harap tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.DETAIL_KELAS_URL_DELETE, id_detail_kls);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailKelasDetail.this,
                        "pesan: "+s, Toast.LENGTH_LONG).show();
                //redirect ke lihat data activity
                Intent myIntent = new Intent(DetailKelasDetail.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail_kelas");
                startActivity(myIntent);
            }

        }
        DeleteDataDetailKelas deleteDataDetailKelas = new DeleteDataDetailKelas();
        deleteDataDetailKelas.execute();
    }

    private void updateDataDetailKelas() {
//        final String id_detail_kls = edit_id_detail_kls.getText().toString().trim();
        final String id_kls = edit_id_kls.getText().toString().trim();
        final String id_pst = edit_id_pst.getText().toString().trim();
        class UpdateDataDetailKelas extends AsyncTask<Void, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetail.this,
                        "Mengubah Data", "Harap Tunggu",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put("id_detail_kls", id_detail_kls);
                kelas.put("id_kls", id_kls);
                kelas.put("id_pst", id_pst);


                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.DETAIL_KELAS_URL_UPDATE, kelas);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailKelasDetail.this,
                        "pesan: "+s, Toast.LENGTH_SHORT).show();
                //redirect ke lihat data activity
                Intent myIntent = new Intent(DetailKelasDetail.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail_kelas");
                startActivity(myIntent);
            }
        }
        UpdateDataDetailKelas updateDataDetailKelas = new UpdateDataDetailKelas();
        updateDataDetailKelas.execute();
    }