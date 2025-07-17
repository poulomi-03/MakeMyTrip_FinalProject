package utils;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	String path = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\test.xlsx";
	ExcelUtil xlutil;
	
	@DataProvider(name="CabBookingValidData")
	public String[][] getCabBookingValidTestData() throws IOException	{
		xlutil = new ExcelUtil(path);
		String sheetName = "CabBooking1";
		int totalRows = xlutil.getRowCount(sheetName);
		int totalCols = xlutil.getCellCount(sheetName, 1);
		String data[][] = new String[totalRows][totalCols];
		
		for(int i=1;i<=totalRows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalCols;j++)  //0    i is rows j is col
			{
				data[i-1][j]= xlutil.getCellData(sheetName,i, j);  //1,0
			}
		}
		return data;
	}
	
	@DataProvider(name="NoCabsData")
	public String[][] getCabBookingNoCabsData() throws IOException {
		xlutil = new ExcelUtil(path);
		String sheetName = "CabBooking2";
		int totalRows = xlutil.getRowCount(sheetName);
		int totalCols = xlutil.getCellCount(sheetName, 1);
		String data[][] = new String[totalRows][totalCols];
		
		for(int i=1;i<=totalRows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalCols;j++)  //0    i is rows j is col
			{
				data[i-1][j]= xlutil.getCellData(sheetName,i, j);  //1,0
			}
		}
		return data;
	}
	
	@DataProvider(name="GiftCardTest1")
	public String[][] getGiftCardData1() throws IOException{
		xlutil = new ExcelUtil(path);
		String sheetName = "GiftCard1";
		int totalRows = xlutil.getRowCount(sheetName);
		int totalCols = xlutil.getCellCount(sheetName, 1);
		String data[][] = new String[totalRows][totalCols];
		
		for(int i=1;i<=totalRows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalCols;j++)  //0    i is rows j is col
			{
				data[i-1][j]= xlutil.getCellData(sheetName,i, j);  //1,0
			}
		}
		return data;
	}
	
	@DataProvider(name="GiftCardTest2")
	public String[][] getGiftCardData2() throws IOException{
		xlutil = new ExcelUtil(path);
		String sheetName = "GiftCard2";
		int totalRows = xlutil.getRowCount(sheetName);
		int totalCols = xlutil.getCellCount(sheetName, 1);
		String data[][] = new String[totalRows][totalCols];
		
		for(int i=1;i<=totalRows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalCols;j++)  //0    i is rows j is col
			{
				data[i-1][j]= xlutil.getCellData(sheetName,i, j);  //1,0
			}
		}
		return data;
	}
	
	@DataProvider(name="GiftCardTest3")
	public String[][] getGiftCardData3() throws IOException{
		xlutil = new ExcelUtil(path);
		String sheetName = "GiftCard3";
		int totalRows = xlutil.getRowCount(sheetName);
		int totalCols = xlutil.getCellCount(sheetName, 1);
		String data[][] = new String[totalRows][totalCols];
		
		for(int i=1;i<=totalRows;i++)  //1   //read the data from xl storing in two deminsional array
		{		
			for(int j=0;j<totalCols;j++)  //0    i is rows j is col
			{
				data[i-1][j]= xlutil.getCellData(sheetName,i, j);  //1,0
			}
		}
		return data;
	}
}
