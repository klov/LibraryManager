/*
   Информационно-вычислительный центр космодрома Байконур
*/

package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Notice;
import java.util.List;

public interface NoticeRepository {
  List<Notice> findAll();
    

    Notice find(Notice notice);

    Notice create(Notice notice);

    Notice update(Notice notice);

    void delete(Notice notice);
}