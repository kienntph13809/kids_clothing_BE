package com.kids_clothing.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.Repository.EventDao;
import com.kids_clothing.Repository.VoucherDao;
import com.kids_clothing.entity.Event;
import com.kids_clothing.entity.Voucher;
import com.kids_clothing.model.request.VoucherRequest;
import com.kids_clothing.service.service.VoucherService;
import com.kids_clothing.utils.CompareDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherDao voucherDao;

    @Autowired
    EventDao eventDao;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Voucher> findAll() {
        // TODO Auto-generated method stub
        return voucherDao.findAll();
    }

    @Override
	public List<Voucher> findAllByIsDeleteFalse() {
		// TODO Auto-generated method stub
		return voucherDao.findAllByIsDeleteFalse();
	}
    
    @Override
    public List<Voucher> findVoucherByAmount() {
        // TODO Auto-generated method stub
        return voucherDao.findVoucherByAmout();
    }

    @Override
    @Transactional
    public List<Voucher> saveAll(List<Voucher> voucher) {
        // TODO Auto-generated method stub
        return voucherDao.saveAll(voucher);
    }

    @Override
    @Transactional
    public void deleteInBatch(List<Voucher> voucher) {
        // TODO Auto-generated method stub
        voucherDao.deleteInBatch(voucher);
    }

    @Override
    public Voucher create(VoucherRequest voucherRequest) throws ParseException {
        Voucher voucher = objectMapper.convertValue(voucherRequest, Voucher.class);

        Event event = eventDao.findByIdAndIsDeleteFalse(voucherRequest.getIdevent()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy sự kiện");
        });
        if (CompareDate.compareDate(new Date(), event.getEndday()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event đã hết");
        }
        if (voucherRequest.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số lượng phải trên 0");
        }
        return voucherDao.save(voucher);
    }

    @Override
    public Voucher update(Long id, VoucherRequest voucherRequest) throws ParseException {
        Voucher voucher = voucherDao.findByIdIsAndIsDeleteFalse(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy Voucher");
        });
        voucher.setAmount(voucherRequest.getAmount());
        voucher.setDiscount(voucherRequest.getDiscount());
        voucher.setName(voucherRequest.getName());

        return voucherDao.save(voucher);
    }

    @Override
    public Voucher detele(Long id) {
        Voucher voucher = voucherDao.findByIdIsAndIsDeleteFalse(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy Voucher");
        });
        voucher.setIsDelete(true);
        return voucherDao.save(voucher);
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return voucherDao.findAllByIsDeleteFalse();
    }

    @Override
    public List<Voucher> findAllVoucherByIdEvent(Long idEvent) {
        return voucherDao.findAllByIdeventAndIsDeleteFalse(idEvent);
    }

    @Override
    public Voucher findById(Long id) {
        return voucherDao.findByIdIsAndIsDeleteFalse(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy voucher");
        });
    }

}
