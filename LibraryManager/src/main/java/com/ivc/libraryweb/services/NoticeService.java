/*
   Информационно-вычислительный центр космодрома Байконур
*/

package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Notice;

/**
 *
 * @author Администратор
 */
public interface NoticeService {

   Notice createNotice(Notice notice,Document document);
   
   void deleteNotice(Notice notice);
}
