//*********************************************
//��ƥ\��G��XUNTNE_ReduceDetail ��ƪ�
//�ѡ@�@�ơG�L
//*********************************************
function getUntneReduceDetail(){
	var alertStr = "";
	var i = 0, j=0;
	var sFlag = false;
	alertStr += checkEmpty(form1.enterOrg,"入帳機關");
	alertStr += checkEmpty(form1.ownership,"權屬");
	alertStr += checkEmpty(form1.caseNo,"電腦單號");
	alertStr += checkEmpty(form1.propertyNo,"財產編號");
	alertStr += checkEmpty(form1.serialNo,"財產分號");
		
	if(alertStr.length!=0){
		//if (isObj(form1.caseNo)) { form1.caseNo.value=""; }	
		//if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
		//if (isObj(form1.propertyNoName)) { form1.propertyName.value=""; }
		//if (isObj(form1.serialNo)) { form1.serialNo.value=""; }	
		form1.caseNo.style.backgroundColor="";
		form1.propertyNo.style.backgroundColor="";
		form1.serialNo.style.backgroundColor="";
		return false;
	} else {
		var sEnterOrg = form1.enterOrg.value;
		//var sOwnership=form1.ownership.value;
		var sCaseNo = form1.caseNo.value;
		var sPropertyNo = form1.propertyNo.value;
		var sSerialNo = form1.serialNo.value;
		var queryValue = "";	
 
		queryValue = "enterOrg="+sEnterOrg+"&caseNo="+sCaseNo+"&propertyNo="+sPropertyNo+"&serialNo="+sSerialNo+"&differenceKind="+sDifferenceKind;

		var xmlDoc=document.createElement("xml");		
		xmlDoc.async=false;		
		
		if(xmlDoc.load("xmlUntneReduceDetail.jsp?"+queryValue)){	
			for(i=0; i<xmlDoc.documentElement.childNodes.length; i++){
				sFlag = true;

				if (isObj(form1.serialNo)) {
						form1.serialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("serialNo");
				}	
				if (isObj(form1.lotNo)) {
						form1.lotNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("lotNo");
				}	
				if (isObj(form1.propertyUnit)) {
						form1.propertyUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyUnit");
				}	
				if (isObj(form1.material)) {
						form1.material.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("material");
				}	
				if (isObj(form1.limitYear)) {
						form1.limitYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("limitYear");
				}	
				if (isObj(form1.otherPropertyUnit)) {
						form1.otherPropertyUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherPropertyUnit");
				}	
				if (isObj(form1.otherMaterial)) {
						form1.otherMaterial.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherMaterial");
				}	
				if (isObj(form1.otherLimitYear)) {
						form1.otherLimitYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("otherLimitYear");
				}
				if (isObj(form1.propertyName1)) {
						form1.propertyName1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyName1");
				}
				if (isObj(form1.enterDate)) {
						form1.enterDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("enterDate");
				}
				if (isObj(form1.buyDate)) {
						form1.buyDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("buyDate");
				}
				if (isObj(form1.reduceDate)) {
						form1.reduceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("reduceDate");
				}	
				if (isObj(form1.propertyKind)) {
						form1.propertyKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("propertyKind");
				}
				if (isObj(form1.fundType)) {
						form1.fundType.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("fundType");
				}
				if (isObj(form1.valuable)) {
						form1.valuable.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("valuable");
				}
				if (isObj(form1.accountingTitle)) {
						form1.accountingTitle.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("accountingTitle");
				}
				if (isObj(form1.oldBookAmount)) {
						form1.oldBookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldBookAmount");
				}	
				if (isObj(form1.oldBookUnit)) {
						form1.oldBookUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldBookUnit");
				}	
				if (isObj(form1.oldBookValue)) {
						form1.oldBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldBookValue");
				}	
				if (isObj(form1.adjustBookAmount)) {
						form1.adjustBookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("adjustBookAmount");
				}
				if (isObj(form1.adjustBookValue)) {
						form1.adjustBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("adjustBookValue");
				}
				if (isObj(form1.newBookAmount)) {
						form1.newBookAmount.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("newBookAmount");
				}	
				if (isObj(form1.newBookValue)) {
						form1.newBookValue.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("newBookValue");
				}	
				if (isObj(form1.articleName)) {
						form1.articleName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("articleName");
				}
				if (isObj(form1.nameplate)) {
						form1.nameplate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("nameplate");
				}
				if (isObj(form1.specification)) {
						form1.specification.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("specification");
				}
				if (isObj(form1.sourceDate)) {
						form1.sourceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("sourceDate");
				}
				if (isObj(form1.licensePlate)) {
						form1.licensePlate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("licensePlate");
				}
				if (isObj(form1.moveDate)) {
						form1.moveDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("moveDate");
				}
				if (isObj(form1.keepUnit)) {
						form1.keepUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keepUnit");
				}
				if (isObj(form1.keepUnitName)) {
						form1.keepUnitName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keepUnitName");
				}
				if (isObj(form1.keeper)) {
						form1.keeper.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeper");
				}
				if (isObj(form1.keeperName)) {
						form1.keeperName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("keeperName");
				}
				if (isObj(form1.useUnit)) {
						form1.useUnit.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useUnit");
				}
				if (isObj(form1.useUnitName)) {
						form1.useUnitName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useUnitName");
				}
				if (isObj(form1.userNo)) {
						form1.userNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("userNo");
				}
				if (isObj(form1.userName)) {
						form1.userName.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("userName");
				}
				if (isObj(form1.place)) {
						form1.place.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("place");
				}
				if (isObj(form1.useYear)) {
						form1.useYear.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useYear");
				}
				if (isObj(form1.useMonth)) {
						form1.useMonth.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("useMonth");
				}
				if (isObj(form1.permitReduceDate)) {
						form1.permitReduceDate.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("permitReduceDate");
				}
				if (isObj(form1.oldPropertyNo)) {
						form1.oldPropertyNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldPropertyNo");
				}
				if (isObj(form1.oldSerialNo)) {
						form1.oldSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("oldSerialNo");
				}
				if (isObj(form1.differenceKind)) {
					form1.differenceKind.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("differenceKind");
			    }
				if (isObj(form1.userNote)) {
					form1.userNote.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("userNote");
			    }	
				if (isObj(form1.place1)) {
					form1.place1.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("place1");
			    }	
				if (isObj(form1.check)) {
						form1.check.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("check");
				}
				if (isObj(form1.caseSerialNo)) {
					form1.caseSerialNo.value=xmlDoc.documentElement.childNodes.item(i).getAttribute("caseSerialNo");
		     	}	
			}
			if (!sFlag) {
				//if (isObj(form1.caseNo)) { form1.caseNo.value=""; }	
				//if (isObj(form1.propertyNo)) { form1.propertyNo.value=""; }		
				//if (isObj(form1.propertyNoName)) { form1.propertyName.value=""; }
				//if (isObj(form1.serialNo)) { form1.serialNo.value=""; }			
				return false;	
			}
		}else{
/**			alert('Cant Load It!'); **/
			return false;	
		}
	}	
}