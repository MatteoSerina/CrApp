package delegate;

import identity.ClassIdentity;
import identity.MethodIdentity;
import identity.SchemeIdentity;

import message.Message;
import utility.MemoryBlock;
import utility.Utils;

public class GenericHandler implements IDelegate
{
	private Delegate innerDelegate = null ;
	
	private ClassIdentity  classIdentity  = null ;	
	private MethodIdentity methodIdentity = null ;
	
	public GenericHandler ( String _call ) throws Exception
	{
		int lastDot = _call.lastIndexOf( '.' ) ;
		
		String fullName = _call.substring( 0  , lastDot ) ;
		String methodName = _call.substring(  ++lastDot , _call.length()  ) ;
		
		classIdentity = new ClassIdentity(  Utils.getClassName ( fullName ) , Utils.getClassNameSpace( fullName )   ) ;
		methodIdentity = new MethodIdentity ( methodName , classIdentity ) ;
		
		innerDelegate = new Delegate ( _call ) ;
	}
	

	public GenericHandler ( Object _reference , String _method ) throws Exception
	{
		classIdentity = new ClassIdentity(  Utils.getClassName (_reference ) , Utils.getClassNameSpace( _reference )   ) ;
		methodIdentity = new MethodIdentity (_method , _reference , classIdentity ) ;
		
		innerDelegate = new Delegate ( _reference , _method ) ;
	}
	


	public void invoke ( Object _context , MemoryBlock _memoryBlock , Message _message  ) throws Exception
	{
		if ( _context == null )
			throw new Exception ( "Context can not be null") ;
		
		if ( _memoryBlock == null )
			throw new Exception( "Memory block can not be null") ;
	
		Object [] arguments =  (_message == null ) ? new Object [] { _context , _memoryBlock , new Message () } : new Object [] { _context , _memoryBlock , _message } ;
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
