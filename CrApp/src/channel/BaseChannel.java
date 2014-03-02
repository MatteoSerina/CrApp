package channel;

import java.util.logging.Logger;

import log.LogService;
import message.MessageStack;
import schemes.BaseScheme;
import utility.MemoryBlock;
import utility.Utils;
import core.UIDGenerator;

public class BaseChannel implements IChannel
{
	private Long UID = UIDGenerator.getInstance().next() ;
	
	private BaseScheme scheme = null ;
	private String name = Utils.STR_EMPTY ;
	
	public BaseChannel ( String _name , BaseScheme _scheme )
	{
		scheme = _scheme ;
		name = _name ;
	}
	
	public MessageStack processMessage ( MessageStack _message )
	{
		LogService.getLogger( this ).info( "[C] Channle named " + name + " with ID " + UID + " is going to process an incoming message using scheme named " + scheme.getSchemeName() ) ;
		
		try
		{
			if ( ! scheme.isLoaded() )
				scheme.load( this , new MemoryBlock () ) ;
			
			MessageStack processedMsg = scheme.processMessage ( _message ) ;
			
			scheme.unload() ;
			
			return processedMsg ;
		}
		catch ( Exception err )
		{
			LogService.getLogger( this ).warning ( name + " channel with ID " + UID + " has encountered an error while processing the message : \n" + _message.getDescription() ) ;
			
			return null ;
		}
		finally 
		{
			LogService.getLogger( this ).info( "Channle named " + name + " with ID " + UID + " has just processed an incoming message" ) ;
		}
		
	}
	
	public Long getUID ()
	{
		return UID ;
	}


	protected void finalize() throws Throwable 
	{
	     try 
	     {
	    	 scheme.unload() ;
	     } 
	     finally 
	     {
	         super.finalize() ;
	     }
	 }


}
