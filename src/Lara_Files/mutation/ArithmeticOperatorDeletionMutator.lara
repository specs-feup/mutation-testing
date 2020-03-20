import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var ArithmeticOperatorDeletionMutator = function() {
	//Parent constructor
     Mutator.call(this);

	this.type =  "ArithmeticOperatorDeletionMutator";

	this.newValue = undefined;
	this.mutationPoints = [];
	
	this.currentIndex = 0;
	this.previousValue = undefined;
	this.isFirst = false;
	
};

// Inheritance
ArithmeticOperatorDeletionMutator.prototype = Object.create(Mutator.prototype);

ArithmeticOperatorDeletionMutator.prototype.getType = function(){
	return this.type;
}

ArithmeticOperatorDeletionMutator.prototype.addJp = function($joinpoint){
	if($joinpoint.instanceOf("binaryExpression")) {
		this.mutationPoints.push($joinpoint);
		return true;
	}
	return false;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
ArithmeticOperatorDeletionMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.mutationPoints.length;
}

ArithmeticOperatorDeletionMutator.prototype.getMutationPoint = function() {
	if(this.isMutated){
		return this.newValue;
	}else{
		if(this.currentIndex < this.mutationPoints.length) {
		return this.mutationPoints[this.currentIndex];
		} else {
			return undefined;
		}
	}
}

ArithmeticOperatorDeletionMutator.prototype._mutatePrivate = function() {

	var mutationPoint = this.mutationPoints[this.currentIndex];

	this.previousValue = mutationPoint;

	if (this.isFirst === false) {
		var leftOperand = mutationPoint.lhs.copy();
		this.newValue = mutationPoint.insertReplace(leftOperand);
		this.isFirst = true;
	}
	else {
		var rightOperand = mutationPoint.rhs.copy();
		this.newValue = mutationPoint.insertReplace(rightOperand);
		this.isFirst = false;
		this.currentIndex++;
	}
	
	//this.newValue = copy;

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "+ this.previousValue 
		  +" to "+ this.newValue); 
	println("/*--------------------------------------*/");

}
ArithmeticOperatorDeletionMutator.prototype._restorePrivate = function() {
	// Restore operator
	println("Restore  prev: " + this.previousValue.code);
	println("Restore new: " + this.newValue.code);	
	this.newValue.insertReplace(this.previousValue);
	this.previousValue = undefined;
	this.newValue = undefined;
	//this.isFirst = false;
}