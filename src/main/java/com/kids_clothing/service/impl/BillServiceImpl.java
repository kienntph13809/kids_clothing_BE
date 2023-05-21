package com.kids_clothing.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kids_clothing.common.EnumRefund;
import com.kids_clothing.common.EnumStatus;
import com.kids_clothing.common.Message;
import com.kids_clothing.controllers.BaseController;
import com.kids_clothing.repository.*;
import com.kids_clothing.entity.*;

import com.kids_clothing.model.request.BillDto;
import com.kids_clothing.model.request.QuantityRequest;
import com.kids_clothing.model.request.ShipingRequest;
import com.kids_clothing.model.request.UpdateBillCutomer;
import com.kids_clothing.service.service.BillService;
import com.kids_clothing.service.service.CustomerService;
import com.kids_clothing.service.service.OrderDetailService;
import com.kids_clothing.shiping.BillShiping;
import com.kids_clothing.shiping.GetBillShiping;
import com.kids_clothing.vnpay.MamipayServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl extends BaseController implements BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    BillDao billDao;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    CustomerDao customerDao;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    MamipayServiceImpl mamipayService;
    @Autowired
    MamiPayDao mamiPayDao;
    @Autowired
    CustomerService customerService;
    @Autowired
    MailServiceImpl mailService;
    @Autowired
    VoucherDao voucherDao;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    QuantityDao quantityDao;

    @Autowired
    HistoryDao historyDao;

    public BillServiceImpl(BillDao billDao, OrderDetailDao orderDetailDao) {
        this.billDao = billDao;
        this.orderDetailDao = orderDetailDao;
    }

    @Override
    public List<Bill> BillByCustomer(Long id) {
        return billDao.findAllByIdCustomer(id);
    }

    @Override
    public List<Bill> FindAll() {
        return billDao.findAll();
    }

    @Override
    public boolean existsById(String id) {
        return billDao.existsById(id);
    }

    @Override
    @Transactional
    public Bill create(BillDto billDto) throws MessagingException, UnsupportedEncodingException {
        //tim kiem nguoi dung
       try {
           Customer customer = new Customer();
           Bill bill = objectMapper.convertValue(billDto, Bill.class);
           if(billDto.getStatusshipping().equals("Đơn không đăng nhập")){
               customer = customerDao.findByIdaccount(8L);
               bill.setIdCustomer(6L);
               if(billDto.getAddress().equals("Mua hàng tại quầy")){
                   bill.setStatus(EnumStatus.KHACH_DA_NHAN_HANG);
                   return getBill(billDto, customer, bill);
               }else {
                   bill.setStatus(EnumStatus.CHUA_XAC_NHAN);
                   return getBill(billDto, customer, bill);
               }

           }
           else if(billDto.getStatusshipping().equals("Đang xử lý")) {
               customer = customerDao.findByIdaccount(getAuthUID());
               bill.setIdCustomer(customer.getId());
               bill.setCreateAt(new Date());
               bill.setUpdateAts(new Date());

               bill.setId(RandomStringUtils.randomNumeric(8));
               while (billDao.existsById(bill.getId())) {
                   bill.setId(RandomStringUtils.randomNumeric(8));
               }
               //dánh sách sp có quantiiy
               // danh sách chi tiết đơn hàng
               List<Orderdetail> orderdetails = new ArrayList<>();

               List<QuantityRequest> quantityRequests = billDto.getList_quantity();
               for (QuantityRequest qty : quantityRequests) {
                   ProductDetail productDetail = quantityDao.findById(qty.getId_quantity()).
                           orElseThrow(() -> new RuntimeException("Lỗi thêm sản phẩm"));
                   Product product = objectMapper.convertValue(productDetail.getProduct(), Product.class);
                   StringBuilder message = new StringBuilder();
                   if (productDetail.getQuantity() < qty.getBill_quantity()) {
                       String er = "Sản phẩm: " + productDetail.getProduct().getName()
                               + " Size: " + productDetail.getSize().getName() + "-" + productDetail.getProperty().getName()
                               + " đã hết hàng.";
                       message.append(er).append("\n");
                   }
                   if (message.toString().length() > 0) {
                       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
                   }
                   Orderdetail orderdetail = new Orderdetail();
                   orderdetail.setIdproductDetail(productDetail.getId());
                   orderdetail.setQuantitydetail(qty.getBill_quantity());
                   orderdetail.setPrice(product.getPrice());
                   orderdetail.setDownprice(product.getDiscount() != null ? product.getPrice() * (product.getDiscount() / 100) : 0);
                   orderdetail.setIntomoney(product.getPrice() - orderdetail.getDownprice());
                   orderdetail.setIdbill(bill.getId());
                   orderdetail.setCreateAt(new Date());

                   orderdetails.add(orderdetail);
               }
               if (!billDto.getPayment()) {
                   //không thanh toán qua ví
                   bill.setDiscount(0d);
                   bill.setVoucher_id(null);
               } else {
                   //thanh toan bang vi
                   Mamipay mamipay = mamipayService.ByCustomer(customer.getId());
                   if (mamipay.getSurplus() < billDto.getTotal()) {
                       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Số dư ví phải lớn hơn hoặc bằng: " + billDto.getTotal());
                   }
                   Voucher voucher;
                   if (billDto.getVoucher_id() != null) {
                       voucher = voucherDao.findByIdIsAndIsDeleteFalse(billDto.getVoucher_id()).orElseThrow(() -> {
                           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy voucher");
                       });
                       if (voucher.getAmount() <= 0) {
                           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voucher đã hết");
                       }
                       bill.setDiscount(voucher.getDiscount());
                       bill.setVoucher_id(voucher.getId());
                       voucher.setAmount(voucher.getAmount() - 1L);
                       voucherDao.save(voucher);
                   }
                   mamipay.setSurplus(mamipay.getSurplus() - billDto.getDowntotal());
                   mamiPayDao.save(mamipay);
                   createPayBill(mamipay, bill);
               }
               billDao.save(bill);
               orderDetailDao.saveAll(orderdetails);
               if (customer.getAccount().getEmail() != null) {
                   mailService.sendCreateBill(customer.getAccount(), bill);
                   System.out.println("gui mail roi nha");
               }
               return bill;
           }
           return null;
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }

    private Bill getBill(BillDto billDto, Customer customer, Bill bill) throws UnsupportedEncodingException, MessagingException {
        bill.setCreateAt(new Date());
        bill.setUpdateAts(new Date());

        bill.setId(RandomStringUtils.randomNumeric(8));
        while (billDao.existsById(bill.getId())) {
            bill.setId(RandomStringUtils.randomNumeric(8));
        }
        // danh sách chi tiết đơn hàng
        List<Orderdetail> orderdetails = new ArrayList<>();

        List<QuantityRequest> quantityRequests = billDto.getList_quantity();
        for (QuantityRequest qty : quantityRequests) {
            ProductDetail productDetail = quantityDao.findById(qty.getId_quantity()).
                    orElseThrow(() -> new RuntimeException("Lỗi thêm sản phẩm"));
            Product product = objectMapper.convertValue(productDetail.getProduct(), Product.class);
            StringBuilder message = new StringBuilder();
            if (productDetail.getQuantity() < qty.getBill_quantity()) {
                String er = "Sản phẩm: " + productDetail.getProduct().getName()
                        + " Size: " + productDetail.getSize().getName() + "-" + productDetail.getProperty().getName()
                        + " đã hết hàng.";
                message.append(er).append("\n");
            }
            if (message.toString().length() > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
            }
            Orderdetail orderdetail = new Orderdetail();
            orderdetail.setIdproductDetail(productDetail.getId());
            orderdetail.setQuantitydetail(qty.getBill_quantity());
            orderdetail.setPrice(product.getPrice());
            orderdetail.setDownprice(product.getDiscount() != null ? product.getPrice() * (product.getDiscount() / 100) : 0);
            orderdetail.setIntomoney(product.getPrice() - orderdetail.getDownprice());
            orderdetail.setIdbill(bill.getId());
            orderdetail.setCreateAt(new Date());

            orderdetails.add(orderdetail);
        }
        if (!billDto.getPayment()) {
            //không thanh toán qua ví
            bill.setDiscount(0d);
            bill.setVoucher_id(null);
        }
        billDao.save(bill);
        orderDetailDao.saveAll(orderdetails);
        if (customer.getAccount().getEmail() != null) {
            mailService.sendCreateBill(customer.getAccount(), bill);
            System.out.println("gui mail roi nha");
        }
        return bill;
    }

    public void createPayBill(Mamipay mamipay, Bill bill) {
        History history = new History();
        history.setAmounts(bill.getTotal());
        history.setStatus(true);
        history.setSurplus(mamipay.getSurplus());
        history.setDescription("Thanh toán hóa đơn mã " + bill.getId());
        history.setContent("Thanh toán hóa đơn");
        history.setTrading_code(0L);
        history.setTime(new Date());
        history.setIdmamipay(mamipay.getIdmamipay());

        historyDao.save(history);
    }

    @Override
    public Bill updateBillCustomer(UpdateBillCutomer updateBillCutomer, String idbill) {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });
        if (!(bill.getStatus().equals(EnumStatus.CHUA_XAC_NHAN))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bạn không thể sửa đơn khi đã xác nhận");
        }
        //tim kiem nguoi dung
        Customer customer = customerService.findByAccount(getAuthUID());
        if (!Objects.equals(customer.getId(), bill.getIdCustomer())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền sửa đơn này");
        }

        bill.setAddress(updateBillCutomer.getAddress());
        bill.setSdt(updateBillCutomer.getSdt());
        bill.setFullname(updateBillCutomer.getFullname());
        bill.setNote(updateBillCutomer.getNote());
        bill.setUpdateAts(new Date());
        return billDao.save(bill);
    }

    @Override
    @Transactional
    public Bill cancelBill(String idbill) throws MessagingException, UnsupportedEncodingException {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });

        //tim kiem nguoi dung
        Customer customer = customerService.findByAccount(getAuthUID());
        if (!Objects.equals(customer.getId(), bill.getIdCustomer())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền sửa đơn này");
        }
        if (bill.getStatus() == EnumStatus.CHUA_XAC_NHAN || bill.getStatus() == EnumStatus.DA_XAC_NHAN_VA_DONG_GOI) {
            bill.setStatus(EnumStatus.HUY);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể hủy đơn");
        }
        //hoàn tiền về nếu thanh toán qua ví
        if (bill.getPayment()) {
            Mamipay mamipay = mamipayService.ByCustomer(customer.getId());
            mamipay.setSurplus(mamipay.getSurplus() + bill.getTotal());
            mamiPayDao.save(mamipay);
            refundPayBill(mamipay, bill);
        }
//        if (customer.getAccount().getEmail() != null) {
//            mailService.sendCancelBill(customer.getAccount(), bill);
//        }
        bill.setUpdateAts(new Date());
        return billDao.save(bill);
    }

    public void refundPayBill(Mamipay mamipay, Bill bill) {
        History history = new History();
        history.setAmounts(bill.getDowntotal());
        history.setStatus(true);
        history.setSurplus(mamipay.getSurplus());
        history.setDescription("Hoàn tiền hóa đơn mã " + bill.getId());
        history.setContent("Hoàn tiền hóa đơn");
        history.setTrading_code(0L);
        history.setTime(new Date());
        history.setIdmamipay(mamipay.getIdmamipay());

        historyDao.save(history);
    }

    @Override
    @Transactional
    public Bill cancelBillManager(String idbill) throws MessagingException, UnsupportedEncodingException {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });

        //tim kiem nguoi dung
        Customer customer = bill.getCustomer();

        if (bill.getStatus() == EnumStatus.CHUA_XAC_NHAN || bill.getStatus() == EnumStatus.DA_XAC_NHAN_VA_DONG_GOI) {
            bill.setStatus(EnumStatus.HUY);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể hủy đơn");
        }
        if (bill.getStatus() == EnumStatus.DA_XAC_NHAN_VA_DONG_GOI) {
            //hoàn số lượng về kho
            List<ProductDetail> quantities = new ArrayList<>();
            List<Orderdetail> orderdetails = bill.getOrderdetails();
            for (Orderdetail orderdetail : orderdetails) {
                ProductDetail productDetail = orderdetail.getProductDetail();

                productDetail.setQuantity(productDetail.getQuantity() + orderdetail.getQuantitydetail());
                quantities.add(productDetail);
            }
            quantityDao.saveAll(quantities);
        }
        //hoàn tiền về ví nếu thanh toán qua ví
        if (bill.getPayment()) {
            Mamipay mamipay = mamipayService.ByCustomer(customer.getId());
            mamipay.setSurplus(mamipay.getSurplus() + bill.getDowntotal());
            mamiPayDao.save(mamipay);
            refundPayBill(mamipay, bill);
        }
        if (customer.getAccount().getEmail() != null) {
            mailService.sendCreateManagerBill(customer.getAccount(), bill);
            System.out.println("đã gửi mail");
        }
        bill.setUpdateAts(new Date());
        return billDao.save(bill);
    }

    @Override
    public Bill confirmBillManager(String idbill) throws MessagingException, UnsupportedEncodingException {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });


        if (bill.getStatus() == EnumStatus.CHUA_XAC_NHAN) {
            bill.setStatus(EnumStatus.DA_XAC_NHAN_VA_DONG_GOI);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không thể xác nhận đơn");
        }
        //hoàn số lượng về kho
        List<ProductDetail> quantities = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        List<Orderdetail> orderdetails = bill.getOrderdetails();
        for (Orderdetail orderdetail : orderdetails) {
            ProductDetail productDetail = orderdetail.getProductDetail();
            if (productDetail.getQuantity() < orderdetail.getQuantitydetail()) {
                String er = "Sản phẩm: " + productDetail.getProduct().getName()
                        + " Size: " + productDetail.getSize().getName() + "-" + productDetail.getProperty().getName()
                        + " đã hết hàng.";
                message.append(er).append("\n");
            }
            productDetail.setQuantity(productDetail.getQuantity() - orderdetail.getQuantitydetail());
            quantities.add(productDetail);
        }
        if (message.toString().length() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
        }
        quantityDao.saveAll(quantities);
        bill.setUpdateAts(new Date());
        return billDao.save(bill);

    }

    @Override
    public Bill shipBillManager(String idbill) throws MessagingException, UnsupportedEncodingException {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });

        if (bill.getStatus() == EnumStatus.DA_XAC_NHAN_VA_DONG_GOI) {
            bill.setStatus(EnumStatus.DA_GIAO_BEN_VAN_CHUYEN);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi chuyển đơn");
        }
        //hoàn số lượng về kho
//        List<Quantity> quantities = new ArrayList<>();
//        StringBuilder message = new StringBuilder();
//        List<Orderdetail> orderdetails = bill.getOrderdetails();
//        for (Orderdetail orderdetail : orderdetails) {
//            Quantity quantity = orderdetail.getQuantity();
//            if (quantity.getQuantity() < orderdetail.getQuantitydetail()) {
//                String er = "Sản phẩm: " + quantity.getProduct().getName()
//                        + " Size: " + quantity.getSize().getName() + "-" + quantity.getProperty().getName()
//                        + " đã hết hàng.";
//                message.append(er).append("\n");
//            }
//            quantity.setQuantity(quantity.getQuantity() - orderdetail.getQuantitydetail());
//            quantities.add(quantity);
//        }
//        if (message.toString().length() > 0) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message.toString());
//        }
//        quantityDao.saveAll(quantities);
//        if (bill.getCustomer().getAccount().getEmail() != null) {
//            mailService.sendConfirmManagerBill(bill.getCustomer().getAccount(), bill);
//        }
        bill.setUpdateAts(new Date());
        return billDao.save(bill);
    }

    @Override
    public Bill receivedBillManager(String idbill) {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });

        if (bill.getStatus() == EnumStatus.DA_GIAO_BEN_VAN_CHUYEN) {
            bill.setStatus(EnumStatus.KHACH_DA_NHAN_HANG);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi chuyển đơn");
        }
        bill.setUpdateAts(new Date());
        return billDao.save(bill);
    }

    @Override
    @Transactional
    public Bill refundBillManager(String idbill, EnumRefund status, String note) {
        Bill bill = billDao.findById(idbill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });

        if (bill.getStatus() == EnumStatus.DA_GIAO_BEN_VAN_CHUYEN) {
            bill.setStatus(EnumStatus.HOAN_HANG);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lỗi chuyển đơn");
        }

        if (status == null || status.toString().length() == 0) {
            bill.setRefund(EnumRefund.KHAC);
            bill.setNoterefund(Message.KHONG_RO);
        } else {
            bill.setRefund(status);
            switch (status) {
                case THAT_BAI:
                    bill.setNoterefund(Message.THAT_BAI);
                    break;
                case SAI_DIA_CHI:
                    bill.setNoterefund(Message.SAI_DIA_CHI);
                    break;
                case KHACH_HUY:
                    bill.setNoterefund(Message.KHACH_HUY);
                    break;
                case SAI_SAN_PHAM:
                    bill.setNoterefund(Message.SAI_SAN_PHAM);
                    break;
                case KHAC:
                    bill.setNoterefund(note);
                    break;
            }
        }
        //hoan sl ve kho
        List<ProductDetail> quantities = new ArrayList<>();
        List<Orderdetail> orderdetails = bill.getOrderdetails();
        for (Orderdetail orderdetail : orderdetails) {
            ProductDetail productDetail = orderdetail.getProductDetail();

            productDetail.setQuantity(productDetail.getQuantity() + orderdetail.getQuantitydetail());
            quantities.add(productDetail);
        }
        quantityDao.saveAll(quantities);
        //hoàn tiền về ví nếu thanh toán qua ví
        Customer customer = customerDao.findByIdaccount(bill.getIdCustomer());
        if (bill.getPayment()) {
            Mamipay mamipay = mamipayService.ByCustomer(bill.getIdCustomer());
            mamipay.setSurplus(mamipay.getSurplus() + bill.getTotal());
            mamiPayDao.save(mamipay);
            refundPayBill(mamipay, bill);
        }

        bill.setUpdateAts(new Date());
        return billDao.save(bill);
    }

    @Override
    public List<Bill> findAllCustomer() {
        return billDao.findAllByIdCustomerOrderByCreateAtDesc(getAuthUID());
    }

    @Override
    public Bill shipingBill(ShipingRequest shipingRequest) {
        Bill bill = billDao.findById(shipingRequest.getIdBill()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });
        bill.setStatusshipping(shipingRequest.getIdShiping());
        return billDao.save(bill);
    }

    @Override
    public List<BillShiping> getShipingBill(String idBill) throws IOException {
        Bill bill = billDao.findById(idBill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });
        return GetBillShiping.getStatusShip(bill.getStatusshipping());
    }

    @Override
    public List<BillShiping> getShipingBillCustomer(String idBill) throws IOException {
        Bill bill = billDao.findById(idBill).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy đơn hàng");
        });

        //tim kiem nguoi dung
        Customer customer = customerService.findByAccount(getAuthUID());
        if (!Objects.equals(customer.getId(), bill.getIdCustomer())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không có quyền sửa đơn này");
        }
        return GetBillShiping.getStatusShip(bill.getStatusshipping());
    }

    @Override
    public List<String> getAddress() {
        Customer customer = customerService.findByAccount(getAuthUID());

        return billDao.getAddress(customer.getId()).stream().map(bill -> {
            String add = bill.getAddress();
            return add;
        }).collect(Collectors.toList());

    }

    @Override
    public List<Bill> findByDayNewCreate() {
        return billDao.findByDayNewCreate();
    }

    @Override
    public List<Bill> findBystatus(String status) {
        return billDao.findByStatus(status);
    }

    @Override
    public List<Bill> findBystatusshipping(String statusshipping) {
        return billDao.findBystatusshipping(statusshipping);
    }

    @Override
    public List<Bill> findbyID(String id) {
        return null;
    }

    @Override
    public List<Bill> findbypayment(int payment) {
        return billDao.findBypayment(payment);
    }

    @Override
    public List<Bill> findByDateAndpaymentAndstatus(String date, String dateto, Integer payment, String status) {
        if (date != "" && dateto != "" && status == "" && payment == null ){
            return billDao.findBydate(date, dateto);
        }
        if (date == "" && dateto == "" && status != "" && payment == null ){
            return billDao.findByStatus(status);
        }
        if (date != "" && dateto != "" && status == "" && payment != null ){
            return billDao.findBydateAndpayment(date, dateto,payment);
        }
        if (date == "" && dateto == "" && status != "" && payment == null ){
            return billDao.findByStatus(status);
        }
        if (date != "" && dateto != "" && status == "" && payment == null ){
            return billDao.findBydate(date, dateto);
        }
        if (date != null && dateto != null && status != "" && payment == null ){
            return billDao.findBydateAndStatus(date, dateto,status);
        }
        if (date == "" && dateto == "" && status  == "" && payment != null ){
            return billDao.findBypayment(payment);
        }
        if (status != "" && payment != null && dateto == "" && date == ""){
            return billDao.findbypaymentAndstatus(payment,status);
        }
            return billDao.findbydateAndpaymentAndstatus(date, dateto, payment, status);
    }


}
