package jnpy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NpyEncoderTest {
    
    public NpyEncoderTest() {
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
     * Test of encodeToAscii method, of class NpyEncoder.
     */
    @Test
    public void testEncodeToAscii() {
        System.out.println("encodeToAscii");
        String s = "";
        byte[] expResult = new byte[0];
        byte[] result = NpyEncoder.encodeToAscii(s);
        assertEquals(expResult, result);
        
        s = "{a:b, c:d}";
        expResult = new byte[s.length()];
        expResult[0] = (byte) 0x7b;
        expResult[1] = (byte) 0x61;
        expResult[2] = (byte) 0x3a;
        expResult[3] = (byte) 0x62;
        expResult[4] = (byte) 0x2c;
        expResult[5] = (byte) 0x20;
        expResult[6] = (byte) 0x63;
        expResult[7] = (byte) 0x3a;
        expResult[8] = (byte) 0x64;
        expResult[9] = (byte) 0x7d;
        
        result = NpyEncoder.encodeToAscii(s);
        assertEquals(expResult, result);
    }
}
