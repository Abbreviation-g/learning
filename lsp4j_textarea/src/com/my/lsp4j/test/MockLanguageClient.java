/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package com.my.lsp4j.test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.text.Element;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ShowDocumentParams;
import org.eclipse.lsp4j.ShowDocumentResult;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.services.LanguageClient;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice.Level;

public class MockLanguageClient extends AbstractParser implements LanguageClient {
	private PublishDiagnosticsParams diagnostics;

	@Override
	public void telemetryEvent(Object object) {
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		this.diagnostics = diagnostics;
	}

	@Override
	public void showMessage(MessageParams messageParams) {
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletableFuture<ShowDocumentResult> showDocument(ShowDocumentParams requestParams) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void logMessage(MessageParams message) {
	}

	@Override
	public CompletableFuture<List<WorkspaceFolder>> workspaceFolders() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ParseResult parse(RSyntaxDocument doc, String style) {

		DefaultParseResult result = new DefaultParseResult(this);

		List<Diagnostic> diagnosticList = diagnostics.getDiagnostics();
		for (Diagnostic diagnostic : diagnosticList) {
			String message = diagnostic.getMessage();
			Range range = diagnostic.getRange();
			Position start = range.getStart();
			Position end = range.getEnd();
			int startLine = start.getLine();
			Element root = doc.getDefaultRootElement();
			int startOffset = root.getElement(startLine).getStartOffset() + start.getCharacter();
			int endOffset = root.getElement(end.getLine()).getStartOffset() + end.getCharacter();
			int length = endOffset - startOffset;
			DefaultParserNotice notice = new DefaultParserNotice(this, message, startLine, startOffset, length);
			notice.setLevel(DiagnosticSeverityLevelMap.getLevel(diagnostic.getSeverity()));
			result.addNotice(notice);
			System.out.println("-------------------");
			System.out.println(notice);
		}
		return result;
	}

	public static enum DiagnosticSeverityLevelMap {
		Error(DiagnosticSeverity.Error, Level.ERROR), Warning(DiagnosticSeverity.Warning, Level.WARNING),
		Information(DiagnosticSeverity.Information, Level.INFO), Hint(DiagnosticSeverity.Hint, Level.INFO);

		private DiagnosticSeverity severity;
		private Level level;

		DiagnosticSeverityLevelMap(DiagnosticSeverity severity, Level level) {
			this.severity = severity;
			this.level = level;
		}

		public static Level getLevel(DiagnosticSeverity severity) {
			for (DiagnosticSeverityLevelMap map : values()) {
				if (map.severity == severity) {
					return map.level;
				}
			}
			return Level.INFO;
		}
	}
}
