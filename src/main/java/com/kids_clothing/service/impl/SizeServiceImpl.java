package com.kids_clothing.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.dao.SizeDao;
import com.kids_clothing.entity.Size;
import com.kids_clothing.entity.Typesize;
import com.kids_clothing.model.request.SizeRequest;
import com.kids_clothing.service.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    SizeDao sizeDao;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Size> findAll() {
        // TODO Auto-generated method stub
        return sizeDao.findAll();
    }

    @Override
	public List<Size> findAllByIsDeleteFalse() {
		// TODO Auto-generated method stub
		return sizeDao.findAllByIsDeleteFalse();
	}
    
    @Override
    public List<Size> findByTypeSize(Typesize typeSize) {
        // TODO Auto-generated method stub
        return sizeDao.findByTypesize(typeSize);
    }

    @Override
    @Transactional
    public List<Size> saveAll(List<Size> size) {
        // TODO Auto-generated method stub
        return sizeDao.saveAll(size);
    }

    @Override
    @Transactional
    public void deleteInBatch(List<Size> size) {
        // TODO Auto-generated method stub
        sizeDao.deleteAllInBatch(size);
    }

    @Override
    public Size create(SizeRequest sizeRequest) {
        Size size;
        new Size();
        Optional<Size> sizeOpt = sizeDao.findByNameAndAndIdtypesize(sizeRequest.getName(), sizeRequest.getIdtypesize());
        size = sizeOpt.orElseGet(() -> objectMapper.convertValue(sizeRequest, Size.class));
        size.setIsDelete(false);
        return sizeDao.save(size);
    }

    @Override
    public Size update(Long idsize, SizeRequest sizeRequest) {
        Size size = sizeDao.findById(idsize).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy size");
        });
        size.setName(sizeRequest.getName());
        size.setIsDelete(false);
        return sizeDao.save(size);
    }

    @Override
    public Size delete(Long idsize) {
        Size size = sizeDao.findById(idsize).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy size");
        });
        size.setIsDelete(true);
        return sizeDao.save(size);
    }
}
