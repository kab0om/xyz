package xyz;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;

/**
 * Created by kab00m on 03.09.15.
 */
@org.springframework.stereotype.Repository
public interface RowRepository extends CrudRepository<Row, BigInteger> {

}
