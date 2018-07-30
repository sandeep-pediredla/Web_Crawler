
package com.wipro.digital.assignment.web.crawler.document;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

/**
 * The Class SaveDocument.
 */
public class SaveDocument {

	/**
	 * Save document to folder.
	 *
	 * @param pageContent
	 *            the page content
	 * @param documentStorageFolder
	 *            the document storage folder
	 */
	@SuppressWarnings("deprecation")
	public void saveDocumentToFolder(final HtmPageContent pageContent, final String documentStorageFolder) {

		final RequestUrl requestUrl = pageContent.getRequestUrl();
		final File opDirectory = new File(documentStorageFolder + File.separator + requestUrl.getHost());

		if (!opDirectory.exists()) {
			opDirectory.mkdirs();
		}

		final File htmlFile = new File(
				opDirectory.getAbsolutePath() + File.separator + pageContent.getJobDetails().getJobId() + ".html");
		final File txtFile = new File(
				opDirectory.getAbsolutePath() + File.separator + pageContent.getJobDetails().getJobId() + ".txt");

		try {
			FileUtils.writeStringToFile(htmlFile, pageContent.getHtml(), false);
			FileUtils.writeStringToFile(txtFile, pageContent.getText(), false);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
