/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpy;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.HashSet;

public class PrimitiveNumpyDtype implements NumpyDtype{
    
    private Class primitive_class;
    
    //always use big endian
    private static char endianIndicator = '>';
    public static ByteOrder endianness = ByteOrder.BIG_ENDIAN;
    private static HashMap<Class, Character> classToTypeCharMap = getClassToTypeCharMap();
    private static HashMap<Class, Integer> classToItemSizeMap = getClassToItemSizeMap();
    
    private static HashMap<Class, Character> getClassToTypeCharMap(){
        HashMap<Class, Character> classToTypeCharMap = new HashMap<Class, Character>();
        classToTypeCharMap.put(Integer.class, 'i');
        classToTypeCharMap.put(Long.class, 'i');
        classToTypeCharMap.put(Float.class, 'f');
        classToTypeCharMap.put(Double.class, 'f');
        return classToTypeCharMap;
    }
    
    private static HashMap<Class, Integer> getClassToItemSizeMap(){
        HashMap<Class, Integer> classToItemSizeMap = new HashMap<Class, Integer>();
        classToItemSizeMap.put(Integer.class, Integer.SIZE/8);
        classToItemSizeMap.put(Long.class, Long.SIZE/8);
        classToItemSizeMap.put(Float.class, Float.SIZE/8);
        classToItemSizeMap.put(Double.class, Double.SIZE/8);
        return classToItemSizeMap;
    }

    @Override
    public byte[] getDescription() {
        String stringDescription = getStringDescription();
        try {
        return stringDescription.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e){
            throw new RuntimeException("Bad charset, I guess.");
        }
    }
    
    private String getStringDescription(){
        char typeChar = classToTypeCharMap.get(primitive_class);
        String sizeChar = ((Integer) getItemSize()).toString();
        String stringDescription = endianIndicator+typeChar+sizeChar;
        return stringDescription;
    }

    @Override
    public int getItemSize() {
        return classToItemSizeMap.get(primitive_class);
    }

    public PrimitiveNumpyDtype(Class primitive_class) {
        this.primitive_class = primitive_class;
        if (!isClassValid()){
            throw new RuntimeException("Unsupported class passed to PrimitiveNumpyDtype constructor.");
        }
    }
    
    private boolean isClassValid(){
        return classToTypeCharMap.containsKey(primitive_class);
    }
    
    
    
    
    
    
    
    
    
}
