/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bala.graph.graph;

/**
 *
 * @author ANNAIENG
 */
public class OpenEvfSupport  implements OpenEvfCookie {
private EvfDataObject evf=null;
    @Override
    public void OpenEvf() {
        System.out.println("opening evf ...");
    }

    public OpenEvfSupport(EvfDataObject evf) {
    this.evf=evf;
    }

}
