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
    
    private static final byte[] preamble = NpyHeaderPreamble.preamble;

    private static final byte padByte = (byte) 0x20;
    private static final byte terminationByte = (byte) 0x0a;
    
    private String dtypeDescription;
    private int[] arrayShape;
    
    private byte[] headerCore;
    private byte[] headerCoreLenBytes = new byte[Short.SIZE/8];
    
    private static byte[] encodeToAscii(String s){
        return NpyEncoder.encodeToAscii(s);
    }
    
    public NpyHeader(String dtypeDescription, int[] arraySize) {
        this.dtypeDescription = dtypeDescription;
        this.arrayShape = arraySize;
        headerCore = getHeaderCore();
        setHeaderCoreLenBytes();
    }
    
    public byte[] getHeader(){
        ByteBuffer buffer = ByteBuffer.allocate(getPaddedHeaderLength());
        
        buffer.put(preamble);
        buffer.put(headerCoreLenBytes);
        buffer.put(headerCore);
        padHeader(buffer);
        
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
        return encodeToAscii(getHeaderCoreLiteral());
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
        
        for (int i=0; i<arrayShape.length; i++){
            s = s + ',' + ((Integer) arrayShape[i]).toString();
        }
        
        s = String.format("(%s)", s); 
        return s;
    }

    private void setHeaderCoreLenBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(headerCoreLenBytes.length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) headerCore.length);
        
        headerCoreLenBytes = buffer.array();
    }
    
    private int computePaddedLength(int occupiedLength) {
        int paddedLength = ((int) Math.ceil(((double) occupiedLength)/16.0))*16;
        return paddedLength;
    }
    
    private void padHeader(ByteBuffer buffer) {
        for (int i=0;i<getNumberPaddedBytes();i++){
            buffer.put(padByte);
        }
    }
    
}
