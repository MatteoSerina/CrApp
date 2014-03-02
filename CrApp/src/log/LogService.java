package log;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import utility.Configuration;
import utility.Utils;
import utility.Value;


public class LogService 
{
	private static HashMap <String,Logger> loggers = new HashMap <String,Logger> () ;
	
	public static Logger getLogger ( Object _caller ) 
	{
		return LogService.getLogger( _caller.getClass() ) ;
	}
	
	public static Logger getLogger ( Class _class ) 
	{
		if ( !Configuration.isLogActive )
		{
			Logger voidLogger = Logger.getLogger( "void" ) ;
			voidLogger.setLevel( Level.OFF ) ;
			
			return voidLogger ;
		}
		
		String className = _class.getName();
		Logger logger = (Logger) loggers.get ( className ) ;
		
		if (logger == null) 
		{
			synchronized ( LogService.class ) 
			{
				logger = (Logger) loggers.get(className) ;
				
				if (logger == null) 
				{
					logger = Logger.getLogger(className) ;
					logger.setUseParentHandlers (false) ;
					logger.setLevel( Level.FINEST ) ;
					
					ConsoleHandler consoleHandler = new ConsoleHandler()  ;
					consoleHandler.setFormatter ( new SimpleFormatter() ) ;
					consoleHandler.setLevel ( Configuration.loggingLevel ); ;
					
					if ( logger.getHandlers().length == 0 )
						 logger.addHandler ( consoleHandler ) ;
					
					if ( Configuration.logsPath != null && Configuration.logsPath != Utils.STR_EMPTY )
					{
						try
						{
							FileHandler fileHandler = new FileHandler( Configuration.logsPath, true ) ;		
							fileHandler.setFormatter (  new XMLFormatter()  ) ;
							fileHandler.setLevel( Level.FINEST ) ;
							
							if ( logger.getHandlers().length == 1 )
								 logger.addHandler ( fileHandler ) ;
						}
						catch ( Exception err )
						{
							logger.warning(  Utils.parseException ( err )  ) ;
						}
					}
					
					loggers.put ( className , logger ) ;
				}
			}
		}
		
		return logger;
	}
	
	
}
