/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bala.graph.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CookieAction;

public final class EvfCookieAction extends CookieAction {


    //private final Project context=null;
    // public MyOpenEvfAction(Project context) {
    //this.context = context;
    // }
    @Override
    protected Class<?>[] cookieClasses() {
        return new Class[]{EvfDataObject.class};
    }

    protected boolean surviveFocusChange() {
        return false;
    }



    @Override
    protected int mode() {
        // throw new UnsupportedOperationException("Not supported yet.");
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    protected void performAction(Node[] nodes) {
        OpenEvfCookie pc = nodes[0].getLookup().lookup(OpenEvfCookie.class);
        pc.OpenEvf();
    }

    @Override
    public String getName() {
        return "ok:";
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        // return null;
        // throw new UnsupportedOperationException("Not supported yet.");
    }
}

   

