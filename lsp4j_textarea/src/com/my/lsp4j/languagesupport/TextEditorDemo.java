package com.my.lsp4j.languagesupport;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.services.LanguageServer;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.my.lsp4j.test.ClangdLsp4j;

public class TextEditorDemo extends JFrame {
	private static final String SYNTAX_STYLE_CPP = "text/cpp";

	private static final long serialVersionUID = 1L;

	static final String CLAND_FILE_PATH = "C:\\sw\\clangd_15.0.0\\bin\\clangd.exe";
	static final String CPP_FILE_PATH = "C:/1/eclipse-rcp-2022-09-R/runtime-test-cdt-lsp/cpp_prj/src/t2.cpp";
	static final URI ROOT_URI = new File(CPP_FILE_PATH).toURI();
	private RSyntaxTextArea textArea;

	private void createCppLanguageSupport() {
		LanguageSupportFactory lsf = LanguageSupportFactory.get();
		lsf.addLanguageSupport(SYNTAX_STYLE_CPP, CppLanguageSupport.class.getName());

		support = (CppLanguageSupport) lsf.getSupportFor(SYNTAX_STYLE_CPP);

		support.setAutoActivationEnabled(true);
		support.setAutoCompleteEnabled(true);
		support.setParameterAssistanceEnabled(true);
		support.setAutoActivationDelay(100);
		support.setShowDescWindow(true);
		support.install(textArea);
	}

	public TextEditorDemo() {
		JPanel cp = new JPanel(new BorderLayout());

		textArea = new RSyntaxTextArea(20, 60);
		LanguageSupportFactory.get().register(textArea);

		createCppLanguageSupport();

		textArea.setSyntaxEditingStyle(SYNTAX_STYLE_CPP);
		textArea.setCodeFoldingEnabled(true);

		try {
			FileReader reader = new FileReader(new File(CPP_FILE_PATH), StandardCharsets.UTF_8);
			textArea.read(reader, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		RTextScrollPane sp = new RTextScrollPane(textArea);
		cp.add(sp);

		setContentPane(cp);
		setTitle("Text Editor Demo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

		init();
	}

	private ClangdLsp4j clangdLsp4j;
	private CppLanguageSupport support;
	private DocumentContentSynchronizer synchronizer;

	void init() {
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				clangdLsp4j = new ClangdLsp4j(CPP_FILE_PATH);
				try {
					clangdLsp4j.start();
					textArea.addParser(clangdLsp4j.getLanguageClient());
					CompletableFuture<LanguageServer> initializeFuture = clangdLsp4j.init(20);
					support.getProvider().setClangd(clangdLsp4j);
					synchronizer = new DocumentContentSynchronizer(new File(CPP_FILE_PATH).toURI(), initializeFuture);
					synchronizer.didOpen();
					addDocumentListener(synchronizer);
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					clangdLsp4j.shutdown(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		setupKeyStorke();
	}

	private void setupKeyStorke() {
		// Add global shortcuts
		java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		// 注册应用程序全局键盘事件, 所有的键盘事件都会被此事件监听器处理.
		toolkit.addAWTEventListener(new java.awt.event.AWTEventListener() {
			public void eventDispatched(java.awt.AWTEvent event) {
				if (event.getClass() == KeyEvent.class) {
					KeyEvent kE = ((KeyEvent) event);
					// 处理按键事件 Ctrl+S
					if (kE.getKeyCode() == KeyEvent.VK_S && kE.isControlDown() && !kE.isAltDown()
							&& kE.getID() == KeyEvent.KEY_PRESSED) {
						saveFile();
					}
				}
			}
		}, java.awt.AWTEvent.KEY_EVENT_MASK);
	}

	public void saveFile() {
		try {
			FileOutputStream fis = new FileOutputStream(new File(CPP_FILE_PATH));
			OutputStreamWriter isr = new OutputStreamWriter(fis, StandardCharsets.UTF_8);
			BufferedWriter r = new BufferedWriter(isr);

			textArea.write(r);

			r.close();

			synchronizer.didSave(textArea.getDocument());
		} catch (Exception ioe) {
			ioe.printStackTrace();
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			return;
		}
	}

	private void addDocumentListener(DocumentContentSynchronizer synchronizer) throws BadLocationException {
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			private String content = textArea.getText();

			@Override
			public void removeUpdate(DocumentEvent event) {
				int offset = event.getOffset();
				int length = event.getLength();
				DidChangeTextDocumentParams didChangeParams = synchronizer.createRemoveEvent(content, offset, length);
				synchronizer.didChange(didChangeParams);

				content = textArea.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent event) {
				try {
					int offset = event.getOffset();
					int length = event.getLength();
					String changedText = event.getDocument().getText(offset, length);

					DidChangeTextDocumentParams didChangeParams = synchronizer.createInsertEvent(content, offset,
							changedText);
					synchronizer.didChange(didChangeParams);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}

				content = textArea.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TextEditorDemo().setVisible(true));
	}

}
