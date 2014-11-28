/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpy;

import java.nio.ByteBuffer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PrimitiveNumpyDtypeTest {
    
    public PrimitiveNumpyDtypeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of allocateByteBuffer method, of class PrimitiveNumpyDtype.
     */
    @Test
    public void testAllocateByteBuffer() {
        System.out.println("allocateByteBuffer");
        int capacity = 0;
        ByteBuffer expResult = null;
        ByteBuffer result = PrimitiveNumpyDtype.allocateByteBuffer(capacity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescription method, of class PrimitiveNumpyDtype.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        PrimitiveNumpyDtype instance = null;
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getItemSize method, of class PrimitiveNumpyDtype.
     */
    @Test
    public void testGetItemSize() {
        System.out.println("getItemSize");
        PrimitiveNumpyDtype instance = null;
        int expResult = 0;
        int result = instance.getItemSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putBytes method, of class PrimitiveNumpyDtype.
     */
    @Test
    public void testPutBytes() {
        System.out.println("putBytes");
        ByteBuffer buffer = null;
        Object arrayElement = null;
        PrimitiveNumpyDtype instance = null;
        instance.putBytes(buffer, arrayElement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
