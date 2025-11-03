/**
 * 資料聯動事件綁定器
 * <br/>Group1 : input1 , select1
 * <br/>Group2 : input2 , select2
 * <br/>couplingTo2 若為true 則會將 Group1 的資料聯動到 Group2 , 不會逆向
 * <br/>couplingTo1 若為true 則會將 Group2 的資料驅動到 Group1 , 不會逆向
 * @param input1      : group1
 * @param select1     : group1
 * @param input2      : group2
 * @param select2     : group2
 * @param couplingTo2 : 若為true 則會將 Group1 的資料聯動到 Group2 , 不會逆向
 * @param couplingTo1 : 若為true 則會將 Group2 的資料驅動到 Group1 , 不會逆向
 */
function DataCouplingCtrl(input1, select1, input2, select2, couplingTo2, couplingTo1) {
	var self = this;
	self.input1 = input1;
	self.select1 = select1;
	self.input2 = input2;
	self.select2 = select2;
	
	self.inputCouplingToSelect = function(input, select) {
		var existInOptions = false;
		for (i = 0; i < select.length; ++i){
		    if (select.options[i].value == input.value){
		    	existInOptions = true;
		    	break;
		    }
		}
		if (existInOptions) {
			select.value = input.value;
			return true;
		} else {
			select.value = '';
			input.value = '';
			return false;
		}
	};
	
	self.input1.onchange = function() {
		var couplingFlag = self.inputCouplingToSelect(self.input1, self.select1);
		if (couplingTo2) {
			if (couplingFlag) {
				self.input2.value = self.input1.value;
				self.select2.value = self.input1.value;
			} else {
				self.input2.value = '';
				self.select2.value = '';
			}
			
		}
	};
	
	self.select1.onchange = function() {
		self.input1.value = self.select1.value;
		if (couplingTo2) {
			self.input2.value = self.select1.value;
			self.select2.value = self.select1.value;
		}
	};
	
	if (isObj(self.input2)) {
		self.input2.onchange = function() {
			var couplingFlag = self.inputCouplingToSelect(self.input2, self.select2);
			if (couplingTo1) {
				if (couplingFlag) {
					self.input1.value = self.input2.value;
					self.select1.value = self.input2.value;
				} else {
					self.input1.value = '';
					self.select1.value = '';
				}
				
			}
		};
		
		self.select2.onchange = function() {
			self.input2.value = self.select2.value;
			if (couplingTo1) {
				self.input1.value = self.select2.value;
				self.select1.value = self.select2.value;
			}
		};
	}
	
	
}

/**
 * 資料聯動事件綁定器 Plus 版, 傳入機關, 可根據機關客製化
 * <br/>Group1 : input1 , select1
 * <br/>Group2 : input2 , select2
 * <br/>couplingTo2 若為true 則會將 Group1 的資料聯動到 Group2 , 不會逆向
 * <br/>couplingTo1 若為true 則會將 Group2 的資料驅動到 Group1 , 不會逆向
 * @param input1      : group1
 * @param select1     : group1
 * @param input2      : group2
 * @param select2     : group2
 * @param couplingTo2 : 若為true 則會將 Group1 的資料聯動到 Group2 , 不會逆向
 * @param couplingTo1 : 若為true 則會將 Group2 的資料驅動到 Group1 , 不會逆向
 */
function DataCouplingCtrlPlus(enterorg, input1, select1, input2, select2, couplingTo2, couplingTo1) {
	var self = this;
	self.enterorg = ""
	if (isObj(enterorg)) {
		self.enterorg = enterorg.value;
	}
	self.input1 = input1;
	self.select1 = select1;
	self.input2 = input2;
	self.select2 = select2;
	
	self.inputCouplingToSelect = function(input, select) {
		var existInOptions = false;
		for (i = 0; i < select.length; ++i){
		    if (select.options[i].value == input.value){
		    	existInOptions = true;
		    	break;
		    }
		}
		if (existInOptions) {
			select.value = input.value;
			return true;
		} else {
			select.value = '';
			input.value = '';
			return false;
		}
	};
	
	self.input1.onchange = function() {
		// 科技部特化: 保管人/保管單位 輸入 10 自動補滿為 000010
		if (self.enterorg == "A42000000G") {
			var str = "" + self.input1.value;
			var pad = "00000";
			self.input1.value = pad.substring(0, pad.length - str.length) + str
		}
		
		var couplingFlag = self.inputCouplingToSelect(self.input1, self.select1);
		if (couplingTo2) {
			if (couplingFlag) {
				self.input2.value = self.input1.value;
				self.select2.value = self.input1.value;
			} else {
				self.input2.value = '';
				self.select2.value = '';
			}
			
		}
	};
	
	self.select1.onchange = function() {
		self.input1.value = self.select1.value;
		if (couplingTo2) {
			self.input2.value = self.select1.value;
			self.select2.value = self.select1.value;
		}
	};
	
	if (isObj(self.input2)) {
		self.input2.onchange = function() {
			
			// 科技部特化: 保管人/保管單位 輸入 10 自動補滿為 000010
			if (self.enterorg == "A42000000G") {
				var str = "" + self.input2.value;
				var pad = "00000";
				self.input2.value = pad.substring(0, pad.length - str.length) + str
			}
			
			var couplingFlag = self.inputCouplingToSelect(self.input2, self.select2);
			if (couplingTo1) {
				if (couplingFlag) {
					self.input1.value = self.input2.value;
					self.select1.value = self.input2.value;
				} else {
					self.input1.value = '';
					self.select1.value = '';
				}
				
			}
		};
		
		self.select2.onchange = function() {
			self.input2.value = self.select2.value;
			if (couplingTo1) {
				self.input1.value = self.select2.value;
				self.select1.value = self.select2.value;
			}
		};
	}
	
	
}

/**
 * 資料聯動事件綁定器
 * <br/>Group1 : input1 , select1
 * <br/>Group2 : input2 , select2
 * <br/>couplingTo2 若為true 則會將 Group1 的資料聯動到 Group2 , 不會逆向
 * <br/>couplingTo1 若為true 則會將 Group2 的資料驅動到 Group1 , 不會逆向
 * @param input1      : group1
 * @param select1     : group1
 * @param group1CallBack : group1 changeValue後執行的callBack (不要丟有參數的)
 * @param input2      : group2
 * @param select2     : group2
 * @param group2CallBack : group2 changeValue後執行的callBack (不要丟有參數的)
 * @param couplingTo2 : 若為true 則會將 Group1 的資料聯動到 Group2 , 不會逆向
 * @param couplingTo1 : 若為true 則會將 Group2 的資料驅動到 Group1 , 不會逆向
 */
function DataCouplingCtrl2(input1, select1, group1CallBack, input2, select2, group2CallBack, couplingTo2, couplingTo1) {
	var self = this;
	self.input1 = input1;
	self.select1 = select1;
	self.group1CallBack = group1CallBack;
	self.input2 = input2;
	self.select2 = select2;
	self.group2CallBack = group2CallBack;
	
	self.inputCouplingToSelect = function(input, select) {
		var existInOptions = false;
		for (i = 0; i < select.length; ++i){
		    if (select.options[i].value == input.value){
		    	existInOptions = true;
		    	break;
		    }
		}
		if (existInOptions) {
			select.value = input.value;
			return true;
		} else {
			select.value = '';
			input.value = '';
			return false;
		}
	};
	
	self.input1.onchange = function() {
		var couplingFlag = self.inputCouplingToSelect(self.input1, self.select1);
		if (couplingTo2) {
			if (couplingFlag) {
				self.input2.value = self.input1.value;
				self.select2.value = self.input1.value;
				if (isObj(self.group1CallBack)) {
					try {
						self.group1CallBack();
					} catch (e) {
						//ignore
					}
				}
			} else {
				self.input2.value = '';
				self.select2.value = '';
			}
			
		}
	};
	
	self.select1.onchange = function() {
		self.input1.value = self.select1.value;
		
		if (isObj(self.group1CallBack)) {
			try {
				self.group1CallBack();
			} catch (e) {
				//ignore
			}
		}
		if (couplingTo2) {
			self.input2.value = self.select1.value;
			self.select2.value = self.select1.value;
			if (isObj(self.group2CallBack)) {
				try {
					self.group2CallBack();
				} catch (e) {
					//ignore
				}
			}
		}
	};
	
	self.input2.onchange = function() {
		var couplingFlag = self.inputCouplingToSelect(self.input2, self.select2);
		if (couplingTo1) {
			if (couplingFlag) {
				self.input1.value = self.input2.value;
				self.select1.value = self.input2.value;
				
				if (isObj(self.group2CallBack)) {
					try {
						self.group2CallBack();
					} catch (e) {
						//ignore
					}
				}
			} else {
				self.input1.value = '';
				self.select1.value = '';
			}
			
		}
	};
	
	self.select2.onchange = function() {
		self.input2.value = self.select2.value;
		if (isObj(self.group2CallBack)) {
			try {
				self.group2CallBack();
			} catch (e) {
				//ignore
			}
		}
		if (couplingTo1) {
			self.input1.value = self.select2.value;
			self.select1.value = self.select2.value;
			if (isObj(self.group1CallBack)) {
				try {
					self.group1CallBack();
				} catch (e) {
					//ignore
				}
			}
		}
	};
}

