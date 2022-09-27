import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var RemoveNullCheck = function($startingPoint) {
	//Parent constructor
     Mutator.call(this);

	this.newValue = undefined;
	this.mutationPoints = [];
	
	this.currentIndex = 0;
	this.previousValue = undefined;
};
// Inheritance
RemoveNullCheck.prototype = Object.create(Mutator.prototype);

RemoveNullCheck.prototype.getType = function (){
	return "RemoveNullCheck";
}

RemoveNullCheck.prototype.addJp = function($joinpoint){
	if ($joinpoint.instanceOf("binaryExpression") && ($joinpoint.operator === '!=' || $joinpoint.operator === '==')) {
		if ($joinpoint.rhs.type === "<nulltype>" || $joinpoint.lhs.type === "<nulltype>") {
			this.mutationPoints.push($joinpoint);
			return true;
		}
	}
	return false;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
RemoveNullCheck.prototype.hasMutations = function() {
	return this.currentIndex < this.mutationPoints.length;
}

RemoveNullCheck.prototype.getMutationPoint = function() {
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

RemoveNullCheck.prototype._mutatePrivate = function() {

	var mutationPoint = this.mutationPoints[this.currentIndex];

	this.previousValue = mutationPoint.copy();
	
	if (mutationPoint.operator === '!=') {
		mutationPoint.setOperator('==');
		this.newValue = mutationPoint;
	}
	
	else if (mutationPoint.operator === '==') {
		mutationPoint.setOperator('!=');
		this.newValue = mutationPoint;
	}	

	this.currentIndex++;

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": " + this.previousValue 
			+ " to "+ this.newValue); 
	println("/*--------------------------------------*/");

}


RemoveNullCheck.prototype._restorePrivate = function() {
	// Restore operator
	println("Restore  prev: " + this.previousValue.code);
	println("Restore new: " + this.newValue.code);	
	this.newValue.insertReplace(this.previousValue);
	this.newValue = undefined;
	this.previousValue = undefined;
}

