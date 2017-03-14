package Models.Tree;

public class OutgoingBranch {
	
	private String branchName;
	Node child;
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public Node getChild() {
		return child;
	}
	public void setChild(Node child) {
		this.child = child;
	}
}
