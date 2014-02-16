package message;

import utility.Utils;


public class Message
{
	private final static String EMPTY = "Empty Plain Message" ;
	
	private String  name       = Utils.STR_EMPTY ;
	private byte [] data       = new byte [0]    ;
	
	private boolean isEncoded  = false           ;
	private boolean isBlank    = false           ;
	

	public Message ()
	{
		name = EMPTY ;
		isBlank = true ;
	}
	
	public Message ( byte [] _data )
	{
		data = _data ;
	}
	
	public Message ( byte [] _data , boolean _isEncoded )
	{
		data      = _data      ;
		isEncoded = _isEncoded ; 
	}
	
	public Message ( byte [] _data , String _name )
	{
		data = _data ;
		name = _name ;
	}
	
	public Message ( byte [] _data , boolean _isEncoded, String _name )
	{
		data      = _data      ;
		isEncoded = _isEncoded ;
		name      = _name      ;
	}
	
	public String getName()
	{
		return name ;
	}
	
//	public void setData ( byte[] _data )
//	{
//		byte [] clonedData = new byte [ _data.length ] ;
//		
//		System.arraycopy ( _data , 0 , clonedData , 0 , _data.length ) ;
//		
//		data = clonedData ;
//	}
	
	public byte[] getData ()
	{
		byte [] clonedData = new byte [ data.length ] ;		
		System.arraycopy ( data , 0 , clonedData , 0 , data.length ) ;
				
		return clonedData ;
	}
	
	public boolean isEncoded ()
	{
		return isEncoded ;
	}
	
}
