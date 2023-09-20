package com.jot.JobOpportunity.service.imp;

import javax.transaction.Transactional;

import com.jot.JobOpportunity.common.Constants;
import com.jot.JobOpportunity.common.Utils;
import com.jot.JobOpportunity.dto.payment.PaymentChartDto;
import com.jot.JobOpportunity.dto.payment.PaymentChartItfDto;
import com.jot.JobOpportunity.dto.payment.PaymentDetailDto;
import com.jot.JobOpportunity.dto.payment.PaymentDetailItfDto;
import com.jot.JobOpportunity.entity.ApprovedPost;
import com.jot.JobOpportunity.entity.Payment;
import com.jot.JobOpportunity.entity.response.DataResponse;
import com.jot.JobOpportunity.repository.PaymentRepository;
import com.jot.JobOpportunity.service.AccountService;
import com.jot.JobOpportunity.service.ApprovedPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jot.JobOpportunity.service.PaymentService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Payment service implement
 *
 */

@Service
@Transactional
public class PaymentServiceImp implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImp.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApprovedPostService approvedPostService;

    @Override
    @Transactional
    public DataResponse payment(BigDecimal amount, String content, String type, Integer quantity, Integer quantityMonth) {
        log.debug("PaymentServiceImp.payment()");
        DataResponse res = new DataResponse();
        try {
            Payment p = new Payment();
            p.setAmount(amount);
            p.setContent(content);
            p.setEmployeeId(accountService.getAccountLogin().getId());
            p.setCreateBy(accountService.getAccountLogin().getName());
            p.setCreateTime(Utils.currentDateTime());
            LocalDate currentDate = LocalDate.now();
            // Định dạng năm thành chuỗi theo định dạng "yyyy"
            DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");
            String currentYear = currentDate.format(formatterYear);
            p.setYear(currentYear);
            // Định dạng năm thành chuỗi theo định dạng "MM"
            DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM");
            String currentMonth = currentDate.format(formatterMonth);
            p.setMonth(currentMonth);
            paymentRepository.save(p);
            if(type.equals("0")){
                ApprovedPost ap = approvedPostService.getByPosterId(accountService.getAccountLogin().getId());
                ap.setQuantity(ap.getQuantity()+ quantity);
                ap.setUpdateBy(accountService.getAccountLogin().getName());
                ap.setUpdateTime(Utils.currentDateTime());
                approvedPostService.save(ap);
            }
            if(type.equals("1")){
                ApprovedPost ap = approvedPostService.getByPosterId(accountService.getAccountLogin().getId());
                String curentExist = ap.getExpiredDate();
                if (curentExist == null){
                    curentExist = "0";
                }
                if(curentExist.compareTo(Utils.currentDate()) > 0){
                    int num1 =  Integer.parseInt(curentExist);
                    int sum = this.addMonth(num1,quantityMonth);
                    String epiredDate = Integer.toString(sum);
                    ap.setExpiredDate(epiredDate);
                }else{
                    int num1 =  Integer.parseInt(Utils.currentDate());
                    int sum = this.addMonth(num1,quantityMonth);
                    String epiredDate = Integer.toString(sum);
                    ap.setExpiredDate(epiredDate);
                }
                ap.setUpdateBy(accountService.getAccountLogin().getName());
                ap.setUpdateTime(Utils.currentDateTime());
                approvedPostService.save(ap);
            }
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.PAID_SUCCESS);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.PAID_ERROR);
            return res;
        }
    }

    @Override
    public DataResponse getIncome() {
        log.debug("PaymentServiceImp.getIncome()");
        DataResponse res = new DataResponse();
        try{
            LocalDate currentDate = LocalDate.now();
            // Định dạng năm thành chuỗi theo định dạng "yyyy"
            DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yyyy");
            String currentYear = currentDate.format(formatterYear);
            List<PaymentChartItfDto> paymentChartItfDtos = paymentRepository.getIncome(currentYear);
            if(paymentChartItfDtos.size() == 0){
                res.setStatus(Constants.NOT_FOUND);
                res.setMessage(Constants.DATA_EMPTY);
                return res;
            }
            List<PaymentChartDto> paymentChartDtos = Utils.mapList(paymentChartItfDtos, PaymentChartDto.class);
            res.setStatus(Constants.SUCCESS);
            res.setResult(paymentChartDtos);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DATA_EMPTY);
            return res;
        }
    }

    @Override
    public DataResponse getDealDetail(String id) {
        log.debug("PaymentServiceImp.getDealDetail()");
        DataResponse res = new DataResponse();
        try {
            Long employeeId = Long.parseLong(id);
            List<PaymentDetailItfDto> paymentDetailItfDtos = paymentRepository.getDealDetaill(employeeId);
            List<PaymentDetailDto> payments = Utils.mapList(paymentDetailItfDtos, PaymentDetailDto.class);
            if(payments.size() == 0){
                res.setStatus(Constants.ERROR);
                res.setMessage(Constants.DATA_EMPTY);
                return res;
            }
            for (PaymentDetailDto p: payments) {
                p.setCreateTime(Utils.getStringDateTimeDisplay(Utils.getDateTime(p.getCreateTime())));
            }
            res.setStatus(Constants.SUCCESS);
            res.setResult(payments);
            return res;
        }catch (Exception e){
            res.setStatus(Constants.ERROR);
            res.setMessage(Constants.DATA_EMPTY);
            return res;
        }
    }

    private int addMonth(int date, int quantityMonth){
        int mod = date % 10000;
        int result = 0;
        if ((mod + quantityMonth*100) > 1200){
            result = date + 10000 + (quantityMonth*100 - 1200);
            return result;
        }else
            return date + quantityMonth*100;
    }
}
