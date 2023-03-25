package com.kids_clothing.service.impl;

import com.kids_clothing.Repository.PropertyDao;
import com.kids_clothing.entity.Property;
import com.kids_clothing.service.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyDao propertyDao;

    @Override
    public List<Property> findAll() {
        // TODO Auto-generated method stub
        return propertyDao.findAll();
    }

    @Override
	public List<Property> findAllByIsDeleteFalse() {
		// TODO Auto-generated method stub
		return propertyDao.findAllByIsDeleteFalse();
	}
    
    @Override
    @Transactional
    public List<Property> saveAll(List<Property> property) {
        // TODO Auto-generated method stub
        return propertyDao.saveAll(property);
    }

    @Override
    @Transactional
    public void deleteInBatch(List<Property> property) {
        // TODO Auto-generated method stub
        propertyDao.deleteAllInBatch(property);
    }

}
