package com.my.learning.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormText;

public class LinkSample {

	public static void main(String[] args) {
		final Display display = new Display ();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		shell.setText("LinkSample");
		
		FormText formText = new FormText(shell, SWT.NONE);
		formText.setText("<s>删除线</s>", true, false);
		
		//创建链接文本对象
		Link link = new Link(shell, SWT.NONE);
		//设置链接文本中的字符，与网页中的超级链接文本标记类似
		//<a href="链接地址">链接文本</a>
		//其中\n可以表示为换行符
		String text = "这是一个没有连接地址的<s>删除线</s>链接文本:<A>点击我</A>.\n\n这是一个有链接地址的链接文本,链接地址是www.sina.com.cn:<A href=\"www.sina.com.cn\">点击我</A>";
		link.setText(text);
		link.setSize(400, 400);
		//注册点击文本时事件
		link.addListener (SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				//显示出打印的文本
				System.out.println("您点击的是: " + event.text);
			}
		});
		shell.pack();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();

	}

}
