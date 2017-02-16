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
public class BookIdFilter implements IColumnFilter  {

    @Override
    public boolean accept(String string, Column column) {
      if(string.equals("book_label_detail")&&column.getColumnName().equals("BOOK_ID")){
      return false;
      }
      return true;
    }
  

}