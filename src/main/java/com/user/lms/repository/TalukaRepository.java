package com.user.lms.repository;

import com.user.lms.entity.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalukaRepository  extends JpaRepository<Taluka,Long> {

    @Query(value = "SELECT * FROM taluka WHERE district_id IN (:ids)", nativeQuery = true)
    List<Taluka> loadTalukaByDistrict(@Param("ids") String[] districtIds);


    @Query(value = "SELECT t from Taluka t where t.pinCode in (?1) group by t.pinCode having count(DISTINCT (t.pinCode))>=1")
    List<Taluka> searchByPinCode(List<Long> pincodes);
}
