package Models.Instances;

public class InstanceValue {
	
	private String attributeName; 
	private String value;
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return attributeName + ": " + value;
	}
}
