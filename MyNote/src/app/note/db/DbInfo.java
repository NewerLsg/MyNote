package app.note.db;

public class DbInfo {	
	//表名
	private static final String DB_Tables[] = {
		"TBL_BUDGET",
		"TBL_EXPENDITURE_SUB_CATEGORY",
		"TBL_INCOME_CATEGORY",
		"TBL_INCOME_SUB_CATEGORY",
		"TBL_ACCOUNT_TYPE",
		"TBL_ACCOUNT_SUB_TYPE",
		"TBL_ACCOUNT",
		"TBL_STORE",
		"TBL_ITEM",
		"TBL_EXPENDITURE",
		"TBL_INCOME",
		"TBL_TRANSFER"
	};
	
	/******
	 * 
	 * 
		public static enum TblName{
		tblExpenditureCategory,	
		tblExpenditureSubCategory,
		tblIncomeCategory,
		tblIncomeSubCategory,
		tblAccountType,
		tblAccountSubType,
		tblAccount,
		tblScore,
		tblItem,
		tblExpenditure,
		tblIncome,
		tblTransfer
	};
	private static final int tblExpenditureCategory = 0;
	private static final int tblExpenditureSubCategory = 1;
	private static final int tblIncomeCategory = 2;
	private static final int tblIncomeSubCategory = 3;
	private static final int tblAccountType = 4;
	private static final int tblAccountSubType = 5;
	private static final int tblAccount = 6;
	private static final int tblScore = 7;
	private static final int tblItem = 8;
	private static final int tblExpenditure= 9;
	private static final int tblIncome = 10;
	private static final int tblTransfer = 11;
	*******/
	
	//字段名
	private static final String DB_Fileds[][] = {
		{"ID","NAME","AMOUNT"},
		{"ID","NAME","PARENT_CATEGORY_ID"},
		{"ID","NAME"},
		{"ID","NAME","PARENT_CATEGORY_ID"},
		{"ID","NAME","POSTIVE"},
		{"ID","NAME","PARENT_TYPE_ID"},
		{"ID","NAME","TYPE_ID","SUB_TYPE_ID","ACCOUNT_BALANCE"},
		{"ID","NAME"},
		{"ID","NAME"},
		{"ID", "AMOUNT", "EXPENDITURE_CATEGORY_ID", "EXPENDITURE_SUB_CATEGORY_ID", "ACCOUNT_ID", "STORE_ID", "ITEM_ID", "DATE", "MEMO"},
		{"ID", "AMOUNT", "INCOME_CATEGORY_ID", "INCOME_SUB_CATEGORY_ID", "ACCOUNT_ID", "ITEM_ID", "DATE", "MEMO"},
		{"ID", "AMOUNT", "ACCOUNT_ID", "ITEM_ID", "DATE", "MEMO"}
	};
	
	//属性
	private static final String DB_Attrs[][] = {
		{"INTEGER PRIMARY KEY AUTOINCREMENT","text","DOUBLE"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER","DOUBLE"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","INTEGER","INTEGER","DOUBLE"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","DOUBLE","INTEGER","INTEGER","INTEGER","INTEGER","INTEGER","TEXT","TEXT"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","DOUBLE","INTEGER","INTEGER","INTEGER","INTEGER","TEXT","TEXT"},
		{"INTEGER PRIMARY KEY AUTOINCREMENT","DOUBLE","INTEGER","INTEGER","TEXT","TEXT"}
	};
	
	public static String[]  getTables() {
		return DbInfo.DB_Tables;	
	}
	
	public static String[][] getFields() {
		return  DbInfo.DB_Fileds;
	}
	
	public static String[][] getAttrs() {
		return DbInfo.DB_Attrs;
	}

}
