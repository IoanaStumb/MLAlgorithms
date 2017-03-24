package MLAlgorithms.ID3Algorithm;

import java.util.ArrayList;
import java.util.List;

import MLAlgorithms.TreeClassifier;
import MLAlgorithms.TreeClassifierData;
import Models.Attributes.Attribute;
import Models.Attributes.DiscreteAttribute;
import Models.Instances.ClassifiedInstance;
import Models.Tree.Node;

public abstract class ID3Classifier implements TreeClassifier {

	private Node root;
	private TreeClassifierData inputData;
	
	private String firstClassification, secondClassification;
	
	public ID3Classifier(TreeClassifierData inputData) {
		this.inputData = inputData;
		firstClassification = inputData.getOutputAttribute().getValues().get(0);
		secondClassification = inputData.getOutputAttribute().getValues().get(1);
	}

	@Override
	public Node learn() {
		
		// check input data and, if any data is continuous, discretize it
		TreeClassifierData discretizedInputData = discretizeInputData(inputData);
		
		List<DiscreteAttribute> inputAttributes = mapDiscreteAttributes(discretizedInputData.getInputAttributes());
		
		root = runID3Algorithm(discretizedInputData.getTrainingInstances(), inputData.getOutputAttribute(), inputAttributes);
		return root;
	}
	
	protected abstract TreeClassifierData discretizeInputData(TreeClassifierData inputData);
	
	private List<DiscreteAttribute> mapDiscreteAttributes(List<Attribute> inputAttributes) {
		
		List<DiscreteAttribute> discreteAttributes = new ArrayList<>();
		
		for (Attribute attribute : inputAttributes) {
			discreteAttributes.add((DiscreteAttribute) attribute);
		}
		
		return discreteAttributes;
	}
	
	private int countClassifiedInstances(String classificationValue, List<ClassifiedInstance> classifiedInstances) {
		
		int classificationCount = 0; 
		for (ClassifiedInstance instance : classifiedInstances) {
			if (instance.getClassification().equals(classificationValue)) {
				classificationCount++;
			}
		}
		return classificationCount;
	}
	
	private String areAllInstancesClassifiedIdentical(List<ClassifiedInstance> classifiedInstances) {
		
		if (countClassifiedInstances(firstClassification, classifiedInstances) == classifiedInstances.size())
			return firstClassification;
		else if (countClassifiedInstances(secondClassification, classifiedInstances) == classifiedInstances.size())
			return secondClassification;
		else return null;
	}
	
	private String mostCommonClassification(List<ClassifiedInstance> classifiedInstances) {
		
		if (countClassifiedInstances(firstClassification, classifiedInstances) >= countClassifiedInstances(secondClassification, classifiedInstances))
			return firstClassification;
		else return secondClassification;
	}
	
	private List<ClassifiedInstance> getPartitionedInstancesAfterCondition(String attributeValue, List<ClassifiedInstance> instances) {
		
		List<ClassifiedInstance> partitionedInstances = new ArrayList<>();
		
		for (ClassifiedInstance instance : instances) {
			if (instance.hasAttributeValue(attributeValue)) {
				partitionedInstances.add(instance);
			}
		}
		return partitionedInstances;
	}
	
	private double log2(double x) {
		return Math.log(x) / Math.log(2);
	}
	
	private double calculateEntropy(int firstClassificationCount, int secondClassificationCount) {
		
		if (firstClassificationCount == 0 || secondClassificationCount == 0) return 0;
		
		else if (firstClassificationCount == secondClassificationCount) return 1;
		
		else {
			int numberOfInstances = firstClassificationCount + secondClassificationCount;
			double firstProbability = (double) firstClassificationCount / numberOfInstances;
			double secondProbability = (double) secondClassificationCount / numberOfInstances;
			
			double first = firstProbability * log2(firstProbability);
			double second = secondProbability * log2(secondProbability);
			
			return - (first + second);
		}
	}
	
	private double calculateEntropy(List<ClassifiedInstance> instances) {
	
		int firstClassificationCount = countClassifiedInstances(firstClassification, instances);
		int secondClassificationCount = countClassifiedInstances(secondClassification, instances);
		
		return calculateEntropy(firstClassificationCount, secondClassificationCount);
	}
	
	private double calculateInformationGain(List<ClassifiedInstance> instances, DiscreteAttribute attribute) {
		
		//Gain(S,A) = Entropy(S) - foreach value of A: |Sv| / S * Entropy(Sv).
		
		double entropyOfInstances = calculateEntropy(instances);
		
		double conditionalSum = 0;
		
		for (String attributeValue : attribute.getValues()) {
			
			List<ClassifiedInstance> partitionedInstances = getPartitionedInstancesAfterCondition(attributeValue, instances);
			double probability = (double) partitionedInstances.size() / instances.size();
		
			double entropyOfPartitionedInstances = calculateEntropy(partitionedInstances);
			
			double probabilityProduct = probability * entropyOfPartitionedInstances;
			conditionalSum += probabilityProduct;
		}
		
		return entropyOfInstances - conditionalSum;
	}
	
	private DiscreteAttribute findBestAttribute(List<ClassifiedInstance> instances, List<DiscreteAttribute> attributes) {
		
		double informationGain, bestInformationGain = -5000;
		DiscreteAttribute bestAttribute = new DiscreteAttribute();
		
		for (DiscreteAttribute attribute : attributes) {
			informationGain = calculateInformationGain(instances, attribute);
			
			if (informationGain >= bestInformationGain) {
				bestInformationGain = informationGain;
				bestAttribute = attribute;
			}
		}
		return bestAttribute;
	}
	
	private Node runID3Algorithm(List<ClassifiedInstance> instances, DiscreteAttribute outputAttribute, List<DiscreteAttribute> inputAttributes) {
		
		// 1. create new root node
		Node root = new Node();
		String generalClassification;
		
		// 2. if all instances are classified identical, return the single-node tree Root, with the label = classification
		generalClassification = areAllInstancesClassifiedIdentical(instances);
		if (generalClassification != null) {
			System.out.println("All training instances are classified as \"" + generalClassification + "\".\nEvery other instance will be classified as such.");
			root.setNodeAsLeaf(generalClassification);
		}
		
		// 3. if there are no attributes, return the single-node tree Root, with the label = most common classification
		else if (inputAttributes.isEmpty()) {
			generalClassification = mostCommonClassification(instances);
			System.out.println("There are no attributes to be tested. All instances will be classified as \"" + generalClassification + "\" (the most common).\nEvery other instance will be classified as such.");
			root.setNodeAsLeaf(generalClassification);
		}
		
		// else, start searching for best attributes
		else {
			DiscreteAttribute bestAttribute = findBestAttribute(instances, inputAttributes);
			System.out.println("--- " + bestAttribute.getName());
			root.setNodeAsInternal(bestAttribute);
			
			for (String attributeValue : bestAttribute.getValues()) {
				System.out.println("------ " + attributeValue);
				
				List<ClassifiedInstance> partitionedInstances = getPartitionedInstancesAfterCondition(attributeValue, instances);
				
				// 4. if there are no attributes after partitioning, add a branch with a leaf node with label = most common classification
				if (partitionedInstances.isEmpty()) {
					generalClassification = mostCommonClassification(partitionedInstances);
				
					System.out.println("At this point, training instances are all classified as \"" + generalClassification + "\".\nAdding a leaf node.");
					Node leafNode = new Node();
					leafNode.setNodeAsLeaf(generalClassification);
					root.addBranch(attributeValue, leafNode);					
				}
				
				// 5. else, run the ID3 algorithm again for the attributeValue
				else {
					inputAttributes.remove(bestAttribute);
					root.addBranch(attributeValue, runID3Algorithm(partitionedInstances,outputAttribute,inputAttributes));
					// inputAttributes.add(bestAttribute);
				}
			}
		}
		return root;
	}
}
