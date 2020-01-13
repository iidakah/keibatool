package src.main.java.keibatool.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import lombok.Builder;

/*
 * 競馬場名、距離、条件などのレース情報をもつエンティティ
 */
@Entity
@Builder
public class RaceInfo {
	@Id
	String raceId;
	/*
	 * 例:有馬記念、３歳以上1勝クラスなど
	 */
	String raceName;
	/*
	 * 芝、ダート、障害
	 */
	String course;
	/*
	 * 距離。1000,2000など
	 */
	String distance;
	/*
	 * 馬場コンディション(良、稍重など）
	 */
	String baba;
	/*
	 * 競馬場名。東京、中山など
	 */
	String place;
	/*
	 * 馬齢条件。3歳,4歳以上など
	 */
	String barei;
	/*
	 * グレード条件(未勝利、G1、1000万、2勝クラスなど)
	 */
	String jouken;
}
