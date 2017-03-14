package Utils;

import java.util.List;

import Models.Attributes.Attribute;
import Models.Attributes.DiscreteAttribute;

public class DataInitializerTest {
	
	public static void main(String[] args) {
		
		DataInitializer di = new DataInitializer();
		List<Attribute> l = di.initializeAllAttributes("attributes.txt");
		l.forEach(a -> System.out.println(a.getName()));
		System.out.println();
		
		List<Attribute> li = di.filterInputAttributes(l);
		li.forEach(a -> System.out.println(a.getName()));
		System.out.println();
		
		DiscreteAttribute oa = di.filterOutputAttribute(l);
		System.out.println(oa.getName());
	}

}
