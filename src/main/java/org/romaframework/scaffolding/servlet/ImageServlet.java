package org.romaframework.scaffolding.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.romaframework.aspect.view.form.ViewComponent;
import org.romaframework.aspect.view.html.HtmlViewAspectHelper;
import org.romaframework.aspect.view.html.area.HtmlViewRenderable;
import org.romaframework.aspect.view.html.image.ImageManager;
import org.romaframework.core.Roma;

/**
 * this servlet creates a chart by a POJO. it requires Chart-JFreeChart module imported
 * 
 */
public class ImageServlet extends org.romaframework.aspect.view.html.ImageServlet {

	private static final long		serialVersionUID			= 4633321951978534225L;

	public static final String	IMAGE_POJO_PARAMETER	= "imagePojo";

	protected static Log				log										= LogFactory.getLog(ImageServlet.class);

	@Override
	public void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("image");
		response.addHeader("Cache-Control", "must-revalidate");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0

		final String pojoId = request.getParameter(IMAGE_POJO_PARAMETER);
		if (pojoId == null) {
			return;
		}
		try {
			final OutputStream writer = response.getOutputStream();
			final HtmlViewRenderable renderable = HtmlViewAspectHelper.getHtmlViewSession().getRenderableById(Long.parseLong(pojoId));
			final Object pojo = ((ViewComponent) renderable).getContent();
			final byte[] result = loadImage(pojo);
			if (result != null) {
				ByteArrayInputStream stream = new ByteArrayInputStream(result);
				response.setContentType(URLConnection.guessContentTypeFromStream(stream));
				writer.write(result);
			}
			writer.flush();
		} catch (final Throwable t) {
			log.error("could not render chart: " + t);
			log.debug("", t);
		}
	}

	public byte[] loadImage(final Object pojo) {
		if (pojo == null) {
			return null;
		}
		if (pojo instanceof byte[]) {
			return (byte[]) pojo;
		}
		String imageName = pojo.toString();
		if (imageName.startsWith("$"))
			imageName = imageName.substring(1);
		final ImageManager manager = Roma.component(ImageManager.class);
		return manager.getImage(imageName);
	}
}