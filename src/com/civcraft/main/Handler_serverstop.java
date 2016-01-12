package com.civcraft.main;

import java.util.Iterator;
import java.util.Map.Entry;

import com.civcraft.structure.Structure;
import com.civcraft.util.BlockCoord;

public class Handler_serverstop extends Thread {
	
	public void run() {
		Iterator<Entry<BlockCoord, Structure>> iter = CivGlobal.getStructureIterator();
		while(iter.hasNext()) { Structure struct = iter.next().getValue();	
			struct.onUnload();
		}
	}
}
