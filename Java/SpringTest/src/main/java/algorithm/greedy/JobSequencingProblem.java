package algorithm.greedy;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Given an array of jobs where every job has a deadline and associated
 * profit if the job is finished before the deadline. It is also given that
 * every job takes single unit of time, so the minimum possible deadline for
 * any job is 1. How to maximize total profit if only one job can be
 * scheduled at a time.
 * 
 * Examples:

Input: Four Jobs with following deadlines and profits
  JobID    Deadline      Profit
    a        4                20   
    b        1                10
    c        1                40  
    d        1                30
Output: Following is maximum profit sequence of jobs
        c, a   


Input:  Five Jobs with following deadlines and profits
   JobID     Deadline     Profit
     a         2               100
     b         1               19
     c         2               27
     d         1               25
     e         3               15
Output: Following is maximum profit sequence of jobs
        c, a, e
 */
public class JobSequencingProblem {

	public void printJobSchedule(Job[] jobs) {
		// Sort all jobs according to decreasing order of profit
		Arrays.sort(jobs);
		System.out.println(Arrays.toString(jobs));
		// To store result (Sequence of jobs)
		ArrayList<Character> result = new ArrayList<>();
		// To keep track of free time slots
		boolean[] slots = new boolean[jobs.length];

		for (int i = 0; i < jobs.length; i++) {
			// Find a free slot for this job (Note that we start from the last
			// possible slot)
			for (int j = Math.min(jobs.length, jobs[i].deadline) - 1; j >= 0; j--) {
				// Free slot found
				if (slots[j] == false) {
					result.add(jobs[i].id); // Add this job to result
					slots[j] = true; // Make this slot occupied
					break;
				}
			}
		}

		System.out.println(result);
	}

	public static void main(String[] args) {
		Job[] jobs = new Job[5];
		jobs[0] = new Job('a', 2, 100);
		jobs[1] = new Job('b', 1, 19);
		jobs[2] = new Job('c', 2, 27);
		jobs[3] = new Job('d', 1, 25);
		jobs[4] = new Job('e', 3, 15);
		JobSequencingProblem ob = new JobSequencingProblem();
		ob.printJobSchedule(jobs);
	}
}
