package core;

public class ProgramInfo 
{

	public final static String PROGRAM_NAME = "CrApp" ;
	public final static double VERSION      = 0.01    ;
	public final static String INFO         = "Test verion." ;
	
	public static String getInfo ()
	{
		return PROGRAM_NAME + " version : " + VERSION + " , " + INFO ;
	}
}