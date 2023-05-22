package com.kids_clothing.service.service;

import com.kids_clothing.common.EnumRefund;
import com.kids_clothing.entity.Bill;
import com.kids_clothing.model.request.BillDto;
import com.kids_clothing.model.request.ShipingRequest;
import com.kids_clothing.model.request.UpdateBillCutomer;
import com.kids_clothing.shiping.BillShiping;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BillService {
    List<Bill> BillByCustomer(Long id);

    List<Bill> FindAll();

    boolean existsById(String id);

    Bill create(BillDto billDto) throws MessagingException, UnsupportedEncodingException;

    Bill updateBillCustomer(UpdateBillCutomer updateBillCutomer, String idbill);

    Bill cancelBill(String idbill) throws MessagingException, UnsupportedEncodingException;

    Bill cancelBillManager(String idbill) throws MessagingException, UnsupportedEncodingException;

    Bill confirmBillManager(String idbill) throws MessagingException, UnsupportedEncodingException;

    Bill shipBillManager(String idbill) throws MessagingException, UnsupportedEncodingException;

    Bill receivedBillManager(String idbill);

    Bill refundBillManager(String idbill, EnumRefund status, String note) throws MessagingException, UnsupportedEncodingException;

    List<Bill> findAllCustomer();

    Bill shipingBill(ShipingRequest shipingRequest);

    List<BillShiping> getShipingBill(String idBill) throws IOException;

    List<BillShiping> getShipingBillCustomer(String idBill) throws IOException;

    List<String> getAddress();

    List<Bill> findByDayNewCreate();

    List<Bill> findBystatus(String status);

    List<Bill> findBystatusshipping(String statusshipping);

    List<Bill> findbyID(String id);

    List<Bill> findbypayment(int payment);

    List<Bill> findByDateAndpaymentAndstatus(String date, String dateto, Integer payment, String status);

}
