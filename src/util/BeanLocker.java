package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <br/>程式目的：針對不可同步操作的bean提供鎖定功能
 * <br/>程式代號：BeanLocker
 * <br/>程式日期：
 * <br/>程式作者：Kang Da
 * <br/>--------------------------------------------------------
 * <br/>修改作者　　     修改日期　　　			修改目的
 * <br/>
 * <br/>--------------------------------------------------------
 */
public class BeanLocker {
	
	private static Map<String, LockedInfo> lockedList = new HashMap<String, LockedInfo>();
	
	/**
	 * 不論原先狀態 強制鎖上
	 */
	public static void forceLock(User user, String lockid, String msg) {
		lockedList.put(lockid, new LockedInfo(user, msg));
	}
	
	/**
	 * 會判斷原先狀態是否已鎖上  , 若已鎖則拋出錯誤 
	 */
	public static void lock(User user, String lockid, String msg) throws Exception {
		if (isLocked(lockid)) {
			throw new Exception(lockid + "已是鎖定狀態");
		} 
		forceLock(user, lockid, msg);
	}
	
	/**
	 * 會檢查是否為原先鎖定人員, 若不是則拋錯 
	 */
	public static void unlock(User user, String lockid) throws Exception {
		LockedInfo li = lockedList.get(lockid);
		if (li != null && !li.isSameUser(user)) {
			throw new Exception("非原始鎖定人員無法解除鎖定");
		}
		forceUnlock(lockid);
	}
	
	/**
	 * 不論原先鎖定狀態 直接解鎖
	 */
	public static void forceUnlock(String lockid) {
		lockedList.remove(lockid);
	}

	/**
	 * 判斷是否已是鎖定狀態 
	 */
	public static boolean isLocked(String lockid) {
		return lockedList.containsKey(lockid);
	}
	
	/**
	 * 取得鎖定Msg
	 */
	public static String getLockedMsg(String lockid) {
		LockedInfo li = lockedList.get(lockid);
		return li == null ? "" : li.getLockedMsg();
	}
	
	/**
	 * 將已鎖定之bean組成options html
	 * @return
	 */
	public static String getLockedOptionsHtml() {
		StringBuilder html = new StringBuilder();
		for (Entry<String, LockedInfo> tmpE : lockedList.entrySet()) {
			html.append("<option value='").append(tmpE.getKey()).append("'>").append(tmpE.getKey()).append(" 訊息:").append(tmpE.getValue().getLockedMsg()).append("</option>");
		}
		return html.toString();
	}
	
	@SuppressWarnings("unused")
	public static class LockedInfo {
		private String userId;
		private String userName;
		private String organId;
		private String organName;
		private String lockedDate;
		private String lockedTime;
		private String msg;
		
		public LockedInfo(User user, String msg) {
			this.userId = user.getUserID();
			this.userName = user.getUserName();
			this.organId = user.getOrganID();
			this.organName = user.getOrganName();
			this.lockedDate = Datetime.getYYYMMDD();
			this.lockedTime = Datetime.getHHMMSS();
			this.msg = msg;
		}
		
		public boolean isSameUser(User user) {
			return this.userId.equals(user.getUserID()) && this.organId.equals(user.getOrganID());
		}
		
		public String getLockedMsg() {
			// 問題單957複測: 不顯示程式資訊
//			return this.msg + "{key:" + this.organId + "_" + this.userId + "_" + this.lockedDate + "_" + this.lockedTime + "}";
			return this.msg;
		}
	}
}
