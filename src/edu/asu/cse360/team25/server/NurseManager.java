package edu.asu.cse360.team25.server;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class NurseManager {

	WeakHashMap<Integer, Nurse> cacheByID = new WeakHashMap<Integer, Nurse>();
	
	
//	public int registerNurse(String name, 	String password) {

//		int id = getNextID();

//		Nurse n = new Nurse(id, name, password);

//		addNewNurseToCache(n);
//		addNewNurseToDatabase(n);

//		return id;
//	}        
        
        
        
	
	
	public synchronized Nurse findNurseByID(int id) {

		if (!cacheByID.containsKey(id)) {
			// not in cache, query the database
//			Nurse one = findNurseByIDFromDatabase(id);
//			if (one != null) {

//				cacheByID.put(id, one);
//			} else {

				// no such user
//				return null;
//			}

		}

		Nurse n = cacheByID.get(id);
		return n;
	}
        
        
        
	public boolean checkNurseLoginRequest(int id, String password) {

		Nurse n = findNurseByID(id);
//		if (n != null)
//			return n.getPassword().equals(password);
//		else
			return false;
	}        
        
        
        
	protected synchronized void addNewNurseToCache(Nurse n) {

		if (n == null)
			return;

		//
		cacheByID.put(n.getNurseID(), n);
		//
                /*
		List<Doctor> list = cacheByDnE.get(n.getDepartment() + "+"
				+ n.getExpertise());
		if (list != null) {
			if (!list.contains(d))
				list.add(d);
		}

		// handle stars in department and expertise

		List<Doctor> listDstar = cacheByDnE.get("*+" + d.getExpertise());
		if (listDstar != null) {
			if (!listDstar.contains(n))
				listDstar.add(d);
		}

		List<Doctor> listStarStar = cacheByDnE.get("*+*");
		if (listStarStar != null) {
			if (!listStarStar.contains(d))
				listStarStar.add(d);
		}
                        */

	}        
        
        

        
	protected synchronized void addNewNurseToDnECache(Doctor d) {

		if (d == null)
			return;
/*
		List<Doctor> list = cacheByDnE.get(d.getDepartment() + "+"
				+ d.getExpertise());
		if (list == null) {
			list = new ArrayList<Doctor>();
			list.add(d);
			cacheByDnE.put(d.getDepartment() + "+" + d.getExpertise(), list);
		} else {
			if (!list.contains(d))
				list.add(d);
		}

		// handle stars in department and expertise

		List<Doctor> listDstar = cacheByDnE.get("*+" + d.getExpertise());
		if (listDstar == null) {
			listDstar = new ArrayList<Doctor>();
			listDstar.add(d);
			cacheByDnE.put("*+" + d.getExpertise(), listDstar);
		} else {
			if (!listDstar.contains(d))
				listDstar.add(d);
		}

		List<Doctor> listSstar = cacheByDnE.get(d.getDepartment() + "+*");
		if (listSstar == null) {
			listSstar = new ArrayList<Doctor>();
			listSstar.add(d);
			cacheByDnE.put(d.getDepartment() + "+*", listSstar);
		} else {
			if (!listSstar.contains(d))
				listSstar.add(d);
		}

		List<Doctor> listStarStar = cacheByDnE.get("*+*");
		if (listStarStar == null) {
			listStarStar = new ArrayList<Doctor>();
			listStarStar.add(d);
			cacheByDnE.put("*+*", listStarStar);
		} else {
			if (!listStarStar.contains(d))
				listStarStar.add(d);
		}
*/
	}        
        
        
        
        
	protected synchronized void addNurseToCache(List<Nurse> ns) {

		if (ns == null || ns.isEmpty())
			return;

		for (Nurse n : ns) {
			addNewNurseToCache(n);
		}

	}        
        
 
//	protected synchronized int getNextID() {

//		return idNext++;
//            return ;
//	}        
        
	protected synchronized Doctor findNurseByIDFromDatabase(int id) {

		return null;
	}        
        
        
        
	public void setupDummyNurse() {

		Nurse[] n = new Nurse[9];
/*
		n[0] = new Nurse(0, "Kousaka Honoka", "GAME", "GTA5", "123456");
		n[1] = new Nurse(1, "Minami Kotori", "GAME", "Naruto", "123456");
		n[2] = new Nurse(2, "Sonoda Umi", "GAME", "LoveLive", "123456");
		n[3] = new Nurse(3, "Koizumi Hanayo", "Cook", "meat ball", "123456");


		for (int i = 0; i < n.length; i++) {
			addNewNurseToCache(n[i]);
			addNewNurseToDnECache(n[i]);
		}
  */

	}        

}
