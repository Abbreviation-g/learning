package com.example.mysql.rcp.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.example.mysql.rcp.db.ConnectionSettings;
import com.example.mysql.rcp.db.ConnectionTestResult;
import com.example.mysql.rcp.db.MySqlConnectionService;

public class MySqlConnectionView extends ViewPart {
    public static final String ID = "com.example.mysql.rcp.ui.mysqlConnectionView";

    private final MySqlConnectionService connectionService = new MySqlConnectionService();

    private Text hostText;
    private Text portText;
    private Text databaseText;
    private Text userText;
    private Text passwordText;
    private Text urlText;
    private Text statusText;
    private Button connectButton;

    @Override
    public void createPartControl(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setLayout(new GridLayout(2, false));

        hostText = createField(content, "Host", "127.0.0.1", SWT.BORDER);
        portText = createField(content, "Port", "3306", SWT.BORDER);
        databaseText = createField(content, "Database", "", SWT.BORDER);
        userText = createField(content, "User", "root", SWT.BORDER);
        passwordText = createField(content, "Password", "password", SWT.BORDER | SWT.PASSWORD);

        createLabel(content, "JDBC URL");
        urlText = new Text(content, SWT.BORDER | SWT.READ_ONLY);
        urlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        new Label(content, SWT.NONE);
        connectButton = new Button(content, SWT.PUSH);
        connectButton.setText("Connect / Test");
        connectButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
        connectButton.addListener(SWT.Selection, event -> testConnection());

        createLabel(content, "Status");
        statusText = new Text(content, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);
        GridData statusData = new GridData(SWT.FILL, SWT.FILL, true, true);
        statusData.minimumHeight = 220;
        statusText.setLayoutData(statusData);

        ModifyListener urlUpdater = event -> updateJdbcUrl();
        hostText.addModifyListener(urlUpdater);
        portText.addModifyListener(urlUpdater);
        databaseText.addModifyListener(urlUpdater);
        updateJdbcUrl();
        statusText.setText("Ready.");
    }

    @Override
    public void setFocus() {
        hostText.setFocus();
    }

    private Text createField(Composite parent, String label, String value, int style) {
        createLabel(parent, label);
        Text text = new Text(parent, style);
        text.setText(value);
        text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        return text;
    }

    private void createLabel(Composite parent, String value) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(value);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
    }

    private void updateJdbcUrl() {
        if (urlText == null || urlText.isDisposed()) {
            return;
        }
        urlText.setText(settings().displayUrl());
    }

    private ConnectionSettings settings() {
        return new ConnectionSettings(
                hostText.getText(),
                portText.getText(),
                databaseText.getText(),
                userText.getText(),
                passwordText.getText());
    }

    private void testConnection() {
        ConnectionSettings currentSettings = settings();
        connectButton.setEnabled(false);
        statusText.setText("Connecting to " + currentSettings.displayUrl() + " ...");

        Thread worker = new Thread(() -> {
            try {
                ConnectionTestResult result = connectionService.test(currentSettings);
                updateStatus("Connection succeeded.\n"
                        + "MySQL version: " + result.version() + "\n"
                        + "Catalog: " + valueOrEmpty(result.catalog()) + "\n"
                        + "URL: " + result.url());
            } catch (Exception exception) {
                updateStatus("Connection failed.\n"
                        + exception.getClass().getName() + ": " + exception.getMessage()
                        + "\n\n" + stackTrace(exception));
            }
        }, "mysql-connection-test");
        worker.setDaemon(true);
        worker.start();
    }

    private void updateStatus(String message) {
        Display display = getSite().getShell().getDisplay();
        display.asyncExec(() -> {
            if (statusText == null || statusText.isDisposed()) {
                return;
            }
            statusText.setText(message);
            connectButton.setEnabled(true);
        });
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }

    private String stackTrace(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
