package Models.Attributes;

public class ContinuousAttribute extends Attribute {

	// ContinuousAttributes are field-wise the same as Attributes. 
	// They need to be discretized. 
	// TODO: override other various methods (e.g. toString()). 
	
	@Override
	public boolean shouldBeDiscretized() {
		return true;
	}
	


}
