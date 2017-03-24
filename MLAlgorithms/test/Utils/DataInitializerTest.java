package Utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import Models.Attributes.Attribute;
import Models.Attributes.AttributeType;
import Models.Attributes.DiscreteAttribute;

public class DataInitializerTest {
	
	private boolean attributeListContainsOutputAttribute(List<Attribute> attributes) {
		
		return attributes.stream().anyMatch(attribute -> attribute.getType().equals(AttributeType.OUTPUT));
	}
	
	@Test
	public void inputAttributesShouldBeFiltered() {
		
		DataInitializer initializer = new DataInitializer();
		List<Attribute> attributes = initializer.initializeAllAttributes("attributes.txt");
		
		boolean outputAttribute = attributeListContainsOutputAttribute(initializer.filterInputAttributes(attributes));
		
		assertFalse("Attribute list contains an output attribute!", outputAttribute);
		
	}
	
	@Test
	public void outputAttributeShouldNotBeNull() {
		
		DataInitializer initializer = new DataInitializer();
		List<Attribute> attributes = initializer.initializeAllAttributes("attributes.txt");
		
		DiscreteAttribute outputAttribute = initializer.filterOutputAttribute(attributes);
		
		assertNotNull("Output attribute is null!", outputAttribute);
		
	}
}
