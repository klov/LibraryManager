/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.services.test.unit;

import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.repositories.CategoryRepository;
import com.ivc.libraryweb.repositories.CategoryRepositoryImpl;
import com.ivc.libraryweb.services.BookPropertiesServiceImpl;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
/**
 *
 * @author Vitaliy Denisov
 */
public class BookPropertiesImplTest {
       
   BookPropertiesServiceImpl bookProperties = new BookPropertiesServiceImpl();
   
   private Category validCategory =  new Category("validCategory") ;
   
   private CategoryRepository mockCategoryRepository;
   
   @Before
   public void before(){
       mockCategoryRepository = mock(CategoryRepositoryImpl.class); 
       bookProperties.setCategoryRepository(mockCategoryRepository);
   }
   //-----------------------------CreateCategory-------------------------------------
    @Test
    public void testCreateCategory(){
       when(mockCategoryRepository.create(validCategory)).thenReturn(validCategory);
       bookProperties.setCategoryRepository(mockCategoryRepository);
       assertEquals(validCategory,mockCategoryRepository.create(validCategory));
       verify(mockCategoryRepository).create(validCategory);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void CreateCategoryShouldThrowExceptionByNullCategory(){
             bookProperties.createCategory(null);
    }

     //------------------------------------DeleteCategory------------------------------
     @Test
     public void testDeleteCategory(){
         bookProperties.deleteCategory(validCategory);
         verify(mockCategoryRepository).delete(validCategory);
     }
     
     @Test(expected = IllegalArgumentException.class)
     public void DeleteCategoryShouldThrowException(){
         bookProperties.deleteCategory(null);
     }

     //--------------------------------------UpdateCategory------------------------------
     @Test
     public void testUpdateCategory(){
         when(mockCategoryRepository.update(validCategory)).thenReturn(validCategory);
         assertEquals(validCategory,bookProperties.updateCategory(validCategory));
         verify(mockCategoryRepository).update(validCategory);
     }
     
     @Test(expected = IllegalArgumentException.class)
     public void UpdateCategoryShouldThrowExceptionByNullCategory(){
         bookProperties.updateCategory(null);
     }

   //--------------------------------------FindCategory------------------------------------
     
     @Test
     public void testFindCategory(){
         when(mockCategoryRepository.find(validCategory)).thenReturn(validCategory);
         assertEquals(validCategory,bookProperties.findCategory(validCategory));
         verify(mockCategoryRepository).find(validCategory);
     }
     
     @Test(expected = IllegalArgumentException.class)
     public void FindCategoryShouldThrowExceptionByNullCategory(){
         bookProperties.findCategory(null);
     }
}
