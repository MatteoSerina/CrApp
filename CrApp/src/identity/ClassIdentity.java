package identity;


import core.UIDGenerator;


public class ClassIdentity extends IIdentity
{
	private final static String UNKNOWN = "Unknown" ;
	
	private String nameSpace = UNKNOWN ;
	private String name      = UNKNOWN ;
	
	private int    mainVersion = 0 ;
	private int    subVersion  = 0 ;
	private int    release     = 0 ;
	
	private long   uid         = UIDGenerator.getInstance().next() ;
	
	public ClassIdentity () {}
	
	public ClassIdentity ( String _name )
	{
		if ( _name == null )
			return ;
		
		name = _name ;
	}
	
	public ClassIdentity ( String _name , String _nameSpace )
	{
		if ( _name == null || _nameSpace == null )
			return ;
		
		name      = _name      ;
		nameSpace = _nameSpace ;
	}
	
	public ClassIdentity ( String _name , String _nameSpace , int _mainVersion , int _subVersion , int _release )
	{
		if ( _name == null || _nameSpace == null )
			return ;
		
		name        = _name        ;
		nameSpace   = _nameSpace   ;
		mainVersion = _mainVersion ;
		subVersion  = _subVersion  ;
		release     = _release     ;
	}
	
	public long getUID ()
	{
		return uid ;
	}
	
	public String getName () 
	{
		return name ;
	}
	
	public String getNameSpace ()
	{
		return nameSpace ;
	}
	
	public String getVersion ()
	{
		return mainVersion + "." + subVersion + "." + release ;
 	}
	
	public int getMainVersion ()
	{
		return mainVersion ;
	}
	
	public int getSubversion ()
	{
		return subVersion ;
	}
	
	public int getRelease ()
	{
		return release ;
	}
	
	public String getDescriptor ()
	{
		return nameSpace + "." + name + "  V. " + this.getVersion() ;
	}
}
