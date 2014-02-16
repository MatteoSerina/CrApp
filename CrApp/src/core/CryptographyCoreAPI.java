package core;

public class CryptographyCoreAPI 
{


	public static byte [] testDummyXor ( byte[] _message , byte [] _key ) throws Exception
	{
		if ( _message.length != _key.length )
			throw new Exception ("Message and key must have the same lenght." ) ;
		
		for ( int i = 0 ; i < _message.length ; i++ )
			_message [i] = (byte)(_message[i] ^ _key[i]) ;
		
		return _message ;
	}
	
	public static byte [] testDummyKeyDerivation ( byte [] _seed , int _lenghtRequired )throws Exception
	{
		if ( _seed.length <= 0 || _lenghtRequired <= 0 )
			throw new Exception ( "Invalid parameter" ) ;
		
		byte result = 17 ;
		
		for ( byte currentByte : _seed )
			result = (byte)( ( result * currentByte ) % 256 ) ;
		
		byte [] buffer = new byte [ _lenghtRequired] ;
		
		for ( int i = 0 ; i< _lenghtRequired ; i++ )
			buffer [i] = result ;
		
		return buffer ;
	}

}
