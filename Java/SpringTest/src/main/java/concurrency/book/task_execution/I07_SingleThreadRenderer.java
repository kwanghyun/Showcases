package concurrency.book.task_execution;

import java.util.*;

/**
 * SingleThreadRendere
 * <p/>
 * Rendering page elements sequentially
 *
 * Downloading an image mostly involves waiting for I/O to complete, and during
 * this time the CPU does little work. So the sequential approach may
 * under-utilize the CPU, and also makes the user wait longer than necessary to
 * see the finished page. We can achieve better utilization and responsiveness
 * by breaking the problem into independent tasks that can execute concurrently.
 */
public abstract class I07_SingleThreadRenderer {
	void renderPage(CharSequence source) {
		renderText(source);
		List<ImageData> imageData = new ArrayList<ImageData>();
		for (ImageInfo imageInfo : scanForImageInfo(source))
			imageData.add(imageInfo.downloadImage());
		for (ImageData data : imageData)
			renderImage(data);
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