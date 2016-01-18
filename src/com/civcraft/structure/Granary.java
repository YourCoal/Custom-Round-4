package com.civcraft.structure;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Location;

import com.civcraft.exception.CivException;
import com.civcraft.object.Town;

public class Granary extends Structure {

	protected Granary(Location center, String id, Town town)
			throws CivException {
		super(center, id, town);
	}
	
	public Granary(ResultSet rs) throws SQLException, CivException {
		super(rs);
	}
	
	@Override
	public boolean showOnDynmap() {
		return false;
	}
	
//	@Override
//	public String getDynmapDescription() {
//		return null;
//	}
//	
//	@Override
//	public String getMarkerIconName() {
//		return "chest";
//	}
}
