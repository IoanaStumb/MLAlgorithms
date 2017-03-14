package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Models.Attributes.Attribute;
import Models.Attributes.AttributeType;
import Models.Attributes.ContinuousAttribute;
import Models.Attributes.DiscreteAttribute;
import Models.Instances.ClassifiedInstance;
import Models.Instances.InstanceValue;
import Models.Instances.UnclassifiedInstance;

public class DataInitializer {
	
	private AttributeType mapAttributeType(String type) {
		
		if (type.equalsIgnoreCase("input")) {
			return AttributeType.INPUT;
		}
		else if (type.equalsIgnoreCase("output")){
			return AttributeType.OUTPUT;
		}
		else return null;
	}
	
	public List<Attribute> initializeAllAttributes(String filePath) {
		
		List<Attribute> attributes = new ArrayList<>();
		
		try (Scanner fileScanner = new Scanner(new File(filePath))) {
			while (fileScanner.hasNextLine()) {
				
				String[] line = fileScanner.nextLine().split(",");
				
				if (line[2].equalsIgnoreCase("discrete")) {
					DiscreteAttribute discreteAttribute = new DiscreteAttribute();
					discreteAttribute.setName(line[0]);
					discreteAttribute.setType(mapAttributeType(line[1]));
						
					line = Arrays.copyOfRange(line, 3, line.length);
					discreteAttribute.setValues(Arrays.asList(line));
						
					attributes.add(discreteAttribute);
				}
				else if (line[2].equalsIgnoreCase("continuous")) {
					ContinuousAttribute continuousAttribute = new ContinuousAttribute();
					continuousAttribute.setName(line[0]);
					continuousAttribute.setType(mapAttributeType(line[1]));
						
					attributes.add(continuousAttribute);
				}
				else {
					System.out.println("Attention! Attribute \"" + line[0] + "\" can only be discrete or continuous.");
					throw new Exception();
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File " + filePath + " cannot be found.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Please re-check the attributes.");
			e.printStackTrace();
		}
		
		return attributes;
	}

	public List<Attribute> filterInputAttributes(List<Attribute> allAttributes) {
		
		List<Attribute> inputAttributes = allAttributes.stream()
				.filter(attribute -> attribute.getType().equals(AttributeType.INPUT))
				.collect(Collectors.toList());
		
		return inputAttributes;
	}
	
	public DiscreteAttribute filterOutputAttribute(List<Attribute> allAttributes) {
		
		Attribute outputAttribute = allAttributes.stream()
				.filter(attribute -> attribute.getType().equals(AttributeType.OUTPUT))
				.findFirst()
				.get();
		
		return (DiscreteAttribute) outputAttribute;
	}
	
	private List<InstanceValue> buildInstanceValues(String[] line, List<Attribute> attributes) {
		
		List<InstanceValue> instanceValues = new ArrayList<>();
		
		int attributePosition = 0;
		for (String attributeValue : line) {
			InstanceValue instanceValue = new InstanceValue();
			
			instanceValue.setAttributeName(attributes.get(attributePosition).getName());
			instanceValue.setValue(attributeValue);
			
			instanceValues.add(instanceValue);
			
			attributePosition++;
		}
		
		return instanceValues;
	}
	
	public List<ClassifiedInstance> initializeClassifiedInstances(String filePath, List<Attribute> attributes) {
		
		List<ClassifiedInstance> classifiedInstances = new ArrayList<>();
		
		try (Scanner fileScanner = new Scanner(new File(filePath))) {
			while (fileScanner.hasNextLine()) {
				
				String[] line = fileScanner.nextLine().split(",");
				
				ClassifiedInstance currentInstance = new ClassifiedInstance();
				currentInstance.setValues(buildInstanceValues(line, attributes));
				currentInstance.setClassification(line[line.length - 1]);
				
				classifiedInstances.add(currentInstance);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File " + filePath + " could not be found.");
			e.printStackTrace();
		}
		
		return classifiedInstances;
	}
	
	public List<UnclassifiedInstance> initializeUnclassifiedInstances(String filePath, List<Attribute> attributes) {
		
		List<UnclassifiedInstance> unclassifiedInstances = new ArrayList<>();
		
		try (Scanner fileScanner = new Scanner(new File(filePath))) {
			while (fileScanner.hasNextLine()) {
				
				String[] line = fileScanner.nextLine().split(",");
				
				UnclassifiedInstance currentInstance = new UnclassifiedInstance();
				currentInstance.setValues(buildInstanceValues(line, attributes));
				
				unclassifiedInstances.add(currentInstance);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File " + filePath + " could not be found.");
			e.printStackTrace();
		}
		
		return unclassifiedInstances;
	}
}
