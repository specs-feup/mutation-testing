import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var NullifyInputVariable = function() {
	//Parent constructor
	Mutator.call(this, "NullifyInputVariable");

	this.newValue = undefined;
	this.mutationPoints = [];


	this.currentIndex = 0;
	this.previousValue = undefined;
	this.currentMutation = undefined;

};

// Inheritance
NullifyInputVariable.prototype = Object.create(Mutator.prototype);

NullifyInputVariable.prototype.getType = function (){
	return "NullifyInputVariable";
}

NullifyInputVariable.prototype.addJp = function($joinpoint){
	if($joinpoint.instanceOf('method')) {
		for(var $param of $joinpoint.params) {
			if (!$param.isPrimitive) {
				this.mutationPoints.push($joinpoint);
				return true;
			}
		}
	}
	return false;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
NullifyInputVariable.prototype.hasMutations = function() {
	return this.currentIndex < this.mutationPoints.length;
}

NullifyInputVariable.prototype.getMutationPoint = function() {
	// If mutation is currently occuring, return mutation point
	if(this.previousValue !== undefined) {
		return this.previousValue;
	}

	// Return next mutation point
	if(this.currentIndex < this.mutationPoints.length) {
		//println("JP!! -> " + this.mutationPoints[this.currentIndex] );
		//println("JPCode!! -> " + this.mutationPoints[this.currentIndex].code ); //FIXME: Aqui imprime
		return this.mutationPoints[this.currentIndex];
	}
	
	return undefined;
}

NullifyInputVariable.prototype.getCurrentMutation = function() {
	return this.currentMutation;
} 

NullifyInputVariable.prototype._mutatePrivate = function() {
	//this.previousValue = this.getMutationPoint().code;
	//this.currentMutation = this.getMutationPoint();
	
	// Get mutation point
	var mutationPoint = this.getMutationPoint();
	// Increment index
	this.currentIndex++;

	
	//println("MUTATION POINT: " + mutationPoint);
	this.previousValue = mutationPoint; //FIXME: Aqui dá erro
	
	this.currentMutation = mutationPoint.copy();
	//println("Current mutation: " + this.currentMutation);
	
	for (var $param of this.currentMutation.params) {
		if (!$param.isPrimitive) {
			this.currentMutation.body.insertBegin(%{[[$param.name]] = null}%);
		}
	}

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "
			+ " to "+ this.currentMutation);
	println("/*--------------------------------------*/");
}


NullifyInputVariable.prototype._restorePrivate = function() {
	// Restore operator
	//println("Restore  prev: " + this.previousValue);
	//println("Restore new: " + this.currentMutation.code);
	//this.getMutationPoint().insertReplace(this.previousValue);
	this.currentMutation.insertReplace(this.previousValue);
	this.currentMutation = undefined;
	//this.currentIndex++;
}