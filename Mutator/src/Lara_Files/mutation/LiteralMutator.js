laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

/**
 * @param {$expression | $expression = function($expression)} $newValue
 */
class LiteralMutator extends Mutator {
	constructor($newValue, $oldValue) {
		//Parent constructor
		super("LiteralMutator");

		checkDefined($newValue, "LiteralMutator($newValue)");

		this.newValue = $newValue;
		this.oldValue = $oldValue;

		this.mutationPoints = [];

		this.currentIndex = 0;
		this.previousValue = undefined;
		this.mutation = undefined;


	}


	addJp($joinpoint) {
		if ($joinpoint.instanceOf('literal')) {
			let $mutationPoint = $joinpoint;

			//if the literal is negative.
			if ($joinpoint.parent.instanceOf('unaryExpression')) {
				$mutationPoint = $joinpoint.parent;
			}


			//mutation will only happen if the literal has other values.
			if (!isFunction(this.newValue) && LiteralMutator._normalize($mutationPoint.code, $mutationPoint.type) === LiteralMutator._normalize(this.newValue.code, this.newValue.type)) {
				return false;
			}

			if (!isUndefined(this.oldValue)) {
				if (isFunction(this.oldValue)) {
					if (!this.oldValue($mutationPoint)) {
						return false;
					}
				} else if ($mutationPoint.code !== this.oldValue.code) {
					return false;
				}
			}

			this.mutationPoints.push($mutationPoint);
			return true;
		}
		return false;
	}

	static _normalize(stringValue, type) {

		checkDefined(stringValue, "No value");

		if (type === "string" || type === "boolean") {
			return stringValue;
		}

		if (stringValue.endsWith("f") || stringValue.endsWith("F")
			|| stringValue.endsWith("d") || stringValue.endsWith("D")
			|| stringValue.endsWith("l") || stringValue.endsWith("L")) {
			stringValue = stringValue.substring(0, stringValue.length - 1);
		}

		if (stringValue.endsWith(".0")) {
			stringValue = stringValue.substring(0, stringValue.length - 2);
		}

		return stringValue;
	}

	/**
	 * @param {expression} $newValue
	 * @param {string} oldValue
	 */
	static newIntShortByteMutator(newValue, oldValue) {
		let $newValue = KadabraNodes.literal(newValue, "int");

		return new LiteralMutator($newValue, function (mutationPoint) {
			if (!isUndefined(oldValue) && mutationPoint.code !== oldValue) {
				return false;
			}

			return mutationPoint.type === "int" || mutationPoint.type === "short" || mutationPoint.type === "byte";
		});
	}

	//more efficient method.
	static newTypeFilteredMutator($newValue, validTypes, oldValue) {
		//var $newValue = KadabraNodes.literal(newValue, "int");
		checkDefined($newValue);
		checkDefined(validTypes);

		return new LiteralMutator($newValue, function (mutationPoint) {
			if (!isUndefined(oldValue) && LiteralMutator._normalize(mutationPoint.code, mutationPoint.type) !== LiteralMutator._normalize(oldValue, mutationPoint.type)) {
				;
				return false;
			}

			if (validTypes === undefined || validTypes.length === 0) {
				return true;
			}

			return validTypes.includes(mutationPoint.type);
			//return mutationPoint.type === "int" || mutationPoint.type === "short" || mutationPoint.type === "byte";
		});
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/
	hasMutations() {
		return this.currentIndex < this.mutationPoints.length;
	}

	getMutationPoint() {
		if (this.isMutated) {
			return this.mutation;
		} else {
			if (this.currentIndex < this.mutationPoints.length) {
				return this.mutationPoints[this.currentIndex];
			} else {
				return undefined;
			}
		}
	}

	_mutatePrivate() {

		let mutationPoint = this.mutationPoints[this.currentIndex];

		// Store current value.
		//this.previousValue = mutationPoint.value;
		this.previousValue = mutationPoint;

		this.currentIndex++;

		let valueToReplace = this.newValue;
		println(valueToReplace);
		if (isFunction(valueToReplace)) {
			valueToReplace = valueToReplace(mutationPoint);
		}

		printFunction(this.currentIndex, this.previousValue, valueToReplace);
		// Set new operator
		this.mutation = this.previousValue.insertReplace(valueToReplace);

	}

	printFunction(currentIndex, previousValue, newValue) {
		println("/*--------------------------------------*/");
		println("Mutating operator n." + currentIndex + ": " + previousValue
			+ " to " + newValue);
		println("/*--------------------------------------*/");
	}

	_restorePrivate() {
		// Restore operator
		this.mutation.insertReplace(this.previousValue);
		//mutationPoint=this.previousValue;

		this.previousValue = undefined;
		this.mutation = undefined;
	}
}

