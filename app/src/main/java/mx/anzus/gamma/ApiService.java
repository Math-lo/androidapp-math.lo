package mx.anzus.gamma;

import java.lang.reflect.Array;
import java.util.ArrayList;
import mx.anzus.gamma.entidades.*;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.*;

public interface ApiService {

    @GET("/login")
    Call<user> getLogin(@Query("user") String login, @Query("pass") String password);

    @GET("/consultaAGProfesor")
    Call<groups> getGroupsProf(@Query("tok") String tok);

    @GET("/consultaCuestionarioProfesor")
    Call<cuestionarios> getCuestionariosProf(@Query("tok") String tok);

}