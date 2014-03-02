package utility;

import java.math.BigInteger;

public class Value <T>
{
	private T innerValue ;

	
	public Value ( T _value )
	{
		innerValue = _value ;
	}
	
	public T read ()
	{
		return   innerValue ;
	}
	
	public String readAsString ()
	{
		if ( innerValue.getClass() == String.class )
			return (String) innerValue ;
		
		return Utils.STR_EMPTY ;	
	}
	
	public boolean readAsBoolean ()
	{
		if ( innerValue.getClass() == Boolean.class )
			return  ( (Boolean)innerValue ).booleanValue() ;
		
		return false ;
	}
	
	public char readAsChar ()
	{
		if ( innerValue.getClass() == Character.class )
			return  ( (Character)innerValue ).charValue() ;
		
		return (char)0 ;
	}
	
	public byte readAsByte ()
	{
		if ( innerValue.getClass() == Byte.class )
			return  ( (Byte)innerValue ).byteValue() ;
		
		return Byte.MIN_VALUE ;
	}
	
	public int readAsInteger ()
	{
		if ( innerValue.getClass() == Integer.class )
			return  ( (Integer)innerValue ).intValue() ;
		
		return Integer.MIN_VALUE ;
	}
	
	public long readAsLong ()
	{
		if ( innerValue.getClass() == Long.class )
			return  ( (Long)innerValue ).longValue() ;
		
		return Long.MIN_VALUE ;
	}
	
	public float readAsFloat ()
	{
		if ( innerValue.getClass() == Float.class )
			return  ( (Float)innerValue ).floatValue() ;
		
		return Float.MIN_VALUE ;
	}
	
	public double readAsDouble ()
	{
		if ( innerValue.getClass() == Double.class )
			return  ( (Float)innerValue ).doubleValue() ;
		
		return Double.MIN_VALUE ;
	}
	
	public BigInteger readAsBigInteger ()
	{
		if ( innerValue.getClass() == BigInteger.class )
		{
			byte [] bytes = ((BigInteger)innerValue).toByteArray() ;
			byte [] copy = new byte [bytes.length] ;
			
			System.arraycopy( bytes , 0 , copy , 0 , bytes.length ) ;
			return new BigInteger ( copy ) ;
		}

		return BigInteger.ZERO ;
	}
	
	
	
	public String [] readAsStringArray ()
	{
		if ( innerValue.getClass().isArray() &&  innerValue.getClass().getComponentType() == String.class )
		{
			String [] src  = ((String[])innerValue) ;
			String [] copy = new String [ src.length ] ;
			
			System.arraycopy( src , 0 , copy , 0 , src.length ) ;
			return copy ;
		}
		
		return new String[0] ;	
	}
	
	public byte [] readAsByteArray ()
	{
		if ( innerValue.getClass().isArray() &&  innerValue.getClass().getComponentType() == Byte.class )
		{
			Byte[] src = (Byte[]) innerValue ;
			byte[] result = new byte[ src.length ] ;
			
			for ( int i=0 ; i< src.length ; i++ )
				result[i]= src[i].byteValue() ;

			return result;
		}
		
		return new byte[0] ;	
	}
	
	
}
