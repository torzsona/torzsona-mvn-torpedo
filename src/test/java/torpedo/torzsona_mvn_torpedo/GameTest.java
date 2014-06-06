package torpedo.torzsona_mvn_torpedo;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for Game.
 */
public class GameTest extends TestCase {
	
	/**
	 * Logger class of {@code logger}.<br>
	 * This class is used for logging purposes.<br>
	 *
	 */
    private static Logger logger = LoggerFactory.getLogger(Game.class);
    
    /**
     * Testcase of {@code testHit}
     *
     */
	public void testHit() {
 
		int[] coor = new int[] {0};
		int[][] ships = new int[1][1];
		ships[0][0] = 1;
		
		logger.debug(coor + " - " + ships);
		Assert.assertEquals(false, Game.hit(coor, ships));
	}

    /**
     * Basic testcase {@code GameTest}
     *
     * @param testName name of the test case
     */
    public GameTest( String testName )
    {
        super( testName );
    }

    /**
     * Basic testcase of test {@code suite}
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GameTest.class );
    }

}
