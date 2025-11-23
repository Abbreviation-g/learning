package com.my.swt.cef.win64.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.part.ViewPart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.my.swt.cef.win64.browser.SWTCefBrowser;

public class CEFBrowserView extends ViewPart {

	private SWTCefBrowser browser;
	private List list;
	private Composite browserComp;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout());
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		SashForm sashForm = new SashForm(composite, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite left = new Composite(sashForm, SWT.NONE);
		left.setLayoutData(new GridData(GridData.FILL_BOTH));
		left.setLayout(new GridLayout());
		list = new List(left, SWT.SINGLE);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		java.util.List<NewGridData> sampleData = NewGridData.sampleData();
		for (NewGridData newGridData : sampleData) {
			list.add(newGridData.getName());
			list.setData(newGridData.getName(), newGridData);
		}
		
//		Button btn1 = new Button(left, SWT.PUSH);
//		btn1.setText("addSeriesTest();");
//		btn1.addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				browser.executeJavaScript("addSeriesTest();");
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent e) {
//				
//			}
//		});
		Button btn2 = new Button(left, SWT.PUSH);
		btn2.setText("addChartTest0();");
		btn2.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.executeJavaScript("addChartTest0();");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

//		CTabFolder tabFolder = new CTabFolder(sashForm, SWT.TOP);
		
		browserComp = new Composite(sashForm, SWT.NONE);
		browserComp.setLayout(new GridLayout());
		browserComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		browser = new SWTCefBrowser(browserComp);
		browser.setUrl("file://C:/work/sorce_code/mine/com.my.swt.cef.win64/resources/add_chart.html");

		sashForm.setWeights(new int[] { 10, 30 });

		initDragDrop();
	}

	private void initDragDrop() {
		DragSource source = new DragSource(list, DND.DROP_COPY);
		source.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		source.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				String[] selection = list.getSelection();
				String selectVariable = selection[0];
				Object data = list.getData(selectVariable);
				event.data = toJsonData(data);
			}

			public void dragStart(DragSourceEvent event) {
				event.doit = true;
			}
		});
		DropTarget targetMyType = new DropTarget(browser, DND.DROP_COPY | DND.DROP_DEFAULT);
		targetMyType.setTransfer(new Transfer[] { TextTransfer.getInstance() });

	}

	private Gson gson = new GsonBuilder().create();
	private String toJsonData(Object data) {
		String jsonStr =gson .toJson(data);
		
		return jsonStr;		
	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}
}
