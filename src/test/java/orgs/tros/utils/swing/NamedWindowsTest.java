package orgs.tros.utils.swing;

import static org.junit.Assert.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tros.utils.swing.NamedWindow;

public class NamedWindowsTest
{

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }


    @Before
    public void setUp() throws Exception
    {
    }


    @After
    public void tearDown() throws Exception
    {
    }


    @Test
    public void testWindow()
    {
        for (int i = 0; i < 10000; i++) {
        NamedWindow win = new NamedWindow("Huh", i, i);
        ComponentEvent event = new ComponentEvent(win, i);
        }
    }
    
    @Test
    public void testComponentMethods() {
    	NamedWindow win = new NamedWindow("Yes", 100, 100);
    	assertFalse(win.getTest());
    	assertFalse(win.getTestCheck());
    	win.testOverrideMethods();
    	assertFalse(win.getTest());
    	assertFalse(win.getTestCheck());
    	win.setTesting();
    	assertTrue(win.getTest());
    	assertFalse(win.getTestCheck());
    	win.testOverrideMethods();
    	assertFalse(win.getTest());
    	assertTrue(win.getTestCheck());
    	
    }

}
