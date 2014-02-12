package delegate;

import identity.ClassIdentity;
import identity.MethodIdentity;
import identity.SchemeIdentity;

import message.Message;
import utility.MemoryBlock;

public class GenericHandler implements IDelegate
{
	private Delegate innerDelegate = null ;
	
	private ClassIdentity  classIdentity  = null ;	
	private MethodIdentity methodIdentity = null ;
	
	public GenericHandler ( String _call ) throws Exception
	{
		innerDelegate = new Delegate ( _call ) ;
	}
	

	public GenericHandler ( Object _reference , String _method ) throws Exception
	{
		innerDelegate = new Delegate ( _reference , _method ) ;
	}
	


	public void invoke ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		if ( _context == null )
			throw new Exception ( "Context can not be null") ;
		
		if ( _memoryBlock == null )
			throw new Exception( "Memory block can not be null") ;
		
		Object [] arguments =  (_message == null ) ? new Object [] { _context , _memoryBlock , _scope , new Message () } : new Object [] { _context , _memoryBlock , _scope , _message } ;
		innerDelegate.untypedInvoke( arguments ) ;
	}
	
	
	public boolean hasReference ()
	{
		return innerDelegate.hasReference() ;
	}
	
	public String getReferenceInfo ()
	{
		return innerDelegate.getReferenceInfo() ;
	}
	
	public String getMethodName ()
	{
		return innerDelegate.getMethodName() ;
	}
	
	public String getClassName ()
	{
		return innerDelegate.getClassName() ;
	}
	
	public boolean hasArguments ()
	{
		return innerDelegate.hasArguments() ;
	}
	
	public boolean needArguments ()
	{
		return innerDelegate.needArguments() ;
	}
	
	public boolean isStatic ()
	{
		return innerDelegate.isStatic() ;
	}
	
	public String getDescription ()
	{
		return innerDelegate.getDescription() ; 
	}
}
