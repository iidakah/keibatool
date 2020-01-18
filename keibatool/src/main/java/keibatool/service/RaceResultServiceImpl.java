package keibatool.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import keibatool.dao.HorseMstDao;
import keibatool.dao.RaceDataDao;
import keibatool.dao.RaceInfoDao;
import keibatool.dao.RacePayDao;
import keibatool.entity.RaceData;

@Service
public class RaceResultServiceImpl implements RaceResultService {
	private static final String BREEDER_TEXT = "生産者";

	@Autowired
	RaceDataDao raceDataDao;

	@Autowired
	RaceInfoDao raceInfoDao;

	@Autowired
	RacePayDao racePayDao;

	@Autowired
	HorseMstDao horseMstDao;

	@Override
	public void createRaceData(Document document, String raceId) {
		Elements elements = document.select(".race_table_01");
		ArrayList<RaceData> RaceDataList = new ArrayList<>();
		for (Element element : elements) {
			// 1頭単位でtrがある
			Elements trs = element.select("tr");
			for(int a = 1; a < trs.size(); a++) {
				Element tr = trs.get(a);
				raceDataDao.insert(createRaceData(tr));
			}
		}
	}

	private RaceData createRaceData(Element tr) {
		// 順位、枠、馬番まで
		RaceData raceData = new RaceData();
		Elements tds = tr.select("td");
		raceData.setHorseId(tr.select("a").attr("href").split("/horse/")[1].replaceAll("/|ped", ""));
		raceData.setOrder(Integer.parseInt(tds.get(0).text()));
		raceData.setWakuban(Integer.parseInt(tds.get(1).text()));
		raceData.setUmaban(Integer.parseInt(tds.get(2).text()));
		raceData.setHorseInfo(tds.get(4).text());
		raceData.setAge(Integer.parseInt(tds.get(4).text().substring(1,2)));
		raceData.setSex(tds.get(4).text().substring(0,1));
		raceData.setBurden(Float.parseFloat(tds.get(5).text()));
		raceData.setJockey(tds.get(6).text());
		raceData.setTime(tds.get(7).text());
		raceData.setChakusa(tds.get(8).text());
		raceData.setExtraCode(tds.get(9).text());
		raceData.setPass(tds.get(10).text());
		raceData.setLast(Float.parseFloat(tds.get(11).text()));
		raceData.setSingle(Float.parseFloat(tds.get(12).text()));
		raceData.setNinki(Integer.parseInt(tds.get(13).text()));
		setWeightsAndDiff(raceData, tds.get(14).text());
		return raceData;
	}

	private void setWeightsAndDiff(RaceData raceData, String weightAndDiff) {
		if (weightAndDiff.equals("計不")){
			return;
		}
		raceData.setWeight(Integer.parseInt(weightAndDiff.substring(0, 3)));
		String diff = weightAndDiff.substring(3).replaceAll("[¥¥(¥¥)]", "");
		raceData.setWeightDiff(Integer.parseInt(diff));
	}

	@Override
	public void createRaceInfo(Document document, String raceId) {
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
		RaceInfo raceInfo = RaceInfo.builder()
				.raceId(raceId)
				.raceName(raceName)
				.course(course)
				.distance(distance)
				.baba(baba)
				.place(place)
				.barei(barei)
				.jouken(jouken)
				.build();
		raceInfoDao.insert(raceInfo);
	}

	private String findCondition(String conditions, String ptnStr) {
		Pattern ptn = Pattern.compile(ptnStr);
		Matcher m = ptn.matcher(conditions);
		if (m.find()) {
			return m.group();
		}
		return "";
}

	public void createRacePay(Document document, String raceId) {
		Elements els = document.select(".pay_table_01").get(0).select("tr");
		// 単勝
		String singleNo = getValue(els,0,0);
		String singlePay = getValue(els,0,1).replaceAll(",", "");
		String[] fukushoNo = getValue(els,1,0).split(" ");//11 9 5
		String[] fukushoPay = getValue(els,1,1).replaceAll(",", "").split(" ");
		RacePay racePay = RacePay.builder().singleNo(Integer.parseInt(singleNo))
				.singlePay(Integer.parseInt(singlePay))
				.fukushoNo1(Integer.parseInt(fukushoNo[0]))
				.fukushoNo2(Integer.parseInt(fukushoNo[1]))
				.fukushoNo3(Integer.parseInt(fukushoNo.length > 2?fukushoNo[2]:"0"))
				.fukushoNo4(Integer.parseInt(fukushoNo.length > 3?fukushoNo[3]:"0"))
				.fukushoPay1(Integer.parseInt(fukushoPay[0]))
				.fukushoPay2(Integer.parseInt(fukushoPay[1]))
				.fukushoPay3(Integer.parseInt(fukushoPay.length > 2?fukushoPay[2]:"0"))
				.fukushoPay4(Integer.parseInt(fukushoPay.length > 3?fukushoPay[3]:"0"))
				.build();
		racePayDao.insert(racePay);
	}
	private static String getValue(Elements els, int i, int j) {
		return els.get(i).select("td").get(j).text();
	}

	@Override
	public void upsertHorseMst(String horseId, int count) {
		String horseUrlPre = "https://db.netkeiba.com/horse/";
		try {
			if(canSkip(horseId)) {

			} else {
				Document horsePage = Jsoup.connect(horseUrlPre+horseId+"/").get();
				Elements horseTitles = horsePage.select(".horse_title");
				if(horseTitles.isEmpty()) return;
				Element horseTitle = horseTitles.get(0);
				String horseName = horseTitle.select("h1").get(0).text().replaceAll(" 　","");
				//現役牝3歳鹿毛ボンドロール
				String horseTitleStr = horseTitle.select(".txt_01").get(0).text();
				String sex = findCondition(horseTitleStr,"牡|牝|セ");
				String age = findCondition(horseTitleStr, "[0-9]");
				Elements bloodTable = horsePage.select(".blood_table").get(0).select("td");
				String sire = getHorseId(bloodTable.get(0));
				String broodMare = getHorseId(bloodTable.get(3));
				String bms = getHorseId(bloodTable.get(4));
				Elements prof = horsePage.select(".db_prof_table").get(0).select("td");
				Elements profTh = horsePage.select(".db_prof_table").get(0).select("th");
				String owner = prof.get(2).text();
				String trainer = prof.get(1).text();
				String breeder = null;
				String career = null;
				if (StringUtils.equals(profTh.get(3).text(),BREEDER_TEXT)) {
					 breeder = prof.get(3).text();
					 career = prof.get(7).text();
				} else {
					breeder = prof.get(4).text();
					career = prof.get(8).text();
				}
				//HorseMst("horseId,horceName,sex,age,father,mother,bms,owner,trainer,breader,career");
				HorseMst horseData = HorseMst.builder()
						.horseId(horseId)
						.horseName(horseName)
						.sex(sex)
						.age(Integer.parseInt(age))
						.sire(sire)
						.broodMare(broodMare)
						.bms(bms)
						.owner(owner)
						.breeder(breeder)
						.career(career)
						.updateDate(LocalDate.now())
						.build();
				horseMstDao.insert(horseData);
				ArrayList<String> parents = new ArrayList<>();
				parents.add(sire);
				parents.add(broodMare);
				int numNext = count - 1;
				if (count > 0) {
					upsertHorseMstBulk(parents,numNext, true);
				}
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

	private void upsertHorseMstBulk(ArrayList<String> horseIdList, int count, boolean b) {
		for(String horseId : horseIdList) {
			upsertHorseMst(horseId, count);
		}

	}

	private boolean canSkip(String horseId) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	private static String getHorseId(Element e) {
		String horseId = e.select("a").attr("href").split("/horse/")[1].replaceAll("/|ped", "");
		return horseId;
	}

}
