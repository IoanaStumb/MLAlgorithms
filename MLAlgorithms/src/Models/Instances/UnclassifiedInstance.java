package Models.Instances;

import java.util.List;

import Models.Attributes.Attribute;

public class UnclassifiedInstance {

	private List<InstanceValue> values;
	
	public List<InstanceValue> getValues() {
		return values;
	}
	public void setValues(List<InstanceValue> values) {
		this.values = values;
	}
	
	//TODO: create equals for attributes, in order to check attr1.equals(attr2)
	//TODO: to test!
	public boolean hasAttribute(Attribute attribute) {
		return this.values.stream()
				.anyMatch(value -> value.getAttributeName().equals(attribute.getName()));
	}
	
	public boolean hasAttributeValue(String attributeValue) {
		
		return this.values.stream()
				.anyMatch(value -> value.getValue().equals(attributeValue));
	}
	
	//TODO: create equals for attributes, in order to check attr1.equals(attr2)
	//TODO: to test!
	public String getValueByAttribute(Attribute attribute) {
		
		return this.values.stream()
				.filter(value -> value.getAttributeName().equals(attribute.getName()))
				.findFirst()
				.get()
				.getValue();
	}

}
