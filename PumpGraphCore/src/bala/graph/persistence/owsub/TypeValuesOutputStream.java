/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bala.graph.persistence.owsub;

import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/**
 *
 * @author bala
 */
public class TypeValuesOutputStream  extends ObjectOutputStream {
      public TypeValuesOutputStream(OutputStream os) throws IOException{
          super(os);
      }
    @Override
    protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException{
       super.writeClassDescriptor(ObjectStreamClass.lookup(TypeValues.class));
        
    }
   
}
