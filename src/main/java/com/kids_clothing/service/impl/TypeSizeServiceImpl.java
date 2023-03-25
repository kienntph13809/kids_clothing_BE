package com.kids_clothing.service.impl;

import com.kids_clothing.Repository.TyperSizeDao;
import com.kids_clothing.entity.Typesize;

import com.kids_clothing.model.request.TypeSizeRequest;
import com.kids_clothing.service.service.TypeSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TypeSizeServiceImpl implements TypeSizeService {

    @Autowired
    TyperSizeDao typeSizeDao;

    @Override
    public List<Typesize> findAll() {
        // TODO Auto-generated method stub
		return typeSizeDao.findAll();
    }

    @Override
	public List<Typesize> findAllByIsDeleteFalse() {
		// TODO Auto-generated method stub
		return typeSizeDao.findAllByIsDeleteFalse();
	}
    
    @Override
    public Optional<Typesize> findById(Long id) {
        // TODO Auto-generated method stub
        return typeSizeDao.findById(id);
    }

    @Override
    public List<Typesize> saveAll(List<Typesize> typeSize) {
        // TODO Auto-generated method stub
        return typeSizeDao.saveAll(typeSize);
    }

    @Override
    public void deleteInBatch(List<Typesize> typeSize) {
        // TODO Auto-generated method stub
        typeSizeDao.deleteAllInBatch(typeSize);
    }

    @Override
    public Typesize create(TypeSizeRequest typeSizeRequest) {
        Typesize typesize = new Typesize();
        typesize.setName(typeSizeRequest.getName());

        return typeSizeDao.save(typesize);
    }

    @Override
    public Typesize update(Long id, TypeSizeRequest typeSizeRequest) {
        Typesize typesize = typeSizeDao.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy TyperSize");
        });
        typesize.setName(typeSizeRequest.getName());
        return typeSizeDao.save(typesize);
    }

    @Override
    public Typesize delete(Long id) {
        Typesize typesize = typeSizeDao.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy TyperSize");
        });
        typesize.setIsDelete(true);
        return typeSizeDao.save(typesize);
    }

}
