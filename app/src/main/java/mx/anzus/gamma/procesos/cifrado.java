package mx.anzus.gamma.procesos;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class cifrado {

    private static final String aes = "AES";
    private static final byte[] llave = new byte[]{'c', 'o', 'p', '#', 'l', 'a', '#', '#', '8', '(', '=', 's', '.', '?', '!', 'H','1','Q','d','~','{','[','k','i','7','f','r','%','i','L','k','y'};
    //Aes de 128 = 16 letras Aes de 192 = 24 letras Aes 256 = 32 letras

    public static String crypt(String Data) throws Exception {
        Key key = generarKey();
        Cipher c = Cipher.getInstance(aes);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encriptado = c.doFinal(Data.getBytes());
        return android.util.Base64.encodeToString(encriptado,android.util.Base64.DEFAULT); //Formato Base 64
    }

    public static String decrypt(String Data) throws Exception {
        Key key = generarKey();
        Cipher c = Cipher.getInstance(aes);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] leerdescifrado = c.doFinal(android.util.Base64.decode(Data, android.util.Base64.DEFAULT));
        return new String(leerdescifrado);
    }

    private static Key generarKey() { //Metodo para generar lave de AES (sublaves)
        Key key = new SecretKeySpec(llave, aes);
        return key;
    }


}
