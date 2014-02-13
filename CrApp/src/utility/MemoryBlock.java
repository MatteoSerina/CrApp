package utility;

import identity.ClassIdentity;
import identity.SchemeIdentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;




public class MemoryBlock 
{
	private int                    elements       = 0                             ;
	private int                    maxElements    = Short.MAX_VALUE               ;
	private ClassIdentity          fatherIdentity = null                          ;
	
	private ArrayList <SchemeIdentity> scopes   = new ArrayList <SchemeIdentity> () ;
	private HashMap   <String,Value>   innerMap = new HashMap   <String,Value> ()   ;
	
	public MemoryBlock ()
	{}
	
	public MemoryBlock ( ClassIdentity _fatherIdentity )
	{
		fatherIdentity = _fatherIdentity ;
	}
	
	public MemoryBlock ( ClassIdentity _fatherIdentity , int _maxElements )
	{
		fatherIdentity = _fatherIdentity ;
		maxElements    = _maxElements    ;
	}

	public boolean exist ( String _varName )
	{	
		return innerMap.containsKey( _varName ) ;
	}
	
	public boolean store ( String _varName , Value _value ) throws Exception
	{	
		if ( _varName == null || _value == null )
			return false ;
		
		if ( elements == maxElements )
			throw new Exception ( "Max number of elements reached for this memory block." ) ;
		
		if ( exist ( _varName ) )
			return false ;
		
		if ( innerMap.put( _varName , _value ) != null  )
			return false ;
		
		elements++ ;
		return true ;
	}
	
	public boolean overwrite ( String _varName , Value _value ) throws Exception
	{
		if ( _value == null )
			return false ;
		
		if ( exist( _varName ) )	
			return innerMap.put( _varName , _value ) != null ;
		
		return false ;
	}
	
	public Value load ( String _varName ) throws Exception
	{
		if ( exist( _varName ) )
			return innerMap.get( _varName ) ;
		
		return null ;
	}
	
	public boolean erease ( String _varName ) throws Exception
	{
		if ( exist( _varName ) )
		{
			innerMap.remove( _varName ) ;
			elements-- ; 
			
			return true ;
		}
		
		return false ;
	}
	
	public void clear () throws Exception
	{
		innerMap.clear() ;
		elements = 0     ;
	}
	
	
	
	public boolean scopedExist ( String _varName )
	{
		return resolveScopedKeyToGlobalKey ( _varName ) != null ;
	}
	
	public boolean scopedStore ( String _varName , Value _value ) throws Exception
	{	
		if ( _varName == null || _value == null )
			return false ;
		
		if ( elements == maxElements )
			throw new Exception ( "Max number of elements reached for this memory block." ) ;
		
		if ( scopedExist ( _varName ) )
			return false ;
		
		String realScopedVarName = getScopeDescriptor ( getCurrentScope() ) + _varName ;
		
		if ( innerMap.put( realScopedVarName , _value ) != null  )
			return false ;
		
		elements++ ;
		return true ;
	}
	
	public boolean scopedOverwrite ( String _varName , Value _value ) throws Exception
	{
		if ( _value == null )
			return false ;
		
		String globalKey = resolveScopedKeyToGlobalKey ( _varName ) ;
		
		if ( globalKey != null  )	
			return innerMap.put( globalKey , _value ) != null ;
		
		return false ;
	}
	
	public Value scopedLoad ( String _varName ) throws Exception
	{
		String globalKey = resolveScopedKeyToGlobalKey ( _varName ) ;
		
		if ( globalKey != null  )
			return innerMap.get( globalKey ) ;
		
		return null ;
	}
	
	
	private boolean addNewScope ( SchemeIdentity _scope ) throws Exception
	{
		if ( scopes.contains( _scope ) )
			return false ;
		
		if ( scopes.size() > Short.MAX_VALUE )
			throw new Exception ( "Stack overflow : scopes stack exceed allocated size." ) ;
		
		return scopes.add( _scope ) ;
	}

	private boolean deleteScope ( SchemeIdentity _scope )
	{
		if ( ! scopes.contains( _scope ) )
			return false ;
		
		List <SchemeIdentity> toRemove = scopes.subList(  scopes.indexOf(_scope ) , scopes.size()  ) ;
		 
		for ( int i = toRemove.size() - 1 ; i >= 0 ; i-- )
		{
			String scopeDescriptor = getScopeDescriptor (  toRemove.get( i )  ) ;
			
			String [] keys = innerMap.keySet().toArray( new String [] {} );
			
			for ( int j = 0 ; j < keys.length ; j++ )
				if (  keys[j].indexOf( scopeDescriptor ) == 0  )
  					innerMap.remove( keys[j] ) ;
		}
		
		return true ;
	}
	
	
	/*
	 * PRIVATE METHODs
	 */

	private SchemeIdentity getCurrentScope ()
	{
		if ( scopes.isEmpty() )
			return null ;
		
		return scopes.get(  scopes.size() -1 ) ;
	}
	
	private String getScopeDescriptor ( SchemeIdentity _scope )
	{
		if ( _scope == null )
			return  ( ":Blank:" ) ;
		
		return ":" + _scope.getDescription() + ":" ;
	}
	
	private String resolveScopedKeyToGlobalKey ( String _scopedKey )
	{		
		List <SchemeIdentity> toCheck = scopes.subList(  0 , scopes.indexOf( getCurrentScope () ) + 1  ) ;
		 
		for ( int i = toCheck.size() - 1 ; i >= 0 ; i-- )
		{
			String globalKeyToCheck = getScopeDescriptor (  toCheck.get( i )  ) + _scopedKey ;
			
			if ( this.exist( globalKeyToCheck) )
				return globalKeyToCheck ;
		}
		
		return null ;
	}
}
