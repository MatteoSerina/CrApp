package message;

import java.util.ArrayList;

public class MessageStack 
{
	private ArrayList <Message> messageStack = null ;
	
	
	public MessageStack ()
	{
		messageStack = new ArrayList <Message> () ;
	}
	
	public boolean addMessage ( Message _msg )
	{
		if ( _msg == null )
			return false ;
		
		return messageStack.add( _msg ) ;
	}
	
	public Message readRootMessage ()
	{
		return messageStack.get ( 0 ) ;
	}
	
	public Message readMessage ()
	{
		return messageStack.get( messageStack.size() - 1 ) ;
	}
	
	public Message readMessage ( int _index )
	{
		if ( _index < 0 || _index >= messageStack.size() )
			return null ;
		
		return messageStack.get( _index ) ;
	}

}
