/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bala.graph.persistence.mb;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 *
 * @author bala
 */
public class EntryValuesInputStream  extends ObjectInputStream {
      public EntryValuesInputStream(InputStream is) throws IOException{
          super(is);
      }
    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
         ObjectStreamClass classDesc=super.readClassDescriptor();
         System.out.println("Got class name :"+classDesc.getName());
         if(classDesc.getName().equals("EntryValues")){
             System.out.println("Returning class name :"+ObjectStreamClass.lookup(bala.graph.persistence.mb.EntryValues.class).getName());
            return ObjectStreamClass.lookup(bala.graph.persistence.mb.EntryValues.class);
        }
         return classDesc;
    }
   
}
