package mx.anzus.gamma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mx.anzus.gamma.procesos.cifrado;
import mx.anzus.gamma.procesos.validacion;

public class Database extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 1;
    private static final String DATATBASE_NAME = "mychemis_algebrae_v01.db";

    static SQLiteDatabase database;

    private static final String TABLE_NAME1 = "musuario";
    private static final String COLUMN_IDUS = "id_usu";
    private static final String COLUMN_NOM = "nom_usu";
    private static final String COLUMN_CURP = "curp_usu";
    private static final String COLUMN_CORUSU = "cor_usu";
    private static final String COLUMN_PASS = "pas_usu";
    private static final String COLUMN_TIPO = "tipo_usu";
    private static final String COLUMN_APIKEY = "key_usu";

    private static final String TABLE_NAME2 = "etime";
    private static final String COLUMN_IDTIM = "id_tim";
    private static final String COLUMN_VAL = "val_tim";
    private static final String COLUMN_DATE = "dat_tim";
    private static final String COLUMN_IDUSU = "id_usu";

    private static final String TABLE_NAME3 = "ctipousuario";
    private static final String COLUMN_IDTUS = "id_tus";
    private static final String COLUMN_CATUS = "cat_tus";

    private static final String TABLE_NAME4 = "eusuariosgrupo";
    private static final String COLUMN_IDUGR = "id_ugr";
    private static final String COLUMN_IDU = "id_usu";
    private static final String COLUMN_IDGRU = "id_gru";

    private static final String TABLE_NAME5 = "cgrupo";
    private static final String COLUMN_IDGR = "id_gru";
    private static final String COLUMN_NOMGRU = "nom_gru";
    private static final String COLUMN_CLAGRU = "cla_gru";

    private static final String TABLE_NAME6 = "ecuestionario";
    private static final String COLUMN_IDCUE = "id_cue";
    private static final String COLUMN_NOMCUE = "nom_cue";
    private static final String COLUMN_FECINI = "fec_ini";
    private static final String COLUMN_FECFIN = "fec_fin";
    private static final String COLUMN_IDBPR = "id_bpr";
    private static final String COLUMN_APRRPR = "aprrpr";
    private static final String COLUMN_IDG = "id_gru";

    private static final String TABLE_NAME7 = "mbancopreguntas";
    private static final String COLUMN_IDBP = "id_bpr";
    private static final String COLUMN_CONPRE = "con_pre";
    private static final String COLUMN_RESCOR = "res_cor";
    private static final String COLUMN_OPCA = "opc_a";
    private static final String COLUMN_OPCB = "opc_b";
    private static final String COLUMN_OPCC = "opc_c";
    private static final String COLUMN_OPCD = "opc_d";
    private static final String COLUMN_IDTE = "id_tem";
    private static final String COLUMN_IDDI = "id_dif";

    private static final String TABLE_NAME8 = "ctemas";
    private static final String COLUMN_IDTEM = "id_tem";
    private static final String COLUMN_NOMTEM = "nom_tem";


    public Database(Context context) {
        super(context, DATATBASE_NAME, null, DATABSE_VERSION);
        database = getWritableDatabase();
    }

    public void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME6);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME8);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME7);
    }

    public void create(SQLiteDatabase database)throws Exception{
        String sql ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " ( " + COLUMN_IDTUS + " INTEGER PRIMARY KEY ," + COLUMN_CATUS + " TEXT)";
        database.execSQL(sql);
        String sql2 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + " ( " + COLUMN_IDUS + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CORUSU + " TEXT,"+COLUMN_NOM+" TEXT,"+COLUMN_CURP+" TEXT,"+COLUMN_PASS+" TEXT,"+COLUMN_APIKEY+" TEXT,"+COLUMN_IDTUS+" INT,FOREIGN KEY("+COLUMN_IDTUS+") REFERENCES "+TABLE_NAME3+"("+COLUMN_IDTUS+"))";
        database.execSQL(sql2);
        String sql3 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " ( " + COLUMN_IDTIM + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT,"+COLUMN_VAL+" TEXT,"+COLUMN_IDUSU+" TEXT,FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))";
        database.execSQL(sql3);
        String sql4 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME5+ " ( " + COLUMN_IDGR + " INTEGER PRIMARY KEY," + COLUMN_NOMGRU + " TEXT,"+COLUMN_CLAGRU+" TEXT)";
        database.execSQL(sql4);
        String sql5 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME4 + " ( " + COLUMN_IDUGR + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IDU + " INTEGER,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"),FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))";
        database.execSQL(sql5);
        String sql6 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME6 + " ( " + COLUMN_IDCUE + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOMCUE + " INTEGER,"+COLUMN_FECINI+" INTEGER,"+COLUMN_FECFIN+" INTEGER,"+COLUMN_IDBPR+" TEXT,"+COLUMN_APRRPR+" TEXT,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"))";
        database.execSQL(sql6);
        String sql7 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME8 + " ( " + COLUMN_IDTEM + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOMTEM + " TEXT)";
        database.execSQL(sql7);
        String sql8 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME7 + " ( " + COLUMN_IDBP + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CONPRE + " TEXT,"+COLUMN_RESCOR+" TEXT,"+COLUMN_OPCA+" TEXT,"+COLUMN_OPCB+" TEXT,"+COLUMN_OPCC+" TEXT,"+COLUMN_OPCD+" TEXT,"+COLUMN_IDTEM+" INTEGER,"+COLUMN_IDDI+" INTEGER,FOREIGN KEY("+COLUMN_IDTE+") REFERENCES "+TABLE_NAME8+"("+COLUMN_IDTEM+"))";
        database.execSQL(sql8);
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (1,'Alumno')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (2,'Tutor')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (3,'Profesor')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (4,'Autoridad')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (5,'Administrador')");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " ( " + COLUMN_IDTUS + " INTEGER PRIMARY KEY ," + COLUMN_CATUS + " TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + " ( " + COLUMN_IDUS + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CORUSU + " TEXT,"+COLUMN_NOM+" TEXT,"+COLUMN_CURP+" TEXT,"+COLUMN_PASS+" TEXT,"+COLUMN_APIKEY+" TEXT,"+COLUMN_IDTUS+" INT,FOREIGN KEY("+COLUMN_IDTUS+") REFERENCES "+TABLE_NAME3+"("+COLUMN_IDTUS+"))");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " ( " + COLUMN_IDTIM + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT,"+COLUMN_VAL+" TEXT,"+COLUMN_IDUSU+" TEXT,FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME5 + " ( " + COLUMN_IDGR + " INTEGER PRIMARY KEY," + COLUMN_NOMGRU + " TEXT,"+COLUMN_CLAGRU+" TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME4 + " ( " + COLUMN_IDUGR + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IDU + " INTEGER,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"),FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME6 + " ( " + COLUMN_IDCUE + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOMCUE + " INTEGER,"+COLUMN_FECINI+" INTEGER,"+COLUMN_FECFIN+" INTEGER,"+COLUMN_IDBPR+" TEXT,"+COLUMN_APRRPR+" TEXT,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"))");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME8 + " ( " + COLUMN_IDTEM + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOMTEM + " TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME7 + " ( " + COLUMN_IDBP + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CONPRE + " TEXT,"+COLUMN_RESCOR+" TEXT,"+COLUMN_OPCA+" TEXT,"+COLUMN_OPCB+" TEXT,"+COLUMN_OPCC+" TEXT,"+COLUMN_OPCD+" TEXT,"+COLUMN_IDTEM+" INTEGER,"+COLUMN_IDDI+" INTEGER,FOREIGN KEY("+COLUMN_IDTE+") REFERENCES "+TABLE_NAME8+"("+COLUMN_IDTEM+"))");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (1,'Alumno')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (2,'Tutor')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (3,'Profesor')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (4,'Autoridad')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (5,'Administrador')");
        }catch(Exception e){
            System.out.println("Error en crear la BASE DE DATOS: "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME6);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME8);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME7);
        onCreate(db);
    }

    public static void changeMade(String mail){
        try {
            Calendar today = Calendar.getInstance();
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='PASS',"+COLUMN_DATE+"='"+String.valueOf(today.getTimeInMillis())+"' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            System.out.println(q);
            database.execSQL(q);
        }catch(Exception e){
            System.out.println("Erro en changeMade: "+e.getMessage());
        }
    }

    public static void changeFailed(String mail){
        try {
            Calendar today = Calendar.getInstance();
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='FAIL',"+COLUMN_DATE+"='"+String.valueOf(today.getTimeInMillis())+"' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            System.out.println(q);
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error in change Failed: "+e.getMessage());
        }
    }

    public static void cerrarSesion(String mail){
        try {
            Calendar today = Calendar.getInstance();
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='CLOSE',"+COLUMN_DATE+"='"+String.valueOf(today.getTimeInMillis())+"' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            System.out.println(q);
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error in cerrarSesion: "+e.getMessage());
        }
    }

    public static int getIdUserLastUsedApp(long today){
        int id = 0;
        try {
            String sql ="SELECT * FROM " + TABLE_NAME2+";";
            System.out.println(sql);
            Cursor cursor = database.rawQuery(sql, null);
            long todaylessdate = 0;
            long antDiaF = 0;
            while (cursor.moveToNext()) {
                if(!cursor.getString(2).equals("CLOSE")){
                    System.out.println(cursor.getString(1));
                    long date = Long.valueOf(cursor.getString(1));
                    long x = (today-date)/(60*60*24*1000);
                    if(antDiaF == 0){
                        id=cursor.getInt(0);
                        todaylessdate = x;
                        antDiaF++;
                    }else{
                        if(x<todaylessdate){
                            id=cursor.getInt(0);
                            todaylessdate = x;
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error en getIDUserLastUsedApp: "+e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public static long getDate(String mail){
        long date = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_IDUSU + "=" + getIdMail(mail), null);
            while (cursor.moveToNext()) {
                date = Long.valueOf(cursor.getString(1));
            }
        }catch (Exception e){
            System.out.println("Error en getDate: "+e.getMessage());
        }
        return date;
    }

    public static boolean checkChange(String mail){
        boolean check = false;
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_IDUSU + "=" + getIdMail(mail), null);
            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(1));
                if (cursor.getString(2).equals("WAIT") || cursor.getString(2).equals("FAIL")) {
                    check = false;
                }else{
                    check = true;
                }
            }
        }catch (Exception e){
            System.out.println("Error en checkChange: "+e.getMessage());
        }
        return check;
    }

    public static String getToken(String mail){
        String check = "";
        try {
            String sql = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_IDUSU + "=" + getIdMail(mail);
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                check = cursor.getString(5);
            }
        }catch (Exception e){
            System.out.println("Error en getToken: "+e.getMessage());
            check = mail;
        }
        return check;
    }

    public static String getPass(String mail){
        String check = "";
        try {
            String sql = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_IDUSU + "=" + getIdMail(mail);
            Cursor cursor = database.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                check = cifrado.decrypt(cursor.getString(4));
            }
        }catch (Exception e){
            System.out.println("Error en getToken: "+e.getMessage());
            check = mail;
        }
        return check;
    }

    public static String getIdMail(String mail){
        String idM = "";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_CORUSU + "='" + mail + "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idM = cursor.getString(0);
            }
        }catch (Exception e){
            System.out.println("Error en getIdMail: "+e.getMessage() );
        }
        return idM;
    }

    public static int getTipoMail(String mail){
        int idM = 0;
        try {
            String slq = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_CORUSU + "='" + mail + "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idM = cursor.getInt(6);
            }
        }catch (Exception e){
            System.out.println("Error en getTipoMail: "+e.getMessage() );
        }
        return idM;
    }

    public static void makingChange(String mail){
        try {
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='WAIT' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail) + "";
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Erro en makingChange: "+e.getMessage());
        }
    }

    public static void saveUserFirst(String mail,String nombre,String curp,String pass,String tipo,String key){
        try {
            String q = "INSERT INTO " + TABLE_NAME1 + "(" + COLUMN_CORUSU + "," + COLUMN_NOM + "," + COLUMN_CURP + "," + COLUMN_PASS + " ," + COLUMN_APIKEY + "," + COLUMN_IDTUS + ") VALUES ('" + mail + "','"+nombre+"','"+curp+"','" + cifrado.crypt(pass) + "','" + cifrado.crypt(key) + "'," + tipo + ")";
            database.execSQL(q);
            Calendar today = Calendar.getInstance();
            q = "INSERT INTO " + TABLE_NAME2 + "(" + COLUMN_DATE + "," + COLUMN_VAL + " ," + COLUMN_IDUSU + ") VALUES ('" + String.valueOf(today.getTimeInMillis()) + "','" + "CLOSE" + "'," + getIdMail(mail) + ")";
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error en saveUserFirts: "+e.getMessage());
        }
    }

    public static void updateUser(String mail,String nombre,String curp,String pass,String tipo,String key){
        try {
            String q = "UPDATE " + TABLE_NAME1 + " SET " + COLUMN_NOM + "='"+nombre+"'," + COLUMN_CURP + "='"+curp+"'," + COLUMN_PASS + "='"+cifrado.crypt(pass)+"',"+COLUMN_APIKEY+"='"+cifrado.crypt(key)+"',"+COLUMN_IDTUS+"="+tipo+" WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error en updateUser: "+e.getMessage());
        }
    }

    public static boolean login(String mail,String password){
        boolean log = false;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_CORUSU + "='" + mail + "' AND " + COLUMN_PASS + " = '" + cifrado.crypt(password) + "'";
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                log = true;
            }
        }catch(Exception e){
            System.out.println("Error en login: "+e.getMessage());
        }
        return log;
    }

    public static int userType(String mail){
        int i = 0;
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_CORUSU + "='" + mail + "'", null);
            if (cursor.moveToNext()) {
                i = cursor.getInt(cursor.getColumnIndex(COLUMN_IDTUS));
            }
        }catch(Exception e){
            System.out.println("Error en userType: "+e.getMessage());
        }
        return i;
    }

    public static void relacionarGrupo(String idusu,String nomgru){
        try{
            if(getIdRelationAluGrup(idusu,getIdGroup(nomgru)).equals("")){
                String q = "INSERT INTO " + TABLE_NAME4 + "(" + COLUMN_IDUS + "," + COLUMN_IDGR + ") VALUES (" + idusu + "," + getIdGroup(nomgru) + ")";
                database.execSQL(q);
            }
        }catch (Exception e){
            System.out.println("Error en relacionarGrupo: "+e.getMessage());
        }

    }

    public static String getIdGroup(String nomGrup){
        String idG="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME5 + " WHERE " + COLUMN_NOMGRU + "='" + nomGrup + "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idG = cursor.getString(0);
            }
        }catch (Exception e){
            System.out.println("Error en getIdGroup: "+e.getMessage() );
        }
        return idG;
    }

    public static String getNomGroup(String IdGrup){
        String nomG="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME5 + " WHERE " + COLUMN_IDGR + "=" + IdGrup + "";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                nomG = cursor.getString(1);
            }
        }catch (Exception e){
            System.out.println("Error en getNomGroup: "+e.getMessage() );
        }
        return nomG;
    }

    public static String getIdRelationAluGrup(String idAlu,String idGrup){
        String idG="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME4 + " WHERE " + COLUMN_IDUS + "=" + idAlu + " AND "+COLUMN_IDGR+"="+idGrup;
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idG = cursor.getString(0);
            }
        }catch (Exception e){
            System.out.println("Error en getIdMail: "+e.getMessage() );
        }
        return idG;
    }

    public static void saveGroup(String nomGrup){
        try{
            if(getIdGroup(nomGrup).equals("")){
                String q = "INSERT INTO " + TABLE_NAME5 + "(" + COLUMN_NOMGRU + "," + COLUMN_CLAGRU + ") VALUES ('" + nomGrup + "','')";
                database.execSQL(q);
            }else{
                String q = "UPDATE " + TABLE_NAME5 + " SET " + COLUMN_NOMGRU + " = '"+nomGrup+"'," + COLUMN_CLAGRU + " = '' WHERE "+COLUMN_NOMGRU+" = '" + nomGrup + "'";
                database.execSQL(q);
            }
        }catch (Exception e){
            System.out.println("Error en saveGroup: "+e.getMessage());
        }
    }

    public static void saveAluGroupProf(ArrayList<ArrayList> array,String correoProf){
        try{
            for (int i=0;i<array.size();i++){
                ArrayList alumnos = (ArrayList)array.get(i).get(0);
                String grupo = array.get(i).get(2).toString();
                String idgru = array.get(i).get(1).toString();
                saveGroup(grupo);
                relacionarGrupo(getIdMail(correoProf),grupo);
                for (int contAlu=0;contAlu<alumnos.size();contAlu++){
                    ArrayList alumno = (ArrayList) alumnos.get(contAlu);
                    String nombre = alumno.get(0).toString();
                    String correo = alumno.get(1).toString();
                    String curp = alumno.get(2).toString();
                    if(Database.getIdMail(correo).equals("")){
                        saveUserFirst(correo,nombre,curp,"","1","");
                        relacionarGrupo(getIdMail(correo),grupo);
                    }else{
                        updateUser(correo,nombre,curp,"","1","");
                        relacionarGrupo(getIdMail(correo),grupo);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error en saveALuGroupProf: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static ArrayList getGroups(String correoProf){
    ArrayList grupos= new ArrayList();
    try{
        String slq = "SELECT * FROM " + TABLE_NAME4 + " WHERE " + COLUMN_IDUSU + "=" + getIdMail(correoProf) ;
        Cursor cursor = database.rawQuery(slq, null);
        while (cursor.moveToNext()) {
            grupos.add(getNomGroup(cursor.getString(2)));
        }
    }catch (Exception e){
        System.out.println("Error ne getGroupsProf: "+e.getMessage());
    }
    return grupos;
    }

    public static ArrayList getDataUser(String id){
        ArrayList alu = new ArrayList();
        try{
            String slq = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_IDUSU + "=" + id ;
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                alu.add(cursor.getString(1));
                alu.add(cursor.getString(2));
                alu.add(cursor.getString(3));
            }
        }catch (Exception e){
            System.out.println("Error en getDataALu: "+e.getMessage());
        }
        return alu;
    }

    public static ArrayList getDataCuest(String idCue){
        ArrayList cue = new ArrayList();
        try{
            String slq = "SELECT * FROM " + TABLE_NAME6 + " WHERE " + COLUMN_IDCUE + "='" + idCue +"'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                cue.add(cursor.getString(1));
                cue.add(cursor.getString(2));
                cue.add(cursor.getString(3));
                cue.add(cursor.getString(4));
                cue.add(cursor.getString(5));
            }
        }catch (Exception e){
            System.out.println("Error en getDataCuest: "+e.getMessage());
        }
        return cue;
    }

    public static ArrayList getAlumnosGroup(String nomGrup,String idProf){
        ArrayList alumnos= new ArrayList();
        try{
            String slq = "SELECT * FROM " + TABLE_NAME4 + " WHERE " + COLUMN_IDGRU + "=" + getIdGroup(nomGrup) ;
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                if(!idProf.equals(cursor.getString(1))) {
                    alumnos.add(getDataUser(cursor.getString(1)));
                }
            }
        }catch (Exception e){
            System.out.println("Error ne getGroupsProf: "+e.getMessage());
        }
        return alumnos;
    }

    public static ArrayList getCuestionarios(String idProf){
        ArrayList cuestionarios= new ArrayList();
        try{
            ArrayList gruposProf = getGroups(idProf);
            for (int i=0;i<gruposProf.size();i++) {
                String slq = "SELECT * FROM " + TABLE_NAME6 + " WHERE " + COLUMN_IDGRU + "=" + getIdGroup(gruposProf.get(i).toString());
                Cursor cursor = database.rawQuery(slq, null);
                while (cursor.moveToNext()) {
                    ArrayList cuestionario = getDataCuest(cursor.getString(0));
                    cuestionarios.add(cuestionario);
                }
            }
        }catch (Exception e){
            System.out.println("Error ne getGroupsProf: "+e.getMessage());
        }
        return cuestionarios;
    }

    public static String getCuestId(String nomCue){
        String idC="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME6 + " WHERE " + COLUMN_NOMCUE + "='" + nomCue + "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idC = cursor.getString(0);
            }
        }catch (Exception e){
            System.out.println("Error en getCuestId: "+e.getMessage() );
        }
        return idC;
    }

    public static String[] getCuestAprRpr(String idCue){
        String[] aprrpr = null;
        try {
            String slq = "SELECT * FROM " + TABLE_NAME6 + " WHERE " + COLUMN_IDCUE + "='" + idCue + "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                aprrpr = cursor.getString(5).split(",");
            }
        }catch (Exception e){
            System.out.println("Error en getCuestAprRpr: "+e.getMessage() );
        }
        return aprrpr;
    }

    public static void saveCuestionarios(ArrayList<ArrayList> array){
        try{
            for (int i=0;i<array.size();i++){
                ArrayList cuestionario = (ArrayList) array.get(i);
                String nombre = cuestionario.get(1).toString();
                String fecini = cuestionario.get(2).toString();
                String fecfin = cuestionario.get(3).toString();
                String AprRpr = cuestionario.get(5).toString().replace("]","").replace("[","");
                String idgru = cuestionario.get(4).toString();
                saveCuestionario(nombre,fecini,fecfin,"",AprRpr,idgru);
            }
        }catch (Exception e){
            System.out.println("Error en saveCuestionarios: "+e.getMessage());
        }

    }

    public static void saveCuestionario(String nomcue,String fecini,String fecfin,String idbpr,String AprRpr,String idgru){
        try{
            if(getCuestId(nomcue).equals("")){
                String q = "INSERT INTO " + TABLE_NAME6 + "(" + COLUMN_NOMCUE + "," + COLUMN_FECINI + "," + COLUMN_FECFIN + "," + COLUMN_IDBPR + ",'"+COLUMN_APRRPR+"'," + COLUMN_IDG+ ") VALUES ('" + nomcue+ "','"+fecini+"','"+fecfin+"','"+idbpr+"','"+AprRpr+"',"+idgru+")";
                database.execSQL(q);
            }else{
                String q = "UPDATE " + TABLE_NAME6 + " SET " + COLUMN_NOMCUE + " = '"+nomcue+"'," + COLUMN_FECINI + " = '"+fecini+"'," + COLUMN_FECFIN + " = '"+fecfin+"'," + COLUMN_IDBPR + " = '"+idbpr+"'," + COLUMN_APRRPR + " = '"+AprRpr+"'," + COLUMN_IDG + " = "+idgru+" WHERE "+COLUMN_NOMCUE+" = '" + nomcue + "'";
                database.execSQL(q);
            }
        }catch (Exception e){
            System.out.println("Error en saveCuestionario: "+e.getMessage());
        }
    }

    public static String getIdTema(String nom_tem){
    String idtema ="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME8 + " WHERE " + COLUMN_NOMTEM + "='" +nom_tem+ "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idtema = cursor.getString(0);
            }
        }catch (Exception e){
            System.out.println("Error en getIdTema: "+e.getMessage() );
        }
    return idtema;
    }

    public static String getTema(String idTem){
        String idtema ="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME8 + " WHERE " + COLUMN_IDTEM + "='" +idTem+ "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idtema = cursor.getString(1);
            }
        }catch (Exception e){
            System.out.println("Error en getTema: "+e.getMessage() );
        }
        return idtema;
    }

    public static void saveTema(String nom_tem){
        try {
            if(getIdTema(nom_tem).equals("")){
                String q = "INSERT INTO " + TABLE_NAME8 + "(" + COLUMN_NOMTEM + ") VALUES ('" + nom_tem+ "')";
                database.execSQL(q);
            }
        }catch (Exception e){
            System.out.println("Error en saveTema: "+e.getMessage() );
        }
    }

    public static String getIdPregunta(String nomPre){
        String idPre ="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME7 + " WHERE " + COLUMN_CONPRE + "='" +nomPre+ "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idPre = cursor.getString(0);
            }
        }catch (Exception e){
            System.out.println("Error en getIdPregunta: "+e.getMessage() );
        }
        return idPre;
    }

    public static ArrayList getPregunta(String idPre){
        ArrayList pregunta = new ArrayList<>();
        try {
            String slq = "SELECT * FROM " + TABLE_NAME7 + " WHERE " + COLUMN_IDBPR + "='" +idPre+ "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                pregunta.add(cursor.getString(1));
                pregunta.add(cursor.getString(2));
                pregunta.add(cursor.getString(3));
                pregunta.add(cursor.getString(4));
                pregunta.add(cursor.getString(5));
                pregunta.add(cursor.getString(6));
                pregunta.add(getTema(cursor.getString(7)));
                pregunta.add(cursor.getString(8));
            }
        }catch (Exception e){
            System.out.println("Error en getPregunta: "+e.getMessage() );
        }
        return pregunta;
    }

    public static String getPreguntaCuest(String idCue){
        String idPre ="";
        try {
            String slq = "SELECT * FROM " + TABLE_NAME6 + " WHERE " + COLUMN_IDCUE + "='" +idCue+ "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                idPre = cursor.getString(4);
            }
        }catch (Exception e){
            System.out.println("Error en getPreguntasCuest: "+e.getMessage() );
        }
        return idPre;
    }

    public static void relateCuestPre(String idPre,String idCue){
        try {
            String preguntas = getPreguntaCuest(idCue);
            if(preguntas.equals("")){
                String q = "UPDATE " + TABLE_NAME6 + " SET " + COLUMN_IDBPR + " = '"+idPre+"' WHERE "+COLUMN_IDCUE+" = '" + idCue + "'";
                database.execSQL(q);
            }else{
                preguntas += ","+idPre;
                String q = "UPDATE " + TABLE_NAME6 + " SET " + COLUMN_IDBPR + " = '"+preguntas+"' WHERE "+COLUMN_IDCUE+" = '" + idCue + "'";
                database.execSQL(q);
            }
        }catch (Exception e){
            System.out.println("Error en relateCuestPre: "+e.getMessage() );
        }
    }

    public static void savePregunta(String nom_pre,String res_cor,String tema,String opca,String opcb,String opcc,String opcd,String dif,String idcue){
        try{
            saveTema(tema);
            if(getIdPregunta(nom_pre).equals("")){
                String q = "INSERT INTO " + TABLE_NAME7 + "(" + COLUMN_CONPRE + "," + COLUMN_RESCOR + "," + COLUMN_OPCA + "," + COLUMN_OPCB + "," + COLUMN_OPCC+ "," + COLUMN_OPCD+ "," + COLUMN_IDTEM+ "," + COLUMN_IDDI+ ") VALUES ('" + nom_pre+ "','"+res_cor+"','"+opca+"','"+opcb+"','"+opcc+"','"+opcd+"',"+getIdTema(tema)+","+dif+")";
                database.execSQL(q);
            }else{
                String q = "UPDATE " + TABLE_NAME7 + " SET " + COLUMN_CONPRE + " = '"+nom_pre+"'," + COLUMN_RESCOR + " = '"+res_cor+"'," + COLUMN_OPCA + " = '"+opca+"'," + COLUMN_OPCB + " = '"+opcb+"'," + COLUMN_OPCC + " ='"+opcc+"'," + COLUMN_OPCD + " = '"+opcd+"'," + COLUMN_IDTEM + " = "+getIdTema(tema)+" ," + COLUMN_IDDI + " = "+dif+" WHERE "+COLUMN_CONPRE+" = '" + nom_pre + "'";
                database.execSQL(q);
            }
            relateCuestPre(getIdPregunta(nom_pre),idcue);
        }catch (Exception e){
            System.out.println("Error en savePregunta: "+e.getMessage());
        }
    }

    public static ArrayList getPreguntasCuestionario(String idCue){
        ArrayList preguntas = new ArrayList();
        try{
            String slq = "SELECT * FROM " + TABLE_NAME6 + " WHERE " + COLUMN_IDCUE + "='" +idCue+ "'";
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                String[] pres = cursor.getString(4).split(",");
                for (int i =0;i<pres.length;i++){
                    preguntas.add(getPregunta(pres[i]));
                }
            }
        }catch (Exception e){
            System.out.println("Error en getPreguntasCuestionario: "+e.getMessage());
        }
        return preguntas;
    }
}
