package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import keibatool.entity.RaceInfo;


@Dao
public interface RaceInfoDao {
	@Insert
    @Transactional
    int insert(RaceInfo raceInfo);
}
