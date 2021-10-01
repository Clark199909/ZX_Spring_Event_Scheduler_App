package app.dao;

import app.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
