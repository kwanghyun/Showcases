package concurrency.book.atomic;

import java.util.concurrent.atomic.*;

import net.jcip.annotations.*;

/**
 * I07_LinkedQueue
 * <p/>
 * Insertion in the Michael-Scott nonblocking queue algorithm
 *
 * The two nonblocking algorithms we've seen so far, the counter and the stack,
 * illustrate the basic pattern of using CAS to update a value speculatively,
 * retrying if the update fails. The trick to building nonblocking algorithms is
 * to limit the scope of atomic changes to a single variable. With counters this
 * is trivial, and with a stack it is straightforward enough, but for more
 * complicated data structures such as queues, hash tables, or trees, it can get
 * a lot trickier.
 * 
 * A linked queue is more complicated than a stack because it must support fast
 * access to both the head and the tail. To do this, it maintains separate head
 * and tail pointers. Two pointers refer to the node at the tail: the next
 * pointer of the current last element, and the tail pointer. To insert a new
 * element successfully, both of these pointers must be updated—atomically. At
 * first glance, this cannot be done with atomic variables; separate CAS
 * operations are required to update the two pointers, and if the first succeeds
 * but the second one fails the queue is left in an inconsistent state. And,
 * even if both operations succeed, another thread could try to access the queue
 * between the first and the second. Building a nonblocking algorithm for a
 * linked queue requires a plan for both these situations.
 * 
 * We need several tricks to develop this plan. The first is to ensure that the
 * data structure is always in a consistent state, even in the middle of an
 * multi-step update. That way, if thread A is in the middle of a update when
 * thread B arrives on the scene, B can tell that an operation has been
 * partially completed and knows not to try immediately to apply its own update.
 * Then B can wait (by repeatedly examining the queue state) until A finishes,
 * so that the two don't get in each other's way.
 * 
 * While this trick by itself would suffice to let threads “take turns”
 * accessing the data structure without corrupting it, if one thread failed in
 * the middle of an update, no thread would be able to access the queue at all.
 * To make the algorithm nonblocking, we must ensure that the failure of a
 * thread does not prevent other threads from making progress. Thus, the second
 * trick is to make sure that if B arrives to find the data structure in the
 * middle of an update by A, enough information is already embodied in the data
 * structure for B to finish the update for A. If B “helps” A by finishing A's
 * operation, B can proceed with its own operation without waiting for A. When A
 * gets around to finishing its operation, it will find that B already did the
 * job for it.
 * 
 * LinkedQueue in Listing 15.7 shows the insertion portion of the Michael-Scott
 * nonblocking linked-queue algorithm (Michael and Scott, 1996), which is used
 * by ConcurrentLinkedQueue. As in many queue algorithms, an empty queue
 * consists of a “sentinel” or “dummy” node, and the head and tail pointers are
 * initialized to refer to the sentinel. The tail pointer always refers to the
 * sentinel (if the queue is empty), the last element in the queue, or (in the
 * case that an operation is in mid-update) the second-to-last element. Figure
 * 15.3 illustrates a queue with two elements in the normal, or quiescent,
 * state.
 * 
 * Inserting a new element involves updating two pointers. The first links the
 * new node to the end of the list by updating the next pointer of the current
 * last element; the second swings the tail pointer around to point to the new
 * last element. Between these two operations, the queue is in the intermediate
 * state, shown in Figure 15.4. After the second update, the queue is again in
 * the quiescent state, shown in Figure 15.5.
 * 
 * The key observation that enables both of the required tricks is that if the
 * queue is in the quiescent state, the next field of the link node pointed to
 * by tail is null, and if it is in the intermediate state, tail.next is
 * non-null. So any thread can immediately tell the state of the queue by
 * examining tail.next. Further, if the queue is in the intermediate state, it
 * can be restored to the quiescent state by advancing the tail pointer forward
 * one node, finishing the operation for whichever thread is in the middle of
 * inserting an element.[7]
 * 
 * LinkedQueue.put first checks to see if the queue is in the intermediate state
 * before attempting to insert a new element (step A). If it is, then some other
 * thread is already in the process of inserting an element (between its steps C
 * and D). Rather than wait for that thread to finish, the current thread helps
 * it by finishing the operation for it, advancing the tail pointer (step B). It
 * then repeats this check in case another thread has started inserting a new
 * element, advancing the tail pointer until it finds the queue in the quiescent
 * state so it can begin its own insertion.
 * 
 * The CAS at step C, which links the new node at the tail of the queue, could
 * fail if two threads try to insert an element at the same time. In that case,
 * no harm is done: no changes have been made, and the current thread can just
 * reload the tail pointer and try again. Once C succeeds, the insertion is
 * considered to have taken effect; the second CAS (step D) is considered
 * “cleanup”, since it can be performed either by the inserting thread or by any
 * other thread. If D fails, the inserting thread returns anyway rather than
 * retrying the CAS, because no retry is needed—another thread has already
 * finished the job in its step B! This works because before any thread tries to
 * link a new node into the queue, it first checks to see if the queue needs
 * cleaning up by checking if tail.next is non-null. If it is, it advances the
 * tail pointer first (perhaps multiple times) until the queue is in the
 * quiescent state.
 * 
 * 
 */
@ThreadSafe
public class I07_LinkedQueue<E> {

	private static class Node<E> {
		final E item;
		final AtomicReference<I07_LinkedQueue.Node<E>> next;

		public Node(E item, I07_LinkedQueue.Node<E> next) {
			this.item = item;
			this.next = new AtomicReference<I07_LinkedQueue.Node<E>>(next);
		}
	}

	private final I07_LinkedQueue.Node<E> dummy = new I07_LinkedQueue.Node<E>(null, null);
	private final AtomicReference<I07_LinkedQueue.Node<E>> head = new AtomicReference<I07_LinkedQueue.Node<E>>(dummy);
	private final AtomicReference<I07_LinkedQueue.Node<E>> tail = new AtomicReference<I07_LinkedQueue.Node<E>>(dummy);

	public boolean put(E item) {
		I07_LinkedQueue.Node<E> newNode = new I07_LinkedQueue.Node<E>(item, null);
		while (true) {
			I07_LinkedQueue.Node<E> curTail = tail.get();
			I07_LinkedQueue.Node<E> tailNext = curTail.next.get();
			if (curTail == tail.get()) {
				if (tailNext != null) {
					// Queue in intermediate state, advance tail
					tail.compareAndSet(curTail, tailNext);
				} else {
					// In quiescent state, try inserting new node
					if (curTail.next.compareAndSet(null, newNode)) {
						// Insertion succeeded, try advancing tail
						tail.compareAndSet(curTail, newNode);
						return true;
					}
				}
			}
		}
	}
}