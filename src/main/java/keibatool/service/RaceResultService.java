package keibatool.service;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public interface RaceResultService {
	void createRaceData(Document document, String raceId);

	void createRaceInfo(Document document, String raceId);

	void createRacePay(Document document, String raceId);

	/*
	 * horseIdからcountの数の代まで競走馬情報を取得して、馬マスタを更新します。データがない場合は新規作成します。
	 */
	void upsertHorseMst(String horseId, int count);
}
