package com.adhito.inixindo_task_individual;


public class Konfigurasi {
    // API URL untuk GET, GET_DETAIL, ADD, UPDATE dan DELETE

    // Web API untuk Peserta
    public static final String URL_PESERTA_GET_ALL       = "http://192.168.5.93/api_task_individual/get_all_peserta.php";
    public static final String URL_PESERTA_GET_DETAIL    = "http://192.168.5.93/api_task_individual/get_detail_peserta.php?id_pst=";
    public static final String URL_PESERTA_ADD           = "http://192.168.5.93/api_task_individual/add_peserta.php";
    public static final String URL_PESERTA_UPDATE        = "http://192.168.5.93/api_task_individual/update_peserta.php";
    public static final String URL_PESERTA_DELETE        = "http://192.168.5.93/api_task_individual/delete_peserta.php?id_pst=";

    // Web API untuk Instruktur
    public static final String URL_INSTRUKTUR_GET_ALL       = "http://192.168.5.93/api_task_individual/get_all_instruktur.php";
    public static final String URL_INSTRUKTUR_GET_DETAIL    = "http://192.168.5.93/api_task_individual/get_detail_instruktur.php?id_ins=";
    public static final String URL_INSTRUKTUR_ADD           = "http://192.168.5.93/api_task_individual/add_instruktur.php";
    public static final String URL_INSTRUKTUR_UPDATE        = "http://192.168.5.93/api_task_individual/update_instruktur.php";
    public static final String URL_INSTRUKTUR_DELETE        = "http://192.168.5.93/api_task_individual/delete_instruktur.php?id_ins=";

    // Web API untuk Materi
    public static final String URL_MATERI_GET_ALL       = "http://192.168.5.93/api_task_individual/get_all_materi.php";
    public static final String URL_MATERI_GET_DETAIL    = "http://192.168.5.93/api_task_individual/get_detail_materi.php?id_mat=";
    public static final String URL_MATERI_ADD           = "http://192.168.5.93/api_task_individual/add_materi.php";
    public static final String URL_MATERI_UPDATE        = "http://192.168.5.93/api_task_individual/update_materi.php";
    public static final String URL_MATERI_DELETE        = "http://192.168.5.93/api_task_individual/delete_materi.php?id_mat=";

    // Web API untuk Kelas
    public static final String URL_KELAS_GET_ALL       = "http://192.168.5.93/api_task_individual/get_all_kelas.php";
    public static final String URL_KELAS_GET_DETAIL    = "http://192.168.5.93/api_task_individual/get_detail_kelas.php?id_kls=";
    public static final String URL_KELAS_ADD           = "http://192.168.5.93/api_task_individual/add_kelas.php";
    public static final String URL_KELAS_UPDATE        = "http://192.168.5.93/api_task_individual/update_kelas.php";
    public static final String URL_KELAS_DELETE        = "http://192.168.5.93/api_task_individual/delete_kelas.php?id_kls=";

    // Web API untuk Kelas Detail
    public static final String URL_KELAS_DETAIL_GET_ALL             = "http://192.168.5.93/api_task_individual/get_all_detail_kelas.php";
    public static final String URL_KELAS_DETAIL_JUMLAH_GET_ALL      = "http://192.168.5.93/api_task_individual/get_all_detail_kelas_jumlah.php";
    public static final String URL_KELAS_DETAIL_GET_DETAIL          = "http://192.168.5.93/api_task_individual/get_detail_detail_kelas.php?id_detail_kls=";
    public static final String URL_KELAS_DETAIL_ADD                 = "http://192.168.5.93/api_task_individual/add_detail_kelas.php";
    public static final String URL_KELAS_DETAIL_UPDATE              = "http://192.168.5.93/api_task_individual/update_detail_kelas.php";
    public static final String URL_KELAS_DETAIL_DELETE              = "http://192.168.5.93/api_task_individual/delete_detail_kelas.php?id_detail_kls=";

    // Web API untuk Search
    public static final String URL_SEARCH_PESERTA    = "http://192.168.5.93/api_task_individual/search_peserta.php?id_pst=";
    public static final String URL_SEARCH_INSTRUKTUR    = "http://192.168.5.93/api_task_individual/search_instruktur.php?id_ins=";

    // key adn value JSON yang muncul di browser
    public static final String KEY_PGW_ID = "id";
    public static final String KEY_PGW_NAMA = "name";
    public static final String KEY_PGW_JABATAN ="desg";
    public static final String KEY_PGW_GAJI ="salary";

    // flag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_pst";
    public static final String TAG_JSON_ID_INS = "id_ins";
    public static final String TAG_JSON_ID_MAT = "id_mat";
    public static final String TAG_JSON_ID_KLS = "id_kls";
    public static final String TAG_JSON_NAMA ="name";
    public static final String TAG_JSON_JABATAN ="desg";
    public static final String TAG_JSON_GAJI ="salary";

    // variabel alias ID Pegawai
    public static final String PGW_ID = "emp_id"; // Memberikan Alias
}
