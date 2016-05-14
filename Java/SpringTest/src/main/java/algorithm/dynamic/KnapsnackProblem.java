package algorithm.dynamic;

import java.util.ArrayList;

/*
 * Given a set of items, each with weight and benefit, determine the items
 * to include in the knapsack so that the total weight is less than or equal
 * to a given weight limit and the total benefit is maximized. For example,
 * if we are given following four items with their corresponding weights and
 * benefits which items should we include in the knapsack to maximize the
 * benefit. The weight limit for this knapsack is 10?
 */

public class KnapsnackProblem {

	public int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}

	public int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}

	public class ListWithBenefit {
		ArrayList<Integer> listItems;
		int benefit;

		public ListWithBenefit(int benefit) {
			listItems = new ArrayList<>();
			this.benefit = benefit;
		}

		public ListWithBenefit(ListWithBenefit obj) {
			listItems = new ArrayList();
			for (int i = 0; i < obj.listItems.size(); i++) {
				listItems.add(obj.listItems.get(i));
			}
			this.benefit = obj.benefit;
		}

	}

	ListWithBenefit findOptimalItems(int w, int n, int[] val, int[] weight, ListWithBenefit[][] optimalKnapsack) {
		// nothing can be added to Knapsack.
		if (w == 0 || n == weight.length) {
			optimalKnapsack[w][n] = new ListWithBenefit(0);
			return optimalKnapsack[w][n];

		}

		// this node can not be added to Knapsack.
		if (weight[n] > w)
			return (optimalKnapsack[w][n + 1] == null) ? findOptimalItems(w, n + 1, val, weight, optimalKnapsack)
					: optimalKnapsack[w][n + 1];

		// compute optimal knapsack if we want to include this item in it.
		ListWithBenefit include_n_benefit = (optimalKnapsack[w - weight[n]][n + 1] == null)
				? new ListWithBenefit(findOptimalItems(w - weight[n], n + 1, val, weight, optimalKnapsack))
				: new ListWithBenefit(optimalKnapsack[w - weight[n]][n + 1]);

		// now include this item and its benefit in the knapsack
		include_n_benefit.listItems.add(weight[n]);
		include_n_benefit.benefit += val[n];

		// compute optimal knapsack if we do not want to include this item in
		// it.
		ListWithBenefit exclude_n_benefit = (optimalKnapsack[w][n + 1] == null)
				? new ListWithBenefit(findOptimalItems(w, n + 1, val, weight, optimalKnapsack))
				: new ListWithBenefit(optimalKnapsack[w][n + 1]);

		// check which knapsack is gives us better benefit?
		if (include_n_benefit.benefit > exclude_n_benefit.benefit) {
			optimalKnapsack[w][n] = new ListWithBenefit(include_n_benefit);
			return include_n_benefit;
		}

		optimalKnapsack[w][n] = new ListWithBenefit(exclude_n_benefit);
		return exclude_n_benefit;
	}

	public int findMaximumBenefit(int w, int n, int[] val, int[] weight) {
		if (w == 0 || n == weight.length) {
			return 0;
		}

		// if this item's weight is greater than weight limit available
		// then this item cannot be included in the knapsack
		if (weight[n] > w)
			return findMaximumBenefit(w, n + 1, val, weight);

		// Case1: maximum benefit possible by including current item in the
		// knapsack
		int includeCaseBenefit = val[n] + findMaximumBenefit(w - weight[n], n + 1, val, weight);

		// Case2: maximum benefit possible by excluding current item from the
		// knapsack
		int excludeCaseBenefit = findMaximumBenefit(w, n + 1, val, weight);

		// return maximum of case1 and case2 values
		return max(includeCaseBenefit, excludeCaseBenefit);
	}

	public static void main(String[] args) {
		int[] val = { 3, 7, 2, 9 };
		int[] weight = { 2, 2, 4, 5 };

		int weightLimit = 10;
		ListWithBenefit[][] optimalKnapsack = new ListWithBenefit[weightLimit + 1][val.length + 1];

		// ArrayList<Integer> optimumWeights = new ArrayList();
		KnapsnackProblem obj = new KnapsnackProblem();

		// maximum benefit possible using simple recursion
		// System.out.println(obj.findMaximumBenefit(weightLimit, 0, val,
		// weight));

		// maximum benefit possible using dynamic programming
		ListWithBenefit sln = obj.findOptimalItems(weightLimit, 0, val, weight, optimalKnapsack);

		System.out.println("Maximum benefit is " + sln.benefit);
		System.out.println("And the weights to be included are");

		for (int i = 0; i < sln.listItems.size(); i++)
			System.out.println(sln.listItems.get(i));

	}
}
