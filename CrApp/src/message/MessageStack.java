package message;

import java.util.ArrayList;

import utility.MemoryBlock;
import utility.Utils;

import core.UIDGenerator;

public class MessageStack 
{
	private long               UID          = UIDGenerator.getInstance().next() ;
	private ArrayList<Integer> route        = null                              ;
	private MemoryBlock        sharedMemory = null                              ;
	
	private ArrayList <Message> messageStack = null ; 
	
	private Object lock = new Object () ;
	
	public MessageStack ()
	{
		messageStack = new ArrayList <Message> () ;
		sharedMemory = new MemoryBlock () ; 
	}
	
	public boolean addMessage ( Message _msg )
	{
		synchronized (lock)
		{
			if ( _msg == null )
				return false ;
			
			return messageStack.add( _msg ) ;
		}
	}
	
	public Message readRootMessage ()
	{
		return messageStack.get ( 0 ) ;
	}
	
	public Message readMessage ()
	{
		synchronized (lock)
		{
			return messageStack.get( messageStack.size() - 1 ) ;
		}
	}
	
	public Message readMessage ( int _index )
	{
		synchronized (lock)
		{
			if ( _index < 0 || _index >= messageStack.size() )
				return null ;
			
			return messageStack.get( _index ) ;
		}
	}
	
	public int stackLength ()
	{
		synchronized (lock)
		{
			return messageStack.size() ;
		}
	}
	
	public String getDescription ()
	{
		synchronized (lock)
		{
			return this.getDescription ( messageStack.size() - 1 ) ;
		}
	}
	
	public String getDescription ( int _index )
	{
		synchronized (lock)
		{
			if ( _index < 0 || _index >= messageStack.size() )
				return Utils.STR_EMPTY ;
			
			String description = "Current message changed n° : " +  messageStack.size() + "\n" ;
			
			return description + messageStack.get( _index ).getDescription() ;
		}
	}

}
