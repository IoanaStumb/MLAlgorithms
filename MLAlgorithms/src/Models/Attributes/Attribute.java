package Models.Attributes;

public abstract class Attribute {
	
	private String name; 
	private AttributeType type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public AttributeType getType() {
		return type;
	}
	public void setType(AttributeType type) {
		this.type = type;
	}
	
	public abstract boolean shouldBeChanged();
	
}
