package com.wipro.digital.assignment.web.crawler.document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class DocumentIndex {

	public static final String FIELD_PATH = "path";
	public static final String FIELD_CONTENTS = "contents";

	public void createIndex(String filesToIndexDirectory, String indexDirectory)
			throws CorruptIndexException, LockObtainFailedException, IOException {
		Analyzer analyzer = new StandardAnalyzer();
		boolean recreateIndexIfExists = true;
		IndexWriter indexWriter = new IndexWriter(indexDirectory, analyzer, recreateIndexIfExists);
		File dir = new File(filesToIndexDirectory);
		File[] files = dir.listFiles();
		for (File file : files) {
			Document document = new Document();

			String path = file.getCanonicalPath();
			document.add(new Field(FIELD_PATH, path, Field.Store.YES, Field.Index.UN_TOKENIZED));

			Reader reader = new FileReader(file);
			document.add(new Field(FIELD_CONTENTS, reader));

			indexWriter.addDocument(document);
		}
		indexWriter.optimize();
		indexWriter.close();
	}

	public void searchIndex(String searchString, String indexDirectory) throws IOException, ParseException {
		System.out.println("Searching for '" + searchString + "'");
		Directory directory = FSDirectory.getDirectory(indexDirectory);
		IndexReader indexReader = IndexReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Analyzer analyzer = new StandardAnalyzer();
		QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
		Query query = queryParser.parse(searchString);
		Hits hits = indexSearcher.search(query);
		System.out.println("Number of hits: " + hits.length());

		Iterator<Hit> it = hits.iterator();
		while (it.hasNext()) {
			Hit hit = it.next();
			Document document = hit.getDocument();
			String path = document.get(FIELD_PATH);
			System.out.println("Hit: " + path);
		}
	}
}
