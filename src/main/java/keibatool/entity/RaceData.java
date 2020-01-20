package keibatool.entity;

import org.seasar.doma.Entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Entity
public class RaceData {
	private String raceId;
	/*
	 * 馬を一意に示すid
	 */
	private String horseId;
	/*
	 * 着順
	 */
	private int order;
	/*
	 *枠番
	 */
	private int wakuban;
	/*
	 * 馬番
	 */
	private int umaban;

	/*
	 * 性別とばれい。牝3など。あとで分けたい。
	 */
	private String horseInfo;
	/*
	 * レース当時の馬齢
	 */
	private int age;
	/*
	 * レース当時の性別
	 */
	private String sex;
	/*
	 * 負担重量
	 */
	private float burden;
	/*
	 * 騎手
	 */
	private String jockey;
	/*
	 * 走破時計
	 */
	private String time;
	/*
	 * 着差
	 */
	String chakusa;
	/*
	 * 課金データ。特に使わない
	 */
	String extraCode;
	/*
	 * 経過
	 */
	String pass;
	/*
	 * 上がり3F
	 */
	private float last;
	/*
	 * 単勝オッズ
	 */
	private float single;
	/*
	 * 人気
	 */
	private int ninki;
	/*
	 * 馬体重
	 */
	private int weight;
	/*
	 * 前走との体重差
	 */
	private int weightDiff;

}
