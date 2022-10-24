laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

class NullifyInputVariable extends Mutator {
	constructor() {
		//Parent constructor
		super("NullifyInputVariable");

		this.newValue = undefined;
		this.mutationPoints = [];


		this.currentIndex = 0;
		this.previousValue = undefined;
		this.currentMutation = undefined;

	}



	addJp($joinpoint) {
		if ($joinpoint.instanceOf('method')) {
			for (var $param of $joinpoint.params) {
				if (!$param.isPrimitive) {
					this.mutationPoints.push($joinpoint);
					return true;
				}
			}
		}
		return false;
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/
	hasMutations() {
		return this.currentIndex < this.mutationPoints.length;
	}

	getMutationPoint() {
		// If mutation is currently occuring, return mutation point
		if (this.previousValue !== undefined) {
			return this.previousValue;
		}

		// Return next mutation point
		if (this.currentIndex < this.mutationPoints.length) {
			//println("JP!! -> " + this.mutationPoints[this.currentIndex] );
			//println("JPCode!! -> " + this.mutationPoints[this.currentIndex].code ); //FIXME: Aqui imprime
			return this.mutationPoints[this.currentIndex];
		}

		return undefined;
	}

	getCurrentMutation() {
		return this.currentMutation;
	}

	_mutatePrivate() {
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
				this.currentMutation.body.insertBegin(" { [[$param.name]] = null } ");
			}
		}

		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": "
			+ " to " + this.currentMutation);
		println("/*--------------------------------------*/");
	}


	_restorePrivate() {
		// Restore operator
		//println("Restore  prev: " + this.previousValue);
		//println("Restore new: " + this.currentMutation.code);
		//this.getMutationPoint().insertReplace(this.previousValue);
		this.currentMutation.insertReplace(this.previousValue);
		this.currentMutation = undefined;
		//this.currentIndex++;
	}

}
