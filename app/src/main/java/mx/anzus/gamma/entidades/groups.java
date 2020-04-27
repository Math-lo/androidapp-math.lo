package mx.anzus.gamma.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class groups {

    @Expose
    @SerializedName("codigo")
    private String codigo;

    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("mensaje")
    private String mensaje;

    @Expose
    @SerializedName("alumnos")
    private ArrayList<ArrayList> alumnos;

    @Expose
    @SerializedName("alumnosGrupo")
    private ArrayList<ArrayList> alumnosGrupo;

    public ArrayList<ArrayList> getAlumnosGrupo() {
        return alumnosGrupo;
    }

    public void setAlumnosGrupo(ArrayList<ArrayList> alumnosGrupo) {
        this.alumnosGrupo = alumnosGrupo;
    }

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

    public ArrayList<ArrayList> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<ArrayList> alumnos) {
        this.alumnos = alumnos;
    }
}
