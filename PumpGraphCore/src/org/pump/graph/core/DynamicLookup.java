/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pump.graph.core;

/**
 *
 * @author ANNAIENG
 */
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

public class DynamicLookup extends AbstractLookup {
     private static InstanceContent content = new InstanceContent();

    private static DynamicLookup Lookup = new DynamicLookup(content);

   private DynamicLookup(InstanceContent content) {
      super(content);
      this.content=content;
   }
   public static void add(Object instance) {
      content.add(instance);
   }
   public static void remove(Object instance) {
      content.remove(instance);
   }
   public static DynamicLookup getDefault(){
      return Lookup;
   }

}