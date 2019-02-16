/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SecurityLogic;

import entity.Role;
import entity.User;
import entity.UserRoles;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.RoleFacade;
import session.UserRolesFacade;

/**
 *
 * @author Melnikov
 */
public class RoleLogic {
    static enum rolesEnum{ADMINISTRATOR,MANAGER,USER};
    private RoleFacade roleFacade;
    private UserRolesFacade userRolesFacade;
    public RoleLogic() {
        Context context;
        try {
            context = new InitialContext();
            this.userRolesFacade = (UserRolesFacade) context.lookup("java:module/UserRolesFacade");
            this.roleFacade = (RoleFacade) context.lookup("java:module/RoleFacade");
        } catch (NamingException ex) {
            Logger.getLogger(RoleLogic.class.getName()).log(Level.SEVERE, "Не удалось найти Бин", ex);
        }
    }
    
    public void setRole(Role role, User user){
        this.removeAllRoles(user); 
        Role newRole;
        UserRoles ur=new UserRoles();
        ur.setUser(user);
        
        if(null != role.getName())
            switch (role.getName()) {
                case "ADMINISTRATOR":
                    newRole = this.getRole(ROLE.ADMINISTRATOR.toString());
                    ur.setRole(newRole);
                    userRolesFacade.create(ur);
                    newRole = this.getRole(ROLE.MANAGER.toString());
                    ur.setRole(newRole);
                    userRolesFacade.create(ur);
                    newRole = this.getRole(ROLE.USER.toString());
                    ur.setRole(newRole);
                    userRolesFacade.create(ur);
                    break;
                case "MANAGER":
                    newRole = this.getRole(ROLE.MANAGER.toString());
                    ur.setRole(newRole);
                    userRolesFacade.create(ur);
                    newRole = this.getRole(ROLE.USER.toString());
                    ur.setRole(newRole);
                    userRolesFacade.create(ur);
                    break;
                case "USER":
                    newRole = this.getRole(ROLE.USER.toString());
                    ur.setRole(newRole);
                    userRolesFacade.create(ur);
                    break;
                default:
                    break;
            }
    }
    public  Role getRole(String roleName){
        List<Role> roles = roleFacade.findAll();
        for(Role role: roles){
            if(roleName.equals(role.getName())){
                return role;
            }
        }
        return null;
    }
    public Role getRole(User user){
        List<UserRoles> listUserRoles = userRolesFacade.findUserRoles(user);
        List<String> nameRoles = new ArrayList<>();
        for(UserRoles ur : listUserRoles){
            nameRoles.add(ur.getRole().getName());
        }
        if(nameRoles.contains(ROLE.ADMINISTRATOR.toString())){
            return getRole(ROLE.ADMINISTRATOR.toString());
        }
        if(nameRoles.contains(ROLE.MANAGER.toString())){
            return getRole(ROLE.MANAGER.toString());
        }
        if(nameRoles.contains(ROLE.USER.toString())){
            return getRole(ROLE.USER.toString());
        }else{
            return null;
        }
    }
    private boolean removeAllRoles(User user) {
        List<UserRoles> userRoles = userRolesFacade.findUserRoles(user);
        userRoles.forEach((userRole) -> {
            userRolesFacade.remove(userRole);
        });
        return true;
    }
    public void setRole(User user, Role newRole) {
        UserRoles ur = new UserRoles(user, newRole);
        userRolesFacade.create(ur);
    }
    public boolean isRole(String roleName,User user){
        boolean res=false;
        List<UserRoles> listUserRoles = userRolesFacade.findUserRoles(user);
        List<String> listRolesByUser = new ArrayList<>();
        for(UserRoles ur : listUserRoles){
            listRolesByUser.add(ur.getRole().getName());
        }
        return listRolesByUser.contains(roleName);
    }
}
