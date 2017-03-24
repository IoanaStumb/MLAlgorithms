package Models.Attributes;

import java.util.List;

public class DiscreteAttribute extends Attribute {

	private List<String> values;
	
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	@Override
	public boolean shouldBeDiscretized() {
		return false;
	}
	
}
