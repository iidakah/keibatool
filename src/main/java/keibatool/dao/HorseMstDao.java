package keibatool.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.transaction.annotation.Transactional;

import keibatool.AppConfig;
import keibatool.entity.HorseMst;

@ConfigAutowireable
@Dao(config = AppConfig.class)
public interface HorseMstDao {
	@Insert
    @Transactional
    int insert(HorseMst horseMst);
}
