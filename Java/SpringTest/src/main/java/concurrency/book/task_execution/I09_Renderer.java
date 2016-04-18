package concurrency.book.task_execution;

import static concurrency.book.building_blocks.LaunderThrowable.launderThrowable;

import java.util.*;
import java.util.concurrent.*;

/**
 * Renderer
 * <p/>
 * Using CompletionService to render page elements as they become available
 *
 * We can use a CompletionService to improve the performance of the page
 * renderer in two ways: shorter total runtime and improved responsiveness. We
 * can create a separate task for downloading each image and execute them in a
 * thread pool, turning the sequential download into a parallel one: this
 * reduces the amount of time to download all the images. And by fetching
 * results from the CompletionService and rendering each image as soon as it is
 * available, we can give the user a more dynamic and responsive user interface.
 * 
 */
public abstract class I09_Renderer {
	private final ExecutorService executor;

	I09_Renderer(ExecutorService executor) {
		this.executor = executor;
	}

	void renderPage(CharSequence source) {
		final List<ImageInfo> info = scanForImageInfo(source);
		/*
		 * A service that decouples the production of new asynchronous tasks
		 * from the consumption of the results of completed tasks. Producers
		 * submit tasks for execution. Consumers take completed tasks and
		 * process their results in the order they complete. A CompletionService
		 * can for example be used to manage asynchronous I/O, in which tasks
		 * that perform reads are submitted in one part of a program or system,
		 * and then acted upon in a different part of the program when the reads
		 * complete, possibly in a different order than they were requested.
		 */
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for (final ImageInfo imageInfo : info)
			completionService.submit(new Callable<ImageData>() {
				public ImageData call() {
					return imageInfo.downloadImage();
				}
			});

		renderText(source);

		try {
			for (int t = 0, n = info.size(); t < n; t++) {
				/*
				 * take() : Retrieves and removes the Future representing the next
				 * completed task, waiting if none are yet present
				 */
				Future<ImageData> f = completionService.take();
				ImageData imageData = f.get();
				renderImage(imageData);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
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