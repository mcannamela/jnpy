package jnpy;

public class NpyHeaderPreamble {
    
    public static final byte[] preamble = getHeaderPreamble();
    
    private static final byte[] magicBytes = getMagicBytes();
    private static final byte majorVersionNumber = (byte) 0x01;
    private static final byte minorVersionNumber = (byte) 0x00;
    
    
    private static byte[] getMagicBytes(){
        byte[] magic = new byte[6];
        
        magic[0] = (byte) 0x93;
        
        
        String s = "NUMPY";
        byte[] numpyBytes;
        numpyBytes = NpyEncoder.encodeToAscii(s);
        
        for (int i=1; i<6; i++){
            magic[i] = numpyBytes[i-1];
        }
        
        return magic;
    }
    
    private static byte[] getHeaderPreamble(){
        byte[] tmp_preamble = new byte[magicBytes.length+2];
        System.arraycopy(magicBytes, 0, tmp_preamble, 0, magicBytes.length);
        tmp_preamble[magicBytes.length] = majorVersionNumber;
        tmp_preamble[magicBytes.length+1] = minorVersionNumber;
        return tmp_preamble;
    }
}
