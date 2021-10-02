package app.dao;

import app.entity.Status;

public interface StatusDao {
	public Status findStatusByName(String theStatusName);
}
