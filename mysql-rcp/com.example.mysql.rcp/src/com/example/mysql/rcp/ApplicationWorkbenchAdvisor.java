package com.example.mysql.rcp;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import com.example.mysql.rcp.ui.Perspective;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
    @Override
    public String getInitialWindowPerspectiveId() {
        return Perspective.ID;
    }

    @Override
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }
}
