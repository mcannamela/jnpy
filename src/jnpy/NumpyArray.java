package jnpy;

public interface NumpyArray {
    
    public NumpyDtype getDtype();
    public int[] getShape();
    public byte[] getBytes();
    public int getSize();
    
}
