package mx.anzus.gamma.entidades;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class user {

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
    @SerializedName("Correo")
    private String Correo;

    @Expose
    @SerializedName("Tipo")
    private String Tipo;

    @Expose
    @SerializedName("Token")
    private String Token;

    @Expose
    @SerializedName("Nombre")
    private String Nombre;

    @Expose
    @SerializedName("Curp")
    private String Curp;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String curp) {
        Curp = curp;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
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

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}
