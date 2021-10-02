package app.dao;

import app.entity.Type;

public interface TypeDao {
	
	public Type findTypeByName(String theTypeName);
}
