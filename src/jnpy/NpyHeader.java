package jnpy;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * From the numpy source code, github.com/numpy/numpy/blob/master/doc/neps/npy-format.rst
 * 
 * The first 6 bytes are a magic string: exactly "x93NUMPY".

 *The next 1 byte is an unsigned byte: the major version number of the file format, e.g. x01.

 *The next 1 byte is an unsigned byte: the minor version number of the file format, e.g. x00. 
 * Note: the version of the file format is not tied to the version of the numpy package.

 *The next 2 bytes form a little-endian unsigned short int: the length of the header data HEADER_LEN.

 *The next HEADER_LEN bytes form the header data describing the array's format. 
 * It is an ASCII string which contains a Python literal expression of a dictionary. 
 * It is terminated by a newline ('n') and padded with spaces ('x20') to make 
 * the total length of the magic string + 4 + HEADER_LEN be evenly divisible by 
 * 16 for alignment purposes.

 *The dictionary contains three keys:

 *   "descr" : dtype.descr
 *       An object that can be passed as an argument to the numpy.dtype() constructor to create the array's dtype.
 *   "fortran_order" : bool
 *       Whether the array data is Fortran-contiguous or not. Since Fortran-contiguous arrays are a common form of non-C-contiguity, we allow them to be written directly to disk for efficiency.
 *   "shape" : tuple of int
 *       The shape of the array.
 * 
 */
public class NpyHeader {
    
    private static final byte[] magicBytes = getMagicBytes();
    private static final byte majorVersionNumber = (byte) 0x01;
    private static final byte minorVersionNumber = (byte) 0x00;
    private static final byte[] preamble = getHeaderPreamble();
    private static final byte padByte = (byte) 0x20;
    private static final byte terminationByte = (byte) 0x0a;
    
    private String dtypeDescription;
    private byte[] headerCore;
    private byte[] headerCoreLenBytes = new byte[Short.SIZE/8];
    
    private int[] arraySize;
    private static String descriptionEncoding = "UTF-8";
    
    public static byte[] getBytes(String s){
        byte[] bytes;
        try{
            bytes = s.getBytes(descriptionEncoding);
        }
        catch (UnsupportedEncodingException e){
            throw new RuntimeException("Bad charset, I guess.");
        }
        return bytes;
    }
    
    private static byte[] getMagicBytes(){
        byte[] magic = new byte[6];
        
        magic[0] = (byte) 0x93;
        
        
        String s = "NUMPY";
        byte[] numpyBytes;
        numpyBytes = getBytes(s);
        
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

    public NpyHeader(String dtypeDescription, int[] arraySize) {
        this.dtypeDescription = dtypeDescription;
        this.arraySize = arraySize;
        headerCore = getHeaderCore();
        setHeaderCoreLenBytes();
    }
    
    public byte[] getHeader(){
        ByteBuffer buffer = ByteBuffer.allocate(getPaddedHeaderLength());
        
        buffer.put(preamble);
        buffer.put(headerCoreLenBytes);
        buffer.put(headerCore);
        for (int i=0;i<getNumberPaddedBytes();i++){
            buffer.put(padByte);
        }
        
        return buffer.array();
    }
    
    private int getOccupiedHeaderLength(){
        return preamble.length + headerCoreLenBytes.length + headerCore.length; 
    }
    
    private int getPaddedHeaderLength(){
        return computePaddedLength(getOccupiedHeaderLength());
    }
    
    private int getNumberPaddedBytes(){
        return getPaddedHeaderLength()-getOccupiedHeaderLength();
    }
    
    private byte[] getHeaderCore(){
        return getBytes(getHeaderCoreLiteral());
    }
    
    private String getHeaderCoreLiteral(){
        String s = String.format("{descr: %s, fortran_order: False, shape: %s}", 
                getDtypeDescription(), getArraySizeTupleLiteral());
        return s;
    }
    
    private String getDtypeDescription(){
        return dtypeDescription;
    }
    
    private String getArraySizeTupleLiteral(){
        String s = "";
        
        for (int i=0; i<arraySize.length; i++){
            s = s + ',' + ((Integer) arraySize[i]).toString();
        }
        
        s = String.format("(%s)", s); 
        return s;
    }

    private void setHeaderCoreLenBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(Short.SIZE/8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) headerCore.length);
        
        headerCoreLenBytes[0] = buffer.get();
        headerCoreLenBytes[1] = buffer.get();
    }
    
    private int computePaddedLength(int occupiedLength) {
        int paddedLength = ((int) Math.ceil(((double) occupiedLength)/16.0))*16;
        return paddedLength;
    }
    
}
