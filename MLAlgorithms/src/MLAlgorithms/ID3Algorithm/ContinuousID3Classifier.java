package MLAlgorithms.ID3Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import MLAlgorithms.TreeClassifierData;
import Models.Attributes.Attribute;
import Models.Attributes.DiscreteAttribute;
import Models.Instances.ClassifiedInstance;
import Models.Instances.InstanceValue;

public class ContinuousID3Classifier extends ID3Classifier {

	public ContinuousID3Classifier(TreeClassifierData inputData) {
		super(inputData);
	}

	@Override
	protected TreeClassifierData discretizeInputData(TreeClassifierData inputData) {

		TreeClassifierData discretizedInputData = new TreeClassifierData();

		// 1. set the output attribute - it remains the same
		discretizedInputData.setOutputAttribute(inputData.getOutputAttribute());

		// 2. discretize continuous attributes & change the instance values
		List<Attribute> discretizedAttributes = new ArrayList<>();

		for (Attribute attribute : inputData.getInputAttributes()) {
			if (attribute.shouldBeDiscretized()) {

				// 2a. get the instance values of the continuous attribute
				List<ClassifiedInstance> continuousAttributeInstances = getContinuousAttributeInstances(attribute, inputData.getTrainingInstances());

				// 2b. sort the values in descending order
				continuousAttributeInstances = sortByInstanceValue(continuousAttributeInstances);

				// 2c. create a new attribute with thresholds (instead of continuous values)
				DiscreteAttribute discreteAttribute = new DiscreteAttribute();
				List<String> attributeValues = new ArrayList<>();
				SortedMap<Double, String> thresholds = new TreeMap<>(Collections.reverseOrder());

				discreteAttribute.setName(attribute.getName());
				discreteAttribute.setType(attribute.getType());

				boolean isFirstValue = true;

				for (int i = 0; i < continuousAttributeInstances.size() - 1; i++) {

					ClassifiedInstance firstInstance = continuousAttributeInstances.get(i);
					ClassifiedInstance secondInstance = continuousAttributeInstances.get(i+1);

					// if classification of two adjacent instances is different, create a threshold from the average value
					if (!firstInstance.getClassification().equals(secondInstance.getClassification())) {

						double firstValue = Double.parseDouble(firstInstance.getValues().get(0).getValue());
						double secondValue = Double.parseDouble(secondInstance.getValues().get(0).getValue());

						double average = (firstValue + secondValue) / 2;

						if (isFirstValue) {
							String firstAttributeValue = attribute.getName() + "<" + average;
							attributeValues.add(firstAttributeValue);

							thresholds.put((average - 0.00005), firstAttributeValue);

							isFirstValue = false;
						}

						String attributeValue = attribute.getName() + ">" + average;
						attributeValues.add(attributeValue);

						thresholds.put(average, attributeValue);
					}
				}
				discreteAttribute.setValues(attributeValues);

				discretizedAttributes.add(discreteAttribute);

				// 2d. change all the instance values
				for (ClassifiedInstance instance : inputData.getTrainingInstances()) {

					instance.getValues().stream()
					.filter(i -> i.getAttributeName().equals(discreteAttribute.getName()))
					.map(i -> {
						String discretizedValue = discretizeInstance(i.getValue(), thresholds);
						i.setValue(discretizedValue);
						return i;
					})
					.collect(Collectors.toList());
				}
			}
			else {
				discretizedAttributes.add((DiscreteAttribute) attribute);
			}
		}

		discretizedInputData.setInputAttributes(discretizedAttributes);
		discretizedInputData.setTrainingInstances(inputData.getTrainingInstances());

		return discretizedInputData;
	}


	private List<ClassifiedInstance> getContinuousAttributeInstances(Attribute attribute, List<ClassifiedInstance> instances) {

		List<ClassifiedInstance> continuousAttributeInstances = new ArrayList<>();

		for (ClassifiedInstance instance : instances) {

			ClassifiedInstance currentInstance = new ClassifiedInstance();
			InstanceValue instanceValue = new InstanceValue();
			List<InstanceValue> instanceValues = new ArrayList<>();

			instanceValue.setAttributeName(attribute.getName());
			instanceValue.setValue(instance.getValueByAttribute(attribute));

			instanceValues.add(instanceValue);

			currentInstance.setValues(instanceValues);
			currentInstance.setClassification(instance.getClassification());

			continuousAttributeInstances.add(currentInstance);
		}

		return continuousAttributeInstances;
	}


	private List<ClassifiedInstance> sortByInstanceValue(List<ClassifiedInstance> instances) {

		return instances.stream()
				.sorted((i1, i2) -> Double.compare(Double.parseDouble(i1.getValues().get(0).getValue()),
						Double.parseDouble(i2.getValues().get(0).getValue())))
				.collect(Collectors.toList());
	}
	
	
	private String discretizeInstance(String value, SortedMap<Double, String> thresholds) {

		String discretizedValue = null;
		double originalValue = Double.parseDouble(value);

		for (Double thresholdValue : thresholds.keySet()) {
			if (originalValue >= thresholdValue) {
				discretizedValue = thresholds.get(thresholdValue);
				break;
			}
		}

		if (discretizedValue == null) {
			discretizedValue = thresholds.get(thresholds.lastKey());
		}

		return discretizedValue;
	}
}
