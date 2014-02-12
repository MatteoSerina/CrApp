package identity;

import utility.Utils;
import core.UIDGenerator;

public class SchemeIdentity 
{
	private String name = Utils.STR_EMPTY                   ;
	private long   uid  = UIDGenerator.getInstance().next() ;
	
	public SchemeIdentity ( String _name )
	{
		name = _name ;
	}
	
	public String getDescription ()
	{
		return name + "_" + uid ;
	}
}
