package Main;

import java.util.List;

import MLAlgorithms.TreeClassifierData;
import MLAlgorithms.ID3Algorithm.DiscreteID3Classifier;
import MLAlgorithms.ID3Algorithm.ID3Classifier;
import Models.Attributes.Attribute;
import Models.Attributes.DiscreteAttribute;
import Models.Instances.ClassifiedInstance;
import Models.Tree.Node;
import Utils.DataInitializer;

public class LearningAlgorithm {
	
	public static void main (String[] args) { 
		
		DataInitializer initializer = new DataInitializer();
		
		List<Attribute> allAttributes = initializer.initializeAllAttributes("attributes.txt");
		List<Attribute> inputAttributes = initializer.filterInputAttributes(allAttributes);
		DiscreteAttribute outputAttribute = initializer.filterOutputAttribute(allAttributes);
		
		allAttributes.forEach(attr -> System.out.println(attr.getName()));
		System.out.println("-----------------");
		inputAttributes.forEach(attr -> System.out.println(attr.getName()));
		System.out.println("-----------------");
		System.out.println(outputAttribute.getName());
		System.out.println("-----------------");
		System.out.print("\n\n");
		
		List<ClassifiedInstance> trainingInstances = initializer.initializeClassifiedInstances("training.txt", allAttributes);
		
		TreeClassifierData inputData = new TreeClassifierData();
		inputData.setInputAttributes(inputAttributes);
		inputData.setOutputAttribute(outputAttribute);
		inputData.setTrainingInstances(trainingInstances);
		
		System.out.println("--------START ALGORITHM---------");
		ID3Classifier id3 = new DiscreteID3Classifier(inputData);
		Node tree = id3.learn();
		System.out.print("\n\n");
		
		System.out.println("--------ID3 TREE---------");
		tree.print();
	}
}
