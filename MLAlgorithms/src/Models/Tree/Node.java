package Models.Tree;

import java.util.ArrayList;
import java.util.List;

import Models.Attributes.Attribute;

public class Node {
	
	private String label; // if leaf node => classification; else => attribute name
	private Attribute attribute; // NULL if leaf node
	private String incomingBranch;
	private List<OutgoingBranch> outgoingBranches;
	
	public Node() {
		this.outgoingBranches = new ArrayList<>();
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	public String getIncomingBranch() {
		return incomingBranch;
	}
	public void setIncomingBranch(String incomingBranch) {
		this.incomingBranch = incomingBranch;
	}
	
	public List<OutgoingBranch> getOutgoingBranches() {
		return outgoingBranches;
	}
	public void setOutgoingBranches(List<OutgoingBranch> outgoingBranches) {
		this.outgoingBranches = outgoingBranches;
	}

	public void setNodeAsLeaf(String classification) {
		this.label = classification;
	}
	
	public void setNodeAsInternal(Attribute attribute) {
		this.attribute = attribute;
		this.label = attribute.getName();
	}
	
	//TODO: to test!
	public boolean hasBranch (String branchName) {
		return this.outgoingBranches.stream()
				.anyMatch(branch -> branch.getBranchName().equals(branchName));
	}
	
	//TODO: to test!
	public void addBranch(String branchName, Node node) {
		node.incomingBranch = branchName;
		
		OutgoingBranch outgoingBranch = new OutgoingBranch();
		outgoingBranch.setBranchName(branchName);
		outgoingBranch.setChild(node);
		
		this.outgoingBranches.add(outgoingBranch);
	}
	
	//TODO: to test!
	public Node getChildByBranch(String branchName) {
		
		return this.outgoingBranches.stream()
				.filter(branch -> branch.getBranchName().equals(branchName))
				.findFirst()
				.get()
				.getChild();
	}
	
	//TODO: to implement!
	private void print(String indentation, boolean isTail) {
		System.out.println(indentation + (isTail ? "'-- " : "|-- ") + (this.getIncomingBranch() != null ? this.getIncomingBranch() + " --> " : "") + this.getLabel());
		
		for (OutgoingBranch branch : this.outgoingBranches) {
			branch.getChild().print(indentation + (isTail ? "    " : "|         "), false);
		}
	}
	
	public void print() {
		print("", true);
	}

}
