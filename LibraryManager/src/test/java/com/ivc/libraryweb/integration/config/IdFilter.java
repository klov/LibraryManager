/*
   Информационно-вычислительный центр космодрома Байконур
*/

package com.ivc.libraryweb.integration.config;

import org.apache.log4j.Logger;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.filter.IColumnFilter;

/**
 *
 * @author Администратор
 */
public class IdFilter implements IColumnFilter {
  
    public IdFilter(){
        }
        
        @Override
        public boolean accept(String string, Column column) {
           if(!column.getColumnName().equals("ID")){
               return true;
           }
           return false;
        }
}