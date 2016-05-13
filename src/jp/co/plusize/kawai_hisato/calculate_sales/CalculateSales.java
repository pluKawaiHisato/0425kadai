package jp.co.plusize.kawai_hisato.calculate_sales;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class CalculateSales
{
	public static void main(String[] args) throws FileNotFoundException
	{
		HashMap<String, Long> branchSumMap = new HashMap<String, Long>();
		HashMap<String, Long> commoditySumMap = new HashMap<String, Long>();
		if(args.length != 1)
		{
			System.out.println("予期せぬエラーが発生しました");
			return;
		}

		//支店定義ファイル
		HashMap<String, String> branchListMap = new HashMap<String, String>();
		if(!fileRead (args[0], "branch.lst", "支店", "^\\d{3}$", branchSumMap, branchListMap))
		{
			return;
		}
		
//		BufferedReader brBranchList = null;
//		try
//		{
//			brBranchList = new BufferedReader(new FileReader(new File (args[0] + File.separator + "branch.lst")));
//			String str;
//			while((str = brBranchList.readLine()) != null)
//			{
//				String[] braSpl = str.split(",", -1);
//
//
//				if(braSpl.length != 2)
//				{
//					System.out.println("支店定義ファイルのフォーマットが不正です");
//					return;
//				}
//
//				if(!braSpl[0].matches("^\\d{3}$"))
//				{
//					System.out.println("支店定義ファイルのフォーマットが不正です");
//					return;
//				}
//
//				branchListMap.put(braSpl[0], braSpl[1]);
//				branchSumMap.put(braSpl[0], 0L);
//
//			}
//		}
//		catch(IOException e)
//		{
//			System.out.println("支店定義ファイルが存在しません");
//			return;
//		}
//		finally
//		{
//			try
//			{
//				if(brBranchList != null)
//				{
//					brBranchList.close();
//				}
//			}
//			catch(IOException e)
//			{
//				System.out.println("予期せぬエラーが発生しました");
//				return;
//			}
//		}

		//商品定義ファイル
		HashMap<String, String> commodityListMap = new HashMap<String, String>();
		if(!fileRead(args[0], "commodity.lst", "商品", "^\\w{8}$", commoditySumMap, commodityListMap))
		{
			return;
		}
//		BufferedReader brCommodityList = null;
//		try
//		{
//			brCommodityList = new BufferedReader(new FileReader(new File (args[0] + File.separator + "commodity.lst")));
//			String s;
//			while((s = brCommodityList.readLine()) != null)
//			{
//				String[] comSpl = s.split(",",-1);
//
//				if(comSpl.length != 2)
//				{
//					System.out.println("商品定義ファイルのフォーマットが不正です");
//					return;
//				}
//				if(!comSpl[0].matches("^\\$"))
//				{
//					System.out.println("商品定義ファイルのフォーマットが不正です");
//					return;
//				}
//
//				commodityListMap.put(comSpl[0], comSpl[1]);
//				commoditySumMap.put(comSpl[0], 0L);
//
//			}
//		}
//		catch(IOException e)
//		{
//			System.out.println("商品定義ファイルが存在しません");
//			return;
//		}
//		finally
//		{
//			try
//			{
//				if(brCommodityList != null)
//				{
//					brCommodityList.close();
//				}
//			}
//			catch(IOException e)
//			{
//				System.out.println("予期せぬエラーが発生しました");
//				return;
//			}
//		}

		//rcd8桁連番のファイルだけArrayListに格納して加算処理
		if(!sumSales(args[0], branchSumMap, commoditySumMap))
		{
			return;
		}
//		File dir = new File(args[0]);
//		File files[] = dir.listFiles();
//
//		ArrayList<File> rcdList = new ArrayList<File>();
//
//		for(int i = 0; i < files.length; i++)
//		{
//			if(files[i].getName().endsWith(".rcd"))
//			{
//				String[] fileSpl = files[i].getName().toString().split("\\.", -1);
//				int j = 0;
//				if(fileSpl.length == 2)
//				{
//					j = Integer.parseInt(fileSpl[0]);
//					if(files[i].isFile())
//					{
//						if(fileSpl[0].matches("^\\d{8}$"))
//						{
//							rcdList.add(files[i]);
//						}
//					}
//				}
//				if(j - rcdList.size() != 0)
//				{
//					System.out.println("売上ファイル名が連番になっていません");
//					return;
//				}
//			}
//		}
//		//
//		BufferedReader brRcdFile = null;
//		try
//		{
//			for(int i = 0; i < rcdList.size(); i++)
//			{
//				String listStr = rcdList.get(i).toString();
//				brRcdFile = new BufferedReader(new FileReader(new File(listStr)));
//				String sale;
//				ArrayList<String> contensOfrcdFile= new ArrayList<String>();
//				while((sale = brRcdFile.readLine()) !=null)
//				{
//					contensOfrcdFile.add(sale);
//				}
//
//				if(contensOfrcdFile.size() != 3)
//				{
//					System.out.println(rcdList.get(i).getName() + "のフォーマットが不正です");
//					return;
//				}
//
//				long salesSum = Long.parseLong(contensOfrcdFile.get(2));
//
//				//コード不正時のエラー処理
//				if(!branchSumMap.containsKey(contensOfrcdFile.get(0)))
//				{
//					System.out.println(rcdList.get(i).getName() + "の支店コードが不正です");
//					return;
//				}
//
//				if(!commoditySumMap.containsKey(contensOfrcdFile.get(1)))
//				{
//					System.out.println(rcdList.get(i).getName() + "の商品コードが不正です");
//					return;
//				}
//
//				long bratotal = salesSum + branchSumMap.get(contensOfrcdFile.get(0));
//				long comtotal = salesSum + commoditySumMap.get(contensOfrcdFile.get(1));
//
//				branchSumMap.put(contensOfrcdFile.get(0), bratotal);
//				commoditySumMap.put(contensOfrcdFile.get(1), comtotal);
//
//				//10桁を超えた場合のエラー処理
//				String sbt = String.valueOf(bratotal);
//				if(sbt.length() > 10)
//				{
//					System.out.println( "合計金額が10桁を超えました");
//					return;
//				}
//
//				String sct = String.valueOf(comtotal);
//				if(sct.length() > 10)
//				{
//					System.out.println("合計金額が10桁を超えました");
//					return;
//				}
//			}
//		}
//		catch(IOException e)
//		{
//			System.out.println("予期せぬエラーが発生しました");
//			return;
//		}
//		finally
//		{
//			try
//			{
//				if(brRcdFile != null)
//				{
//					if(brRcdFile != null)
//					{
//						brRcdFile.close();
//					}
//				}
//			}
//			catch(IOException e)
//			{
//				System.out.println("予期せぬエラーが発生しました");
//				return;
//			}
//		}

		//支店別集計ファイルの出力
		if(!fileWrite(args[0], "branch.out", branchSumMap, branchListMap))
		{
			System.out.println("予期せぬエラーが発生しました");
			return;
		}
//		File branchOutFile = new File(args[0] + File.separator +  "branch.out");
//		BufferedWriter bwBranchOut = null;
// 		try
//		{
//			bwBranchOut = new BufferedWriter(new FileWriter(branchOutFile));
//
//			List<Map.Entry<String,Long>> branchSumEntries =
//					new ArrayList<Map.Entry<String,Long>>(branchSumMap.entrySet());
//			Collections.sort(branchSumEntries, new Comparator<Map.Entry<String,Long>>()
//			{
//				@Override
//				public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2)
//				{
//					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
//				}
//			});
//
//			for(Entry<String, Long> b : branchSumEntries)
//			{
//				bwBranchOut.write(b.getKey() + "," + .get(b.getKey()) + "," + b.getValue());
//				bwBranchOut.newLine();
//			}
//		}
//		catch(IOException e)
//		{
//			System.out.println("予期せぬエラーが発生しました");
//			return;
//		}
// 		finally
// 		{
// 			try
// 			{
// 				if(bwBranchOut != null)
// 				{
// 					bwBranchOut.close();
// 				}
// 			}
// 			catch(IOException e)
// 			{
// 				System.out.println("予期せぬエラーが発生しました");
// 				return;
// 			}
// 		}
		
		//商品別集計ファイルの出力
		if(!fileWrite(args[0], "commodity.out", commoditySumMap, commodityListMap))
		{
			return;
		}
		
//		File commodityOutFile = new File(args[0] + File.separator + "commodity.out");
//		BufferedWriter bwCommodityOut =null;
//		try
//		{
//			bwCommodityOut = new BufferedWriter(new FileWriter(commodityOutFile));
//
//			List<Map.Entry<String,Long>> commoditySumEntries = new ArrayList<Map.Entry<String,Long>>(.entrySet());
//			Collections.sort(commoditySumEntries, new Comparator<Map.Entry<String,Long>>()
//			{
//				@Override
//				public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2)
//				{
//					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
//				}
//			});
//
//			for(Entry<String, Long> c : commoditySumEntries)
//			{
//				bwCommodityOut.write(c.getKey() + "," + .get(c.getKey()) + "," + c.getValue());
//				bwCommodityOut.newLine();
//			}
//			bwCommodityOut.close();
//		}
//		catch(IOException e)
//		{
//			System.out.println("予期せぬエラーが発生しました");
//		}
//		finally
//		{
//			try
//			{
//				if(bwCommodityOut != null)
//				{
//					bwCommodityOut.close();
//				}
//			}
//			catch(IOException e)
//			{
//				System.out.println("予期せぬエラーが発生しました");
//				return;
	}

	//メソッド分け
	//定義ファイル読み込み
	private static boolean fileRead(String path, String filename, String nameTag, String preg, HashMap<String, Long> sumMap, HashMap<String, String> listMap)
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(new File (path + File.separator + filename)));
			String str;
			while((str = br.readLine()) != null)
			{
				String[] split = str.split(",", -1);


				if(split.length != 2)
				{
					System.out.println(nameTag + "定義ファイルのフォーマットが不正です");
					return false;
				}

				if(!split[0].matches(preg))
				{
					System.out.println(nameTag + "定義ファイルのフォーマットが不正です");
					return false;
				}

				listMap.put(split[0], split[1]);
				sumMap.put(split[0], 0L);
			}
		}
		catch(IOException e)
		{
			System.out.println(nameTag + "定義ファイルが存在しません");
			return false;
		}
		finally
		{
			try
			{
				if(br != null)
				{
					br.close();
				}
			}
			catch(IOException e)
			{
				System.out.println("予期せぬエラーが発生しました");
				return false;
			}
		}
		return true;
	}
	
	//集計結果出力
	private static boolean fileWrite(String path, String fileName, HashMap<String, Long> sumMap, HashMap<String, String> listMap)
	{
		File OutFile = new File(path + File.separator + fileName);
		BufferedWriter bw = null;
 		try
		{
			bw = new BufferedWriter(new FileWriter(OutFile));

			List<Map.Entry<String,Long>> Entries =
					new ArrayList<Map.Entry<String,Long>>(sumMap.entrySet());
			Collections.sort(Entries, new Comparator<Map.Entry<String,Long>>()
			{
				@Override
				public int compare(Entry<String,Long> entry1, Entry<String,Long> entry2)
				{
					return ((Long)entry2.getValue()).compareTo((Long)entry1.getValue());
				}
			});

			for(Entry<String, Long> en : Entries)
			{
				bw.write(en.getKey() + "," + listMap.get(en.getKey()) + "," + en.getValue());
				bw.newLine();
			}
			
		}
		catch(IOException e)
		{
			System.out.println("予期せぬエラーが発生しました");
			return false;
		}
 		finally
 		{
 			try
 			{
 				if(bw != null)
 				{
 					bw.close();
 				}
 			}
 			catch(IOException e)
 			{
 				System.out.println("予期せぬエラーが発生しました");
 				return false;
 			}
 		}
 		return true;
	}
	private static boolean sumSales(String path, HashMap<String, Long> branchSumMap, HashMap<String, Long> commoditySumMap)
	{
		File dir = new File(path);
		File files[] = dir.listFiles();

		ArrayList<File> rcdList = new ArrayList<File>();

		for(int i = 0; i < files.length; i++)
		{
			if(files[i].getName().endsWith(".rcd"))
			{
				String[] fileSpl = files[i].getName().toString().split("\\.", -1);
				int j = 0;
				if(fileSpl.length == 2)
				{
					j = Integer.parseInt(fileSpl[0]);
					if(files[i].isFile())
					{
						if(fileSpl[0].matches("^\\d{8}$"))
						{
							rcdList.add(files[i]);
						}
					}
				}
				if(j - rcdList.size() != 0)
				{
					System.out.println("売上ファイル名が連番になっていません");
					return false;
				}
			}
		}
		//加算処理
		BufferedReader brRcdFile = null;
		try
		{
			for(int i = 0; i < rcdList.size(); i++)
			{
				String listStr = rcdList.get(i).toString();
				brRcdFile = new BufferedReader(new FileReader(new File(listStr)));
				String sale;
				ArrayList<String> contensOfrcdFile= new ArrayList<String>();
				while((sale = brRcdFile.readLine()) !=null)
				{
					contensOfrcdFile.add(sale);
				}

				if(contensOfrcdFile.size() != 3)
				{
					System.out.println(rcdList.get(i).getName() + "のフォーマットが不正です");
					return false;
				}

				long salesSum = Long.parseLong(contensOfrcdFile.get(2));

				//コード不正時のエラー処理
				if(!branchSumMap.containsKey(contensOfrcdFile.get(0)))
				{
					System.out.println(rcdList.get(i).getName() + "の支店コードが不正です");
					return false;
				}

				if(!commoditySumMap.containsKey(contensOfrcdFile.get(1)))
				{
					System.out.println(rcdList.get(i).getName() + "の商品コードが不正です");
					return false;
				}

				long bratotal = salesSum + branchSumMap.get(contensOfrcdFile.get(0));
				long comtotal = salesSum + commoditySumMap.get(contensOfrcdFile.get(1));

				branchSumMap.put(contensOfrcdFile.get(0), bratotal);
				commoditySumMap.put(contensOfrcdFile.get(1), comtotal);

				//10桁を超えた場合のエラー処理
				String sbt = String.valueOf(bratotal);
				if(sbt.length() > 10)
				{
					System.out.println( "合計金額が10桁を超えました");
					return false;
				}

				String sct = String.valueOf(comtotal);
				if(sct.length() > 10)
				{
					System.out.println("合計金額が10桁を超えました");
					return false;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("予期せぬエラーが発生しました");
			return false;
		}
		finally
		{
			try
			{
			if(brRcdFile != null)
				{
					brRcdFile.close();
				}
			}
			catch(IOException e)
			{
				System.out.println("予期せぬエラーが発生しました");
				return false;
			}
		}
		return true;
	}
}

