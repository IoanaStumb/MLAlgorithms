package MLAlgorithms.ID3Algorithm;

import MLAlgorithms.TreeClassifierData;

public class DiscreteID3Classifier extends ID3Classifier {

	public DiscreteID3Classifier(TreeClassifierData inputData) {
		super(inputData);
	}

	@Override
	protected TreeClassifierData discretizeInputData(TreeClassifierData inputData) {
		return inputData;
	}
}
