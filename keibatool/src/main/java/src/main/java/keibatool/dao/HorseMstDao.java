package src.main.java.keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.springframework.transaction.annotation.Transactional;

import src.main.java.keibatool.entity.HorseMst;

@Dao
public interface HorseMstDao {
	@Insert
    @Transactional
    int insert(HorseMst horseMst);
}
