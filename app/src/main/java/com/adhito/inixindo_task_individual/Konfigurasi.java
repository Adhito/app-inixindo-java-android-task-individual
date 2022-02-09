package com.adhito.inixindo_task_individual;


public class Konfigurasi {
    // API URL untuk GET, GET_DETAIL, ADD, UPDATE dan DELETE

    // Web API untuk detail_kelas
    public static final String URL_DETAIL_KELAS_GET_ALL     = "http://192.168.5.83/api_task_individual/get_all_detail_kelas.php";

    // Web API untuk peserta
    public static final String URL_PESERTA_GET_ALL       = "http://192.168.5.83/api_task_individual/get_all_peserta.php";
    public static final String URL_PESERTA_GET_DETAIL    = "http://192.168.5.83/api_task_individual/get_detail_peserta.php?id_pst=";
    public static final String URL_PESERTA_ADD           = "http://192.168.5.83/api_task_individual/add_peserta.php";
    public static final String URL_PESERTA_UPDATE        = "http://192.168.5.83/api_task_individual/update_peserta.php";
    public static final String URL_PESERTA_DELETE        = "http://192.168.5.83/api_task_individual/delete_peserta.php?id_pst=";

    // Web API untuk instruktur
    public static final String URL_INSTRUKTUR_GET_ALL       = "http://192.168.5.83/api_task_individual/get_all_instruktur.php";
    public static final String URL_INSTRUKTUR_GET_DETAIL    = "http://192.168.5.83/api_task_individual/get_detail_instruktur.php";
    public static final String URL_INSTRUKTUR_ADD           = "http://192.168.5.83/api_task_individual/add_instruktur.php";
    public static final String URL_INSTRUKTUR_UPDATE        = "http://192.168.5.83/api_task_individual/update_instruktur.php";
    public static final String URL_INSTRUKTUR_DELETE        = "http://192.168.5.83/api_task_individual/delete_instruktur.php";

    // key adn value JSON yang muncul di browser
    public static final String KEY_PGW_ID = "id";
    public static final String KEY_PGW_NAMA = "name";
    public static final String KEY_PGW_JABATAN ="desg";
    public static final String KEY_PGW_GAJI ="salary";

    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_pst";
    public static final String TAG_JSON_NAMA ="name";
    public static final String TAG_JSON_JABATAN ="desg";
    public static final String TAG_JSON_GAJI ="salary";

    // variabel alias ID Pegawai
    public static final String PGW_ID = "emp_id"; // Memberikan Alias
}
