package schemes;

import identity.SchemeIdentity;
import java.util.ArrayList;
import message.Message;
import message.MessageStack;
import delegate.*;
import utility.*;

public class BaseScheme 
{
	private final static int ON_AFTER_LOADING    = 0 ;
	private final static int ON_MESSAGE_RECEIVED = 1 ;
	private final static int ON_BEFORE_UNLOADING = 2 ;
	
	private String      name           = Utils.STR_EMPTY ;
	private MemoryBlock memory         = null            ;
	private Object      context        = null            ;
	private boolean     isLoaded       = false           ;
	private boolean     hasStarted     = false           ;
	
	private SchemeIdentity identity = null ;
	
	private ArrayList <Object> afterLoadingBehaviours    = new ArrayList <Object> () ;
	private ArrayList <Object> messageReceivedBehaviours = new ArrayList <Object> () ;
	private ArrayList <Object> beforeUnloadingBehaviours = new ArrayList <Object> () ;
	
	//TODO : implementa anchve versioni di delegate specifici che ereditino da quelli generici e che tipizzino maggiormente i parametri (poi usa quelli)
	
	public BaseScheme ( String _name ) 
	{
		name = _name ;
		identity = new SchemeIdentity ( name ) ;
	}
	
	public void load ( Object _context , MemoryBlock _memory ) throws Exception
	{
		if ( isLoaded )
			throw new Exception ( "Can not laod again a loaded scheme." ) ;
		
		if ( _context == null || _memory == null )
			throw new Exception ( "Invalid parameters can not be null." ) ;
		
		memory         = _memory         ;
		context        = _context        ;
		isLoaded       = true            ;
		hasStarted     = true            ;
			
		Delegate addScopeDelegate = new Delegate ( memory , "addNewScope" ) ;
		addScopeDelegate.invoke( identity ) ;
				
		for ( Object action : afterLoadingBehaviours )
			if ( action.getClass() == this.getClass() )
				(( BaseScheme ) action ).load( this , memory ) ;
			else if ( action instanceof GenericHandler )
				((GenericHandler) action ).invoke ( this , memory , null ) ;
	}
	
	public void unload () throws Exception
	{
		if ( ! isLoaded )
			throw new Exception ( "Can not unlaod an unloaded scheme." ) ;
		
		for ( Object action : beforeUnloadingBehaviours )
			if ( action.getClass() == this.getClass() )
				if ( ! (( BaseScheme ) action ).isLoaded() ) 
				{
					(( BaseScheme ) action ).load( this , memory) ;
					(( BaseScheme ) action ).unload() ;
				}
				else 
				{
					(( BaseScheme ) action ).unload() ;
				}
			else if ( action instanceof GenericHandler )
				((GenericHandler) action ).invoke ( this , memory , null ) ;
		
 		Delegate delScopeDelegate = new Delegate ( memory , "deleteScope" ) ;
 		delScopeDelegate.invoke( identity ) ;
		
		if (  !( context instanceof BaseScheme )  )
			memory.clear() ;
		
		context = null  ;
		isLoaded       = false ;
	}
	
	public void reload () throws Exception
	{
		if ( ! isLoaded )
			throw new Exception ( "Can not relaod an unloaded scheme." ) ;
		
		MemoryBlock _memory  = memory  ;
		Object      _context = context ;
		
		this.unload() ;
		
		this.load ( _context , _memory ) ;
	}
	
	public MessageStack processMessage ( MessageStack _message ) throws Exception
	{
		if ( ! isLoaded )
			throw new Exception ( "Unloaded scheme can not process a message." ) ;
		
		if ( _message == null )
			throw new Exception ( "Invalid message can not be null." ) ;
		
		for ( Object action : messageReceivedBehaviours )
			if ( action.getClass() == this.getClass() )
				if ( ! (( BaseScheme ) action ).isLoaded() ) 
				{
					(( BaseScheme ) action ).load( this , memory) ;
					
					_message = (( BaseScheme ) action ).processMessage( _message ) ;
					
					(( BaseScheme ) action ).unload() ;
				}
				else 
				{
					_message = (( BaseScheme ) action ).processMessage( _message ) ;
				}	
			else if ( action instanceof GenericHandler )
				((GenericHandler) action ).invoke ( context , memory , _message ) ;
		
		return _message ;
	}
	
	
	
	private boolean addBehaviour ( int _behaviourKind , Object _obj , int _position ) throws Exception
	{
		if ( hasStarted )
			throw new Exception ( "Can not change scheme's behaviour when the scheme has started running.") ;
		
		if ( _obj == null )
			return false ;
		
		
		if ( _position < 0 )
		{
			switch ( _behaviourKind )
			{
				case 0 : afterLoadingBehaviours.add ( _obj )      ; break ;
				case 1 : messageReceivedBehaviours.add ( _obj ) ; break ;
				case 2 : beforeUnloadingBehaviours.add ( _obj )   ; break ;
				default : throw new Exception ( "Unknown behavoiur kind with number : " + _behaviourKind + " can not add it." ) ;
			}
		}
		else 
		{
			switch ( _behaviourKind )
			{
				case 0 :  	if ( _position < afterLoadingBehaviours.size() )
								afterLoadingBehaviours.add ( _position , _obj ) ; 
							else 
								afterLoadingBehaviours.add ( _obj ) ;		
							break ;
							
				case 1 : 	if ( _position < messageReceivedBehaviours.size() )
								messageReceivedBehaviours.add ( _position , _obj ) ; 
							else
								messageReceivedBehaviours.add ( _obj ) ; 
							break ;
							
				case 2 : 	if ( _position < beforeUnloadingBehaviours.size() )
								beforeUnloadingBehaviours.add ( _position , _obj ) ;
							else
								beforeUnloadingBehaviours.add ( _obj ) ;
							break ;
							
				default : throw new Exception ( "Unknown behavoiur kind with number : " + _behaviourKind + " can not add it." ) ;
			}
		}
		
		return true ;	
	}
	
	
	public boolean addOnAfterLoadingScheme ( BaseScheme _scheme , int _position ) throws Exception
	{
		return addBehaviour ( ON_AFTER_LOADING , _scheme , _position ) ;
	}

	public boolean addOnAfterLoadingHandler ( GenericHandler _delegate , int _position ) throws Exception
	{
		return addBehaviour ( ON_AFTER_LOADING , _delegate , _position ) ;	
	}
	
	public boolean addOnMessageReceivedScheme ( BaseScheme _scheme , int _position ) throws Exception
	{
		return addBehaviour ( ON_MESSAGE_RECEIVED , _scheme , _position ) ;
	}

	public boolean addOnMessageReceivedHandler ( GenericHandler _delegate , int _position ) throws Exception
	{
		return addBehaviour ( ON_MESSAGE_RECEIVED , _delegate , _position ) ;	
	}
	
	public boolean addOnBeforeUnloadingScheme ( BaseScheme _scheme , int _position ) throws Exception
	{
		return addBehaviour ( ON_BEFORE_UNLOADING , _scheme , _position ) ;
	}

	public boolean addOnBeforeUnloadingHandler ( GenericHandler _delegate , int _position ) throws Exception
	{
		return addBehaviour ( ON_BEFORE_UNLOADING , _delegate , _position ) ;	
	}
	
	
	public boolean addOnAfterLoadingScheme ( BaseScheme _scheme ) throws Exception
	{
		return addBehaviour ( ON_AFTER_LOADING , _scheme , -1 ) ;
	}

	public boolean addOnAfterLoadingHandler ( GenericHandler _delegate ) throws Exception
	{
		return addBehaviour ( ON_AFTER_LOADING , _delegate , -1 ) ;	
	}
	
	public boolean addOnMessageReceivedScheme ( BaseScheme _scheme ) throws Exception
	{
		return addBehaviour ( ON_MESSAGE_RECEIVED , _scheme , -1 ) ;
	}

	public boolean addOnMessageReceivedHandler ( GenericHandler _delegate ) throws Exception
	{
		return addBehaviour ( ON_MESSAGE_RECEIVED , _delegate , -1 ) ;	
	}
	
	public boolean addOnBeforeUnloadingScheme ( BaseScheme _scheme ) throws Exception
	{
		return addBehaviour ( ON_BEFORE_UNLOADING , _scheme , -1 ) ;
	}

	public boolean addOnBeforeUnloadingHandler ( GenericHandler _delegate ) throws Exception
	{
		return addBehaviour ( ON_BEFORE_UNLOADING , _delegate , -1 ) ;	
	}
	
	
	
	public boolean isLoaded ()
	{
		return isLoaded ;
	}
	
	public String getSchemeName ()
	{
		return name ;
	}
}
