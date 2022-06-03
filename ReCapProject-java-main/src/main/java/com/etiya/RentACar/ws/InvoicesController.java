package com.etiya.RentACar.ws;


import com.etiya.RentACar.business.abstracts.InvoiceService;
import com.etiya.RentACar.business.dtos.InvoiceSearchListDto;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.CreateIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceDateRequest;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.business.requests.Invoice.DeleteInvoiceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/invoces/")
public class InvoicesController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("getall")
    public DataResult<List<InvoiceSearchListDto>> getAllInoives(){
        return this.invoiceService.getAll();
    }
    @GetMapping("getInvoiceByUserId")
    public DataResult<List<InvoiceSearchListDto>> getRentingInvoiceByUserId(int userId){
        return this.invoiceService.getRentingInvoiceByUserId(userId);
    }
    @GetMapping("getInvoiceByDate")
    public DataResult<List<InvoiceSearchListDto>> getInvoiceDateBetween(
            @RequestParam String  beginDate, @RequestParam String endDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bDate = LocalDate.parse(beginDate,dateTimeFormatter);
        LocalDate eDate = LocalDate.parse(endDate,dateTimeFormatter);
        return this.invoiceService.getByCreateDateBetweenBeginDateAndEndDate(bDate, eDate);
    }


    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest){
        return this.invoiceService.add(createInvoiceRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest) {
        return this.invoiceService.delete(deleteInvoiceRequest);
    }
}
