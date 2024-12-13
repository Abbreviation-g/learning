package com.my.lsp4j.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.ClientInfo;
import org.eclipse.lsp4j.CodeActionCapabilities;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.CodeActionKindCapabilities;
import org.eclipse.lsp4j.CodeActionLiteralSupportCapabilities;
import org.eclipse.lsp4j.CodeActionResolveSupportCapabilities;
import org.eclipse.lsp4j.CodeLensCapabilities;
import org.eclipse.lsp4j.ColorProviderCapabilities;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemCapabilities;
import org.eclipse.lsp4j.CompletionItemInsertTextModeSupportCapabilities;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DefinitionCapabilities;
import org.eclipse.lsp4j.DocumentHighlightCapabilities;
import org.eclipse.lsp4j.DocumentLinkCapabilities;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.FailureHandlingKind;
import org.eclipse.lsp4j.FoldingRangeCapabilities;
import org.eclipse.lsp4j.FormattingCapabilities;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverCapabilities;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.lsp4j.InlayHintCapabilities;
import org.eclipse.lsp4j.InsertTextMode;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.RangeFormattingCapabilities;
import org.eclipse.lsp4j.ReferencesCapabilities;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.ResourceOperationKind;
import org.eclipse.lsp4j.ShowDocumentCapabilities;
import org.eclipse.lsp4j.SignatureHelpCapabilities;
import org.eclipse.lsp4j.SymbolCapabilities;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.SymbolKindCapabilities;
import org.eclipse.lsp4j.SynchronizationCapabilities;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TypeDefinitionCapabilities;
import org.eclipse.lsp4j.WindowClientCapabilities;
import org.eclipse.lsp4j.WindowShowMessageRequestCapabilities;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEditCapabilities;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageServer;

public class ClangdLsp4j {
	static final String CLAND_FILE_PATH = "C:\\sw\\clangd_15.0.0\\bin\\clangd.exe";

	private String cpp_file_path;
	private URI root_uri;

	private ProcessStreamConnectionProvider provider;
	private Future<Void> launcherFuture;
	private Launcher<LanguageServer> launcher;
	private LanguageServer languageServerInstance;

	private MockLanguageClient languageClient;

	public ClangdLsp4j(String cpp_file_path) {
		this.cpp_file_path = cpp_file_path;
		root_uri = new File(cpp_file_path).toURI();
	}

	public LanguageServer getLanguageServer() {
		return languageServerInstance;
	}

	public void start() throws IOException, InterruptedException {
		provider = new ProcessStreamConnectionProvider();
		provider.setCommands(Arrays.asList(CLAND_FILE_PATH, "--log=verbose", "--pretty"));
		Map<String, String> environment = new HashMap<>();
		environment.put("CPLUS_INCLUDE_PATH",
				"C:\\MinGW\\lib\\gcc\\mingw32\\9.2.0\\include\\c++;"
						+ "C:\\MinGW\\lib\\gcc\\mingw32\\9.2.0\\include\\c++\\mingw32;" + "C:\\MinGW\\include;"
						+ "C:\\MinGW\\lib\\gcc\\mingw32\\9.2.0\\include");
		provider.setEnvironment(environment);

		provider.start();

		languageClient = new MockLanguageClient();
		launcher = createLauncherBuilder().setLocalService(languageClient).setInput(provider.getInputStream())
				.setOutput(provider.getOutputStream()).setRemoteInterface(LanguageServer.class)
				.setExecutorService(Executors.newCachedThreadPool(Executors.defaultThreadFactory())).create();
		launcherFuture = launcher.startListening();
		languageServerInstance = launcher.getRemoteProxy();
	}

	public MockLanguageClient getLanguageClient() {
		return languageClient;
	}

	public CompletableFuture<LanguageServer> init(int timeoutSeconds) throws InterruptedException {
		CompletableFuture<LanguageServer> initializeFuture = CompletableFuture.supplyAsync(() -> {
			languageServerInstance.initialize(createInitializeParams());
			languageServerInstance.initialized(new InitializedParams());
			return languageServerInstance;
		});

		return initializeFuture;
	}

	public String hover(int line, int characterOffsetOfLine)
			throws InterruptedException, ExecutionException, TimeoutException {
		Hover hover = languageServerInstance.getTextDocumentService()
				.hover(createHoverParams(line, characterOffsetOfLine)).get(20, TimeUnit.SECONDS);
		if (hover == null) {
			return null;
		}
		MarkupContent content = hover.getContents().getRight();
		return content.getValue();
	}

	public Either<List<CompletionItem>, CompletionList> completion(int line, int characterOffsetOfLine)
			throws InterruptedException, ExecutionException, TimeoutException {
		Position start = new Position(line, characterOffsetOfLine);
		CompletionParams param = new CompletionParams();
		param.setPosition(start);
		TextDocumentIdentifier id = new TextDocumentIdentifier();
		id.setUri(new File(cpp_file_path).toURI().toString());
		param.setTextDocument(id);

		Either<List<CompletionItem>, CompletionList> either = languageServerInstance.getTextDocumentService()
				.completion(param).get(20, TimeUnit.SECONDS);
		return either;
	}

	public void shutdown(int timeoutSeconds) throws InterruptedException {
		Runnable shutdownKillAndStopFutureAndProvider = () -> {
			if (languageServerInstance != null) {
				CompletableFuture<Object> shutdown = languageServerInstance.shutdown();
				try {
					shutdown.get(5, TimeUnit.SECONDS);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			if (launcherFuture != null) {
				launcherFuture.cancel(true);
			}

			if (languageServerInstance != null) {
				languageServerInstance.exit();
			}

			if (provider != null) {
				provider.stop();
			}
		};
		CompletableFuture.runAsync(shutdownKillAndStopFutureAndProvider);
	}

	public InitializeParams createInitializeParams() {
		WorkspaceClientCapabilities workspaceClientCapabilities = new WorkspaceClientCapabilities();
		workspaceClientCapabilities.setApplyEdit(Boolean.TRUE);
		workspaceClientCapabilities.setExecuteCommand(new ExecuteCommandCapabilities(Boolean.TRUE));
		workspaceClientCapabilities.setSymbol(new SymbolCapabilities(Boolean.TRUE));
		workspaceClientCapabilities.setWorkspaceFolders(Boolean.TRUE);
		WorkspaceEditCapabilities editCapabilities = new WorkspaceEditCapabilities();
		editCapabilities.setDocumentChanges(Boolean.TRUE);
		editCapabilities.setResourceOperations(Arrays.asList(ResourceOperationKind.Create, ResourceOperationKind.Delete,
				ResourceOperationKind.Rename));
		editCapabilities.setFailureHandling(FailureHandlingKind.Undo);
		workspaceClientCapabilities.setWorkspaceEdit(editCapabilities);
		TextDocumentClientCapabilities textDocumentClientCapabilities = new TextDocumentClientCapabilities();
		CodeActionCapabilities codeAction = new CodeActionCapabilities(new CodeActionLiteralSupportCapabilities(
				new CodeActionKindCapabilities(Arrays.asList(CodeActionKind.QuickFix, CodeActionKind.Refactor,
						CodeActionKind.RefactorExtract, CodeActionKind.RefactorInline, CodeActionKind.RefactorRewrite,
						CodeActionKind.Source, CodeActionKind.SourceOrganizeImports))),
				true);
		codeAction.setDataSupport(true);
		codeAction.setResolveSupport(new CodeActionResolveSupportCapabilities(Arrays.asList("edit"))); //$NON-NLS-1$
		textDocumentClientCapabilities.setCodeAction(codeAction);
		textDocumentClientCapabilities.setCodeLens(new CodeLensCapabilities());
		textDocumentClientCapabilities.setInlayHint(new InlayHintCapabilities());
		textDocumentClientCapabilities.setColorProvider(new ColorProviderCapabilities());
		CompletionItemCapabilities completionItemCapabilities = new CompletionItemCapabilities(Boolean.TRUE);
		completionItemCapabilities.setDocumentationFormat(Arrays.asList(MarkupKind.MARKDOWN, MarkupKind.PLAINTEXT));
		completionItemCapabilities.setInsertTextModeSupport(new CompletionItemInsertTextModeSupportCapabilities(
				Arrays.asList(InsertTextMode.AsIs, InsertTextMode.AdjustIndentation)));
		textDocumentClientCapabilities.setCompletion(new CompletionCapabilities(completionItemCapabilities));
		DefinitionCapabilities definitionCapabilities = new DefinitionCapabilities();
		definitionCapabilities.setLinkSupport(Boolean.TRUE);
		textDocumentClientCapabilities.setDefinition(definitionCapabilities);
		TypeDefinitionCapabilities typeDefinitionCapabilities = new TypeDefinitionCapabilities();
		typeDefinitionCapabilities.setLinkSupport(Boolean.TRUE);
		textDocumentClientCapabilities.setTypeDefinition(typeDefinitionCapabilities);
		textDocumentClientCapabilities.setDocumentHighlight(new DocumentHighlightCapabilities());
		textDocumentClientCapabilities.setDocumentLink(new DocumentLinkCapabilities());
		DocumentSymbolCapabilities documentSymbol = new DocumentSymbolCapabilities();
		documentSymbol.setHierarchicalDocumentSymbolSupport(true);
		documentSymbol.setSymbolKind(new SymbolKindCapabilities(Arrays.asList(SymbolKind.Array, SymbolKind.Boolean,
				SymbolKind.Class, SymbolKind.Constant, SymbolKind.Constructor, SymbolKind.Enum, SymbolKind.EnumMember,
				SymbolKind.Event, SymbolKind.Field, SymbolKind.File, SymbolKind.Function, SymbolKind.Interface,
				SymbolKind.Key, SymbolKind.Method, SymbolKind.Module, SymbolKind.Namespace, SymbolKind.Null,
				SymbolKind.Number, SymbolKind.Object, SymbolKind.Operator, SymbolKind.Package, SymbolKind.Property,
				SymbolKind.String, SymbolKind.Struct, SymbolKind.TypeParameter, SymbolKind.Variable)));
		textDocumentClientCapabilities.setDocumentSymbol(documentSymbol);
		textDocumentClientCapabilities.setFoldingRange(new FoldingRangeCapabilities());
		textDocumentClientCapabilities.setFormatting(new FormattingCapabilities(Boolean.TRUE));
		HoverCapabilities hoverCapabilities = new HoverCapabilities();
		hoverCapabilities.setContentFormat(Arrays.asList(MarkupKind.MARKDOWN, MarkupKind.PLAINTEXT));
		textDocumentClientCapabilities.setHover(hoverCapabilities);
		textDocumentClientCapabilities.setOnTypeFormatting(null); // TODO
		textDocumentClientCapabilities.setRangeFormatting(new RangeFormattingCapabilities());
		textDocumentClientCapabilities.setReferences(new ReferencesCapabilities());
		final var renameCapabilities = new RenameCapabilities();
		renameCapabilities.setPrepareSupport(true);
		textDocumentClientCapabilities.setRename(renameCapabilities);
		textDocumentClientCapabilities.setSignatureHelp(new SignatureHelpCapabilities());
		textDocumentClientCapabilities
				.setSynchronization(new SynchronizationCapabilities(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE));

		WindowClientCapabilities windowClientCapabilities = getWindowClientCapabilities();

		InitializeParams initParams = new InitializeParams();
		initParams.setProcessId((int) ProcessHandle.current().pid());

		initParams.setRootUri(root_uri.toString());
		initParams.setRootPath(root_uri.getPath());

		initParams.setCapabilities(new ClientCapabilities(workspaceClientCapabilities, textDocumentClientCapabilities,
				windowClientCapabilities, null));
		initParams.setClientInfo(getClientInfo());
		initParams.setTrace("off");

		initParams.setWorkspaceFolders(Arrays.asList(toWorkspaceFolder()));

		return initParams;
	}

	public WorkspaceFolder toWorkspaceFolder() {
		WorkspaceFolder folder = new WorkspaceFolder();
		File parentFile = new File(cpp_file_path).getParentFile();
		URI folderUri = parentFile.toURI();
		folder.setUri(folderUri != null ? folderUri.toString() : ""); //$NON-NLS-1$
		folder.setName(parentFile.getName());
		return folder;
	}

	public ClientInfo getClientInfo() {
		ClientInfo clientInfo = new ClientInfo("Eclipse IDE", "1.0");
		return clientInfo;
	}

	public WindowClientCapabilities getWindowClientCapabilities() {
		WindowClientCapabilities windowClientCapabilities = new WindowClientCapabilities();
		windowClientCapabilities.setShowDocument(new ShowDocumentCapabilities(true));
		windowClientCapabilities.setWorkDoneProgress(true);
		windowClientCapabilities.setShowMessage(new WindowShowMessageRequestCapabilities());
		return windowClientCapabilities;
	}

	public HoverParams createHoverParams(int line, int characterOffsetOfLine) {
		HoverParams param = new HoverParams();
		Position res = new Position();
		res.setLine(line);
		res.setCharacter(characterOffsetOfLine);
		param.setPosition(res);
		TextDocumentIdentifier id = new TextDocumentIdentifier();
		id.setUri(new File(cpp_file_path).toURI().toString());
		param.setTextDocument(id);
		return param;
	}

	public static <S extends LanguageServer> Launcher.Builder<S> createLauncherBuilder() {
		return new Launcher.Builder<>();
	}
}
