package jnpy;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class PrimitiveNumpyDtype implements NumpyDtype{
    
    private Class primitiveClass;
    
    //always use little endian
    private static char endianIndicator = '<';
    public static ByteOrder endianness = ByteOrder.LITTLE_ENDIAN;
    private static HashMap<Class, Character> classToTypeCharMap = getClassToTypeCharMap();
    private static HashMap<Class, Integer> classToItemSizeMap = getClassToItemSizeMap();
    
    
    public static ByteBuffer allocateByteBuffer(int capacity){
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        buffer.order(endianness);
        return buffer;
        
    }
    private static HashMap<Class, Character> getClassToTypeCharMap(){
        HashMap<Class, Character> tmpClassToTypeCharMap = new HashMap<Class, Character>();
        tmpClassToTypeCharMap.put(Integer.class, 'i');
        tmpClassToTypeCharMap.put(Long.class, 'i');
        tmpClassToTypeCharMap.put(Float.class, 'f');
        tmpClassToTypeCharMap.put(Double.class, 'f');
        return tmpClassToTypeCharMap;
    }
    
    private static HashMap<Class, Integer> getClassToItemSizeMap(){
        HashMap<Class, Integer> tmpClassToItemSizeMap = new HashMap<Class, Integer>();
        tmpClassToItemSizeMap.put(Integer.class, Integer.SIZE/8);
        tmpClassToItemSizeMap.put(Long.class, Long.SIZE/8);
        tmpClassToItemSizeMap.put(Float.class, Float.SIZE/8);
        tmpClassToItemSizeMap.put(Double.class, Double.SIZE/8);
        return tmpClassToItemSizeMap;
    }
    
     public PrimitiveNumpyDtype(Class primitive_class) {
        this.primitiveClass = primitive_class;
        if (!isClassValid()){
            throw new RuntimeException("Unsupported class passed to PrimitiveNumpyDtype constructor.");
        }
    }

    /**
     * From the numpy documentation of the __array__ interface:
     * 
     * A string providing the basic type of the homogenous array 
     * The basic string format consists of 3 parts: 
     * a character describing the byteorder of the data (<: little-endian, >: big-endian, |: not-relevant), 
     * a character code giving the basic type of the array, 
     * and an integer providing the number of bytes the type uses.
     * @return 
     */
    @Override
    public String getDescription(){
        char typeChar = classToTypeCharMap.get(primitiveClass);
        String sizeChar = ((Integer) getItemSize()).toString();
        String stringDescription = endianIndicator+typeChar+sizeChar;
        return stringDescription;
    }

    @Override
    public int getItemSize() {
        return classToItemSizeMap.get(primitiveClass);
    }
    
    public void putBytes(ByteBuffer buffer, Object arrayElement){
        if (primitiveClass==Integer.class){
            buffer.putInt((Integer) arrayElement);
        }
        else if (primitiveClass==Long.class){
            buffer.putLong((Long) arrayElement);
        }
        else if (primitiveClass==Float.class){
            buffer.putFloat((Float) arrayElement);
        }
        else if (primitiveClass==Double.class){
            buffer.putDouble((Double) arrayElement);
        }
        else{
            throw new RuntimeException("Unsupported operation for this dtype");
        }
    }
    
    private boolean isClassValid(){
        return classToTypeCharMap.containsKey(primitiveClass);
    }
    
}
