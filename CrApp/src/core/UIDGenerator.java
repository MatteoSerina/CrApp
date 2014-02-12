package core;

import identity.ClassIdentity;

import java.security.SecureRandom;





public class UIDGenerator 
{
		private long next = 0 ;
		
	    private static class Inner 
	    {
	        private static final UIDGenerator CONTEXT = new UIDGenerator() ;
	        
	        public static UIDGenerator getContext ()
	        {
	        	return CONTEXT ;
	        }
	    }

	    public static UIDGenerator getInstance() 
	    {
	        return Inner.getContext() ;
	    }
	    
	    public static byte[] generateSecureIdentity ()
	    {
	    	return ( new SecureRandom () ).generateSeed( 64 ) ;
	    }	    
	    
	    public long next ()
	    {
	    	return ++next ;
	    }
}