
/**
 * Das Mutex funktioniert als Tokensystem mit einem Token
 * 
 * @author Nico Korn
 * @version 10.05.2016
 */
public class MUTEX
{
    //instance variables
    private static boolean bol_token_here;

    /**
     * Constructor for objects of class MUTEX
     */
    public MUTEX()
    {
        bol_token_here = true;
    }

    public void getToken()
    {
        bol_token_here = false;
    }
    
    public void releaseToken()
    {
        bol_token_here = true;
    }
    
    public boolean waitForToken()
    {
        return bol_token_here;
    }
}
