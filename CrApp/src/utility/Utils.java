package utility;


public class Utils 
{
	public static String STR_EMPTY = "" ;
	
	public static String getClassName ( String _fullName )
	{
		int lastDot = _fullName.lastIndexOf( '.' ) ;
		
		if ( lastDot < 0 )
			return STR_EMPTY ;
		
		return _fullName.substring ( ++lastDot , _fullName.length() ) ;
	}
	
	public static String getClassName ( Object _reference )
	{
		String fullName = _reference.getClass().getName() ;
		
		int lastDot = fullName.lastIndexOf( '.' ) ;
		
		if ( lastDot < 0 )
			return STR_EMPTY ;
		
		return fullName.substring ( ++lastDot , fullName.length() ) ;
	}
	
	public static String getClassNameSpace ( String _fullName ) 
	{
		int lastDot = _fullName.lastIndexOf( '.' ) ;
		
		if ( lastDot < 0 )
			return STR_EMPTY ;
		
		return _fullName.substring( 0 , lastDot ) ;	
	}
	
	public static String getClassNameSpace ( Object _reference ) 
	{
		String fullName = _reference.getClass().getName() ;

		int lastDot = fullName.lastIndexOf( '.' ) ;
		
		if ( lastDot < 0 )
			return STR_EMPTY ;
		
		return fullName.substring( 0 , lastDot ) ;	
	}
	
	public static byte[] castToPrimitiveByte(Byte[] _objByte){
		byte[] result = new byte[_objByte.length];
		for(int i=0; i<_objByte.length; i++){
			result[i]=_objByte[i].byteValue();
		}
		return result;
	}
	
	public static Byte[] castToObjectByte(byte[] _primByte){
		Byte[] result = new Byte[_primByte.length];
		for(int i=0; i<_primByte.length; i++){
			result[i] = new Byte(_primByte[i]);
		}
		return result;
	}
}