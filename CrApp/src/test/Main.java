package test;

import message.Message;
import message.MessageStack;
import schemes.BaseScheme;
import utility.MemoryBlock;

public class Main 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try
		{
			test () ;
		}
		catch ( Exception err )
		{
			printException ( err ) ;
		}
		
		try
		{
			fixedTest () ;
		}
		catch ( Exception err )
		{
			printException ( err ) ;
		}
		
		System.out.println( "Operation Done");

	}
	
	private static void test ( ) throws Exception
	{
		byte[] data = new byte [] { 65 , 66 , 67 , 68 } ;
		
		MessageStack stack = new MessageStack () ;
		Message msg = new Message ( data , false , "dummyMsg" ) ;
		
		stack.addMessage( msg ) ;
		
		SomeSchemeRepository repo = new SomeSchemeRepository () ;
		BaseScheme scheme = repo.getAlgorithmScheme() ;
		
		scheme.load( new Object() ,  new MemoryBlock() ) ;
		
		System.out.println( "\n\tSrc Msg Name : " + stack.readMessage().getName()  + "   Src Msg Val : " +  new String ( stack.readMessage().getData() ) + "\n" );
		
		scheme.processMessage( stack ) ;
		
		System.out.println( "\n\tDst Msg Name : " + stack.readMessage().getName()  + "   Dst Msg Val : " +  new String ( stack.readMessage().getData() ) + "\n" );
		
		scheme.unload() ;
	}
	
	private static void fixedTest ( ) throws Exception
	{
		byte[] data = new byte [] { 65 , 66 , 67 , 68 } ;
		MessageStack stack = new MessageStack () ;
		Message msg = new Message ( data , false , "dummyMsg" ) ;
		SomeSchemeRepository repo = new SomeSchemeRepository () ;
		BaseScheme scheme = repo.getAlgorithmScheme() ;
		
		stack.addMessage( msg ) ;
		
		Fix.applyFix( scheme ) ;
		
		System.out.println( "\n\nTry again with fix ^^\n\n");
		
		scheme.load( new Object() ,  new MemoryBlock() ) ;
		
		System.out.println( "\n\tSrc Msg Name : " + stack.readMessage().getName()  + "   Src Msg Val : " +  new String ( stack.readMessage().getData() ) + "\n" );
		
		scheme.processMessage( stack ) ;
		
		System.out.println( "\n\tDst Msg Name : " + stack.readMessage().getName()  + "   Dst Msg Val : " +  new String ( stack.readMessage().getData() ) + "\n" );
		
		scheme.unload() ;
	}
	
	private static void printException ( Throwable exc )
	{
		System.out.println();
		System.out.println();
		
		printRecursive (  exc ) ;
		
		System.out.println();
		System.out.println();
	}
	
	private static void printRecursive ( Throwable exc )
	{
		if ( exc == null )
			return ;
		
		printRecursive ( exc.getCause() ) ;
		
		System.out.println( exc.toString() );
		
		for ( StackTraceElement elem : exc.getStackTrace())
			System.out.println( elem.toString() );

	}

}
