package com.my.learning.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ImageSaveExample {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

        SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
        sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));
        Composite leftComposite = new Composite(sashForm, SWT.NONE);
        leftComposite.setLayout(new GridLayout());
        Button saveImgBtn = new Button(leftComposite, SWT.PUSH);
        saveImgBtn.setText("保存图片");

        Composite rightComposite = new Composite(sashForm, SWT.NONE);
        rightComposite.setLayout(new FillLayout());
        Browser browser = new Browser(rightComposite, SWT.BORDER);
        browser.setUrl("https://www.baidu.com/");
        browser.setLayoutData(new GridData(GridData.FILL_BOTH));

        sashForm.setWeights(new int[] { 1, 4 });

        saveImgBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                GC gc = new GC(browser);
                Image image = new Image(display, browser.getSize().x, browser.getSize().y);
                gc.copyArea(image, 0, 0);

                ImageLoader imageLoader = new ImageLoader();
                imageLoader.data = new ImageData[] { image.getImageData() };
                imageLoader.save("C:/Temp/save.png", SWT.IMAGE_PNG);
            }
        });

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
