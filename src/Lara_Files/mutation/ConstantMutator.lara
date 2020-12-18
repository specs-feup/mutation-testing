import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var ConstantMutator = function($expr, $startingPoint) {
	//Parent constructor
     Mutator.call(this);

	checkDefined($expr, "ConstantMutator");

	if($startingPoint === undefined) {
		$startingPoint = WeaverJps.root();
	}

	this.$expr = $expr;
	//ConstantMutator._parseNewValue($expr);
	
	this.newValue = undefined;
	
	this.mutationPoints = [];

	this.currentIndex = 0;
	this.previousValue = undefined;

};

// Inheritance
ConstantMutator.prototype = Object.create(Mutator.prototype);

ConstantMutator.prototype.getType = function(){
	return "ConstantMutator";
}

ConstantMutator.prototype.addJp = function($joinpoint){

	if($joinpoint.instanceOf('field') || $joinpoint.instanceOf( 'localVariable')) {
		if($joinpoint.init === undefined || !$joinpoint.isFinal || !ConstantMutator._isCompatible($joinpoint.type, this.$expr.type)) {
			return false;
		}
		this.mutationPoints.push($joinpoint);

		return true;
	}

	if($joinpoint.instanceOf('assignment')){
		if(!$joinpoint.isFinal || !ConstantMutator._isCompatible($joinpoint.type, this.$expr.type)) {
			return false;
		}

		this.mutationPoints.push($joinpoint);

		return true;
	}

	return false;
}

/**ConstantMutator._parseNewValue = function(newValue) {
	if(Weaver.isJoinPoint(newValue) && newValue.instanceOf("expression")) {
		return newValue;
	}else if(isFunction(newValue)){
		var value = KadabraNodes.literal($expr.toString(),$expr.type);
		Number(newValue.code) + 1; 
		println("New value:"+ this.$expr.code);
		return this.$expr;
	}
	throw "ConstantMutator: input newValue not supported: " + newValue;
}*/

ConstantMutator._isCompatible = function(type1, type2) {
	return true;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
ConstantMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.mutationPoints.length;
}

ConstantMutator.prototype.getMutationPoint = function() {
	if(this.isMutated){
		println("Potato	" + this.newValue);
		return this.newValue;
	}else{
		if(this.currentIndex < this.mutationPoints.length) {
			println("Potato1");
			return this.mutationPoints[this.currentIndex];
		} else {
			println("Potato2");
			return undefined;
		}
	}
}

ConstantMutator.prototype._mutatePrivate = function() {

	var mutationPoint = this.mutationPoints[this.currentIndex];
	
	if(mutationPoint.instanceOf('field') || mutationPoint.instanceOf('localVariable')) {
		this.previousValue = mutationPoint.init;
	} else if(mutationPoint.instanceOf('assignment')) {
		this.previousValue = mutationPoint.rhs;
	}

	this.currentIndex++;
	
	if(isFunction(this.$expr)){
			var tem= this.$expr(this.previousValue);
			this.newValue = this.previousValue.insertReplace(tem);
	}else{
			this.newValue = this.previousValue.insertReplace(this.$expr);
			//this.newValue = this.$expr;
	}
	
	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "+ this.previousValue 
		  +" to "+ this.newValue); 
	println("/*--------------------------------------*/");
	
}

ConstantMutator.prototype._restorePrivate = function() {
	// Restore operator
	this.newValue.insertReplace(this.previousValue);
	this.previousValue = undefined;
	this.newValue = undefined;
}