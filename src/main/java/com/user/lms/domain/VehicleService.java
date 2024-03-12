package com.user.lms.domain;

import com.user.lms.entity.Photo;
import com.user.lms.entity.Taluka;
import com.user.lms.entity.TruckProviderArea;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.PhotoModel;
import com.user.lms.models.UserDetailsModel;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleListRepository vehicleListRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private TalukaRepository talukaRepository;

    @Autowired
    private TruckProviderAreaRepository truckProviderAreaRepository;
    @Autowired
    private UserRepository userRepository;

    public List<VehicleDetailsModel> getAllVehicles() {
        List<VehicleList> vehicleLists = this.vehicleListRepository.findAll();
        List<VehicleDetailsModel> detailsModels = new ArrayList<>();
        vehicleLists.forEach(vehicle -> {
            VehicleDetailsModel vehicleDetailsModel = this.mapVehicle(vehicle);
            detailsModels.add(vehicleDetailsModel);
        });
        return detailsModels;
    }


    public VehicleDetailsModel getVehicleById(Long id) {
        VehicleList vehicle = this.vehicleListRepository.getReferenceById(id);
        return this.mapVehicle(vehicle);
    }

    private VehicleDetailsModel mapVehicle(VehicleList vehicle) {
        VehicleDetailsModel vehicleDetailsModel = new VehicleDetailsModel();

        List<Photo> photos = this.photoRepository.getPhotoByVehicle(String.valueOf(vehicle.getId()));

        List<PhotoModel> photoModels = photos.stream().map(photo -> {
            PhotoModel photoModel = new PhotoModel();
            photoModel.setPhotoUrl(photo.getPath());
            photoModel.setId(photo.getId());
            return photoModel;
        }).toList();

        vehicleDetailsModel.setId(vehicle.getId());
        vehicleDetailsModel.setCapacity(vehicle.getCapacity());
        vehicleDetailsModel.setPhoto(photoModels);

        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setEmail(vehicle.getDriver().getEmail());
        userDetailsModel.setFirstName(vehicle.getDriver().getFirstName());
        userDetailsModel.setLastName(vehicle.getDriver().getLastName());
        userDetailsModel.setMobileNo(vehicle.getDriver().getMobileNo());

        vehicleDetailsModel.setTruckProvider(userDetailsModel);

        vehicleDetailsModel.setLicensePlate(vehicle.getLicensePlate());
        //vehicleDetailsModel.setTruckProvider(vehicle.getDriver());
        vehicleDetailsModel.setManufacturer(vehicle.getManufacturer());
        vehicleDetailsModel.setModel(vehicle.getModel());
        vehicleDetailsModel.setWheel(vehicle.getWheel());
        vehicleDetailsModel.setCurrentMileage(vehicle.getCurrentMileage());
        vehicleDetailsModel.setFuelType(vehicle.getFuelType());
        return vehicleDetailsModel;
    }

    public List<VehicleDetailsModel> searchVehicles(String startDestination, String endDestination) {
        List<Long> pincodes = new ArrayList<>();
        pincodes.add(Long.parseLong(startDestination));
        pincodes.add(Long.parseLong(endDestination));

        List<Taluka> talukas = this.talukaRepository.searchByPinCode(pincodes);
        List<Long> talukIds = talukas.stream().map(Taluka::getId).toList();
        System.out.println("talukas"+ Arrays.toString(pincodes.toArray()));
        List<TruckProviderArea> truckProviderAreas = this.truckProviderAreaRepository.getTruckProviderAreaByTaluka(talukIds);
        System.out.println("SIZE:" + truckProviderAreas.size());
        List<Long> tpIds = truckProviderAreas.stream().map(truckProviderArea -> truckProviderArea.getTruckProvider().getId()).collect(Collectors.toList());
        System.out.println("TPIds"+ Arrays.toString(tpIds.toArray()));
        List<VehicleList> vehicleLists = this.vehicleListRepository.getVehicleListByTruckProvider(tpIds);
        List<VehicleDetailsModel> detailsModels = new ArrayList<>();
        vehicleLists.forEach(vehicle -> {
            VehicleDetailsModel vehicleDetailsModel = this.mapVehicle(vehicle);
            detailsModels.add(vehicleDetailsModel);
        });

        return detailsModels;

    }
}
