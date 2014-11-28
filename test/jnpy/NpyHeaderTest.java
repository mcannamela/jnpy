package jnpy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NpyHeaderTest {
    
    public NpyHeaderTest() {
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
     * Test of getHeader method, of class NpyHeader.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        NpyHeader instance = null;
        byte[] expResult = null;
        byte[] result = instance.getHeader();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeaderCore method, of class NpyHeader.
     */
    @Test
    public void testGetHeaderCore() {
        System.out.println("getHeaderCore");
        NpyHeader instance = null;
        byte[] expResult = null;
        byte[] result = instance.getHeaderCore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
