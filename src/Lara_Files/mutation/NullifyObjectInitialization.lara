import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var NullifyObjectInitialization = function($startingPoint) {
//Parent constructor
     Mutator.call(this);

	this.newValue = undefined;
	this.mutationPoints = [];
	
	this.currentIndex = 0;
	this.previousValue = undefined;

};
// Inheritance
NullifyObjectInitialization.prototype = Object.create(Mutator.prototype);

NullifyObjectInitialization.prototype.getType = function (){
	return "NullifyObjectInitialization";
}

NullifyObjectInitialization.prototype.addJp = function($joinpoint){
	if($joinpoint.instanceOf('new')) {
		this.mutationPoints.push($joinpoint);
		return true;
	}

	return false;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
NullifyObjectInitialization.prototype.hasMutations = function() {
	return this.currentIndex < this.mutationPoints.length;
}

NullifyObjectInitialization.prototype.getMutationPoint = function() {
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

NullifyObjectInitialization.prototype._mutatePrivate = function() {

	var mutationPoint = this.mutationPoints[this.currentIndex];
	this.previousValue = mutationPoint.code;
	
	this.newValue = mutationPoint.insertReplace("null");

	this.currentIndex++;

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": " + this.previousValue
			+ " to "+ this.newValue); 
	println("/*--------------------------------------*/");

}
NullifyObjectInitialization.prototype._restorePrivate = function() {
	// Restore operator
	println("Restore  prev: " + this.previousValue);
	println("Restore new: " + this.newValue.parent);	
	this.newValue.insertReplace(this.previousValue);
	this.newValue = undefined;
}