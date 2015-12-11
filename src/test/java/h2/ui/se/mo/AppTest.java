package h2.ui.se.mo;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    static Logger logger = Logger.getLogger(AppTest.class);
	 
    public static void main(String[] args)
    {
    	try {
    		int i = 1/0;
    	}
    	catch (Exception e) {
    		//Log in console in and log file
            logger.debug("Log4j appender configuration is successful !!");
    	}
        
    }
}
