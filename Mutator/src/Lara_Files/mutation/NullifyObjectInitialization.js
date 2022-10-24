laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

class NullifyObjectInitialization extends Mutator {

	constructor($startingPoint) {
		//Parent constructor
		super("NullifyObjectInitialization")

		this.newValue = undefined;
		this.mutationPoints = [];

		this.currentIndex = 0;
		this.previousValue = undefined;

	}


	addJp($joinpoint) {
		if ($joinpoint.instanceOf('new')) {
			this.mutationPoints.push($joinpoint);
			return true;
		}

		return false;
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/
	hasMutations() {
		return this.currentIndex < this.mutationPoints.length;
	}

	getMutationPoint() {
		if (this.isMutated) {
			return this.newValue;
		} else {
			if (this.currentIndex < this.mutationPoints.length) {
				return this.mutationPoints[this.currentIndex];
			} else {
				return undefined;
			}
		}
	}

	_mutatePrivate() {

		var mutationPoint = this.mutationPoints[this.currentIndex];
		this.previousValue = mutationPoint.code;

		this.newValue = mutationPoint.insertReplace("null");

		this.currentIndex++;

		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": " + this.previousValue
			+ " to " + this.newValue);
		println("/*--------------------------------------*/");

	}
	_restorePrivate() {
		// Restore operator
		println("Restore  prev: " + this.previousValue);
		println("Restore new: " + this.newValue.parent);
		this.newValue.insertReplace(this.previousValue);
		this.newValue = undefined;
	}
}