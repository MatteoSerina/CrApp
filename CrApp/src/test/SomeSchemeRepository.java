package test;

import identity.SchemeIdentity;
import core.CryptographyCoreAPI;
import delegate.GenericHandler;
import message.Message;
import schemes.BaseScheme;
import utility.MemoryBlock;
import utility.Value;

public class SomeSchemeRepository 
{
	public SomeSchemeRepository ()
	{}
	
	
	// METODI PER LO SCHEMA DI GENERAIZONE CHIAVI (SEME)
	private static void seedSetup ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		_memoryBlock.scopedStore( "BASE_SEED",  new Value <Long> ( System.nanoTime() ) ) ;
		
		System.out.println( "Seed's scheme setted up.");
	}
	
	private static void seedExecute ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		Value <Long> base = _memoryBlock.scopedLoad( "BASE_SEED" ) ;
		Value <Integer> length = _memoryBlock.load( "KEY_LEN" ) ;
		
		long now = System.nanoTime() ;
		
		byte [] tempresult = CryptographyCoreAPI.testDummyKeyDerivation ( new byte[] { ((byte) (base.read() % 256 )) ,  ((byte) (now % 256 )) } , length.read() ) ;
		Byte [] result = new Byte [tempresult.length] ;
		
		int i = 0 ;
		for ( byte currentByte : tempresult )
		{
			result[i] = new Byte ( currentByte ) ;
			i++ ;
		}
		
		_memoryBlock.store( "SEED_RESULT", new Value (result)  ) ;
		
		System.out.println( "Seed's scheme has just process one message.");
	}
	
	private static void seedTeardown ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		System.out.println( "Seed's scheme is about to be shutted down.");
	}
	
	//METODI PER LO SCHEMA DI CODIFICA
	private static void cypSetup ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		System.out.println( "Cypher's scheme setted up.");
	}
	
	
	private static void cypStep1Execute ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		int len = _message.getData().length ;
		
		_memoryBlock.store("KEY_LEN", new Value ( len ) ) ;

		System.out.println( "Cypher's scheme starting to process one message.");
	}
	
	private static void cypStep3Execute ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		Value <Byte[]> wrappedKey = _memoryBlock.load( "SEED_RESULT" ) ;

		byte[] key = new byte [wrappedKey.read().length] ;
		int i = 0 ;
		
		for ( Byte currentByte : wrappedKey.read() )
		{
			key[i] = currentByte.byteValue() ;
			i++ ;
		}
		
		byte[] result = CryptographyCoreAPI.testDummyXor( ((Message)_message).getData() , key ) ;
		
		System.out.println( "Cypher's scheme has just process one message.");
	}
	
	private static void cypTeardown ( Object _context , MemoryBlock _memoryBlock , SchemeIdentity _scope , Message _message  ) throws Exception
	{
		System.out.println( "Cypher's scheme is about to be shutted down.");
	}
	
	public static BaseScheme getAlgorithmScheme () throws Exception
	{
		BaseScheme seedScheme = new BaseScheme ( "Seed" ) ;
		
		seedScheme.addOnAfterLoadingHandler( new GenericHandler ( "test.SomeSchemeRepository.seedSetup()" ) ) ;
		seedScheme.addOnMessageReceivedHandler( new GenericHandler ( "test.SomeSchemeRepository.seedExecute()" ) ) ;
		seedScheme.addOnBeforeUnloadingHandler( new GenericHandler ( "test.SomeSchemeRepository.seedTeardown()" ) ) ;
		
		BaseScheme cypScheme = new BaseScheme ( "Cypher" ) ;
		
		cypScheme.addOnAfterLoadingHandler( new GenericHandler ( "test.SomeSchemeRepository.cypSetup()" ) ) ;
		cypScheme.addOnMessageReceivedHandler( new GenericHandler ( "test.SomeSchemeRepository.cypStep1Execute()" ) ) ;
		cypScheme.addOnMessageReceivedScheme(seedScheme) ;
		cypScheme.addOnMessageReceivedHandler( new GenericHandler ( "test.SomeSchemeRepository.cypStep3Execute()" ) ) ;
		cypScheme.addOnBeforeUnloadingHandler( new GenericHandler ( "test.SomeSchemeRepository.cypTeardown()" ) ) ;
		
		return cypScheme ;
	}

}
