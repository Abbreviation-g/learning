package com.my.lsp4j.languagesupport;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.services.LanguageServer;

public class DocumentContentSynchronizer {
	private final URI fileUri;
	private int version = 0;
	private final AtomicReference<CompletableFuture<LanguageServer>> lastChangeFuture;

	public DocumentContentSynchronizer(URI fileUri, CompletableFuture<LanguageServer> initializeFuture) {
		this.fileUri = fileUri;
		lastChangeFuture = new AtomicReference<>(initializeFuture);
	}

	public DidOpenTextDocumentParams createDidOpenTextDocumentParams() throws IOException {
		TextDocumentItem textDocument = new TextDocumentItem();
		textDocument.setUri(fileUri.toString());
		textDocument.setText(new String(Files.readAllBytes(Paths.get(fileUri))));
		textDocument.setLanguageId("cpp");
		textDocument.setVersion(++version);

		return new DidOpenTextDocumentParams(textDocument);
	}

	public DidChangeTextDocumentParams createInsertEvent(String content, int offset, String str) {
		DidChangeTextDocumentParams changeParams = new DidChangeTextDocumentParams(
				new VersionedTextDocumentIdentifier(), Collections.singletonList(new TextDocumentContentChangeEvent()));
		changeParams.getTextDocument().setUri(fileUri.toString());
		changeParams.getTextDocument().setVersion(++version);
		TextDocumentContentChangeEvent changeEvent = changeParams.getContentChanges().get(0);

		Position startPosition = toPosition2(offset, content);
		Range range = new Range(startPosition, startPosition);
		changeEvent.setRange(range);
		changeEvent.setText(str);

		return changeParams;
	}

	public DidChangeTextDocumentParams createRemoveEvent(String content, int offset, int length) {
		DidChangeTextDocumentParams changeParams = new DidChangeTextDocumentParams(
				new VersionedTextDocumentIdentifier(), Collections.singletonList(new TextDocumentContentChangeEvent()));
		changeParams.getTextDocument().setUri(fileUri.toString());
		changeParams.getTextDocument().setVersion(++version);
		TextDocumentContentChangeEvent changeEvent = changeParams.getContentChanges().get(0);

		Position startPosition = toPosition2(offset, content);
		Position endPosition = toPosition2(offset + length, content);
		Range range = new Range(startPosition, endPosition);
		changeEvent.setRange(range);
		changeEvent.setText("");

		return changeParams;
	}

	public void didChange(DidChangeTextDocumentParams didChangeParams) {
		lastChangeFuture.updateAndGet(f -> f.thenApplyAsync(ls -> {
			ls.getTextDocumentService().didChange(didChangeParams);
			return ls;
		}));
	}

	private DocumentSymbolParams createDocumentSymbolParams() {
		DocumentSymbolParams params = new DocumentSymbolParams(new TextDocumentIdentifier(fileUri.toString()));
		return params;
	}

	public void didOpen() {
		lastChangeFuture.updateAndGet(f -> f.thenApplyAsync(ls -> {
			try {
				ls.getTextDocumentService().didOpen(createDidOpenTextDocumentParams());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ls;
		}).thenApply(ls -> {
			ls.getTextDocumentService().documentSymbol(createDocumentSymbolParams());
			return ls;
		}));
	}

	public void didSave(Document document) throws BadLocationException {
		TextDocumentIdentifier identifier = new TextDocumentIdentifier(fileUri.toString());
		int length = document.getLength();
		String text = document.getText(0, length);
		DidSaveTextDocumentParams saveParams = new DidSaveTextDocumentParams(identifier, text);

		List<FileEvent> fileEvents = new ArrayList<>();
		fileEvents.add(new FileEvent(fileUri.toString(), FileChangeType.Changed));
		lastChangeFuture.updateAndGet(f -> f.thenApplyAsync(ls -> {
			ls.getTextDocumentService().didSave(saveParams);
			return ls;
		}).thenApply(ls -> {
//			DidChangeWatchedFilesParams changedParams = new DidChangeWatchedFilesParams(fileEvents);
//			ls.getWorkspaceService().didChangeWatchedFiles(changedParams);
			return ls;
		}));
	}

	public static Position toPosition2(int offset, String content) {
		Position position = new Position();
		try (LineNumberReader reader = new LineNumberReader(new StringReader(content))) {
			int currentIndex = 0;
			String lineStr = null;
			while ((lineStr = reader.readLine()) != null) {
				int endOfLine = currentIndex + lineStr.length() + 1;
				if (endOfLine > offset) {
					int lineNumber = reader.getLineNumber() - 1;
					int character = offset - currentIndex;
					position.setLine(lineNumber);
					position.setCharacter(character);
					return position;
				}
				currentIndex = endOfLine;
			}
			int lineNumber = reader.getLineNumber();
			int character = offset - currentIndex;
			position.setLine(lineNumber);
			position.setCharacter(character);
			return position;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return position;
	}
}
