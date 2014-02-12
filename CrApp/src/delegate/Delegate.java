package delegate;

import java.lang.reflect.Method;

import test.Main;
import utility.Utils;


public class Delegate <T> implements IDelegate
{

	private Method    method         = null ;
	private Object [] arguments      = null ;
	private Object    reference      = null ;
	private String    nameOfClass    = null ;
	
	private boolean   needArguments  = false ;
	private boolean   isStaticMethod = false ;
	private String    description    = Utils.STR_EMPTY ;
	
	
	
	
	
	public Delegate ( String _call ) throws Exception
	{

		if ( _call == null )
			throw new Exception ( "Invalid parameter, _call can not be null." ) ;
		
		_call = _call.replaceAll( "(\\(|\\))" , Utils.STR_EMPTY ) ;

		String [] input = new String [] { Utils.STR_EMPTY , Utils.STR_EMPTY }  ;
		
		int lastDot = _call.lastIndexOf( '.' ) ;
		
		if ( lastDot < 0 )
			throw new Exception ( "Invalid parameter, _call must be like \"namespace.StaticObject.method()\"" ) ;
		
		input[0]  = _call.substring( 0 , lastDot ) ;
		
		if ( lastDot++ < _call.length() )
			input[1] = _call.substring ( lastDot , _call.length() ) ;
		
		if ( input[0] == "" || input[1] == "" )
			throw new Exception ( "Invalid parameter, _call must be like \"namespace.StaticObject.method()\"" ) ;
		
		String className  = input[0].trim() ;
		String methodName = input[1].trim() ;
		
		Class currentClass = null ;
		
		try 
		{
			currentClass = Class.forName( className ) ;
		} catch( ClassNotFoundException e )
		{
			try
			{
				Delegate.class.getClassLoader().loadClass( className ) ;
				currentClass = Class.forName( className ) ;
			}
			catch ( Exception err )
			{
				throw new Exception ( "Invalid parameter, _call must refer to a valid static class, class : " + className + " does not exisit in current scope." ) ;
			}
		}
		
		for ( Method currentMethod : currentClass.getDeclaredMethods() ) 
			if ( currentMethod.getName().equals(methodName) )
			{
				method = currentMethod ;
				break ;
			}
		
		if ( method == null )
			throw new Exception ( "Invalid parameter, _call must refer to a valid static method of the class : " + className + ", this class does not contain any definition for the method : " + methodName + " ." ) ;
		
		nameOfClass    = className ;
		needArguments  = ( method.getParameterTypes().length > 0 ) ;
		isStaticMethod = true  ;
			
		description = "Static call ->  " + className + "." + methodName + "()" ;
	}
	
	public Delegate ( String _call , Object [] _params ) throws Exception
	{
		this( _call ) ;
		
		if ( _params == null || _params.length == 0 )
			throw new Exception ( "Invalid parameter, _params must be valorized.") ;
		
		arguments = _params ;
	}

	public <A> Delegate ( String _call , A _param ) throws Exception
	{
		this( _call ) ;	
		arguments =  new Object [] { _param } ;
	}
	
	public <A, B> Delegate ( String _call , A _param0 , B _param1  ) throws Exception
	{
		this( _call ) ;
		arguments =  new Object [] { _param0 , _param1 } ;
	}
	
	public <A, B, C> Delegate ( String _call , A _param0 , B _param1 , C _param2 ) throws Exception
	{
		this( _call ) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 } ;
	}
	
	public <A, B, C, D> Delegate ( String _call , A _param0 , B _param1 , C _param2 , D _param3 ) throws Exception
	{
		this( _call ) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 } ;
	}
	
	public <A, B, C, D, E> Delegate ( String _call , A _param0 , B _param1 , C _param2 , D _param3 , E _param4 ) throws Exception
	{
		this( _call ) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 , _param4 } ;
	}
	
	public <A, B, C, D, E, F> Delegate ( String _call , A _param0 , B _param1 , C _param2 , D _param3 , E _param4 , F _param5 ) throws Exception
	{
		this( _call ) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 , _param4 , _param5 } ;
	}
	
	
	
	
	
	public Delegate ( Object _reference , String _method ) throws Exception
	{
		if ( _reference == null )
			throw new Exception ( "Invalid parameter, _reference can not be null." ) ;
		
		if ( _method == null )
			throw new Exception ( "Invalid parameter, _method can not be null." ) ;
		
		_method = _method.replaceAll( "(\\(|\\))" , Utils.STR_EMPTY ).trim() ;

		Class currentClass = _reference.getClass() ;
		String methodName  = _method               ;
		
		if ( currentClass == null )
			 throw new Exception ( "Invalid parameter, _reference does not refer to any valid class actual _reference value is : " + _reference.toString() ) ;
		
		for ( Method currentMethod : currentClass.getDeclaredMethods() ) 
			if ( currentMethod.getName().equals(methodName) )
			{
				method = currentMethod ;
				break ;
			}
		
		if ( method == null )
			throw new Exception ( "Invalid parameter, _method must refer to a valid method of the class : " + currentClass.getName() + ", this class does not contain any definition for the method : " + methodName + " ." ) ;
		
		nameOfClass    = currentClass.getName()                    ;
		reference      = _reference                                ; 
		needArguments  = ( method.getParameterTypes().length > 0 ) ;
		isStaticMethod = false ;
			
		description = "Instance call ->  " + currentClass.getName() + "." + methodName + "()  where current instance for " + currentClass.getName() + " is " + _reference.toString() ;
	}
	
	public Delegate ( Object _reference , String _method , Object _params [] ) throws Exception
	{
		this(_reference, _method) ;
		
		if ( _params == null || _params.length == 0 )
			throw new Exception ( "Invalid parameter, _params must be valorized.") ;
		
		arguments = _params ;
	}
	
	public <A> Delegate ( Object _reference , String _method , A _param ) throws Exception
	{
		this(_reference, _method) ;	
		arguments =  new Object [] { _param } ;
	}
	
	public <A, B> Delegate ( Object _reference , String _method , A _param0 , B _param1  ) throws Exception
	{
		this(_reference, _method) ;
		arguments =  new Object [] { _param0 , _param1 } ;
	}
	
	public <A, B, C> Delegate ( Object _reference , String _method , A _param0 , B _param1 , C _param2 ) throws Exception
	{
		this(_reference, _method) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 } ;
	}
	
	public <A, B, C, D> Delegate ( Object _reference , String _method , A _param0 , B _param1 , C _param2 , D _param3 ) throws Exception
	{
		this(_reference, _method) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 } ;
	}
	
	public <A, B, C, D, E> Delegate ( Object _reference , String _method , A _param0 , B _param1 , C _param2 , D _param3 , E _param4 ) throws Exception
	{
		this(_reference, _method) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 , _param4 } ;
	}
	
	public <A, B, C, D, E, F> Delegate ( Object _reference , String _method , A _param0 , B _param1 , C _param2 , D _param3 , E _param4 , F _param5 ) throws Exception
	{
		this(_reference, _method) ;
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 , _param4 , _param5 } ;
	}
	
	
	
	
	
	public T invoke () throws Exception
	{
		
		if ( method == null )
			throw new Exception ( "Can not invoke a delegate with null method associated." ) ;
		
		if (  needArguments && ( ! this.hasArguments() )  )
		{
			throw new Exception ( "Can not invoke this method : " + method.getName() + " without any parameter." ) ;
		}
		else
		{
			if ( (! needArguments) && this.hasArguments() )
				arguments = null ;
		}
		
		method.setAccessible ( true ) ;
		
		if ( isStaticMethod )
		{
			return (T) method.invoke( null , arguments) ;
		}
		else 
		{
			if ( reference == null )
				throw new Exception ( "Can not invoke this method : " + method.getName() + " without any instanced object \\( of type " + nameOfClass + "\\) on which apply it.") ;
			
			return (T) method.invoke( reference , arguments ) ;
		}
	}
	
	public T untypedInvoke ( Object [] _params ) throws Exception
	{
		if ( _params == null || _params.length == 0 )
			throw new Exception ( "Invalid parameter, _params must be valorized.") ;
		
		arguments = _params ;
		
		return this.invoke() ;
	}
	
	public <A> T invoke ( A _param ) throws Exception
	{
		arguments =  new Object [] { _param } ;
		return this.invoke() ;
	}
	
	public <A, B> T invoke ( A _param0 , B _param1  ) throws Exception
	{
		arguments =  new Object [] { _param0 , _param1 } ;
		return this.invoke() ;
	}
	
	public <A, B, C> T invoke ( A _param0 , B _param1 , C _param2 ) throws Exception
	{
		arguments =  new Object [] { _param0 , _param1 , _param2 } ;
		return this.invoke() ;
	}
	
	public <A, B, C, D> T invoke ( A _param0 , B _param1 , C _param2 , D _param3 ) throws Exception
	{
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 } ;
		return this.invoke() ;
	}
	
	public <A, B, C, D, E> T invoke ( A _param0 , B _param1 , C _param2 , D _param3 , E _param4 ) throws Exception
	{
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 , _param4 } ;
		return this.invoke() ;
	}
	
	public <A, B, C, D, E, F>  T invoke ( A _param0 , B _param1 , C _param2 , D _param3 , E _param4 , F _param5 ) throws Exception
	{
		arguments =  new Object [] { _param0 , _param1 , _param2 , _param3 , _param4 , _param5 } ;
		return this.invoke() ;
	}
	
		
	
	
	
	public boolean hasReference ()
	{
		return ( reference != null ) ;
	}
	
	public String getReferenceInfo ()
	{
		return reference.toString() ;
	}
	
	public String getMethodName ()
	{
		return method.getName() ;
	}
	
	public String getClassName ()
	{
		return nameOfClass ;
	}
	
	public boolean hasArguments ()
	{
		return ( arguments != null && arguments.length > 0 ) ;
	}
	
	public boolean needArguments ()
	{
		return needArguments ;
	}
	
	public boolean isStatic ()
	{
		return isStaticMethod ;
	}
	
	public String getDescription ()
	{
		return description ; 
	}

}

