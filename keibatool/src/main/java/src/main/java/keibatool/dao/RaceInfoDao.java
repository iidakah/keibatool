package src.main.java.keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import src.main.java.keibatool.entity.RaceInfo;

@Dao
public interface RaceInfoDao {
	@Insert
    @Transactional
    int insert(RaceInfo raceInfo);
}
