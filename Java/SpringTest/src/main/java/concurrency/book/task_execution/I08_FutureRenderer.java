package concurrency.book.task_execution;

import java.util.*;
import java.util.concurrent.*;

import concurrency.book.building_blocks.LaunderThrowable;

/**
 * FutureRenderer
 * <p/>
 * Waiting for image download with \Future
 *
 * Two people can divide the work of cleaning the dinner dishes fairly
 * effectively: one person washes while the other dries. However, assigning a
 * different type of task to each worker does not scale well; if several more
 * people show up, it is not obvious how they can help without getting in the
 * way or significantly restructuring the division of labor. Without finding
 * finer-grained parallelism among similar tasks, this approach will yield
 * diminishing returns.
 * 
 * A further problem with dividing heterogeneous tasks among multiple workers is
 * that the tasks may have disparate sizes. If you divide tasks A and B between
 * two workers but A takes ten times as long as B, you've only speeded up the
 * total process by 9%. Finally, dividing a task among multiple workers always
 * involves some amount of coordination overhead; for the division to be
 * worthwhile, this overhead must be more than compensated by productivity
 * improvements due to parallelism.
 * 
 * FutureRenderer uses two tasks: one for rendering text and one for downloading
 * the images. If rendering the text is much faster than downloading the images,
 * as is entirely possible, the resulting performance is not much different from
 * the sequential version, but the code is a lot more complicated. And the best
 * we can do with two threads is speed things up by a factor of two. Thus,
 * trying to increase concurrency by parallelizing heterogeneous activities can
 * be a lot of work, and there is a limit to how much additional concurrency you
 * can get out of it.
 */
public abstract class I08_FutureRenderer {
	private final ExecutorService executor = Executors.newCachedThreadPool();

	void renderPage(CharSequence source) {
		final List<ImageInfo> imageInfos = scanForImageInfo(source);
		Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
			public List<ImageData> call() {
				List<ImageData> result = new ArrayList<ImageData>();
				for (ImageInfo imageInfo : imageInfos)
					result.add(imageInfo.downloadImage());
				return result;
			}
		};

		Future<List<ImageData>> future = executor.submit(task);
		renderText(source);

		try {
			List<ImageData> imageData = future.get();
			for (ImageData data : imageData)
				renderImage(data);
		} catch (InterruptedException e) {
			// Re-assert the thread's interrupted status
			Thread.currentThread().interrupt();
			// We don't need the result, so cancel the task too
			future.cancel(true);
		} catch (ExecutionException e) {
			throw LaunderThrowable.launderThrowable(e.getCause());
		}
	}

	interface ImageData {
	}

	interface ImageInfo {
		ImageData downloadImage();
	}

	abstract void renderText(CharSequence s);

	abstract List<ImageInfo> scanForImageInfo(CharSequence s);

	abstract void renderImage(ImageData i);
}