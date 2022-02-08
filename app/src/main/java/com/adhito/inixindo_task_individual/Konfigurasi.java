package com.adhito.inixindo_task_individual;


public class Konfigurasi {
    // API URL untuk GET, GET_DETAIL, ADD, UPDATE dan DELETE

    // Web API untuk detail_kelas
    public static final String URL_DETAIL_KELAS_GET_ALL     = "http://192.168.5.83/api_task_individual/get_all_detail_kelas.php";

    // Web API untuk instruktur
    public static final String URL_INSTRUKTUR_GET_ALL       = "http://192.168.5.83/api_task_individual/get_all_instruktur.php";
    public static final String URL_INSTRUKTUR_GET_DETAIL    = "http://192.168.5.83/api_task_individual/get_detail_instruktur.php";
    public static final String URL_INSTRUKTUR_ADD           = "http://192.168.5.83/api_task_individual/add_instruktur.php";
    public static final String URL_INSTRUKTUR_UPDATE        = "http://192.168.5.83/api_task_individual/update_instruktur.php";
    public static final String URL_INSTRUKTUR_DELETE        = "http://192.168.5.83/api_task_individual/delete_instruktur.php";

    // Flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_pst";

}
