import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var NullifyReturnValue = function() {

	//Parent constructor
     Mutator.call(this);

	this.newValue = undefined;
	this.mutationPoints = [];

	this.currentIndex = 0;
	this.previousValue = undefined;

};
// Inheritance
NullifyReturnValue.prototype = Object.create(Mutator.prototype);


NullifyReturnValue.prototype.getType = function (){
	return "NullifyReturnValue";
}

NullifyReturnValue.prototype.addJp = function($joinpoint){
	if($joinpoint.instanceOf('return')) {
		if (!$joinpoint.ancestor('method').returnRef.isPrimitive) {
			this.mutationPoints.push($joinpoint);
			return true;
		}
	}

	return false;
}


/*** IMPLEMENTATION OF INSTANCE METHODS ***/
NullifyReturnValue.prototype.hasMutations = function() {
	return this.currentIndex < this.mutationPoints.length;
}

NullifyReturnValue.prototype.getMutationPoint = function() {
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

NullifyReturnValue.prototype._mutatePrivate = function() {

	var mutationPoint = this.mutationPoints[this.currentIndex];
	this.previousValue = mutationPoint.code;
	
	this.newValue = mutationPoint.insertReplace("return null");

	this.currentIndex++;

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": " + this.previousValue
			+ " to "+ this.newValue); 
	println("/*--------------------------------------*/");

}
NullifyReturnValue.prototype._restorePrivate = function() {
	// Restore operator
	println("Restore  prev: " + this.previousValue);
	println("Restore new: \n" + this.newValue);	
	this.newValue.insertReplace(this.previousValue);
	this.newValue = undefined;
}