package org.herba.plugin.junit.approvaltest;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class ApprovalTestButtonPlugin extends AbstractUIPlugin {
    public static final String PLUGIN_ID = "org.herba.plugins.approvaltestbutton";
    private static ApprovalTestButtonPlugin plugin;

    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        System.out.println("ApprovalTestButtonPlugin started");
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    public static ApprovalTestButtonPlugin getDefault() {
        return plugin;
    }
}
