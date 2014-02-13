package channel;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import core.UIDGenerator;

public class CryptoChannel 
{

	private Long UID = UIDGenerator.getInstance().next() ;
	
	public CryptoChannel ()
	{
		//TODO : cerca di capire come assegnare il nome al provider (per la distruzione futura)
		BouncyCastleProvider provider = new BouncyCastleProvider (  ) ;
		
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