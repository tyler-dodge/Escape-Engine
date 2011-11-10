package edu.ncsu.uhp.escape.engine.utilities;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

import android.util.Log;

public class Profiler {
	private class ProfileSection {
		public Date start;
		public String name;

		public ProfileSection(String name) {
			this.name = name;
			start = new Date();
		}

		public String toString() {
			Date end = new Date();
			return String.format("%s ms \"%s\" \"%s\" \"%s\"", end.getTime()
					- start.getTime(), name, start.toString(), end.toString());
		}
	}

	private static final boolean DO_PROFILE = false;

	private Profiler() {
		sections = new Stack<Profiler.ProfileSection>();
		threadId = threadCount++;
	}

	private static int threadCount = 0;
	private int threadId = 0;
	private Stack<ProfileSection> sections;
	private static Dictionary<Thread, Profiler> instance = new Hashtable<Thread, Profiler>();
	private int frameNum = 0;

	public static Profiler getInstance() {
		Profiler current = instance.get(Thread.currentThread());
		if (current == null) {
			current = new Profiler();
			instance.put(Thread.currentThread(), current);
		}
		return current;
	}

	public void incrementFrame() {
		frameNum++;
	}

	public void startSection(String name) {
		if (DO_PROFILE) {
			ProfileSection section = new ProfileSection(name);
			sections.push(section);
			Log.d("Frame " + frameNum,
					"T: " + threadId + " S:" + sections.size()
							+ " Start Empty \"Starting " + name + "\"");
		}
	}

	public void endSection() {
		if (DO_PROFILE) {
			int size = sections.size();
			ProfileSection section = sections.pop();
			Log.d("Frame " + frameNum, "T: " + threadId + " S:" + size
					+ " End " + section.toString());
		}
	}
}
