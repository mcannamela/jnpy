package jnpy;

import java.io.UnsupportedEncodingException;

public class NpyHeader {
    
    private static final byte majorVersionNumber = (byte) 0x01;
    private static final byte minorVersionNumber = (byte) 0x00;
    private static final byte padByte = (byte) 0x20;
    private static final byte terminationByte = (byte) 0x0a;
    
    private NumpyDtype dtype;
    
    private static final byte[] getMagicBytes(){
        byte[] magic = new byte[6];
        
        magic[0] = (byte) 0x93;
        
        
        String s = new String("NUMPY");
        byte[] numpyBytes;
        try{
            numpyBytes = s.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e){
            throw new RuntimeException("Bad charset, I guess.");
        }
        
        for (int i=1; i<6; i++){
            magic[i] = numpyBytes[i-1];
        }
        
        
        return magic;
    }

    public NpyHeader(NumpyDtype dtype, ) {
        this.dtype=dtype;
        
    }
    
    private void getDtypeDescription(){
        return dtype.get_description();
    }
            
    
    
    
    
    
    
    
}
