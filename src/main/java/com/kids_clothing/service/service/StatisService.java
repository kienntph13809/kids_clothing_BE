package com.kids_clothing.service.service;

import com.kids_clothing.common.EnumStatus;

import com.kids_clothing.model.request.QtyByDayRequest;
import com.kids_clothing.model.response.BillDashboardResponse;
import com.kids_clothing.model.response.EveryDayResponse;

import com.kids_clothing.model.response.EveryMonthResponse;
import com.kids_clothing.model.response.SumQtyProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisService {
    List<EveryDayResponse> revenueEveryDayOfTheMonth(Integer year, Integer mount, EnumStatus status);

    List<EveryMonthResponse> getEveryMonthOfTheYear(Integer year, EnumStatus status);

    List<SumQtyProductResponse> quantityByDay(QtyByDayRequest qtyByDayRequest);

    List<SumQtyProductResponse> quantityByMonth(EnumStatus status);

    List<BillDashboardResponse> getBillDashBoard();

    List<BillDashboardResponse> getBillDashBoardRefund();
}
