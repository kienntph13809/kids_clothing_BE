package com.kids_clothing.model.request;

public interface ProductRequest {
    String getname();
    String getimage();
    Double getprice();
    String getidproduct();
    String getcategorydetail_name();
    String getcategorydetail_id();
    String getcategory_name();
    String getcategory_id();
    Integer getquantity();
    String getdescription();
    String getdescription_detail();
    Double getdiscount();
}
