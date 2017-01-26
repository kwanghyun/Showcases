package algorithm.stackNQueue;

import java.util.Arrays;
import java.util.Stack;

/*	Given an absolute path for a file (Unix-style), simplify it.

For example,

path = "/home/", => "/home"
path = "/a/./b/../../c/", => "/c"
path = "/../", => "/"
path = "/home//foo/", => "/home/foo"
*/
public class SimplyPath {
	public String simplifyPath(String path) {
		System.out.println("input = " + path);
		StringBuilder result = new StringBuilder();

		String[] paths = path.split("\\/");
		Stack<String> stack = new Stack<>();

		for (int i = 0; i < paths.length; i++) {
			String curr = paths[i];
			if (curr.equalsIgnoreCase("..") && !stack.isEmpty()) {
				stack.pop();
			} else if (curr.length() > 0 && !curr.equalsIgnoreCase(".") && !curr.equalsIgnoreCase("..")) {
				stack.push(curr);
			}
		}

		if (stack.isEmpty())
			return "/";

		while (!stack.isEmpty()) {
			result.insert(0, "/" + stack.pop());
		}

		return result.toString();
	}

	public static void main(String[] args) {
		SimplyPath ob = new SimplyPath();
		System.out.println(ob.simplifyPath("/home/"));
		System.out.println(ob.simplifyPath("/a/./b/../../c/"));
		System.out.println(ob.simplifyPath("/../"));
		System.out.println(ob.simplifyPath("/home//foo/"));

	}
}
