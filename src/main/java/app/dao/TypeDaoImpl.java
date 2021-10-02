package app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import app.entity.Type;

@Repository
public class TypeDaoImpl implements TypeDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Type findTypeByName(String theTypeName) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using name
		Query<Type> theQuery = currentSession.createQuery("from Type where type=:typeName", Type.class);
		theQuery.setParameter("typeName", theTypeName);
				
		Type theType = null;
				
		try {
			theType = theQuery.getSingleResult();
		} catch (Exception e) {
			theType = null;
		}
				
			return theType;
	}

}
