package com.my.lsp4j.languagesupport;

import javax.swing.ListCellRenderer;

import org.fife.rsta.ac.AbstractLanguageSupport;
import org.fife.rsta.ac.c.CCompletionProvider;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.ToolTipSupplier;

public class CppLanguageSupport extends AbstractLanguageSupport {

	/**
	 * The completion provider, shared amongst all text areas editing C.
	 */
	private CppCompletionProvider provider;

	/**
	 * Constructor.
	 */
	public CppLanguageSupport() {
		setParameterAssistanceEnabled(true);
		setShowDescWindow(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ListCellRenderer<Object> createDefaultCompletionCellRenderer() {
		return new CCellRenderer();
	}

	public CppCompletionProvider getProvider() {
		if (provider == null) {
			provider = new CppCompletionProvider();
		}
		return provider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void install(RSyntaxTextArea textArea) {

		CppCompletionProvider provider = getProvider();
		AutoCompletion ac = createAutoCompletion(provider);
		ac.install(textArea);
		installImpl(textArea, ac);

		textArea.setToolTipSupplier(provider);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uninstall(RSyntaxTextArea textArea) {
		uninstallImpl(textArea);
		textArea.setToolTipSupplier(null);
	}
}
