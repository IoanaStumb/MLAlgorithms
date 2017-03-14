package MLAlgorithms.ID3Algorithm;

import java.util.ArrayList;
import java.util.List;

import MLAlgorithms.TreeClassifierData;
import Models.Attributes.Attribute;
import Models.Attributes.DiscreteAttribute;
import Models.Instances.ClassifiedInstance;

public class DiscreteID3Classifier extends ID3Classifier {

	public DiscreteID3Classifier(TreeClassifierData inputData) {
		super(inputData);
	}

	@Override
	protected List<DiscreteAttribute> discretizeInputAttributes(List<ClassifiedInstance> instances,
			DiscreteAttribute outputAttribute, List<Attribute> inputAttributes) {
		
		List<DiscreteAttribute> discretizedAttributes = new ArrayList<>();
		for (Attribute attribute : inputAttributes) {
			discretizedAttributes.add((DiscreteAttribute) attribute);
		}
		
		return discretizedAttributes;
	}
}
