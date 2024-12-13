package com.my.lsp4j.languagesupport;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.fife.rsta.ac.c.CCompletionProvider;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.VariableCompletion;
import org.fife.ui.rtextarea.RTextArea;

import com.my.lsp4j.test.ClangdLsp4j;

public class CppCompletionProvider extends CCompletionProvider {

	ClangdLsp4j clangd;

	public CppCompletionProvider() {
	}

	public void setClangd(ClangdLsp4j clangd) {
		this.clangd = clangd;
	}

	@Override
	protected List<Completion> getCompletionsImpl(JTextComponent comp) {
		List<Completion> result = new ArrayList<>();

		Document doc = comp.getDocument();

		int offset = comp.getCaretPosition();
		Element root = doc.getDefaultRootElement();
		int line = root.getElementIndex(offset);
		Element elem = root.getElement(line);
		int start = elem.getStartOffset();
		int characterOffsetOfLine = offset - start;

		try {
			Either<List<CompletionItem>, CompletionList> completionList = clangd.completion(line,
					characterOffsetOfLine);
			List<CompletionItem> items = completionList.isLeft() ? completionList.getLeft()
					: completionList.getRight().getItems();

			System.out.println("CppCompletionProvider.getCompletionsImpl()");
			System.out.println("line: " + line);
			System.out.println("characterOffsetOfLine: " + characterOffsetOfLine);
			for (CompletionItem completionItem : items) {
				System.out.println(completionItem);
				CompletionItemKind kind = completionItem.getKind();
				BasicCompletion completion;
				switch (kind) {
				case Variable:
					completion = new VariableCompletion(this, completionItem.getInsertText(),
							completionItem.getDetail());
					completion.setShortDescription(completionItem.getDetail());
					break;
				case Function:
					completion = new VariableCompletion(this, completionItem.getLabel().trim(),
							completionItem.getDetail());
					completion.setShortDescription(completionItem.getDetail());
					break;
				default:
					continue;
				}
				result.add(completion);
			}

			return result;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return result;
	}

	protected boolean isValidChar(char ch) {
		return Character.isLetterOrDigit(ch) || ch == '_';
	}

	@Override
	public String getToolTipText(RTextArea tc, MouseEvent p) {
		int offset = tc.viewToModel(p.getPoint());
		if (offset < 0 || offset >= tc.getDocument().getLength()) {
			return null;
		}

		Document doc = tc.getDocument();
		Element root = doc.getDefaultRootElement();
		int line = root.getElementIndex(offset);
		Element elem = root.getElement(line);
		int start = elem.getStartOffset();

		try {
			int characterOffsetOfLine = offset - start - 1;
			String hoverMsg = clangd.hover(line, characterOffsetOfLine);
			return hoverMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
