package com.my.learning.swt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ByteArrayDragAndDropExample {
	static class MyType {
		String fileName;
		long fileLength;
		long lastModified;
	}

	static class MyTransfer extends ByteArrayTransfer {

		private static final String MYTYPENAME = "MytypeTransfer";
		private static final int MYTYPEID = registerType(MYTYPENAME);
		private static MyTransfer _instance = new MyTransfer();

		public static Transfer getInstance() {
			return _instance;
		}

		byte[] javaToByteArray(Object object) {
			MyType data = (MyType) object;
			try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				DataOutputStream writeOut = new DataOutputStream(out)){
				byte[] buffer = data.fileName.getBytes();
				writeOut.writeInt(buffer.length);
				writeOut.write(buffer);
				writeOut.writeLong(data.fileLength);
				writeOut.writeLong(data.lastModified);
				buffer = out.toByteArray();
				writeOut.close();
				return buffer;
			} catch (IOException e) {
			}
			return null;
		}

		Object byteArrayToJava(byte[] bytes) {
			MyType data = new MyType();
			try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
				DataInputStream readIn = new DataInputStream(in)){
				int size = readIn.readInt();
				byte[] buffer = new byte[size];
				readIn.read(buffer);
				data.fileName = new String(buffer);
				data.fileLength = readIn.readLong();
				data.lastModified = readIn.readLong();
				readIn.close();
			} catch (IOException ex) {
				return null;
			}
			return data;
		}

		@Override
		public void javaToNative(Object object, TransferData transferData) {
			if (!checkMyType(object) || !isSupportedType(transferData)) {
				DND.error(DND.ERROR_INVALID_DATA);
			}
			byte[] buffer = javaToByteArray(object);
			super.javaToNative(buffer, transferData);
		}

		@Override
		public Object nativeToJava(TransferData transferData) {
			if (isSupportedType(transferData)) {
				byte[] buffer = (byte[]) super.nativeToJava(transferData);
				if (buffer == null)
					return null;
				return byteArrayToJava(buffer);
			}
			return null;
		}

		@Override
		protected String[] getTypeNames() {
			return new String[] { MYTYPENAME };
		}

		@Override
		protected int[] getTypeIds() {
			return new int[] { MYTYPEID };
		}

		boolean checkMyType(Object object) {
			return object != null && object instanceof MyType;
		}

		@Override
		protected boolean validate(Object object) {
			return checkMyType(object);
		}
	}

	/*
	 * The data being transferred is an <bold>array of type MyType2</bold>
	 * where MyType2 is define as:
	 */
	static class MyType2 extends MyType {
		String version;
	}

	static class MyTransfer2 extends MyTransfer {

		private static final String MYTYPE2NAME = "Mytype2Transfer";
		private static final int MYTYPE2ID = registerType(MYTYPE2NAME);
		private static MyTransfer _instance = new MyTransfer2();

		public static Transfer getInstance() {
			return _instance;
		}

		@Override
		protected String[] getTypeNames() {
			return new String[] { MYTYPE2NAME };
		}

		@Override
		protected int[] getTypeIds() {
			return new int[] { MYTYPE2ID };
		}

		@Override
		byte[] javaToByteArray(Object object) {
			MyType2 data = (MyType2) object;
			try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				DataOutputStream writeOut = new DataOutputStream(out)) {
				byte[] buffer = data.fileName.getBytes();
				writeOut.writeInt(buffer.length);
				writeOut.write(buffer);
				writeOut.writeLong(data.fileLength);
				writeOut.writeLong(data.lastModified);
				buffer = data.version.getBytes();
				writeOut.writeInt(buffer.length);
				writeOut.write(buffer);
				buffer = out.toByteArray();
				writeOut.close();
				return buffer;
			} catch (IOException e) {
			}
			return null;
		}

		@Override
		Object byteArrayToJava(byte[] bytes) {
			MyType2 data = new MyType2();
			try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
				DataInputStream readIn = new DataInputStream(in)) {
				int size = readIn.readInt();
				byte[] buffer = new byte[size];
				readIn.read(buffer);
				data.fileName = new String(buffer);
				data.fileLength = readIn.readLong();
				data.lastModified = readIn.readLong();
				size = readIn.readInt();
				buffer = new byte[size];
				readIn.read(buffer);
				data.version = new String(buffer);
				readIn.close();
			} catch (IOException ex) {
				return null;
			}
			return data;
		}

		@Override
		public void javaToNative(Object object, TransferData transferData) {
			if (!checkMyType2(object)) {
				DND.error(DND.ERROR_INVALID_DATA);
			}
			super.javaToNative(object, transferData);
		}

		boolean checkMyType2(Object object) {
			if (!checkMyType(object))
				return false;
			return object != null && object instanceof MyType2;
		}

		@Override
		protected boolean validate(Object object) {
			return checkMyType2(object);
		}
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Label label1 = new Label(shell, SWT.BORDER | SWT.WRAP);
		label1.setText("Drag Source for MyData and MyData2");
		final Label label2 = new Label(shell, SWT.BORDER | SWT.WRAP);
		label2.setText("Drop Target for MyData");
		final Label label3 = new Label(shell, SWT.BORDER | SWT.WRAP);
		label3.setText("Drop Target for MyData2");

		DragSource source = new DragSource(label1, DND.DROP_COPY);
		source.setTransfer(MyTransfer.getInstance(), MyTransfer2.getInstance());
		source.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				MyType2 myType = new MyType2();
				myType.fileName = "abc.txt";
				myType.fileLength = 1000;
				myType.lastModified = 12312313;
				myType.version = "version 2";
				event.data = myType;
			}
		});
		DropTarget targetMyType = new DropTarget(label2, DND.DROP_COPY | DND.DROP_DEFAULT);
		targetMyType.setTransfer(MyTransfer.getInstance());
		targetMyType.addDropListener(new DropTargetAdapter() {
			@Override
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;
			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;
			}

			@Override
			public void drop(DropTargetEvent event) {
				if (event.data != null) {
					MyType myType = (MyType) event.data;
					if (myType != null) {
						String string = "MyType: " + myType.fileName;
						label2.setText(string);
					}
				}
			}

		});
		DropTarget targetMyType2 = new DropTarget(label3, DND.DROP_COPY	| DND.DROP_DEFAULT);
		targetMyType2.setTransfer(MyTransfer2.getInstance());
		targetMyType2.addDropListener(new DropTargetAdapter() {
			@Override
			public void dragEnter(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;
			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT)
					event.detail = DND.DROP_COPY;
			}

			@Override
			public void drop(DropTargetEvent event) {
				if (event.data != null) {
					MyType2 myType = (MyType2) event.data;
					if (myType != null) {
						String string = "MyType2: " + myType.fileName + ":"
								+ myType.version;
						label3.setText(string);
					}
				}
			}

		});
		shell.setSize(300, 200);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
