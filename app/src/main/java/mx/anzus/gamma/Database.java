package mx.anzus.gamma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mx.anzus.gamma.procesos.cifrado;
import mx.anzus.gamma.procesos.validacion;

public class Database extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 1;
    private static final String DATATBASE_NAME = "mychemis_algebrae_v01.db";

    static SQLiteDatabase database;

    private static final String TABLE_NAME1 = "musuario";
    private static final String COLUMN_IDUS = "id_usu";
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
    private static final String COLUMN_IDG = "id_gru";



    public Database(Context context) {
        super(context, DATATBASE_NAME, null, DATABSE_VERSION);
        database = getWritableDatabase();
    }

    public void create(SQLiteDatabase database)throws Exception{
        String sql ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " ( " + COLUMN_IDTUS + " INTEGER PRIMARY KEY ," + COLUMN_CATUS + " TEXT)";
        database.execSQL(sql);
        String sql2 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME1 + " ( " + COLUMN_IDUS + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CORUSU + " TEXT,"+COLUMN_PASS+" TEXT,"+COLUMN_APIKEY+" TEXT,"+COLUMN_IDTUS+" INT,FOREIGN KEY("+COLUMN_IDTUS+") REFERENCES "+TABLE_NAME3+"("+COLUMN_IDTUS+"))";
        database.execSQL(sql2);
        String sql3 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " ( " + COLUMN_IDTIM + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT,"+COLUMN_VAL+" TEXT,"+COLUMN_IDUSU+" TEXT,FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))";
        database.execSQL(sql3);
        String sql4 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME5+ " ( " + COLUMN_IDGR + " INTEGER PRIMARY KEY," + COLUMN_NOMGRU + " TEXT,"+COLUMN_CLAGRU+" TEXT)";
        database.execSQL(sql4);
        String sql5 ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME4 + " ( " + COLUMN_IDUGR + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IDU + " INTEGER,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"),FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))";
        database.execSQL(sql5);
        String sql6 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME6 + " ( " + COLUMN_IDCUE + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOMCUE + " INTEGER,"+COLUMN_FECINI+" INTEGER,"+COLUMN_FECFIN+" INTEGER,"+COLUMN_IDBPR+" TEXT,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"))";
        database.execSQL(sql6);
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (1,'Alumno')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (2,'Tutor')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (3,'Profesor')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (4,'Autoridad')");
        database.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (5,'Administrador')");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME3 + " ( " + COLUMN_IDTUS + " INTEGER PRIMARY KEY ," + COLUMN_CATUS + " TEXT)");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (1,'Alumno')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (2,'Tutor')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (3,'Profesor')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (4,'Autoridad')");
            db.execSQL("INSERT INTO "+TABLE_NAME3 +"("+COLUMN_IDTUS+","+COLUMN_CATUS+") VALUES (5,'Administrador')");
            db.execSQL("CREATE TABLE " + TABLE_NAME1 + " ( " + COLUMN_IDUS + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CORUSU + " TEXT,"+COLUMN_PASS+" TEXT,"+COLUMN_APIKEY+" TEXT,"+COLUMN_IDTUS+" INT,FOREIGN KEY("+COLUMN_IDTUS+") REFERENCES "+TABLE_NAME3+"("+COLUMN_IDTUS+"))");
            db.execSQL("CREATE TABLE " + TABLE_NAME2 + " ( " + COLUMN_IDTIM + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DATE + " TEXT,"+COLUMN_VAL+" TEXT,"+COLUMN_IDUSU+" TEXT,FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))");
            db.execSQL("CREATE TABLE " + TABLE_NAME5 + " ( " + COLUMN_IDGR + " INTEGER PRIMARY KEY," + COLUMN_NOMGRU + " TEXT,"+COLUMN_CLAGRU+" TEXT)");
            db.execSQL("CREATE TABLE " + TABLE_NAME4 + " ( " + COLUMN_IDUGR + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IDU + " INTEGER,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"),FOREIGN KEY("+COLUMN_IDUSU+") REFERENCES "+TABLE_NAME1+"("+COLUMN_IDUSU+"))");
            db.execSQL("CREATE TABLE " + TABLE_NAME6 + " ( " + COLUMN_IDCUE + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOMCUE + " INTEGER,"+COLUMN_FECINI+" INTEGER,"+COLUMN_FECFIN+" INTEGER,"+COLUMN_IDBPR+" TEXT,"+COLUMN_IDGRU+" INTEGER,FOREIGN KEY("+COLUMN_IDGR+") REFERENCES "+TABLE_NAME5+"("+COLUMN_IDGR+"))");
        }catch(Exception e){
            System.out.println("Error en crear la BASE DE DATOS: "+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(db);
    }

    public static void changeMade(String mail){
        try {
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='PASS' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            database.execSQL(q);
        }catch(Exception e){
            System.out.println("Erro en changeMade: "+e.getMessage());
        }
    }

    public static void changeFailed(String mail){
        try {
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='FAIL' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error in change Failed: "+e.getMessage());
        }
    }

    public static boolean checkChange(String mail){
        boolean check = false;
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_IDUSU + "=" + getIdMail(mail), null);
            while (cursor.moveToNext()) {
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
                check = cursor.getString(3);
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

    public static void makingChange(String mail){
        try {
            String q = "UPDATE " + TABLE_NAME2 + " SET " + COLUMN_VAL + "='WAIT' WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail) + "";
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Erro en makingChange: "+e.getMessage());
        }
    }

    public static void saveUserFirst(String mail,String pass,String tipo,String key){
        try {
            String q = "INSERT INTO " + TABLE_NAME1 + "(" + COLUMN_CORUSU + "," + COLUMN_PASS + " ," + COLUMN_APIKEY + "," + COLUMN_IDTUS + ") VALUES ('" + mail + "','" + cifrado.crypt(pass) + "','" + cifrado.crypt(key) + "'," + tipo + ")";
            database.execSQL(q);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            String currentDateandTime = sdf.format(new Date());
            q = "INSERT INTO " + TABLE_NAME2 + "(" + COLUMN_DATE + "," + COLUMN_VAL + " ," + COLUMN_IDUSU + ") VALUES ('" + currentDateandTime + "','" + "PASS" + "'," + getIdMail(mail) + ")";
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error en saveUserFirts: "+e.getMessage());
        }
    }

    public static void updateUser(String mail,String pass,String tipo,String key){
        try {
            String q = "UPDATE " + TABLE_NAME1 + " SET " + COLUMN_PASS + "='"+cifrado.crypt(pass)+"',"+COLUMN_APIKEY+"='"+cifrado.crypt(key)+"',"+COLUMN_IDTUS+"="+tipo+" WHERE " + COLUMN_IDUSU + " = " + getIdMail(mail);
            database.execSQL(q);
        }catch (Exception e){
            System.out.println("Error en updateUser: "+e.getMessage());
        }
    }

    public static boolean login(String mail,String password){
        boolean log = false;
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_CORUSU + "='" + mail + "' AND " + COLUMN_PASS + " = '" + cifrado.crypt(password) + "'", null);
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
                i = cursor.getInt(4);
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
            String slq = "SELECT * FROM " + TABLE_NAME5 + " WHERE " + COLUMN_IDGR + "='" + IdGrup + "'";
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
                String q = "UPDATE " + TABLE_NAME5 + " SET " + COLUMN_NOMGRU + " = "+nomGrup+"," + COLUMN_CLAGRU + " = '' WHERE "+COLUMN_NOMGRU+" = '" + nomGrup + "'";
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
                    if(Database.getIdMail(correo).equals("")){
                        saveUserFirst(correo,"","1","");
                        relacionarGrupo(getIdMail(correo),grupo);
                    }else{
                        updateUser(correo,"","1","");
                        relacionarGrupo(getIdMail(correo),grupo);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error en saveALuGroupProf: "+e.getMessage());
        }
    }

    public static ArrayList getGroupsProf(String correoProf){
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

    public static String getDataAlu(String idAlu){
        String alu= "";
        try{
            String slq = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + COLUMN_IDUSU + "=" + idAlu ;
            Cursor cursor = database.rawQuery(slq, null);
            while (cursor.moveToNext()) {
                alu = cursor.getString(1);
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
                    alumnos.add(getDataAlu(cursor.getString(1)));
                }
            }
        }catch (Exception e){
            System.out.println("Error ne getGroupsProf: "+e.getMessage());
        }
        return alumnos;
    }

    public static ArrayList getCuestionariosProf(String idProf){
        ArrayList cuestionarios= new ArrayList();
        try{
            ArrayList gruposProf = getGroupsProf(idProf);
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

    public static void saveCuestionarios(ArrayList<ArrayList> array){
        try{
            for (int i=0;i<array.size();i++){
                ArrayList cuestionario = (ArrayList) array.get(i);
                String nombre = cuestionario.get(1).toString();
                String fecini = cuestionario.get(2).toString();
                String fecfin = cuestionario.get(3).toString();
                String idgru = cuestionario.get(4).toString();
                saveCuestionario(nombre,fecini,fecfin,"",idgru);
            }
        }catch (Exception e){
            System.out.println("Error en saveCuestionarios: "+e.getMessage());
        }

    }

    public static void saveCuestionario(String nomcue,String fecini,String fecfin,String idbpr,String idgru){
        try{
            if(getCuestId(nomcue).equals("")){
                String q = "INSERT INTO " + TABLE_NAME6 + "(" + COLUMN_NOMCUE + "," + COLUMN_FECINI + "," + COLUMN_FECFIN + "," + COLUMN_IDBPR + "," + COLUMN_IDG+ ") VALUES ('" + nomcue+ "','"+fecini+"','"+fecfin+"','"+idbpr+"',"+idgru+")";
                database.execSQL(q);
            }else{
                String q = "UPDATE " + TABLE_NAME6 + " SET " + COLUMN_NOMCUE + " = '"+nomcue+"'," + COLUMN_FECINI + " = '"+fecini+"'," + COLUMN_FECFIN + " = '"+fecfin+"'," + COLUMN_IDBPR + " = '"+idbpr+"'," + COLUMN_IDG + " = "+idgru+" WHERE "+COLUMN_NOMCUE+" = '" + nomcue + "'";
                database.execSQL(q);
            }
        }catch (Exception e){
            System.out.println("Error en saveCuestionario: "+e.getMessage());
        }
    }
}
