/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.Cover;
import entity.CoverBook;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class CoverBookFacade extends AbstractFacade<CoverBook> {

    @PersistenceContext(unitName = "Ptvr16WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoverBookFacade() {
        super(CoverBook.class);
    }
    
    public Cover findCover(Book book) {
        try {
            CoverBook coverBook = (CoverBook) em.createQuery("SELECT cb FROM CoverBook cb WHERE cb.book = :book")
                    .setParameter("book", book)
                    .getSingleResult();
            return coverBook.getCover();
        } catch (Exception e) {
            return null;
        }
    }
    
}
