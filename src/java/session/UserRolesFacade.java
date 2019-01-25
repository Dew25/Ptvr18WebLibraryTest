/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.UserRoles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Melnikov
 */
@Stateless
public class UserRolesFacade extends AbstractFacade<UserRoles> {

    @PersistenceContext(unitName = "Ptvr16WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRolesFacade() {
        super(UserRoles.class);
    }

    public Boolean isRole(String roleName) {
        try {
            UserRoles ur = (UserRoles) em.createQuery("SELECT ur FROM UserRoles ur WHERE ur.role.name=:roleName")
                    .setParameter("roleName", roleName)
                    .getSingleResult();
            if(ur != null){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
}
