package src.main.java.keibatool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataShoriUtil {

	private static final String BREEDER_TEXT = "生産者";
	static void updateHorseMstecursively(ArrayList<String> horseIdList, int num, boolean isPed) {
		String horseUrlPre = "https://db.netkeiba.com/horse/";
		for(String horseId : horseIdList) {
			try {
				if(canSkip(horseId)) {

				} else {
					FileWriter raceDataCsv = new FileWriter("./horseMst.csv", true);
					PrintWriter p4horseMst = new PrintWriter(new BufferedWriter(raceDataCsv));
					Document horsePage = Jsoup.connect(horseUrlPre+horseId+"/").get();
					Elements horseTitles = horsePage.select(".horse_title");
					if(horseTitles.isEmpty()) break;
					Element horseTitle = horseTitles.get(0);
					String horseName = horseTitle.select("h1").get(0).text().replaceAll(" 　","");
					//現役牝3歳鹿毛ボンドロール
					String horseTitleStr = horseTitle.select(".txt_01").get(0).text();
					String sex = findCondition(horseTitleStr,"牡|牝|セ");
					String age = findCondition(horseTitleStr, "[0-9]");
					Elements bloodTable = horsePage.select(".blood_table").get(0).select("td");
					String father = getHorseId(bloodTable.get(0));
					String mother = getHorseId(bloodTable.get(3));
					String bms = getHorseId(bloodTable.get(4));
					Elements prof = horsePage.select(".db_prof_table").get(0).select("td");
					Elements profTh = horsePage.select(".db_prof_table").get(0).select("th");
					String owner = prof.get(2).text();
					String trainer = prof.get(1).text();
					if (StringUtil.equals(profTh.get(3),BREEDER_TEXT)) {
						String breeder = prof.get(3).text();
						String career = prof.get(7).text();
					} else {
						String breeder = prof.get(4).text();
						String career = prof.get(8).text();
					}
					//HorseMst("horseId,horceName,sex,age,father,mother,bms,owner,trainer,breader,career");
					String horseData = horseId +","+ horseName +","+ sex +","+ age +","+ father +","+ mother +","+ bms +","+
					owner +","+ trainer +","+breeder+","+ career;
					p4horseMst.println(horseData);
					p4horseMst.close();
					ArrayList<String> parents = new ArrayList<>();
					parents.add(father);
					parents.add(mother);
					int numNext = num - 1;
					if (num > 0) {
						updateHorseMstecursively(parents,numNext, true);
					}
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}

		}
	}

	private static boolean canSkip(String horseId) {
		String fileName = "horseMst.csv";
		Path fileIn = Paths.get(fileName);
		try(
	            BufferedReader fIn  = Files.newBufferedReader(fileIn);
	        ) {
	            String delId = horseId;

	            long existingOpt = fIn.lines()
	                .filter(line -> line.startsWith(delId))
	                .count();
	            return existingOpt > 0;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return true;
	}

	static void createRacePayInfo(Document document, String raceId, PrintWriter p) {
		// RacePayInfo("raceId,singleNo,singlePay,fukushoNo1,fukushoPay1,fukushoNo2,fukushoPay2,fukushoNo3,fukushoPay3),
		Elements els = document.select(".pay_table_01").get(0).select("tr");
		// 単勝
		String singleNo = getValue(els,0,0);
		String singlePay = getValue(els,0,1).replaceAll(",", "");
		String[] fukushoNo = getValue(els,1,0).split(" ");//11 9 5
		String[] fukushoPay = getValue(els,1,1).replaceAll(",", "").split(" ");
		p.print(raceId + "," + singleNo + "," + singlePay + "," + fukushoNo[0] + "," + fukushoPay[0] + ","
				+ fukushoNo[1] + "," + fukushoPay[1] );
		if(fukushoNo.length == 3 && fukushoPay.length == 3){
			p.print(","+fukushoNo[2]+","+fukushoPay[2]);
		}
		p.println();
	}

	private static String getValue(Elements els, int i, int j) {
		return els.get(i).select("td").get(j).text();
	}

	static void createRaceInfo(Document document, String raceId, PrintWriter p) {
		System.out.println(raceId);
		String raceName = document.select(".raceData").get(0).select("h1").get(0).text();
		// ex. ダ右1800m / 天候 : 晴 / ダート : 良 / 発走 : 14:25
		String conditions = document.select(".raceData").get(0).select("span").get(0).text();
		String course = String.valueOf(conditions.charAt(0));
		String distance = conditions.substring(2, 6);
		String baba = findCondition(conditions, "[良|稍重|重|不良]");
		// ex. 2019年1月5日 1回中山1日目 サラ系4歳以上1600万下  (混)[指](ハンデ)
		String conditions2 = document.select(".data_intro").get(0).select(".smalltxt").text();
		String place = findCondition(conditions2, "東京|中山|阪神|京都|小倉|中京|新潟|福島|札幌|函館");
		String barei = findCondition(conditions2, "2歳|3歳|3歳以上|4歳以上");
		String jouken = findCondition(conditions2, "G1|G2|G3|OP|1600万|1000万|500万|新馬|未勝利|未出走");
		p.print(raceId + "," + raceName + "," + course + "," + distance  + "," + baba  + "," + place  + "," + barei +  "," + jouken );
		p.println();
	}

	static String findCondition(String conditions, String ptnStr) {
		Pattern ptn = Pattern.compile(ptnStr);
		Matcher m = ptn.matcher(conditions);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	static ArrayList<String> updateRaceData(Document document, String raceId, FileWriter raceDataCsv, PrintWriter p) {
		Elements elements = document.select(".race_table_01");
		ArrayList<String> horseIdList = new ArrayList<>();
		for (Element element : elements) {
			Elements trs = element.select("tr");
			for(int a = 1; a < trs.size(); a++) {
				Element e = trs.get(a);
				p.print(raceId + ",");
				horseIdList.add(createCsvLineForRd(e, p));
			}
		}
		return horseIdList;
	}

	private static String getHorseId(Element e) {
		String horseId = e.select("a").attr("href").split("/horse/")[1].replaceAll("/|ped", "");
		return horseId;
	}

	static void createFirstLine(PrintWriter p) {
		p.print(columns.RaceInfo.getValue());
		p.println();
	}

	private static String createCsvLineForRd(Element e, PrintWriter p) {
		// 順位、枠、馬番まで
		String horseId = null;
		  for(int n = 0 ; n < e.select("td").size();n++) {
			  if (n==3) {
				 horseId = getHorseId(e);
				p.print(horseId);
			  } else if(n==14) {
				printWeight(e,p,e.select("td").get(n).text());
			  } else {
				  p.print(e.select("td").get(n).text());
			  }

			 if (n == e.select("td").size() - 1) {
				  p.println();
			  } else {
				  p.print(",");
			  }
		  }
		return horseId;
	}
	private static void printWeight(Element e, PrintWriter p, String weightAndDiff) {
		if (weightAndDiff.equals("計不")){
			p.print(weightAndDiff);
			return;
		}
		p.print(weightAndDiff.substring(0, 3));
		p.print(",");
		String diff = weightAndDiff.substring(3).replaceAll("[¥¥(¥¥)]", "");
		p.print(diff);
	}
	public enum columns{
	    RaceInfo("raceId,raceName,course,distance,baba,place,barei,jouken"),
	    RaceData("raceId,order,wakuban,umaban,horseId,horseInfo,burden,jockey,time,chakusa,extraCode,pass,last,single,ninki,weight,weightDiff"),
	    RacePayInfo("raceId,singleNo,singlePay,fukushoNo1,fukushoPay1,fukushoNo2,fukushoPay2,fukushoNo3,fukushoPay3"),
	    HorseMst("horseId,horceName,sex,age,father,mother,bms,owner,trainer,breader,weight,career");

	  private String name;

	  columns(String name){
	    this.name = name;
	  }
	  public String getValue(){
	    return this.name;
	  }
	}

}
