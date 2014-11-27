package jnpy;

import java.io.UnsupportedEncodingException;

public class NpyEncoder {
    private static String npyEncoding = "UTF-8";
    
    public static byte[] encodeToAscii(String s){
        byte[] bytes;
        try{
            bytes = s.getBytes(npyEncoding);
        }
        catch (UnsupportedEncodingException e){
            throw new RuntimeException("Bad charset, I guess.");
        }
        return bytes;
    }
    
}
