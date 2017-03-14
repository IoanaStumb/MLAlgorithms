package Models.Instances;

public class ReclassifiedInstance extends ClassifiedInstance {
	
	private String reclassification;
	private boolean isReclassifiedCorrectly;
	
	public String getReclassification() {
		return reclassification;
	}
	public void setReclassification(String reclassification) {
		this.reclassification = reclassification;
	}
	
	public boolean isReclassifiedCorrectly() {
		return isReclassifiedCorrectly;
	}
	public void setReclassifiedCorrectly(boolean isReclassifiedCorrectly) {
		this.isReclassifiedCorrectly = isReclassifiedCorrectly;
	}

}
