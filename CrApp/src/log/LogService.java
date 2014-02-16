package log;

public class LogService 
{

	public static void logMessage ( String _message , int _ciricity )
	{
		System.out.println( "Level : " +  _ciricity + "\n\t o) " + _message + "\n" );
	}

}
