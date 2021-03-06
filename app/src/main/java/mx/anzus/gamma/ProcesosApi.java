package mx.anzus.gamma;

import android.util.Log;

import java.util.ArrayList;
import mx.anzus.gamma.entidades.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProcesosApi {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String url = "http://192.168.0.9:3000/";
    //private final static  String url = "https://mychemistlab.com.mx/";

    public static void connectApiServiceLogInAsync(final String id_user,final String password){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<user> login = retrofit.create(ApiService.class).getLogin(id_user, password);
        System.out.println(login.request().toString());
        login.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                System.out.println("RAW: "+response.raw());
                    if(Database.getIdMail(response.body().getCorreo()).equals("") && response.body().getCorreo() !=null){
                        Database.saveUserFirst(response.body().getCorreo(),response.body().getNombre(),response.body().getCurp(),password,response.body().getTipo(),response.body().getToken());
                        Database.changeMade(response.body().getCorreo());
                    }else if(response.body().getCorreo() !=null){
                        Database.updateUser(response.body().getCorreo(),response.body().getNombre(),response.body().getCurp(),password,response.body().getTipo(),response.body().getToken());
                        Database.changeMade(response.body().getCorreo());
                    }
            }
            @Override
            public void onFailure(Call<user> call, Throwable throwable) {
                System.out.println("ERROR");
                Log.e(TAG, throwable.toString());
                if(!Database.getIdMail(id_user).equals("")){
                    Database.changeFailed(id_user);
                }else {
                    System.out.println("FAILED");
                }
            }
        });
    }

    public static void connectApiServiceGroupsAluProfAsync(final String token,final String user){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<groups> groups = retrofit.create(ApiService.class).getGroupsProf(token);
        System.out.println(groups.request().toString());
        groups.enqueue(new Callback<groups>() {
            @Override
            public void onResponse(Call<groups> call, Response<groups> response) {
                System.out.println("RAW: "+response.raw());
                try {
                    Database.saveAluGroupProf(response.body().getAlumnos(), user);
                    Database.changeMade(user);
                }catch (Exception e){
                    System.out.println("Error en onResponseGroupsAlu: "+e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<groups> call, Throwable throwable) {
                System.out.println("ERROR");
                Log.e(TAG, throwable.toString());
                if(!Database.getIdMail(user).equals("")){
                    Database.changeFailed(user);
                }else {
                    System.out.println("FAIlED");
                }
            }
        });
    }

    public static void connectApiServiceGroupsAluAsync(final String token,final String user){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<groups> groups = retrofit.create(ApiService.class).getGroups(token);
        System.out.println(groups.request().toString());
        groups.enqueue(new Callback<groups>() {
            @Override
            public void onResponse(Call<groups> call, Response<groups> response) {
                System.out.println("RAW: "+response.raw());
                try {
                    Database.saveAluGroupProf(response.body().getAlumnosGrupo(), user);
                    Database.changeMade(user);
                }catch (Exception e){
                    System.out.println("Error en onResponseGroupsAlu: "+e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<groups> call, Throwable throwable) {
                System.out.println("ERROR");
                Log.e(TAG, throwable.toString());
                if(!Database.getIdMail(user).equals("")){
                    Database.changeFailed(user);
                }else {
                    System.out.println("FAIlED");
                }
            }
        });
    }

    public static void connectApiServiceCuestionariosProfAsync(final String token,final String user){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<cuestionarios> cuestionarios = retrofit.create(ApiService.class).getCuestionariosProf(token);
        System.out.println(cuestionarios.request().toString());
        cuestionarios.enqueue(new Callback<cuestionarios>() {
            @Override
            public void onResponse(Call<cuestionarios> call, Response<cuestionarios> response) {
                System.out.println("RAW: "+response.raw());
                try {
                    Database.saveCuestionarios(response.body().getCuestionarios());
                    Database.changeMade(user);
                }catch (Exception e){
                    System.out.println("Error en onResponseCuestionarioProfe: "+e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<cuestionarios> call, Throwable throwable) {
                System.out.println("ERROR");
                Log.e(TAG, throwable.toString());
                if(!Database.getIdMail(user).equals("")){
                    Database.changeFailed(user);
                }else {
                    System.out.println("FAIlED");
                }
            }
        });
    }

    public static void connectApiServiceCuestionariosAsync(final String token,final String user){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<cuestionarios> cuestionarios = retrofit.create(ApiService.class).getCuestionarios(token);
        System.out.println(cuestionarios.request().toString());
        cuestionarios.enqueue(new Callback<cuestionarios>() {
            @Override
            public void onResponse(Call<cuestionarios> call, Response<cuestionarios> response) {
                System.out.println("RAW: "+response.raw());
                try {
                    Database.saveCuestionarios(response.body().getCuestionarios());
                    Database.changeMade(user);
                }catch (Exception e){
                    System.out.println("Error en onResponseCuestionarioProfe: "+e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<cuestionarios> call, Throwable throwable) {
                System.out.println("ERROR");
                Log.e(TAG, throwable.toString());
                if(!Database.getIdMail(user).equals("")){
                    Database.changeFailed(user);
                }else {
                    System.out.println("FAIlED");
                }
            }
        });
    }

    public static void connectApiServiceDetalleCuestionariosAsync(final String token,final String user,final String idCue){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<detallecuestionario> cuestionario = retrofit.create(ApiService.class).getDetalleCuestionario(token,idCue);
        System.out.println(cuestionario.request().toString());
        cuestionario.enqueue(new Callback<detallecuestionario>() {
            @Override
            public void onResponse(Call<detallecuestionario> call, Response<detallecuestionario> response) {
                System.out.println("RAW: "+response.raw());
                try {
                    System.out.println(response.body().getCodigo());
                    System.out.println(response.body().getMensaje());
                    System.out.println(response.body().getError());;
                    ArrayList preguntas = response.body().getCuestionario();
                    for (int i=0;i<preguntas.size();i++){
                        ArrayList pregunta = (ArrayList) preguntas.get(i);
                        System.out.println(pregunta);
                        Database.savePregunta(pregunta.get(0).toString(),pregunta.get(1).toString(),pregunta.get(2).toString(),pregunta.get(3).toString(),pregunta.get(4).toString(),pregunta.get(5).toString(),pregunta.get(6).toString(),pregunta.get(7).toString(),idCue);
                    }
                    Database.changeMade(user);
                }catch (Exception e){
                    System.out.println("Error en onResponseDetalleCuestionario: "+e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<detallecuestionario> call, Throwable throwable) {
                System.out.println("ERROR");
                Log.e(TAG, throwable.toString());
                if(!Database.getIdMail(user).equals("")){
                    Database.changeFailed(user);
                }else {
                    System.out.println("FAIlED");
                }
            }
        });
    }

    public static void connectApiServiceLogInSync(final String id_user,final String password){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        Call<user> login = retrofit.create(ApiService.class).getLogin(id_user, password);
        System.out.println(login.request().toString());
        try {
            Response<user> response = login.execute();
            user apiResponse = response.body();
            //API response
            System.out.println(apiResponse.getCorreo());
        }catch (Exception e){
            System.out.println("Error en connectApiServiceLoginSync: "+e.getMessage());
            e.printStackTrace();
        }
    }

}
