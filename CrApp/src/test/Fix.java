package test;

import delegate.GenericHandler;
import identity.SchemeIdentity;
import message.Message;
import schemes.BaseScheme;
import utility.MemoryBlock;
import utility.Value;

public class Fix 
{

	private static void cypStep1Execute ( Object _context , MemoryBlock _memoryBlock , Message _message  ) throws Exception
	{
		int len = _message.getData().length ;
		
		_memoryBlock.store("KEY_LEN", new Value ( len ) ) ;

		System.out.println( "Cypher's scheme starting to process one message.");
	}
	
	public static void applyFix ( BaseScheme _scheme) throws Exception 
	{
		_scheme.addOnMessageReceivedHandler( new GenericHandler ( "test.Fix.cypStep1Execute()" ) , 0 ) ;
	}
	
	

}
