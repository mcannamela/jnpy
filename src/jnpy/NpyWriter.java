package jnpy;

public class NpyWriter {
    
    public static byte[] write(NumpyArray array){
        String dtypeDescription = array.getDtype().getDescription();
        int[] arrayShape = array.getShape();
        NpyHeader header = new NpyHeader(dtypeDescription, arrayShape);
        
        byte[] headerBytes = header.getHeader();
        byte[] dataBytes = array.getBytes();
        
        int npyLen = headerBytes.length + dataBytes.length;
        
        byte[] npy = new byte[npyLen];
        System.arraycopy(headerBytes, 0, npy, 0, headerBytes.length);
        System.arraycopy(dataBytes, 0, npy, headerBytes.length, dataBytes.length);
        
        return npy;
    }
    
}
