package mx.anzus.gamma;

import java.lang.reflect.Array;
import java.util.ArrayList;
import mx.anzus.gamma.entidades.*;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.*;

public interface ApiService {

    @GET("/rest/login")
    Call<user> getLogin(@Query("user") String login, @Query("pass") String password);

    @GET("/rest/consultaAGProfesor")
    Call<groups> getGroupsProf(@Query("tok") String tok);

    @GET("/rest/consultaAlumnosGrupos")
    Call<groups> getGroups(@Query("tok") String tok);

    @GET("/rest/consultaCuestionarioProfesor")
    Call<cuestionarios> getCuestionariosProf(@Query("tok") String tok);

    @GET("/rest/consultaCuestionarios")
    Call<cuestionarios> getCuestionarios(@Query("tok") String tok);

    @GET("/rest/consultaCuestPreguntas")
    Call<detallecuestionario> getDetalleCuestionario(@Query("tok") String tok,@Query("cues") String cues);

}