package jnpy;

public interface NumpyDtype {
    
    /**
     * from the numpy documentation:
     * "descr" : dtype.descr
     *          An object that can be passed as an argument to the numpy.dtype() 
     *          constructor to create the array's dtype.
     * @return 
     */
    public String getDescription();
    
    /**
     * 
     * @return The number of bytes each item in the array occupies.
     */
    public int getItemSize(); 
    
    
}
