package MLAlgorithms;

import java.util.List;

import Models.Attributes.Attribute;
import Models.Attributes.DiscreteAttribute;
import Models.Instances.ClassifiedInstance;

public class TreeClassifierData {
	
	private List<Attribute> inputAttributes;
	private DiscreteAttribute outputAttribute;
	
	private List<ClassifiedInstance> trainingInstances;
	

	public List<Attribute> getInputAttributes() {
		return inputAttributes;
	}
	public void setInputAttributes(List<Attribute> inputAttributes) {
		this.inputAttributes = inputAttributes;
	}
	
	public DiscreteAttribute getOutputAttribute() {
		return outputAttribute;
	}
	public void setOutputAttribute(DiscreteAttribute outputAttribute) {
		this.outputAttribute = outputAttribute;
	}

	public List<ClassifiedInstance> getTrainingInstances() {
		return trainingInstances;
	}
	public void setTrainingInstances(List<ClassifiedInstance> trainingInstances) {
		this.trainingInstances = trainingInstances;
	}
	
}
