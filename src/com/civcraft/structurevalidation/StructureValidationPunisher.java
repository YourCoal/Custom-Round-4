package com.civcraft.structurevalidation;

import java.util.Iterator;
import java.util.Map.Entry;

import com.civcraft.main.CivGlobal;
import com.civcraft.structure.Structure;
import com.civcraft.util.BlockCoord;

public class StructureValidationPunisher implements Runnable {

	@Override
	public void run() {
		if (!StructureValidator.isEnabled()) {
			return;
		}

		Iterator<Entry<BlockCoord, Structure>> structIter = CivGlobal.getStructureIterator();
		while (structIter.hasNext()) {
			Structure struct = structIter.next().getValue();
			if (struct.getCiv().isAdminCiv()) {
				continue;
			}
		
			if (struct.validated && struct.isActive()) {
				if (!struct.isValid()) {
					struct.onInvalidPunish();					
				}
			}
		}
	}

}
