laraImport("lara.mutation.IterativeMutation");
laraImport("lara.mutation.MutationResult");


class ArithmeticOperatorDeletionMutation extends IterativeMutation {
	constructor() {
		//Parent constructor
		super("ArithmeticOperatorDeletionMutation");
	}



	/*** IMPLEMENTATION OF INSTANCE METHODS ***/

	isMutationPoint($jp) {
		return $jp.instanceOf("binaryExpression");
	}

	// = function* 
	mutate($jp) {


		let leftOperand = $jp.lhs.copy();

		debug("/*--------------------------------------*/");
		debug("Mutating operator: " + $jp + " to " + leftOperand);
		debug("/*--------------------------------------*/");

		return new MutationResult(leftOperand);


		let rightOperand = $jp.rhs.copy();

		debug("/*--------------------------------------*/");
		debug("Mutating operator: " + $jp + " to " + rightOperand);
		debug("/*--------------------------------------*/");

		return new MutationResult(rightOperand);
	}
}