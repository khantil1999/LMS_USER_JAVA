package com.user.lms.repository;

import com.user.lms.entity.TruckProviderArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckProviderAreaRepository extends JpaRepository<TruckProviderArea,Long> {

    @Query("SELECT tpa FROM TruckProviderArea tpa WHERE tpa.truckProvider.id = ?1")
    List<TruckProviderArea> getTruckProviderAreaById(long id);

    @Modifying
    @Query("DELETE  FROM TruckProviderArea tpa WHERE tpa.truckProvider.id = ?1 and tpa.taluka.id in (?2)")
    void deleteTruckProviderAreaByTalukaAndTruckProvider(long id, List<Long> ids);

    @Query("SELECT tpa from TruckProviderArea tpa where tpa.taluka.id in (?1)")
    List<TruckProviderArea> getTruckProviderAreaByTaluka(List<Long> taluks);

}
