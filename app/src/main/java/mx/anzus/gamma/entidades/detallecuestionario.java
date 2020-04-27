package mx.anzus.gamma.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class detallecuestionario {

    @Expose
    @SerializedName("codigo")
    private String codigo;

    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("mensaje")
    private String mensaje;


    @SerializedName("cuestionario")
    @Expose
    private ArrayList<ArrayList> cuestionario;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<ArrayList> getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(ArrayList<ArrayList> cuestionario) {
        this.cuestionario = cuestionario;
    }
}
