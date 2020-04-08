package mx.anzus.gamma.procesos;

import java.util.Random;

public class validacion {

    final private static String characters = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static String stringGenerator(int lenght){
        String strong = "";
        Random rand = new Random();
        for (int i=0;i<lenght;i++){
        strong += characters.charAt(rand.nextInt(characters.length()));
        }
        return strong;
    }

}
