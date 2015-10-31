package com.civcraft.event;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.civcraft.camp.CampHourlyTick;
import com.civcraft.config.CivSettings;
import com.civcraft.exception.CivException;
import com.civcraft.exception.InvalidConfiguration;
import com.civcraft.main.CivLog;
import com.civcraft.main.CivMessage;
import com.civcraft.mobs.MobSpawner;
import com.civcraft.mobs.timers.MobDespawnTimer;
import com.civcraft.threading.TaskMaster;
import com.civcraft.threading.tasks.CultureProcessAsyncTask;
import com.civcraft.threading.timers.EffectEventTimer;
import com.civcraft.threading.timers.SyncTradeTimer;

public class HourlyTickEvent implements EventInterface {

	@Override
	public void process() {
		CivLog.info("TimerEvent: Hourly -------------------------------------");
		TaskMaster.asyncTask("cultureProcess", new CultureProcessAsyncTask(), 0);
		TaskMaster.asyncTask("EffectEventTimer", new EffectEventTimer(), 0);
		TaskMaster.syncTask(new SyncTradeTimer(), 0);
		TaskMaster.syncTask(new CampHourlyTick(), 0);
		CivLog.info("TimerEvent: Hourly Finished -----------------------------");
		try {
			CivMessage.mob("Despawning all mobs...");
			MobSpawner.despawnAll();
			MobDespawnTimer.despawn_yobo();
			MobDespawnTimer.despawn_angryyobo();
			MobDespawnTimer.despawn_behemoth();
			MobDespawnTimer.despawn_ruffian();
			MobDespawnTimer.despawn_savage();
			CivMessage.mob("All mobs have been despawned!");
		} catch (CivException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Calendar getNextDate() throws InvalidConfiguration {
		SimpleDateFormat sdf = new SimpleDateFormat("M/dd h:mm:ss a z");
		Calendar cal = EventTimer.getCalendarInServerTimeZone();

		int hourly_peroid = CivSettings.getInteger(CivSettings.civConfig, "global.hourly_tick");
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.add(Calendar.SECOND, hourly_peroid);
		sdf.setTimeZone(cal.getTimeZone());
		return cal;
	}
}
