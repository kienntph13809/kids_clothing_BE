package com.kids_clothing.service.service;

import com.kids_clothing.entity.Voucher;
import com.kids_clothing.model.request.VoucherRequest;

import java.text.ParseException;
import java.util.List;

public interface VoucherService {
    List<Voucher> findAll();
    
    List<Voucher> findAllByIsDeleteFalse();

    List<Voucher> findVoucherByAmount();

    List<Voucher> saveAll(List<Voucher> voucher);

    void deleteInBatch(List<Voucher> voucher);

    Voucher create(VoucherRequest voucherRequest) throws ParseException;

    Voucher update(Long id, VoucherRequest voucherRequest) throws ParseException;

    Voucher detele(Long id);

    List<Voucher> findAllVoucher();

    List<Voucher> findAllVoucherByIdEvent(Long idEvent);

    Voucher findById(Long id);

    List<Voucher> findbylikename(String name);
}
