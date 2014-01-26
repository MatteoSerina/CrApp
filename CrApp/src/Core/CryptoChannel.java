package Core;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptoChannel 
{
	public enum CIPHER
	{
		AES     ,
		AES_CTR     
	}
	
	
	private static Long UID = UIDGenerator.getInstance().next() ;
	
	public CryptoChannel ()
	{
		//TODO : cerca di capire come assegnare il nome al provider (per la distruzione futura)
		BouncyCastleProvider provider = new BouncyCastleProvider () ;
		
		Security.addProvider ( provider ) ;
	}


	protected void finalize() throws Throwable 
	{
	     try 
	     {
	    	 //Security.removeProvider( UID.toString() );
	     } 
	     finally 
	     {
	         super.finalize() ;
	     }
	 }
}
