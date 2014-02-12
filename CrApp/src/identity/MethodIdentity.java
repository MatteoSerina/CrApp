package identity;

import core.UIDGenerator;
import utility.Utils;

public class MethodIdentity extends IIdentity
{
	private final static String UNKNOWN = "Unknown" ;
	
	private ClassIdentity membershipClass = null    ;
	private Object        instance        = null    ;
	private String        name            = UNKNOWN ;
	private String        returnType      = UNKNOWN ;
	private String []     argumentsName   = null    ;
	private boolean       isStatic        = false   ;
	
	private long          uid             = UIDGenerator.getInstance().next() ;
	
	
	public MethodIdentity () {} ;
	
	public MethodIdentity ( String _name )
	{
		if ( _name == null )
			return ;
		
		name = _name ; 
	}
	
	public MethodIdentity ( String _name , boolean _isStatic )
	{
		if ( _name == null )
			return ;
		
		name     = _name     ; 
		isStatic = _isStatic ;
	}
	
	
	public MethodIdentity ( String _name , Object _instance )
	{
		if ( _name == null || _instance == null )
			return ;
		
		name     = _name     ;
		instance = _instance ;
		
		String className = _instance.getClass().getSimpleName()        ;
		String nameSpace = _instance.getClass().getPackage().getName() ;
		
		if ( className != null && nameSpace != null )
			membershipClass = new ClassIdentity ( className , nameSpace ) ;
		
		if ( className != null && nameSpace == null )
			membershipClass = new ClassIdentity ( className ) ;
	}
	
	public MethodIdentity ( String _name , Object _instance , String _returnedType )
	{
		if ( _name == null || _instance == null || _returnedType == null )
			return ;
		
		name       = _name         ;
		instance   = _instance     ;
		returnType = _returnedType ;
		
		String className = _instance.getClass().getSimpleName()        ;
		String nameSpace = _instance.getClass().getPackage().getName() ;
		
		if ( className != null && nameSpace != null )
			membershipClass = new ClassIdentity ( className , nameSpace ) ;
		
		if ( className != null && nameSpace == null )
			membershipClass = new ClassIdentity ( className ) ;
	}
	
	public MethodIdentity ( String _name , Object _instance , ClassIdentity _classIdentity )
	{
		if ( _name == null || _instance == null || _classIdentity == null )
			return ;
		
		name            = _name          ;
		instance        = _instance      ;
		membershipClass = _classIdentity ;
	}
	
	public MethodIdentity ( String _name , Object _instance , ClassIdentity _classIdentity , String _returnedType  )
	{
		if ( _name == null || _instance == null || _classIdentity == null || _returnedType == null )
			return ;
		
		name            = _name          ;
		instance        = _instance      ;
		membershipClass = _classIdentity ;
		returnType      = _returnedType  ;
	}
	
	public MethodIdentity ( String _name , Object _instance , ClassIdentity _classIdentity , String _returnedType , String [] _argumentsName )
	{
		if ( _name == null || _instance == null || _classIdentity == null || _returnedType == null || _argumentsName == null )
			return ;
		
		name            = _name          ;
		instance        = _instance      ;
		membershipClass = _classIdentity ;
		returnType      = _returnedType  ;
		argumentsName   = _argumentsName ;
	}
	


	
	public MethodIdentity ( String _name , String _returnedType )
	{
		if ( _name == null || _returnedType == null )
			return ;
		
		name       = _name         ;
		returnType = _returnedType ;
	}
	
	public MethodIdentity ( String _name , String _returnedType , boolean _isStatic )
	{
		if ( _name == null || _returnedType == null )
			return ;
		
		name       = _name         ;
		returnType = _returnedType ;
		isStatic   = _isStatic     ;
	}
	
	public MethodIdentity ( String _name , ClassIdentity _classIdentity )
	{
		if ( _name == null || _classIdentity == null )
			return ;
		
		name            = _name          ;
		membershipClass = _classIdentity ;
	}
	
	public MethodIdentity ( String _name , ClassIdentity _classIdentity , boolean _isStatic )
	{
		if ( _name == null || _classIdentity == null )
			return ;
		
		name            = _name          ;
		membershipClass = _classIdentity ;
		isStatic        = _isStatic      ;
	}
	
	public MethodIdentity ( String _name , ClassIdentity _classIdentity , String _returnedType  )
	{
		if ( _name == null || _classIdentity == null || _returnedType == null )
			return ;
		
		name            = _name          ;
		membershipClass = _classIdentity ;
		returnType      = _returnedType  ;
	}
	
	public MethodIdentity ( String _name , ClassIdentity _classIdentity , String _returnedType , boolean _isStatic  )
	{
		if ( _name == null || _classIdentity == null || _returnedType == null )
			return ;
		
		name            = _name          ;
		membershipClass = _classIdentity ;
		returnType      = _returnedType  ;
		isStatic        = _isStatic      ;
	}
	
	public MethodIdentity ( String _name , ClassIdentity _classIdentity , String _returnedType , String [] _argumentsName )
	{
		if ( _name == null ||_classIdentity == null || _returnedType == null || _argumentsName == null )
			return ;
		
		name            = _name          ;
		membershipClass = _classIdentity ;
		returnType      = _returnedType  ;
		argumentsName   = _argumentsName ;
	}
	
	public MethodIdentity ( String _name , ClassIdentity _classIdentity , String _returnedType , String [] _argumentsName , boolean _isStatic )
	{
		if ( _name == null ||_classIdentity == null || _returnedType == null || _argumentsName == null )
			return ;
		
		name            = _name          ;
		membershipClass = _classIdentity ;
		returnType      = _returnedType  ;
		argumentsName   = _argumentsName ;
		isStatic        = _isStatic      ;
	}
	
	
	public long getUID ()
	{
		return uid ;
	}
	
	public String getName ()
	{
		return name ;
	}
	
	public ClassIdentity getClassIdentity ()
	{
		return membershipClass ;
	}
	
	public boolean isStatic ()
	{
		return isStatic ;
	}
	
	public String getReturnedType ()
	{
		return returnType ;
	}
	
	public String [] getArgumentsName ()
	{
		String [] argsName = new String [ argumentsName.length ] ;
		
		System.arraycopy( argumentsName , 0 ,  argsName , 0 , argumentsName.length ) ;
		
		return argsName ;
	}
	
	public String getShortDescription ()
	{
		String className = UNKNOWN ;
		
		if ( membershipClass != null )
			className = membershipClass.getName() ;
		
		if ( className == null )
			className = UNKNOWN ;
		
		if ( instance != null )
			return className + "[" + instance.hashCode() + "]." + name ;
		else 
			return className + "." + name ;
	}
	
	public String getDescription ()
	{
		String result = Utils.STR_EMPTY ;

		if ( membershipClass != null )
			result = membershipClass.getDescriptor() + "  --  " ;
		
		if ( returnType != null )
			result += "<" + returnType + "> " ;
		
		result += this.getShortDescription() ;
		
		if ( argumentsName != null )
		{
			result += " (" ;
			
			for ( int i = 0 ; i < argumentsName.length ; i++ )
				result += " <" + (  ( argumentsName[i] == null ) ? UNKNOWN : argumentsName[i]  ) + "> " ;
			
			result += ")  " ;
		}
			
		result += ( isStatic ) ? "[STATIC METHOD]" : "[INSTANCE METHOD]" ;
		
		return result ;
	}
	
	public boolean equals ( Object _obj )
	{
		if (  !( _obj instanceof MethodIdentity )  )
			return false ;
		
		return ( this.uid == ( (MethodIdentity) _obj ).getUID() ) ;
	}
}
