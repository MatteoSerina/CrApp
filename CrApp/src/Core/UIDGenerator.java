package Core;


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
	    
	    
	    public long next ()
	    {
	    	return ++next ;
	    }
}