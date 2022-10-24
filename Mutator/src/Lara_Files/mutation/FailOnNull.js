laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

class FailOnNull extends Mutator {
	constructor($startingPoint) {

		//Parent constructor
		super("FailOnNull")

		if ($startingPoint === undefined) {
			$startingPoint = WeaverJps.root();
		}

		this.mutationPoints = [];

		for (var $var of WeaverJps.search('var').get()) {
			if ($var.reference.toString() === "read" && !$var.isPrimitive) {
				this.mutationPoints.push($var);
			}
		}

		println("Mutation points:");
		for (var $point of this.mutationPoints) {
			println("Point: " + $point.code);
		}

		this.currentIndex = 0;
		this.newValue = undefined;

	};



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

		/* if (mutationPoint.type === "String") {
			this.newValue = mutationPoint.insertBefore(% { if([[mutationPoint]] == null)
			{ throw new java.lang.NullPointerException("Fail on empty") }
		}%);
	}
		else {
	this.newValue = mutationPoint.insertBefore(% { if([[mutationPoint]] == null)
	{ throw new java.lang.NullPointerException("fail on null") }
}%); */
	}


	//this.currentIndex++;

	// println("/*--------------------------------------*/");
	// println("Mutating operator n." + this.currentIndex + ": " +
	// 	" to " + this.newValue);
	// println("/*--------------------------------------*/");


	_restorePrivate() {
		// Restore operator
		this.newValue.remove();
		//println("value restored:" + this.newValue);
		this.newValue = undefined;
	}
}