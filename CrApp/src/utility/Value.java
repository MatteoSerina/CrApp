package utility;

public class Value <T>
{
	private T innerValue ;

	
	public Value ( T _value )
	{
		innerValue = _value ;
	}
	
	public T read ()
	{
		return   innerValue ;
	}
}
