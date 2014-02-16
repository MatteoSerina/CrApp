package channel;

import core.UIDGenerator;

public class BaseChannel implements IChannel
{
	private Long UID = UIDGenerator.getInstance().next() ;
	
	public BaseChannel ()
	{
	}


	protected void finalize() throws Throwable 
	{
	     try 
	     {
	    	 //Security.removeProvider( UID.toString() );
	     } 
	     finally 
	     {
	         super.finalize() ;
	     }
	 }


}
