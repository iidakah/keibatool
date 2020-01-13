package src.main.java.keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import src.main.java.keibatool.entity.RacePay;

@Dao
public interface RacePayDao {
	@Insert
    @Transactional
    int insert(RacePay racePay);
}
