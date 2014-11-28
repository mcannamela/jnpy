package jnpy;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PrimitiveNumpyArray<T> implements NumpyArray {

     private Class<T> tClass;
     private PrimitiveNumpyDtype dtype;
     
     private int[] shape;
     
     private ArrayList<T> array;
     private ByteBuffer byteBuffer;
     
    private static int computeSize(int[] someShape){
        int size = 1;
        for (int i=0;i<someShape.length;i++){
            size*=i;
        }
        return size;
    }

    public PrimitiveNumpyArray(Class<T> tClass, ArrayList<T> array, int[] shape) {
        this.tClass = tClass;
        this.array = array;
        this.shape = shape;
        postConstruction();
    }
    
    public PrimitiveNumpyArray(Class<T> tClass, ArrayList<T> array){
        this.tClass = tClass;
        this.array=array;
        inferShape(array);
        postConstruction();
    }
    
    public PrimitiveNumpyArray(Class<T> tClass, int[] shape){
        this.tClass = tClass;
        this.shape = shape;
        inferEmptyArray(shape);
        postConstruction();
    }
     
    @Override
    public NumpyDtype getDtype() {
        return dtype;
    }

    @Override
    public int[] getShape() {
        return shape;
    }

    @Override
    public byte[] getBytes() {
        byteBuffer = PrimitiveNumpyDtype.allocateByteBuffer(getSizeBytes());
        
        for (T arrayElement : array){
            dtype.putBytes(byteBuffer, arrayElement);
        }
        
        return byteBuffer.array();
    }

    @Override
    public int getSize() {
        return computeSize(shape);
    }
    
    public int getSizeBytes(){
        return getSize()*dtype.getItemSize();
    }

    public T get(int index){
        return array.get(index);
    }
    
    public void set(int index, T element){
        array.set(index, element);
    }
    
    private void inferShape(ArrayList<T> array) {
        this.shape = new int[1];
        this.shape[0] = array.size();
    }
    
    private void inferEmptyArray(int[] shape) {
        int size = computeSize(shape);
        this.array= new ArrayList<T>(size);
        for (int i=0; i<size;i++){
            this.array.add(null);
        }
    }
    
    private void postConstruction() {
        setDtype();
        checkShape();
    }
    
    private boolean isShapeValid(){
        boolean isValid = getSize()==array.size();
        return isValid;
    }

    private void setDtype() {
       dtype = new PrimitiveNumpyDtype(tClass);
    }

    private void checkShape() {
       if (!isShapeValid()){
           throw new RuntimeException("Invalid shape; product of shape elements must be equal to number of array elements.");
       }
    }
    
}
