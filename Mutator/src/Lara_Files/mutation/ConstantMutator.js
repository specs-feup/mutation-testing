laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

class ConstantMutator extends Mutator {
	//Parent constructor
	constructor($expr, $startingPoint) {
		super("ConstantMutator");

		checkDefined($expr, "ConstantMutator");

		if ($startingPoint === undefined) {
			$startingPoint = WeaverJps.root();
		}

		this.$expr = $expr;
		//ConstantMutator._parseNewValue($expr);

		this.newValue = undefined;

		this.mutationPoints = [];

		this.currentIndex = 0;
		this.previousValue = undefined;

	}


	addJp($joinpoint) {

		if ($joinpoint.instanceOf('field') || $joinpoint.instanceOf('localVariable')) {
			if ($joinpoint.init === undefined || !$joinpoint.isFinal || !ConstantMutator._isCompatible($joinpoint.type, this.$expr.type)) {
				return false;
			}
			this.mutationPoints.push($joinpoint);

			return true;
		}

		if ($joinpoint.instanceOf('assignment')) {
			if (!$joinpoint.isFinal || !ConstantMutator._isCompatible($joinpoint.type, this.$expr.type)) {
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

	static _isCompatible = function (type1, type2) {
		return true;
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/
	hasMutations() {
		return this.currentIndex < this.mutationPoints.length;
	}

	getMutationPoint() {
		if (this.isMutated) {
			println("Potato	" + this.newValue);
			return this.newValue;
		} else {
			if (this.currentIndex < this.mutationPoints.length) {
				println("Potato1");
				return this.mutationPoints[this.currentIndex];
			} else {
				println("Potato2");
				return undefined;
			}
		}
	}

	_mutatePrivate() {

		let mutationPoint = this.mutationPoints[this.currentIndex];

		if (mutationPoint.instanceOf('field') || mutationPoint.instanceOf('localVariable')) {
			this.previousValue = mutationPoint.init;
		} else if (mutationPoint.instanceOf('assignment')) {
			this.previousValue = mutationPoint.rhs;
		}

		this.currentIndex++;

		if (isFunction(this.$expr)) {
			let tem = this.$expr(this.previousValue);
			this.newValue = this.previousValue.insertReplace(tem);
		} else {
			this.newValue = this.previousValue.insertReplace(this.$expr);
			//this.newValue = this.$expr;
		}

		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": " + this.previousValue
			+ " to " + this.newValue);
		println("/*--------------------------------------*/");

	}

	_restorePrivate() {
		// Restore operator
		this.newValue.insertReplace(this.previousValue);
		this.previousValue = undefined;
		this.newValue = undefined;
	}
}