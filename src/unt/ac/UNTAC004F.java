package unt.ac;

import util.BeanLocker;
import util.Common;
import util.Datetime;
import util.User;

public class UNTAC004F extends UNTAC003F{

	//=================================================
	public void calculateBalanceInMonth(User user){
		if(getsKeySet()!=null){
			String lockid = this.getLockId(user.getOrganID());
			if (BeanLocker.isLocked(lockid)) {
				this.setErrorMsg(BeanLocker.getLockedMsg(lockid));
			} else {
				BeanLocker.forceLock(user, lockid, "月結回復作業未完成，不可再執行月結回復作業");
				setRestoration1ID(getUserID());
				setRestoration1Name(getUserName());
				setRestoration1Date(Datetime.getYYYMMDD());
	
				try {
					execCalculateBalanceInMonth(false);
					setErrorMsg("月結回復執行完成");
				} catch (Exception e) {
					e.printStackTrace();
					setErrorMsg("執行月結回復發生錯誤，回復已月結狀態(" + Common.checkGet(e.getMessage()) + ")");
				} finally {
					BeanLocker.forceUnlock(lockid);
				}
			}
		} else {
			setErrorMsg("請勾選資料");
		}		
	}	
	//=================================================

}