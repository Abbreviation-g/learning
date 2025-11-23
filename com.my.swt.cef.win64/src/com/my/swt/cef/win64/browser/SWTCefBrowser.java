package com.my.swt.cef.win64.browser;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.my.swt.cef.win64.SystemPathUtil;

public class SWTCefBrowser extends Composite {
	
	public static final String BROWSER_ID = "com.my.swt.cef.win64.browser";

	private CefApp cefApp_;
    private CefClient client_;
    private CefBrowser browser_;
    
    private JTextField address_;
	private Frame frame_;

	String startURL = "about:blank";
	public SWTCefBrowser(Composite parent) {
		super(parent, SWT.EMBEDDED);
		this.setLayout(new FillLayout());
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		super.setDragDetect(true);
	}

	/**
	 * …Ë÷√‰Ø¿¿µÿ÷∑
	 * @param url
	 * @return
	 */
	public boolean setUrl(String url) {
		if(browser_ == null){
			createBrowser(url);
		}
		if (browser_.isLoading()) {
			browser_.stopLoad();
		}
		browser_.loadURL(url != null ? url : "about:blank");
		return true;
	}
	

	public void executeJavaScript(String script){
		browser_.executeJavaScript(script, "about:blank", 1);		
	}
	
	private void createBrowser(String url) {
		
        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = false;
        cefApp_ = CefApp.getInstance(settings);

        client_ = cefApp_.createClient();

        browser_ = client_.createBrowser(url, false,	false);
        
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
            	
            }
        });

        address_ = new JTextField(url, 100);
        address_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.loadURL(address_.getText());
            }
        });

        // Update the address field when the browser URL changes.
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                address_.setText(url);
            }
        });
        // Clear focus from the address field when the browser gains focus.
        client_.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
            }
        });
        
        frame_ = SWT_AWT.new_Frame(this);
        frame_.add(address_,BorderLayout.NORTH);
        frame_.add(browser_.getUIComponent(), BorderLayout.CENTER);
		frame_.pack();
		frame_.setVisible(true);
		
	}
	
	@Override
	public void dispose() {
		frame_.dispose();
		browser_.doClose();
		
		super.dispose();
	}

	@Override
	public boolean forceFocus() {
		return super.forceFocus();
	}
	
	@Override
	public boolean setFocus() {
		return true;
	}
	
	@Override
	public boolean isFocusControl() {
		return true;
	}
	
	@Override
	public Point computeSize(int wHint, int hHint) {
		Point computeSize = this.computeSize(wHint, hHint, true);
		return computeSize;
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point size = getParent().getSize();
		return size;
	}
	
	public static void main(String[] args) {

		String dir = "lib/win64"; 
		SystemPathUtil.addLibraryPath(dir);
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		
		SWTCefBrowser browser = new SWTCefBrowser (shell);
//		browser.setUrl("www.baidu.com");
		browser.setUrl("file:///C:/Users/guoenjing/Desktop/note/add_series.html");
		
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}

