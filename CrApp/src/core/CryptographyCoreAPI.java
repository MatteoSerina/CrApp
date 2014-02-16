package core;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import utility.Utils;

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
	
	public static Byte[] aesAlgorithm (Byte[] _inMessage, Byte[] _keyBytes, Boolean _encrypt) throws Exception{
		//Setup of the cipher
		if(!(_keyBytes.length==16 || _keyBytes.length==24 || _keyBytes.length==32))
			throw new Exception("Key must be 128 or 192 or 256 bit long.");
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("AES", "BC");
		SecretKeySpec key = new SecretKeySpec(Utils.castToPrimitiveByte(_keyBytes), "AES");

		if(_encrypt){
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] ciphertext = new byte[cipher.getOutputSize(_inMessage.length)];
			int ctLength = cipher.update(Utils.castToPrimitiveByte(_inMessage), 0, _inMessage.length, ciphertext, 0);
			ctLength += cipher.doFinal(ciphertext, ctLength);
			
			return Utils.castToObjectByte(ciphertext);
		}
		else{
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plaintext = new byte[cipher.getOutputSize(_inMessage.length)];
			int ptLength = cipher.update(Utils.castToPrimitiveByte(_inMessage), 0, _inMessage.length, plaintext, 0);
			ptLength += cipher.doFinal(plaintext, ptLength);
			
			return Utils.castToObjectByte(plaintext);
		}
	}

}
